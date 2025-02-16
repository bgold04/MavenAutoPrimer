 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 * (at your option) any later version.
public class GenomicRegionParser {
    public static GenomicRegionSummary readRegion(String s) {
        s = s.trim();
        GenomicRegionSummary r = parseAsRegion(s);
        if (r != null) {
        }
        String[] spaceSplit = s.split("\\s+");
        r = parseAsBed(spaceSplit);
        if (r != null) {
        }
        r = parseAsVcf(spaceSplit);
    }
    public static GenomicRegionSummary parseAsRegion(String s) {
        if (! s.matches("^\\w+:[\\d,]+(\\s+\\S+)*$") && ! s.matches("^\\w+:[\\d,]+-[\\d,]+(\\s+\\S+)*$")) {
        }
        String[] spaceSplit = s.split("\\s+");
        String[] chromSplit = spaceSplit[0].split(":");
        if (chromSplit.length < 2) {
        }
        for (int i = 0; i < chromSplit.length; i++) {
            chromSplit[i] = chromSplit[i].trim();
        }
        String[] posSplit = chromSplit[1].split("-");
        if (posSplit.length == 1) {
            start = posSplit[0].replaceAll(",", "");
        } else if (posSplit.length == 2) {
        } else {
        }
        if (! checkInteger(start) || ! checkInteger(end)) {
        }
        GenomicRegionSummary gr = new GenomicRegionSummary(chromSplit[0], Integer.parseInt(start), Integer.parseInt(end));
        if (spaceSplit.length > 1) {
            if (spaceSplit[1].matches(".*\\w.*")) {
                gr.setName(spaceSplit[1]);
            }
        }
    }
    public static GenomicRegionSummary parseAsBed(String[] split) {
        if (split.length < 3) {
        }
        String start = split[1].replaceAll(",", "");
        String end   = split[2].replaceAll(",", "");
        if (! checkInteger(start) || ! checkInteger(end)) {
        }
        GenomicRegionSummary gr = new GenomicRegionSummary(split[0], Integer.parseInt(start) + 1, Integer.parseInt(end));
        if (split.length >= 4) {
            if (split[3].matches(".*\\w.*")) {
                gr.setName(split[3]);
            }
        }
    }
    public static GenomicRegionSummary parseAsVcf(String[] split) {
        if (split.length < 2) {
        }
        String start = split[1].replaceAll(",", "");
        if (! checkInteger(start)) {
        }
        if (split.length >= 8) {
            Pattern p = Pattern.compile("END=(\\d+)");
            Matcher m = p.matcher(split[7]);
            if (m.find()) {
                gr = new GenomicRegionSummary(split[0], Integer.parseInt(start), Integer.parseInt(m.group(1)));
            }
        }
        if (split.length >= 4) {
            Pattern p = Pattern.compile("^(([ATGCN]+),*)+$");
            Matcher m = p.matcher(split[3]);
            if (m.find()) {
                Integer length = m.group(1).length();
                for (int i = 2; i <= m.groupCount(); i++) {
                    length = m.group(i).length() > length ? m.group(i).length() : length;
                }
                gr = new GenomicRegionSummary(split[0], Integer.parseInt(start), Integer.parseInt(start) + length -1);
            }
        }
        if (split.length >= 2) {
            gr = new GenomicRegionSummary(split[0], Integer.parseInt(start), Integer.parseInt(start) );
        }
        if (split.length >= 3 && gr != null) {
            if (split[2].matches(".*\\w.*")) {
                gr.setName(split[2]);
            }
        }
    }
    private static boolean checkInteger(String s) {
        return s.matches("^\\d+$");
    }
}
