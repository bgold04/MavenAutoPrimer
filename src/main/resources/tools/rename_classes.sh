#!/bin/bash

# Directory containing the .java files
SRC_DIR="../../java/com/github"

# Rename files using mv
mv "$SRC_DIR/AboutController.java" "$SRC_DIR/AboutDialogController.java"
mv "$SRC_DIR/AutoPrimer3A.java" "$SRC_DIR/AutoPrimerApplication.java"
mv "$SRC_DIR/AutoPrimer3AConfig.java" "$SRC_DIR/ApplicationConfig.java"
mv "$SRC_DIR/BuildToMisprimingLibrary.java" "$SRC_DIR/MisprimingLibraryBuilder.java"
mv "$SRC_DIR/ChromComparator.java" "$SRC_DIR/ChromosomeComparator.java"
mv "$SRC_DIR/CoordComparator.java" "$SRC_DIR/CoordinateComparator.java"
mv "$SRC_DIR/GeneDetails.java" "$SRC_DIR/GeneDetailsModel.java"
mv "$SRC_DIR/GeneSearchResult.java" "$SRC_DIR/GeneSearchResultModel.java"
mv "$SRC_DIR/GenomicRegionSummary.java" "$SRC_DIR/GenomicRegionSummaryModel.java"
mv "$SRC_DIR/GetEnsemblGeneCoordinates.java" "$SRC_DIR/EnsemblGeneCoordinatesFetcher.java"
mv "$SRC_DIR/GetGeneCoordinates.java" "$SRC_DIR/GeneCoordinatesFetcher.java"
mv "$SRC_DIR/GetSequenceFromFasta.java" "$SRC_DIR/FastaSequenceRetriever.java"
mv "$SRC_DIR/GetUcscBuildsAndTables.java" "$SRC_DIR/UcscBuildAndTableFetcher.java"
mv "$SRC_DIR/GetUcscGeneCoordinates.java" "$SRC_DIR/UcscGeneCoordinatesFetcher.java"
mv "$SRC_DIR/MainApplication.java" "$SRC_DIR/ApplicationMain.java"
mv "$SRC_DIR/Primer3ResultViewController.java" "$SRC_DIR/Primer3ResultsController.java"
mv "$SRC_DIR/RefactorAutoPrimer3A.java" "$SRC_DIR/AutoPrimerRefactorer.java"
mv "$SRC_DIR/RegionParser.java" "$SRC_DIR/GenomicRegionParser.java"
mv "$SRC_DIR/ReverseComplementDNA.java" "$SRC_DIR/DnaReverseComplementer.java"
mv "$SRC_DIR/SequenceFromDasUcsc.java" "$SRC_DIR/DasUcscSequenceFetcher.java"
mv "$SRC_DIR/controllers.java" "$SRC_DIR/ControllerRegistry.java"

echo "All files renamed successfully."
