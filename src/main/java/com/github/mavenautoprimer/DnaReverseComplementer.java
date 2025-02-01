/*
 * baSeq
 * Copyright (C) 2013,2014 David A. Parry
 * d.a.parry@leeds.ac.uk
 * https://sourceforge.net/projects/baseq/
 *
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.mavenautoprimer;
/**
 *
 * @author David A. Parry and edited by Bert Gold <bgold04@gmail.com>
 */


import java.util.Map;

public class DNAReverseComplementer {
    
    // Private constructor to prevent instantiation
    private DNAReverseComplementer() {}
    
    public static String reverseComplement(String dna) {
        String rev = reverse(dna);
        return DNAComplement.complement(rev);
    }
    
    public static String reverse(String dna) {
        StringBuilder rev = new StringBuilder(dna);
        rev.reverse();
        return rev.toString();
    }
    
    public static class DNAComplement {
        // Static map to store nucleotide complements
        private static final Map<Character, Character> COMPLEMENTS = Map.ofEntries(Map.entry('a', 't'), Map.entry('c', 'g'), Map.entry('g', 'c'), Map.entry('t', 'a'), Map.entry('u', 'a'), Map.entry('y', 'r'), Map.entry('r', 'y'), Map.entry('k', 'm'), Map.entry('m', 'k'), Map.entry('d', 'h'), Map.entry('v', 'b'), Map.entry('h', 'd'), Map.entry('b', 'v'), Map.entry('A', 'T'), Map.entry('C', 'G'), Map.entry('G', 'C'), Map.entry('T', 'A'), Map.entry('U', 'A'), Map.entry('Y', 'R'), Map.entry('R', 'Y'), Map.entry('K', 'M'), Map.entry('M', 'K'), Map.entry('D', 'H'), Map.entry('V', 'B'), Map.entry('H', 'D'), Map.entry('B', 'V'));
        public static String complement(String dna) {
            if (dna == null) {
                return "";
                 // Return empty string for null input
            }
            
            StringBuilder comp = new StringBuilder(dna.length());
            for (char nt : dna.toCharArray()) {
                comp.append(COMPLEMENTS.getOrDefault(nt, nt));
            }
            return comp.toString();
        }
    }
    
    public static void main(String[] args) {
        // Test cases
        System.out.println(DNAComplement.complement("acgt"));
         // Expected: tgca
        System.out.println(DNAComplement.complement("ACGT"));
         // Expected: TGCA
        System.out.println(DNAComplement.complement("nxyw"));
         // Expected: nxyw (unchanged)
        System.out.println(DNAComplement.complement("acgtyrkm"));
         // Expected: tgcarymk
        System.out.println(DNAComplement.complement(null));
         // Expected: ""
        System.out.println(DNAComplement.complement("")); // Expected: ""
    }
}
