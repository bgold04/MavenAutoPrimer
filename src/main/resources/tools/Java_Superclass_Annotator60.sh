#!/bin/bash

# Initial Maven cleanup
cd ~/MavenAutoPrimer/src/main/resources/temp || exit 1
rm -f -- *
cd ~/MavenAutoPrimer || exit 1
mvn clean

# Prerequisites
export JAVA_HOME=$(/usr/libexec/java_home)
echo "Using JAVA_HOME: $JAVA_HOME"

# Directories and Temporary Files
JAVA_DIR="/Users/bgold/MavenAutoPrimer/src/main/java/com/github/mavenautoprimer"
TEMP_DIR="/Users/bgold/MavenAutoPrimer/src/main/resources/temp"
TARGET_DIR="/Users/bgold/MavenAutoPrimer/target/classes"
ERROR_LOG="$TEMP_DIR/full_compile_errors.log"
DUP_CLASSES="$TEMP_DIR/duplicate_classes.txt"
DUP_METHODS="$TEMP_DIR/duplicate_methods.txt"
DUP_CONSTRUCTORS="$TEMP_DIR/duplicate_constructors.txt"
DUP_CLASS_LOCATIONS="$TEMP_DIR/duplicate_class_locations.txt"
DUP_METHOD_LOCATIONS="$TEMP_DIR/duplicate_method_locations.txt"
DUP_CONSTRUCTOR_LOCATIONS="$TEMP_DIR/duplicate_constructor_locations.txt"
PUNCTUATION_SUMMARY="$TEMP_DIR/punctuation_summary.txt"

# Ensure cleanup of temporary files
mkdir -p "$TEMP_DIR"
mkdir -p "$TARGET_DIR"
rm -f "$ERROR_LOG"

# Function to indent Java code
indent_code() {
    local file="$1"
    echo "Indenting code in $file..."
    if command -v clang-format &>/dev/null; then
        clang-format -i "$file"
        echo "Formatted $file using clang-format."
    elif command -v astyle &>/dev/null; then
        astyle --style=java "$file" || echo "Error formatting $file with astyle."
        echo "Formatted $file using astyle."
    else
        echo "No code formatter found."
    fi
}

# Function to create stripped-down files
create_stripped_files() {
    local file="$1"
    local stripped_file="$TEMP_DIR/$(basename "${file%.java}_stripped.java")"
    echo "Creating stripped-down version of $file as $stripped_file..."
    > "$stripped_file"

    while IFS= read -r line || [[ -n "$line" ]]; do
        # Keep only lines with braces or parentheses
        if [[ $line == *$'\x7B'* || $line == *$'\x7D'* || $line == *$'\x28'* || $line == *$'\x29'* ]]; then
            echo "$line" >> "$stripped_file"
        fi
    done < "$file"
}

# Function to check semicolon errors with hex regex
check_semicolons() {
    local file="$1"
    echo "Checking semicolons in $file..."
    block_comment=0
    line_number=0

    while IFS= read -r line || [[ -n "$line" ]]; do
        line_number=$((line_number + 1))
        trimmed_line="${line#"${line%%[![:space:]]*}"}"

        if [[ "$trimmed_line" == $'/*'* ]]; then # Match /*
            block_comment=1
        fi
        if (( block_comment == 1 )); then
            if [[ "$trimmed_line" == *$'*/'* ]]; then # Match */
                block_comment=0
            fi
            continue
        fi
        if [[ "$trimmed_line" == $'\x2F\x2F'* || "$trimmed_line" == $'\x40'* || -z "$trimmed_line" ]]; then
            continue
        fi
        if [[ $line != *$'\x3B' && $line != *$'\x7B' && $line != *$'\x7D' ]]; then
            echo "Possible missing semicolon in $file at line $line_number: $line"
        fi
    done < "$file"
}

