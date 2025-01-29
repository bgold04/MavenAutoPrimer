#!/bin/bash

# Fetch JAVA_HOME dynamically
export JAVA_HOME=$(/usr/libexec/java_home)
echo "Using JAVA_HOME: $JAVA_HOME"

# Enable strict error handling
set -euo pipefail

# Directories and Temporary Files
JAVA_DIR="/Users/bgold/MavenAutoPrimer/src/main/java/com/github"
TEMP_OUTPUT="/tmp/java_compile_errors.txt"
RANKING_OUTPUT="/tmp/java_error_ranking.txt"
> "$TEMP_OUTPUT"

# Functions

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
        echo "No code formatter found (clang-format or astyle). Install one for automatic indentation."
    fi
}

check_semicolons() {
    local file="$1"
    echo "Checking semicolons in $file..."
    awk '
    BEGIN { in_multiline_comment = 0; }
    {
        # Detect the start of a multi-line comment
        if ($0 ~ /\/\*/) in_multiline_comment = 1;

        # Detect the end of a multi-line comment
        if ($0 ~ /\*\//) {
            in_multiline_comment = 0;
            next;
        }

        # Skip lines in multi-line comments
        if (in_multiline_comment) next;

        # Skip single-line comments and annotations
        if ($0 ~ /^[[:space:]]*\/\// || $0 ~ /^[[:space:]]*@/) next;

        # Lines not ending in ;, {, }, or blank
        if ($0 !~ /;\s*$/ && $0 !~ /{\s*$/ && $0 !~ /}\s*$/ && $0 !~ /^\s*$/) {
            print "Possible missing semicolon in " FILENAME " at line " NR ": " $0;
        }
    }' "$file"
}

# Check braces and parentheses
check_braces_and_parentheses() {
    local file="$1"
    echo "Checking braces and parentheses in $file..."
    awk '
    BEGIN { brace_count = 0; paren_count = 0; }
    {
        # Count braces and parentheses
        for (i = 1; i <= length($0); i++) {
            char = substr($0, i, 1);
            if (char == "{") brace_count++;
            if (char == "}") brace_count--;
            if (char == "(") paren_count++;
            if (char == ")") paren_count--;
        }
        if (brace_count < 0) {
            print "Extra closing brace at line " NR ": " $0;
            brace_count = 0;
        }
        if (paren_count < 0) {
            print "Extra closing parenthesis at line " NR ": " $0;
            paren_count = 0;
        }
    }
    END {
        if (brace_count != 0) print "Unmatched braces detected.";
        if (paren_count != 0) print "Unmatched parentheses detected.";
    }' "$file"
}

# Compile Java files and count errors
compile_and_record_errors() {
    local file="$1"
    echo "Compiling $file..."
    javac "$file" 2>> "$TEMP_OUTPUT" || echo "Compilation errors found in $file."
}

# Rank files by errors
rank_files_by_errors() {
    echo "Ranking files by errors..."
    awk '
    /\.java/ { file = $0; errors[file] = 0; next }
    /error/ { errors[file]++ }
    END {
        for (file in errors) {
            print errors[file], file
        }
    }' "$TEMP_OUTPUT" | sort -nr > "$RANKING_OUTPUT"
    echo "Ranking complete! Results saved in $RANKING_OUTPUT"
}

# Main Execution
echo "Processing Java files in $JAVA_DIR..."

for file in "$JAVA_DIR"/*.java; do
    echo "Processing $file..."
    indent_code "$file"
    check_semicolons "$file"
    check_braces_and_parentheses "$file"
    compile_and_record_errors "$file"
done

rank_files_by_errors
cat "$RANKING_OUTPUT"

# Display punctuation summary
echo "Punctuation Summary:"
cat "$PUNCTUATION_SUMMARY"

echo "Analysis complete!"
