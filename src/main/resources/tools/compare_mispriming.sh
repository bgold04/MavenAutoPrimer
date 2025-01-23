#!/bin/bash

# Paths to Java files
file1=~/AutoPrimer3AA/src/com/github/autoprimer3A/AutoPrimer3A.java
file2=~/Documents/AutoPrimer3/src/com/github/autoprimer3/AutoPrimer3.java

# Directory to store results
work_dir=~/AutoPrimer3AA/work

# Check if both files exist
if [ ! -f "$file1" ]; then
    echo "Error: File '$file1' not found."
    exit 1
fi

if [ ! -f "$file2" ]; then
    echo "Error: File '$file2' not found."
    exit 1
fi

# Create work directory if it doesn't exist
mkdir -p "$work_dir"

# Function to extract overview
extract_overview() {
    local input_file=$1
    local output_file=$2

    echo "Analyzing $input_file..."
    grep -nE 'mispriming|Mispriming' "$input_file" > "$output_file"
    echo "Overview saved to $output_file"
}

# Generate overviews
overview1="$work_dir/mispriming_AutoPrimer3A.java.txt"
overview2="$work_dir/mispriming_AutoPrimer3.java.txt"
extract_overview "$file1" "$overview1"
extract_overview "$file2" "$overview2"

# Generate two-column comparison
comparison_file="$work_dir/comparison_AutoPrimer3_mispriming.txt"
echo "Creating two-column comparison of misprimiing ..."
sdiff -w 200 -s "$overview1" "$overview2" > "$comparison_file"

echo "Comparison saved to $comparison_file"
