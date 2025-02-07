#!/bin/bash

# Initial Maven cleanup
cd ~/MavenAutoPrimer/src/main/resources/temp
rm *.*
cd ~/MavenAutoPrimer
mvn clean

# Prerequisites
export JAVA_HOME=$(/usr/libexec/java_home)
echo "Using JAVA_HOME: $JAVA_HOME"

# Directories and Temporary Files
JAVA_DIR="/Users/bgold/MavenAutoPrimer/src/main/java/com/github/mavenautoprimer"
TEMP_DIR="/Users/bgold/MavenAutoPrimer/src/main/resources/temp"
TARGET_DIR="/Users/bgold/MavenAutoPrimer/target/classes"
SEARCH_DIR="/Users/bgold/MavenAutoPrimer/src/main/java/com/github/mavenautoprimer"
DUP_CLASSES="$TEMP_DIR/duplicate_classes.txt"
DUP_METHODS="$TEMP_DIR/duplicate_methods.txt"
PUNCTUATION_SUMMARY="$TEMP_DIR/punctuation_summary.txt"
TEMP_OUTPUT="$TEMP_DIR/java_compile_errors.txt"
RANKED_OUTPUT="$TEMP_DIR/ranked_compile_errors.txt"
DUP_CLASS_LOCATIONS="$TEMP_DIR/duplicate_class_locations.txt"
DUP_METHOD_LOCATIONS="$TEMP_DIR/duplicate_method_locations.txt"

# Ensure cleanup of temporary files and Java directory
trap "rm -f $TEMP_OUTPUT $PUNCTUATION_SUMMARY $RANKED_OUTPUT; rm -rf $JAVA_DIR/*.class" EXIT

# Ensure required directories exist
mkdir -p "$TEMP_DIR"
mkdir -p "$TARGET_DIR"

