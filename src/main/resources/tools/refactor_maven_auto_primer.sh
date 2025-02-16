#!/bin/bash

# Define project directories
PROJECT_DIR="/Users/bgold/MavenAutoPrimer/src/main/java/com/github/mavenautoprimer"
RESOURCE_DIR="/Users/bgold/MavenAutoPrimer/src/main/resources"

echo "Starting full project refactoring..."
echo "Project directory: $PROJECT_DIR"
echo "Resource directory: $RESOURCE_DIR"

# 1. Update occurrences INSIDE Java and FXML files
echo "Updating occurrences inside Java and FXML files..."

find "$PROJECT_DIR" "$RESOURCE_DIR" -type f \( -name "*.java" -o -name "*.fxml" -o -name "*.properties" -o -name "*.xml" -o -name "*.md" -o -name "*.txt" \) -exec sed -i '' -E \
    -e 's/AutoPrimer3A/MavenAutoPrimer/g' \
    -e 's/AutoPrimer3/MavenAutoPrimer/g' \
    -e 's/ap3A/mva/g' \
    -e 's/ap3/mva/g' {} +

# 2. Rename FILES and DIRECTORIES
echo "Renaming files and directories..."

find "$PROJECT_DIR" -depth -name "*AutoPrimer3A*" -exec bash -c 'mv "$0" "${0/AutoPrimer3A/MavenAutoPrimer}"' {} \;
find "$PROJECT_DIR" -depth -name "*AutoPrimer3*" -exec bash -c 'mv "$0" "${0/AutoPrimer3/MavenAutoPrimer}"' {} \;
find "$PROJECT_DIR" -depth -name "*ap3A*" -exec bash -c 'mv "$0" "${0/ap3A/mva}"' {} \;
find "$PROJECT_DIR" -depth -name "*ap3*" -exec bash -c 'mv "$0" "${0/ap3/mva}"' {} \;

find "$RESOURCE_DIR" -depth -name "*AutoPrimer3A*" -exec bash -c 'mv "$0" "${0/AutoPrimer3A/MavenAutoPrimer}"' {} \;
find "$RESOURCE_DIR" -depth -name "*AutoPrimer3*" -exec bash -c 'mv "$0" "${0/AutoPrimer3/MavenAutoPrimer}"' {} \;
find "$RESOURCE_DIR" -depth -name "*ap3A*" -exec bash -c 'mv "$0" "${0/ap3A/mva}"' {} \;
find "$RESOURCE_DIR" -depth -name "*ap3*" -exec bash -c 'mv "$0" "${0/ap3/mva}"' {} \;

# 3. Fix FXML fx:controller references
echo "Updating controller references in FXML files..."

find "$RESOURCE_DIR" -type f -name "*.fxml" -exec sed -i '' -E \
    -e 's/fx:controller="com.github.AutoPrimer3Controller"/fx:controller="com.github.MavenAutoPrimerController"/g' {} +

echo "Full refactoring completed successfully!"
