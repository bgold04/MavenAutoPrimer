/*
 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.mavenautoprimer;

/**
 *
 * @author David A. Parry with edits by Bert Gold, PhD <bgold04@gmail.com>
 */

import com.github.mavenautoprimer.GenomicBase;
import java.io.BufferedReader;
import net.sf.picard.reference.ReferenceSequence;
import net.sf.picard.reference.ReferenceSequenceFile;
import net.sf.picard.reference.ReferenceSequenceFileFactory;
import net.sf.samtools.SAMFileHeader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import net.sf.samtools.util.SequenceUtil;

public class FastaSequenceRetriever {

    private File fastaFile;
    private ReferenceSequenceFile fastaRef;
    private File fastaDict;

    public FastaSequenceRetriever(File fasta) throws GenomicBase.SequenceFromFastaException, GenomicBase.SamHeaderFromDictException {
        this.fastaFile = fasta;
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

            String line;
            int lineCount = 0;

            while ((line = reader.readLine()) != null) {
                lineCount++;
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
        return header;
    }
}
