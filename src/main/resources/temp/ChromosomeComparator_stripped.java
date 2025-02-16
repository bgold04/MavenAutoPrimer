* Copyright (C) 2013 David A. Parry
    (at your option) any later version.
public class ChromosomeComparator implements Comparator<String> {
    public int compare(String s1, String s2) {
        if (s1.matches("^\\d+$") && s2.matches("^\\d+$")) {
            return Integer.valueOf(s1).compareTo(Integer.valueOf(s2));
        } else if (s1.matches("^\\d+$")) {
        } else if (s2.matches("^\\d+$")) {
        } else if (s1.matches("^[uU]\\S+") || s2.matches("^[uU]\\S+")) {
            if (s1.matches("^[uU]\\S+") && s2.matches("^[uU]\\S+")) {
                return s1.compareTo(s2);
            } else if (s2.matches("^[uU]\\S+")) {
            } else {
            }
        } else if (s1.matches("^[mM]\\w*") || s2.matches("^[mM]\\w*")) {
            if (s1.matches("^[mM]\\w*") && s2.matches("^[mM]\\w*")) {
                return s1.compareTo(s2);
            } else if (s1.matches("^[mM]\\w*")) {
            } else {
            }
        } else {
            return s1.compareTo(s2);
        }
    }
}
