#!/bin/bash

# Prerequisites
export JAVA_HOME=$(/usr/libexec/java_home)
echo "Using JAVA_HOME: $JAVA_HOME"

# Directories and Temporary Files
JAVA_DIR="/Users/bgold/MavenAutoPrimer/src/main/java/com/github/mavenautoprimer"
TEMP_DIR="/Users/bgold/MavenAutoPrimer/src/main/resources/temp"
TEMP_OUTPUT="/tmp/java_compile_errors.txt"
PUNCTUATION_SUMMARY="/tmp/punctuation_summary.txt"
RANKED_OUTPUT="/tmp/ranked_compile_errors.txt"

# Ensure cleanup of temporary files and Java directory
trap "rm -f $TEMP_OUTPUT $PUNCTUATION_SUMMARY $RANKED_OUTPUT; rm -rf $JAVA_DIR/*.class" EXIT

# Prepare directories
mkdir -p "$TEMP_DIR"
> "$TEMP_OUTPUT"
> "$PUNCTUATION_SUMMARY"
rm -rf "$JAVA_DIR"/*.class

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

# Function to check semicolon errors
check_semicolons() {
    local file="$1"
    echo "Checking semicolons in $file..."
    block_comment=0
    line_number=0

    while IFS= read -r line || [[ -n "$line" ]]; do
        line_number=$((line_number + 1))

        #set up a trimmed line definition
        trimmed_line="${line#"${line%%[![:space:]]*}"}"

        # Handle block comments
        if [[ "$trimmed_line" == $'\x2F\x2A'* ]]; then # Match /*
        block_comment=1
        fi
        if (( block_comment == 1 )); then
        if [[ "$trimmed_line" == *$'\x2A\x2F'* ]]; then # Match */
        block_comment=0
       fi
             continue
       fi

        # Skip lines where @ or // are the first meaningful elements
       if [[ "$trimmed_line" == $'\x2F\x2F'* || "$trimmed_line" == $'\x40'* || -z "$trimmed_line" ]]; then
              continue
       fi


        # Check for missing semicolons
        if [[ $line != *$'\x3B' && $line != *$'\x7B' && $line != *$'\x7D' ]]; then
            echo "Possible missing semicolon in $file at line $line_number: $line"
        fi
    done < "$file"
}

# Count braces and parentheses using hex codes
count_braces_and_parentheses() {
    local file="$1"
    echo "Counting punctuation in $file..."
    open_braces=0
    close_braces=0
    open_parentheses=0
    close_parentheses=0

    while IFS= read -r line || [[ -n "$line" ]]; do
        open_braces=$((open_braces + $(echo "$line" | grep -o $'\x7B' | wc -l || echo 0)))
        close_braces=$((close_braces + $(echo "$line" | grep -o $'\x7D' | wc -l || echo 0)))
        open_parentheses=$((open_parentheses + $(echo "$line" | grep -o $'\x28' | wc -l || echo 0)))
        close_parentheses=$((close_parentheses + $(echo "$line" | grep -o $'\x29' | wc -l || echo 0)))
    done < "$file"

    echo "File: $file" >> "$PUNCTUATION_SUMMARY"
    echo "  Opening braces ({): $open_braces" >> "$PUNCTUATION_SUMMARY"
    echo "  Closing braces (}): $close_braces" >> "$PUNCTUATION_SUMMARY"
    echo "  Opening parentheses (()): $open_parentheses" >> "$PUNCTUATION_SUMMARY"
    echo "  Closing parentheses ())): $close_parentheses" >> "$PUNCTUATION_SUMMARY"
}

# Compile files and rank them based on errors
compile_and_rank_files() {
    > "$TEMP_OUTPUT"
    echo "Compiling individual Java files and recording errors..."
    error_counts=()

    for file in "$JAVA_DIR"/*.java; do
        [ -f "$file" ] || continue
        javac "$file" 2>> "$TEMP_OUTPUT"
        errors=$(grep -c $'\x65\x72\x72\x6F\x72' <<< "$(cat "$TEMP_OUTPUT")")
        error_counts+=("$errors:$file")
    done

    echo "Ranking files by number of errors..."
    for entry in "${error_counts[@]}"; do
        echo "$entry"
    done | sort -t ':' -k1,1nr | awk -F':' '{print $2 " has " $1 " errors"}' > "$RANKED_OUTPUT"
}

# Main Execution
for file in "$JAVA_DIR"/*.java; do
    [ -f "$file" ] || continue
    create_stripped_files "$file"
    check_semicolons "$file"
    count_braces_and_parentheses "$file"
    compile_and_rank_files "$file"
done


echo "--- Java Superclass Annotation Summary ---"
cat "$RANKED_OUTPUT"
cat "$TEMP_OUTPUT"
cat "$PUNCTUATION_SUMMARY"
