#!/bin/bash


# on mac this requires:
# brew install astyle
# brew install checkstyle
# brew install llvm

# Update your script to dynamically fetch JAVA_HOME
export JAVA_HOME=$(/usr/libexec/java_home)
echo "Using JAVA_HOME: $JAVA_HOME"

find "$JAVA_HOME/javafx-src/javafx" -type f || echo "JavaFX sources not found"
"$JAVA_HOME/bin/javac" -version || echo "javac not found in JAVA_HOME"


# Enable strict error handling
set -euo pipefail

# File to process
SOURCE_FILE="/Users/bgold/AutoPrimer3AA/src/com/github/autoprimer3A/AutoPrimer3A.java"

# Functions
count_braces() {
    echo "Counting braces in $SOURCE_FILE..."

    local open_braces=$(grep -o '{' "$SOURCE_FILE" | wc -l)
    local close_braces=$(grep -o '}' "$SOURCE_FILE" | wc -l)

    echo "Opening braces:      $open_braces"
    echo "Closing braces:      $close_braces"

    if [ "$open_braces" -ne "$close_braces" ]; then
        echo "Mismatch detected! Running analysis..."
        analyze_brace_mismatch "$SOURCE_FILE"
    else
        echo "Brace count matches."
    fi
}

analyze_brace_mismatch() {
    local file=$1
    awk '
    BEGIN {
        open_count = 0;
        close_count = 0;
    }
    {
        for (i = 1; i <= length($0); i++) {
            char = substr($0, i, 1);
            if (char == "{") open_count++;
            if (char == "}") close_count++;
            if (close_count > open_count) {
                print "Extra closing brace found at line " NR ": " $0;
                close_count--; # Adjust to avoid false positives
            }
        }
    }
    END {
        if (open_count > close_count) {
            print "Unmatched opening braces detected. Check the file.";
        }
    }
    ' "$file" || echo "Error analyzing braces with awk."
}

indent_code() {
    echo "Indenting code..."
    if command -v clang-format &> /dev/null; then
        clang-format -i "$SOURCE_FILE"
        echo "Formatted $SOURCE_FILE using clang-format."
    elif command -v astyle &> /dev/null; then
        astyle --style=java "$SOURCE_FILE" || echo "Error formatting with astyle."
        echo "Formatted $SOURCE_FILE using astyle."
    else
        echo "No code formatter found (clang-format or astyle). Install one for automatic indentation."
    fi
}

# Main Script Execution
echo "Processing file: $SOURCE_FILE"
if [ ! -f "$SOURCE_FILE" ]; then
    echo "Error: Source file not found at $SOURCE_FILE"
    exit 1
fi

count_braces
indent_code

echo "Done!"

















