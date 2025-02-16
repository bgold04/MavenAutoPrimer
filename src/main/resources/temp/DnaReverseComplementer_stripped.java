 * Copyright (C) 2013,2014 David A. Parry
    (at your option) any later version.
public class DNAReverseComplementer {
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
        private static final Map<Character, Character> COMPLEMENTS = Map.ofEntries(Map.entry('a', 't'), Map.entry('c', 'g'), Map.entry('g', 'c'), Map.entry('t', 'a'), Map.entry('u', 'a'), Map.entry('y', 'r'), Map.entry('r', 'y'), Map.entry('k', 'm'), Map.entry('m', 'k'), Map.entry('d', 'h'), Map.entry('v', 'b'), Map.entry('h', 'd'), Map.entry('b', 'v'), Map.entry('A', 'T'), Map.entry('C', 'G'), Map.entry('G', 'C'), Map.entry('T', 'A'), Map.entry('U', 'A'), Map.entry('Y', 'R'), Map.entry('R', 'Y'), Map.entry('K', 'M'), Map.entry('M', 'K'), Map.entry('D', 'H'), Map.entry('V', 'B'), Map.entry('H', 'D'), Map.entry('B', 'V'));
        public static String complement(String dna) {
            if (dna == null) {
            }
            StringBuilder comp = new StringBuilder(dna.length());
            for (char nt : dna.toCharArray()) {
                comp.append(COMPLEMENTS.getOrDefault(nt, nt));
            }
            return comp.toString();
        }
    }
    public static void main(String[] args) {
        System.out.println(DNAComplement.complement("acgt"));
        System.out.println(DNAComplement.complement("ACGT"));
        System.out.println(DNAComplement.complement("nxyw"));
        // Expected: nxyw (unchanged)
        System.out.println(DNAComplement.complement("acgtyrkm"));
        System.out.println(DNAComplement.complement(null));
        System.out.println(DNAComplement.complement(""));
    }
}
