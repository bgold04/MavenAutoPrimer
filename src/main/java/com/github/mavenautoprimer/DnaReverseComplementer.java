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
    private ReverseComplementDNA() {};
    public static String reverseComplement(String dna) {
        String rev = reverse(dna);
        return complement(rev);
    }
    public static String reverse(String dna) {
        StringBuffer rev = new StringBuffer(dna);
        rev = rev.reverse();
        return rev.toString();
    }
// Code written by Bert Gold, PhD and ChatGPT
public class DNAComplement {
    // Static map to store nucleotide complements
    private static final Map<Character, Character> COMPLEMENTS = Map.ofEntries(Map.entry('a', 't'), Map.entry('c', 'g'), Map.entry('g', 'c'), Map.entry('t', 'a'), Map.entry('u', 'a'), Map.entry('y', 'r'), Map.entry('r', 'y'), Map.entry('k', 'm'), Map.entry('m', 'k'), Map.entry('d', 'h'), Map.entry('v', 'b'), Map.entry('h', 'd'), Map.entry('b', 'v'), Map.entry('A', 'T'), Map.entry('C', 'G'), Map.entry('G', 'C'), Map.entry('T', 'A'), Map.entry('U', 'A'), Map.entry('Y', 'R'), Map.entry('R', 'Y'), Map.entry('K', 'M'), Map.entry('M', 'K'), Map.entry('D', 'H'), Map.entry('V', 'B'), Map.entry('H', 'D'), Map.entry('B', 'V'));
    public static String complement(String dna) {
        // Handle null input
        if (dna == null) {
            return "";
             // Return empty string for null input
        }
        // StringBuilder to build the complement
        StringBuilder comp = new StringBuilder(dna.length());
        // Iterate through each character in the input DNA string
        for (int i = 0; i < dna.length(); i++) {
            char nt = dna.charAt(i);
            // Get the complement or return the original character if not in the map
            char cnt = COMPLEMENTS.getOrDefault(nt, nt);
            // Append the complement (or original character) to the result
            comp.append(cnt);
        }
        return comp.toString();
    }
    public static void main(String[] args) {
        // Test cases
        System.out.println(complement("acgt"));
                 // Expected: tgca
        System.out.println(complement("ACGT"));
                 // Expected: TGCA
        System.out.println(complement("nxyw"));
                 // Expected: nxyw (unchanged)
        System.out.println(complement("acgtyrkm"));
             // Expected: tgcarymk
        System.out.println(complement(null));
                   // Expected: (empty string)
        System.out.println(complement(""));
                     // Expected: (empty string)
    }
}
// David A Parry's code:
//    public static String complement(String dna) {
//        StringBuilder comp = new StringBuilder(dna.length());
//        for (int i = 0; i < dna.length(); i++) {
//            char nt = dna.charAt(i);
//            char cnt;
//            switch (nt) {
//            case 'a': cnt = 't';
//                break;
//            case 'c': cnt = 'g';
//                break;
//            case 'g': cnt = 'c';
//                break;
//            case 't': cnt = 'a';
//                break;
//            case 'u': cnt = 'a';
//                break;
//            case 'y': cnt = 'r';
//                break;
//            case 'r': cnt = 'y';
//                break;
//            case 'k': cnt = 'm';
//                break;
//            case 'm': cnt = 'k';
//                break;
//            case 'd': cnt = 'h';
//                break;
//            case 'v': cnt = 'b';
//                break;
//            case 'h': cnt = 'd';
//                break;
//            case 'b': cnt = 'v';
//                break;
//            case 'A': cnt = 'T';
//                break;
//            case 'C': cnt = 'G';
//                break; 
//            case 'G': cnt = 'C';
//                break;
//            case 'T': cnt = 'A';
//                break;
//            case 'U': cnt = 'A';
//                break;
//            case 'Y': cnt = 'R';
//                break;
//            case 'R': cnt = 'Y';
//                break;
//            case 'K': cnt = 'M';
//                break;
//            case 'M': cnt = 'K';
//                break;
//            case 'D': cnt = 'H';
//                break;
//            case 'V': cnt = 'B';
//                break;
//            case 'H': cnt = 'D';
//                break;
//            case 'B': cnt = 'V';
//                break;
//            default:
//                cnt = nt;
                //applies to w, s, n or x codes as well as unrecognised
//                break;
//            }
//            comp.append(cnt);
//        }
//        return comp.toString();
//    }
//}