# Indent code
indent_code() {
    local file="$1"
    echo "Indenting code in $file..."
    if command -v clang-format &> /dev/null; then
        clang-format -i "$file"
        echo "Formatted $file using clang-format."
    elif command -v astyle &> /dev/null; then
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

# Function: Count unmatched braces and parentheses
count_braces_and_parentheses() {
    local file="$1"
    echo "Counting punctuation in $file..."
    open_braces=$(grep -o $'{' "$file" | wc -l || echo 0)
    close_braces=$(grep -o $'}' "$file" | wc -l || echo 0)
    open_parentheses=$(grep -o $'(' "$file" | wc -l || echo 0)
    close_parentheses=$(grep -o $')' "$file" | wc -l || echo 0)

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

find_duplicates() {
    echo "Finding duplicate class and method names..."
    
    # Find duplicate class declarations
    grep -rhoE $'\x63\x6C\x61\x73\x73 [A-Za-z0-9_]+' "$SEARCH_DIR" | cut -d' ' -f2 | sort | uniq -d > "$DUP_CLASSES"
    touch "$DUP_CLASS_LOCATIONS"
    
    while IFS= read -r class; do
        echo -n "$class seen in " >> "$DUP_CLASS_LOCATIONS"
        grep -rl $"\x63\x6C\x61\x73\x73 $class" "$SEARCH_DIR" | tr '\n' '\t' >> "$DUP_CLASS_LOCATIONS"
        echo "" >> "$DUP_CLASS_LOCATIONS"
    done < "$DUP_CLASSES"

    # Find duplicate method and constructor declarations
    grep -rhoE $'\x70\x75\x62\x6C\x69\x63|\x70\x72\x69\x76\x61\x74\x65|\x70\x72\x6F\x74\x65\x63\x74\x65\x64 [A-Za-z0-9_<>,\[\]]+ [A-Za-z0-9_]+\x28' "$SEARCH_DIR" | grep -oE '[A-Za-z0-9_]+\x28' | cut -d'(' -f1 | sort | uniq -d > "$DUP_METHODS"
    touch "$DUP_METHOD_LOCATIONS"

    while IFS= read -r method; do
        echo -n "$method seen in " >> "$DUP_METHOD_LOCATIONS"
        grep -rl "$method(" "$SEARCH_DIR" | tr '\n' '\t' >> "$DUP_METHOD_LOCATIONS"
        echo "" >> "$DUP_METHOD_LOCATIONS"
    done < "$DUP_METHODS"
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
#
#!/bin/bash

# Initial Maven cleanup
cd ~/MavenAutoPrimer/src/main/resources/temp || exit
rm -f *.*
cd ~/MavenAutoPrimer || exit
mvn clean

# Prerequisites
export JAVA_HOME=$(/usr/libexec/java_home)
echo "Using JAVA_HOME: $JAVA_HOME"

# Directories and Temporary Files
JAVA_DIR="/Users/bgold/MavenAutoPrimer/src/main/java/com/github/mavenautoprimer"
TEMP_DIR="/Users/bgold/MavenAutoPrimer/src/main/resources/temp"
TARGET_DIR="/Users/bgold/MavenAutoPrimer/target/classes"
ERROR_LOG="/Users/bgold/MavenAutoPrimer/src/main/resources/reports/full_compile_errors.log"

# Ensure cleanup of temporary files
mkdir -p "$TEMP_DIR"
mkdir -p "$TARGET_DIR"
rm -f "$ERROR_LOG"

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

# Function: Compile Java files and capture all errors
compile_java_files() {
    echo "Compiling all Java files..."
    
    # Ensure the log file is empty before compilation
    > "$ERROR_LOG"

    # Compile Java files and capture all errors in the log file
    javac -d "$TARGET_DIR" -cp "$CLASSPATH" "$JAVA_DIR/GetUcscBuildsAndTables.java" 2>> "$ERROR_LOG"
    javac -d "$TARGET_DIR" -cp "$TARGET_DIR:$CLASSPATH" "$JAVA_DIR/ApplicationConfig.java" 2>> "$ERROR_LOG"
    javac -d "$TARGET_DIR" -cp "$TARGET_DIR:$CLASSPATH" $(find "$JAVA_DIR" -name "*.java") 2>> "$ERROR_LOG"

    # Display all errors (not truncated)
    if [[ -s "$ERROR_LOG" ]]; then
        echo "Compilation errors detected! Full error log saved at: $ERROR_LOG"
        cat "$ERROR_LOG"
    else
        echo "✅ Compilation successful!"
    fi
}
#compile_java_files() {
#    echo "Compiling all Java files..."
#    javac -J-Xmaxerrs=500 -d "$TARGET_DIR" -cp ".:$CLASSPATH" "$JAVA_DIR/GetUcscBuildsAndTables.java"
#    javac -J-Xmaxerrs=500 -d "$TARGET_DIR" -cp "target/classes:$CLASSPATH" "$JAVA_DIR/ApplicationConfig.java"
#    javac -J-Xmaxerrs=500 -d "$TARGET_DIR" -cp "target/classes:$CLASSPATH" $(find "$JAVA_DIR" -name "*.java") 2>> "$TEMP_OUTPUT"
#    # Do not exit early; just print errors
#    if [[ -s "$TEMP_OUTPUT" ]]; then
#        echo "Compilation errors detected!"
#        cat "$TEMP_OUTPUT"
#    else
#        echo "✅ Compilation successful!"
#    fi
#}

# MAIN EXECUTION
main() {
    for file in "$JAVA_DIR"/*.java; do
        [ -f "$file" ] || continue
        indent_code "$file"
        create_stripped_files "$file"
        check_semicolons "$file"
        count_braces_and_parentheses "$file"
    done
rm -rf /Users/bgold/MavenAutoPrimer/src/main/java/com/github/mavenautoprimer/*.orig
    find_duplicates

    # Retrieve classpath only before compiling
    retrieve_classpath
    compile_java_files

    # Ensure the summary prints even if compilation fails
    echo "--- Java Superclass Annotation Summary ---"
    cat "$RANKED_OUTPUT" 2>/dev/null || echo "No ranking output."
    cat "$TEMP_OUTPUT" || echo "No compilation errors."
    cat "$PUNCTUATION_SUMMARY" || echo "No punctuation errors."

    echo "=== Duplicate Classes Found ==="
    cat "$DUP_CLASS_LOCATIONS" || echo "No duplicate classes found."
    echo "\n=== Duplicate Methods Found ==="
    cat "$DUP_METHOD_LOCATIONS" || echo "No duplicate methods found."

    echo "\nReview these duplicates and move shared logic to GenomicBase.java where appropriate."
}
cat errors.log

# Run the script
main
