 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 * (at your option) any later version.
public class FastaSequenceRetriever {
    public FastaSequenceRetriever(File fasta) throws GenomicBase.SequenceFromFastaException, GenomicBase.SamHeaderFromDictException {
        this.fastaDict = new File(fasta.getName() + ".fai");
        if (!fastaDict.exists()) {
            throw new GenomicBase.SequenceFromFastaException("Fasta dictionary " + fastaDict.getAbsolutePath() + " for fasta file " + fastaFile.getAbsolutePath() + " does not exist.");
        }
        this.fastaRef = ReferenceSequenceFileFactory.getReferenceSequenceFile(fasta);
    }
    public String retrieveSequence(String chrom, Integer start, Integer end, String strand) throws GenomicBase.SequenceFromFastaException {
        try {
            final ReferenceSequence seq = fastaRef.getSubsequenceAt(chrom, start, end);
            final byte[] bases = seq.getBases();
            if (strand != null && strand.equals("-")) {
                SequenceUtil.reverseComplement(bases);
            }
            return new String(bases);
        } catch (UnsupportedOperationException ex) {
            throw new GenomicBase.SequenceFromFastaException("Error retrieving fasta sequence from " + fastaFile.getName(), ex);
        }
    }
    public SAMFileHeader getSamHeaderFromDict(File dict) throws GenomicBase.SamHeaderFromDictException {
        StringBuilder dictString = new StringBuilder();
        try (FileReader fileReader = new FileReader(dict);
                    BufferedReader reader = new BufferedReader(fileReader)) {
            while ((line = reader.readLine()) != null) {
                List<String> splitLine = Arrays.asList(line.split("\t"));
                if (splitLine.size() != 5) {
                    throw new GenomicBase.SamHeaderFromDictException("Invalid sequence dictionary - found " + splitLine.size() + " fields instead of 5.");
                }
                for (int i = 1; i < splitLine.size(); i++) {
                    try {
                        Integer.parseInt(splitLine.get(i));
                    } catch (NumberFormatException ex) {
                        throw new GenomicBase.SamHeaderFromDictException("Invalid field in line " + lineCount + ", field " + (1 + i) + ": '" + splitLine.get(i) + "' is not an integer.", ex);
                    }
                }
                dictString.append("@SQ\tSN:").append(splitLine.get(0)).append("\tLN:").append(splitLine.get(1)).append("\n");
            }
        } catch (IOException ex) {
            throw new GenomicBase.SamHeaderFromDictException("IO error reading sequence dictionary file " + dict.getName(), ex);
        }
        if (dictString.length() < 1) {
            throw new GenomicBase.SamHeaderFromDictException("Dictionary file is empty");
        }
        SAMFileHeader header = new SAMFileHeader();
        header.setTextHeader(dictString.toString());
    }
}