# Function to check Java class duplicates (EXCLUDES .java.orig)
find_duplicate_classes() {
    echo "Finding duplicate classes..."
    
    grep -rhoE $'\x63\x6C\x61\x73\x73\x20[A-Za-z0-9_]+' "$JAVA_DIR" --exclude="*.java.orig" | cut -d' ' -f2 | sort | uniq -d > "$DUP_CLASSES"
    
    > "$DUP_CLASS_LOCATIONS"
    while IFS= read -r class; do
        echo -n "$class seen in " >> "$DUP_CLASS_LOCATIONS"
        grep -rl $'\x63\x6C\x61\x73\x73\x20'"$class" "$JAVA_DIR" --exclude="*.java.orig" | tr '\n' '\t' >> "$DUP_CLASS_LOCATIONS"
        echo "" >> "$DUP_CLASS_LOCATIONS"
    done < "$DUP_CLASSES"
}

# Function to check duplicate methods (EXCLUDES .java.orig)
find_duplicate_methods() {
    echo "Finding duplicate methods..."

    grep -rhoE $'\x70\x75\x62\x6C\x69\x63|\x70\x72\x69\x76\x61\x74\x65|\x70\x72\x6F\x74\x65\x63\x74\x65\x64\x20[A-Za-z0-9_<>,\[\]]+\x20[A-Za-z0-9_]+\x28' "$JAVA_DIR" --exclude="*.java.orig" | grep -oE '[A-Za-z0-9_]+\x28' | cut -d'(' -f1 | sort | uniq -d > "$DUP_METHODS"

    > "$DUP_METHOD_LOCATIONS"
    while IFS= read -r method; do
        echo -n "$method seen in " >> "$DUP_METHOD_LOCATIONS"
        grep -rl "$method(" "$JAVA_DIR" --exclude="*.java.orig" | tr '\n' '\t' >> "$DUP_METHOD_LOCATIONS"
        echo "" >> "$DUP_METHOD_LOCATIONS"
    done < "$DUP_METHODS"
}

# Function to check duplicate constructors (EXCLUDES .java.orig)
find_duplicate_constructors() {
    echo "Finding duplicate constructors..."

    grep -rhoE "^[ \t]*[A-Za-z0-9_<>]+[ \t]*\x28" "$JAVA_DIR" --exclude="*.java.orig" | cut -d'(' -f1 | sort | uniq -d > "$DUP_CONSTRUCTORS"

    > "$DUP_CONSTRUCTOR_LOCATIONS"
    while IFS= read -r constructor; do
        echo -n "$constructor seen in " >> "$DUP_CONSTRUCTOR_LOCATIONS"
        grep -rl "$constructor(" "$JAVA_DIR" --exclude="*.java.orig" | tr '\n' '\t' >> "$DUP_CONSTRUCTOR_LOCATIONS"
        echo "" >> "$DUP_CONSTRUCTOR_LOCATIONS"
    done < "$DUP_CONSTRUCTORS"
}

# Function to count mismatched braces & parentheses
count_braces_and_parentheses() {
    local file="$1"
    echo "Counting punctuation in $file..."
    
    open_braces=$(grep -o $'\x7B' "$file" | wc -l || echo 0)
    close_braces=$(grep -o $'\x7D' "$file" | wc -l || echo 0)
    open_parentheses=$(grep -o $'\x28' "$file" | wc -l || echo 0)
    close_parentheses=$(grep -o $'\x29' "$file" | wc -l || echo 0)

    echo "File: $file" >> "$PUNCTUATION_SUMMARY"
    echo "  Opening braces ({): $open_braces" >> "$PUNCTUATION_SUMMARY"
    echo "  Closing braces (}): $close_braces" >> "$PUNCTUATION_SUMMARY"
    echo "  Opening parentheses (()): $open_parentheses" >> "$PUNCTUATION_SUMMARY"
    echo "  Closing parentheses ())): $close_parentheses" >> "$PUNCTUATION_SUMMARY"

    if [[ $open_braces -ne $close_braces ]]; then
        echo "⚠️  Warning: Mismatched braces in $file ({:$open_braces, }:$close_braces)"
    fi
    if [[ $open_parentheses -ne $close_parentheses ]]; then
        echo "⚠️  Warning: Mismatched parentheses in $file ((:$open_parentheses, )):$close_parentheses)"
    fi
}

