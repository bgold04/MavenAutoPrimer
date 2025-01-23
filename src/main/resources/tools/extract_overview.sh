#!/bin/bash

# Ensure the file is provided as an argument
if [ $# -ne 1 ]; then
    echo "Usage: $0 <Java file>"
    exit 1
fi

java_file=$1

# Check if the file exists
if [ ! -f "$java_file" ]; then
    echo "Error: File '$java_file' not found."
    exit 1
fi

# Extract patterns with line numbers
echo "Analyzing $java_file..."
grep -nE 'public void|public static void|class |public class|private boolean|public boolean|public LinkedHashSet|private LinkedHashSet|public static|private void|private ArrayList|protected' "$java_file" > overview_$java_file.txt

echo "Overview saved to overview_$java_file.txt"

## Suggested Use
#Generate Overviews for Both Files


#Run your modified script for each file:
#./extract_overview.sh ~/AutoPrimer3AA/src/com/github/autoprimer3A/AutoPrimer3A.java
#./extract_overview.sh ~/Documents/AutoPrimer3/src/com/github/autoprimer3/AutoPrimer3.java


#This creates an overview of AutoPrimer3A.java.txt in the same directory as the script
#overview_AutoPrimer3.java.txt in the same directory as the script
Move the Overview Files to the Work Directory
Move the generated files to your ~/AutoPrimer3AA/work directory:

# Move the Overview Files to the Work Directory
#mv overview_AutoPrimer3A.java.txt ~/AutoPrimer3AA/work/
#mv overview_AutoPrimer3.java.txt ~/AutoPrimer3AA/work/

#Use sdiff to Combine the Overviews
#Use the sdiff command to combine the two files into a single two-column file with a delimiter:
sdiff -w 200 -s ~/AutoPrimer3AA/work/overview_AutoPrimer3A.java.txt ~/AutoPrimer3AA/work/overview_AutoPrimer3.java.txt > ~/AutoPrimer3AA/work/comparison_AutoPrimer3_overview.txt

#The -w 200 option ensures enough width for both columns.
#The -s option suppresses the side-by-side difference markers (<, |, >), making it a cleaner two-column output.
#View or Edit the Combined File
#Open the combined file to verify or further process:
#open resulting file
#less ~/AutoPrimer3AA/work/comparison_AutoPrimer3_overview.txt
Resulting File
#The file comparison_AutoPrimer3_overview.txt will have two columns:

#Column 1: Overview of AutoPrimer3A.java
#Column 2: Overview of AutoPrimer3.java