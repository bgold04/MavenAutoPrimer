#!/bin/bash

# Define absolute paths
RESOURCE_DIR="/Users/bgold/MavenAutoPrimer/src/main/resources"

# Rename resource files using mv
mv "$RESOURCE_DIR/MavenAutoPrimer.fxml" "$RESOURCE_DIR/AutoPrimerApplication.fxml"
mv "$RESOURCE_DIR/MavenAutoPrimerMac.fxml" "$RESOURCE_DIR/AutoPrimerApplicationMac.fxml"
mv "$RESOURCE_DIR/Primer3ResultView.fxml" "$RESOURCE_DIR/Primer3ResultsView.fxml"
mv "$RESOURCE_DIR/Primer3ResultViewMac.fxml" "$RESOURCE_DIR/Primer3ResultsViewMac.fxml"
mv "$RESOURCE_DIR/about.fxml" "$RESOURCE_DIR/AboutDialog.fxml"

# Update fx:controller attributes inside .fxml files
for FILE in "$RESOURCE_DIR"/*.fxml; do
    echo "Updating fx:controller in $FILE..."
    sed -i '' -E \
        -e 's/fx:controller="com.github.mavenautoprimer"/fx:controller="com.github.AutoPrimerApplication"/g' \
        -e 's/fx:controller="com.github.Primer3ResultViewController"/fx:controller="com.github.Primer3ResultsController"/g' \
        -e 's/fx:controller="com.github.AboutController"/fx:controller="com.github.AboutDialogController"/g' \
        "$FILE"
done

echo "Resource files renamed and updated successfully."
