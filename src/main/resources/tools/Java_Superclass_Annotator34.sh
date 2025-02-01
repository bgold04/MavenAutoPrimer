#!/bin/bash

# initial maven clean up
cd ~/MavenAutoPrimer
mvn clean

# Prerequisites
export JAVA_HOME=$(/usr/libexec/java_home)
echo "Using JAVA_HOME: $JAVA_HOME"

# Directories and Temporary Files
JAVA_DIR="/Users/bgold/MavenAutoPrimer/src/main/java/com/github/mavenautoprimer"
TEMP_DIR="/Users/bgold/MavenAutoPrimer/src/main/resources/temp"
TEMP_OUTPUT="/tmp/java_compile_errors.txt"
PUNCTUATION_SUMMARY="/tmp/punctuation_summary.txt"
RANKED_OUTPUT="/tmp/ranked_compile_errors.txt"
TARGET_DIR="/Users/bgold/MavenAutoPrimer/target/classes"

# Ensure cleanup of temporary files and Java directory
trap "rm -f $TEMP_OUTPUT $PUNCTUATION_SUMMARY $RANKED_OUTPUT; rm -rf $JAVA_DIR/*.class" EXIT

# Ensure required directories exist
mkdir -p "$TEMP_DIR"
mkdir -p "$TARGET_DIR"

# Retrieve and verify classpath dynamically
echo "Retrieving classpath from Maven..."
CLASSPATH=$(mvn -f ~/MavenAutoPrimer/pom.xml dependency:build-classpath -Dmdep.outputFile=/dev/stdout | tr -d '\n')

if [[ -z "$CLASSPATH" ]]; then
    echo "Error: Classpath is empty. Dependencies may not have resolved correctly."
    exit 1
fi

echo "Classpath: $CLASSPATH"

# Function to count unmatched braces and parentheses using hex codes
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

    # Check if there are unmatched braces/parentheses
    if [[ $open_braces -ne $close_braces ]]; then
        echo "⚠️  Warning: Mismatched braces in $file ({:$open_braces, }:$close_braces)"
    fi
    if [[ $open_parentheses -ne $close_parentheses ]]; then
        echo "⚠️  Warning: Mismatched parentheses in $file ((:$open_parentheses, )):$close_parentheses)"
    fi
}

# Compile and rank files based on errors
compile_and_rank_files() {
    > "$TEMP_OUTPUT"
    echo "Compiling individual Java files and recording errors..."
    error_counts=()

    for file in "$JAVA_DIR"/*.java; do
        [ -f "$file" ] || continue
        echo "Compiling $file..."
        javac -cp ".:$CLASSPATH" -d "$TARGET_DIR" "$file" 2>> "$TEMP_OUTPUT"
        errors=$(grep -c $'\x65\x72\x72\x6F\x72' "$TEMP_OUTPUT")
        error_counts+=("$errors:$file")
    done

    echo "Ranking files by number of errors..."
    for entry in "${error_counts[@]}"; do
        echo "$entry"
    done | sort -t ':' -k1,1nr | awk -F':' '{print $2 " has " $1 " errors"}' > "$RANKED_OUTPUT"

    echo "=== Compilation Errors ==="
    cat "$TEMP_OUTPUT"

    echo "=== Files Ranked by Errors ==="
    cat "$RANKED_OUTPUT"
}

# Main Execution
for file in "$JAVA_DIR"/*.java; do
    [ -f "$file" ] || continue
    count_braces_and_parentheses "$file"
done

# Compile all Java files
echo "Compiling all Java files..."
javac -cp ".:$CLASSPATH" -d "$TARGET_DIR" $(find "$JAVA_DIR" -name "*.java") 2>> "$TEMP_OUTPUT"

# Check for errors and rank files
if [[ -s "$TEMP_OUTPUT" ]]; then
    echo "Compilation errors detected! Ranking files..."
    compile_and_rank_files
    exit 1
fi

echo "✅ Compilation successful!"

# Output punctuation summary
echo "--- Java Superclass Annotation Summary ---"
cat "$RANKED_OUTPUT"
cat "$TEMP_OUTPUT"
cat "$PUNCTUATION_SUMMARY"
