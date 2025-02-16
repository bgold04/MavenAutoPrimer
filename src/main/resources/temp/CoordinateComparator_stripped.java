* Copyright (C) 2013 David A. Parry
    (at your option) any later version.
public class CoordinateComparator implements Comparator<String> {
    ChromComparator chromCompare = new ChromComparator();
    public int compare(String s1, String s2) {
        if (s1.matches("\\w+:\\d+-\\d+") && s2.matches("\\w+:\\d+-\\d+")) {
            List<String> split1 =  Arrays.asList(s1.split(":"));
            List<String> split2 =  Arrays.asList(s2.split(":"));
            String chr1 = split1.get(0);
            String chr2 = split2.get(0);
            chr1 = chr1.replaceFirst("chr", "");
            chr2 = chr1.replaceFirst("chr", "");
            int chrComp = chromCompare.compare(chr1, chr2);
            if (chrComp != 0) {
            } else {
                List<String> pos1 = Arrays.asList(split1.get(1).split("-"));
                List<String> pos2 = Arrays.asList(split2.get(1).split("-"));
                int startComp = Integer.valueOf(pos1.get(0)).compareTo(Integer.valueOf(pos2.get(0)));
                if (startComp != 0) {
                } else {
                    return Integer.valueOf(pos1.get(0)).compareTo(Integer.valueOf(pos2.get(0)));
                }
            }
        } else if (s1.matches("\\w+:\\d+-\\d+")) {
        } else if (s2.matches("\\w+:\\d+-\\d+")) {
        } else {
            return s1.compareTo(s2);
        }
    }
}
