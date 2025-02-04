#!/bin/bash

# Move to project directory
cd /home/bgold/gpt4all/gpt4all-backend/
mvn clean

# Prerequisites
export JAVA_HOME=$(dirname $(dirname $(readlink -f $(which javac))))
echo "Using JAVA_HOME: $JAVA_HOME"

# Directories and Temporary Files
JAVA_DIR="/home/bgold/gpt4all/gpt4all-backend/src/main/java/com/github/mavenautoprimer"
TEMP_DIR="/home/bgold/gpt4all/gpt4all-backend/src/main/resources/temp"
TEMP_OUTPUT="/home/bgold/gpt4all/gpt4all-backend/src/main/resources/temp/java_compile_errors.txt"
RANKED_OUTPUT="/home/bgold/gpt4all/gpt4all-backend/src/main/resources/temp/ranked_compile_errors.txt"
TARGET_DIR="/home/bgold/gpt4all/gpt4all-backend/target/classes"

# Cleanup on exit
trap "rm -f $TEMP_OUTPUT $RANKED_OUTPUT; rm -rf $TARGET_DIR" EXIT

# Ensure required directories exist
mkdir -p "$TEMP_DIR"
mkdir -p "$TARGET_DIR"

# Retrieve and verify classpath dynamically
echo "Retrieving classpath from Maven..."
CLASSPATH=$(mvn -f /home/bgold/gpt4all/gpt4all-backend/pom.xml dependency:build-classpath -Dmdep.outputFile=/dev/stdout | tr -d '\n')

if [[ -z "$CLASSPATH" ]]; then
    echo "⚠️  Error: Classpath is empty. Running 'mvn compile' to resolve dependencies..."
    mvn -f /home/bgold/gpt4all/gpt4all-backend/pom.xml compile
    CLASSPATH=$(mvn -f /home/bgold/gpt4all/gpt4all-backend/pom.xml dependency:build-classpath -Dmdep.outputFile=/dev/stdout | tr -d '\n')
fi

if [[ -z "$CLASSPATH" ]]; then
    echo "❌ Error: Classpath is still empty after Maven compile!"
    exit 1
fi

echo "Classpath: $CLASSPATH"

# Compile Java files
echo "Compiling all Java files in correct order..."
find "$JAVA_DIR" -name "*.java" ! -name "ApplicationMain.java" ! -name "UcscBuildAndTableFetcher.java" ! -name "ApplicationConfig.java" > /tmp/java_files.txt
javac -d "$TARGET_DIR" -cp ".:$CLASSPATH" @/tmp/java_files.txt 2>> "$TEMP_OUTPUT"
javac -d "$TARGET_DIR" -cp "target/classes:$CLASSPATH" "$JAVA_DIR/UcscBuildAndTableFetcher.java" 2>> "$TEMP_OUTPUT"
javac -d "$TARGET_DIR" -cp "target/classes:$CLASSPATH" "$JAVA_DIR/ApplicationConfig.java" 2>> "$TEMP_OUTPUT"
javac -d "$TARGET_DIR" -cp "target/classes:$CLASSPATH" "$JAVA_DIR/ApplicationMain.java" 2>> "$TEMP_OUTPUT"

# Error handling and ranking
if [[ -s "$TEMP_OUTPUT" ]]; then
    echo "❌ Compilation errors detected!"
    cat "$TEMP_OUTPUT"

    echo "🔍 Ranking error frequency..."
    awk '{print $1}' "$TEMP_OUTPUT" | sort | uniq -c | sort -nr > "$RANKED_OUTPUT"

    echo "🔎 Top problematic files:"
    cat "$RANKED_OUTPUT"

    exit 1
fi

echo "✅ Compilation successful!"
