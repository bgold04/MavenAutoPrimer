#!/bin/bash

# on mac this requires:
# brew install astyle
# brew install checkstyle
# brew install llvm

# Enable strict error handling
set -euo pipefail

# Fetch JAVA_HOME dynamically
export JAVA_HOME=$(/usr/libexec/java_home)
echo "Using JAVA_HOME: $JAVA_HOME"

# Directories and Temporary Files
JAVA_DIR="/Users/bgold/MavenAutoPrimer/src/main/java/com/github"
TEMP_OUTPUT="/tmp/java_compile_errors.txt"
RANKING_OUTPUT="/tmp/java_error_ranking.txt"
PUNCTUATION_SUMMARY="/tmp/java_punctuation_summary.txt"
> "$TEMP_OUTPUT"
> "$RANKING_OUTPUT"
> "$PUNCTUATION_SUMMARY"

# Functions

# Indent code in a Java file
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
        echo "No code formatter found (clang-format or astyle)."
    fi
}

# Check for unmatched braces and parentheses and print punctuation summary
check_braces_and_parentheses() {
    local file="$1"
    echo "Checking braces and parentheses in $file..."
    local open_braces close_braces open_parentheses close_parentheses

    open_braces=$(grep -o '{' "$file" | wc -l)
    close_braces=$(grep -o '}' "$file" | wc -l)
    open_parentheses=$(grep -o '(' "$file" | wc -l)
    close_parentheses=$(grep -o ')' "$file" | wc -l)

    echo "File: $file" >> "$PUNCTUATION_SUMMARY"
    echo "  Opening braces ({):       $open_braces" >> "$PUNCTUATION_SUMMARY"
    echo "  Closing braces (}):       $close_braces" >> "$PUNCTUATION_SUMMARY"
    echo "  Opening parentheses (()): $open_parentheses" >> "$PUNCTUATION_SUMMARY"
    echo "  Closing parentheses ())): $close_parentheses" >> "$PUNCTUATION_SUMMARY"

    awk '
    BEGIN { brace_count = 0; paren_count = 0; }
    {
        for (i = 1; i <= length($0); i++) {
            char = substr($0, i, 1);
            if (char == "{") brace_count++;
            if (char == "}") brace_count--;
            if (char == "(") paren_count++;
            if (char == ")") paren_count--;
            if (brace_count < 0) {
                print "Extra closing brace at line " NR ": " $0;
                brace_count = 0;
            }
            if (paren_count < 0) {
                print "Extra closing parenthesis at line " NR ": " $0;
                paren_count = 0;
            }
        }
    }
    END {
        if (brace_count > 0) print "Unmatched opening brace(s) detected.";
        if (paren_count > 0) print "Unmatched opening parenthesis(es) detected.";
    }' "$file" >> "$PUNCTUATION_SUMMARY"
}

# Check for missing semicolons
check_semicolons() {
    local file="$1"
    echo "Checking semicolons in $file..."
    awk '
    {
        # Skip comments and annotations
        if ($1 ~ /^\/\// || $1 ~ /^\/\*/ || $1 ~ /^\*/ || $1 ~ /^@/) next;

        # Lines not ending in ;, {, or } that are not empty
        if ($0 !~ /;\s*$/ && $0 !~ /{\s*$/ && $0 !~ /}\s*$/ && $0 !~ /^\s*$/) {
            print "Missing semicolon in " FILENAME " at line " NR ": " $0;
        }
    }' "$file"
}

# Compile Java files and record errors
compile_and_record_errors() {
    local file="$1"
    echo "Compiling $file..."
    javac "$file" 2>> "$TEMP_OUTPUT"
    if [ $? -ne 0 ]; then
        echo "Compilation errors found in $file."
    fi
}

# Rank files by number of errors
rank_files_by_errors() {
    echo "Ranking files by number of errors..."
    awk '
    /\.java/ { file = $0; errors[file] = 0; next }
    /error/ { errors[file]++ }
    END {
        for (file in errors) {
            print errors[file], file
        }
    }' "$TEMP_OUTPUT" | sort -nr > "$RANKING_OUTPUT"
    echo "Ranking complete! Results saved in $RANKING_OUTPUT."
}

# Main Execution
echo "Processing Java files in $JAVA_DIR..."

for file in "$JAVA_DIR"/*.java; do
    echo "Processing $file..."

    # Indent code
    indent_code "$file"

    # Check semicolons
    check_semicolons "$file"

    # Check braces and parentheses
    check_braces_and_parentheses "$file"

    # Compile and record errors
    compile_and_record_errors "$file"
done

# Rank files by errors
rank_files_by_errors

# Display ranked results
cat "$RANKING_OUTPUT"

# Display punctuation summary
echo "Punctuation Summary:"
cat "$PUNCTUATION_SUMMARY"

echo "Analysis complete!"