# Function to find unmatched braces
find_unmatched_braces() {
    local file="$1"
    echo "Checking for unmatched braces in $file..."
    local -a stack
    local line_number=0

if [[ ! -f "$file" ]]; then
        echo "File not found: $file"
        return 1
    fi

    while IFS= read -r line || [[ -n "$line" ]]; do
        ((line_number++))
        for ((i = 0; i < ${#line}; i++)); do
            char="${line:i:1}"
            case "$char" in
                "{") stack+=("$line_number") ;;
                "}")
                    if [[ ${#stack[@]} -eq 0 ]]; then
                        echo "Unmatched '}' at line $line_number in $file"
                    else
                        unset 'stack[${#stack[@]} - 1]'
                    fi
                    ;;
            esac
        done
    done < "$file"

    if [[ ${#stack[@]} -gt 0 ]]; then
        echo "Unmatched '{' at lines: ${stack[*]} in $file"
    fi
}

# Function: Retrieve and verify classpath dynamically
retrieve_classpath() {
    echo "Retrieving classpath from Maven..."
    CLASSPATH=$(mvn -f ~/MavenAutoPrimer/pom.xml dependency:build-classpath -Dmdep.outputFile=/dev/stdout | tr -d '\n')

    if [[ -z "$CLASSPATH" ]]; then
        echo "Error: Classpath is empty. Dependencies may not have resolved correctly."
        exit 1
    fi

    echo "Classpath: $CLASSPATH"
}

# Function to compile Java files
compile_java_files() {
    echo "Compiling all Java files..."
    
    # Ensure the log file is empty before compilation
    > "$ERROR_LOG"

    # Compile Java files and capture all errors in the log file
        javac -d "$TARGET_DIR" -cp "$TARGET_DIR:$CLASSPATH" "$JAVA_DIR/GeneDetails.java" 2>> "$ERROR_LOG"
        javac -d "$TARGET_DIR" -cp "$TARGET_DIR:$CLASSPATH" "$JAVA_DIR/GeneCoordinatesFetcher.java" 2>> "$ERROR_LOG"
        javac -d "$TARGET_DIR" -cp "$TARGET_DIR:$CLASSPATH" "$JAVA_DIR/UcscGeneCoordinatesFetcher.java" 2>> "$ERROR_LOG"
        javac -d "$TARGET_DIR" -cp "$TARGET_DIR:$CLASSPATH" "$JAVA_DIR/EnsemblGeneCoordinatesFetcher.java" 2>> "$ERROR_LOG"
        javac -d "$TARGET_DIR" -cp "$TARGET_DIR:$CLASSPATH" "$JAVA_DIR/GenomicBase.java" 2>> "$ERROR_LOG"
        javac -d "$TARGET_DIR" -cp "$TARGET_DIR:$CLASSPATH" "$JAVA_DIR/UcscBuildAndTableFetcher.java" 2>> "$ERROR_LOG"
        javac -d "$TARGET_DIR" -cp "$TARGET_DIR:$CLASSPATH" "$JAVA_DIR/ApplicationConfig.java" 2>> "$ERROR_LOG"
        javac -d "$TARGET_DIR" -cp "$TARGET_DIR:$CLASSPATH" "$JAVA_DIR/DasUcscSequenceFetcher.java" 2>> "$ERROR_LOG"
    # Display all errors (not truncated)
    if [[ -s "$ERROR_LOG" ]]; then
        echo "Compilation errors detected! Full error log saved at: $ERROR_LOG"
        cat "$ERROR_LOG"
    else
        echo "✅ Compilation successful!"
    fi
}
# Main execution function
main() {
    echo "=== Running Java Superclass Annotator ==="
    for file in "$JAVA_DIR"/*.java; do
        [ -f "$file" ] || continue
        indent_code "$file"
        create_stripped_files "$file"
        check_semicolons "$file"
        count_braces_and_parentheses "$file"
        find_unmatched_braces "$file"
        find_duplicate_classes "$file"
        find_duplicate_methods "$file"
        find_duplicate_constructors "$file"

    done
    retrieve_classpath
    compile_java_files
    echo "--- Java Superclass Annotation Summary ---"
    cat "$PUNCTUATION_SUMMARY" || echo "No punctuation errors."
}

# Run the script
main
 