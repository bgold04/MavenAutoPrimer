 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 * (at your option) any later version.
public class GenomicRegionSummaryModel implements Comparable<GenomicRegionSummary>, Serializable {
    public String chromosome = new String();
    public String id = new String();
    private String name = new String();
    public GenomicRegionSummaryModel(String chrom, int sp, int ep) {
        this(null, 0, 0, null, null, null, null);
    }
    public GenomicRegionSummaryModel(String chrom, int sp, int ep) {
        this(chrom, sp, ep, null, null, null, null);
    }
    public GenomicRegionSummaryModel(String chrom, int sp, int ep, String sd, String ed) {
        this(chrom, sp, ep, sd, ed, null, null);
    }
    public GenomicRegionSummaryModel(int sp, int ep) {
        this(null, sp, ep,  null, null, null, null);
    }
    public GenomicRegionSummaryModel(int sp, int ep, String sd, String ed) {
        this(null, sp, ep, sd, ed, null, null);
    }
    public GenomicRegionSummaryModel(int sp, int ep, String d) {
        this(null, sp, ep, null, null, d, null);
    }
    public GenomicRegionSummaryModel(String chrom, int sp, int ep, String sd, String ed, String d, String nm) {
    }
    public void setChromosome(String c) {
    }
    public void setStartId(String d) {
    }
    public void setEndId(String d) {
    }
    public void setStartPos(int i) {
    }
    public void setEndPos(int i) {
    }
    public void setId(String d) {
    }
    public void setName(String n) {
    }
    public String getChromosome() {
    }
    public String getStartId() {
    }
    public String getEndId() {
    }
    public int getStartPos() {
    }
    public int getEndPos() {
    }
    public String getId() {
    }
    public String getName() {
    }
    public boolean isEmpty() {
    }
    public Integer getLength() {
    }
    public String getCoordinateString() {
    }
    public String getBedLine() {
    }
    public String getIdLine() {
    }
    public int compareTo(GenomicRegionSummary r) {
        if (chromosome == null && r.getChromosome() != null) {
        } else if (chromosome != null && r.getChromosome() == null) {
        } else if(chromosome != null && r.getChromosome() != null) {
            ChromComparator chromCompare = new ChromComparator();
            int i = chromCompare.compare(chromosome,r.getChromosome());
            //int i =  chromosome.compareToIgnoreCase(r.getChromosome());
            if (i == 0) {
                i = startPos - r.getStartPos();
                if (i != 0) {
                } else {
                    return endPos - r.getEndPos();
                }
            } else {
            }
        } else {
            int i = startPos - r.getStartPos();
            if (i != 0) {
            } else {
                return endPos - r.getEndPos();
            }
        }
    }
    public void mergeRegionsByPosition(ArrayList<GenomicRegionSummary> regions) {
        if (regions.size() < 2) {
        }
        Collections.sort(regions);
        Iterator<GenomicRegionSummary> rIter = regions.iterator();
        GenomicRegionSummary previousRegion = rIter.next();
        ArrayList<GenomicRegionSummary> merged = new ArrayList<>();
        while (rIter.hasNext()) {
            GenomicRegionSummary r = rIter.next();
            if (r.getChromosome() == null && previousRegion.getChromosome() == null) {
                if (previousRegion.getEndPos() >= r.getStartPos()) {
                    if (previousRegion.getEndPos() < r.getEndPos()) {
                        previousRegion.setEndPos(r.getEndPos());
                        if (!previousRegion.getName().equalsIgnoreCase(r.getName())) {
                            previousRegion.setName(previousRegion.getName().concat("/").concat(r.getName()));
                        }
                        if (!previousRegion.getId().equalsIgnoreCase(r.getId())) {
                            previousRegion.setId(previousRegion.getId().concat("/").concat(r.getId()));
                        }
                    }
                } else {
                    merged.add(previousRegion);
                }
            } else if (r.getChromosome() == null || previousRegion.getChromosome() == null) {
                merged.add(previousRegion);
            } else {
                if (r.getChromosome().equalsIgnoreCase(previousRegion.getChromosome()) && previousRegion.getEndPos() >= r.getStartPos()) {
                    if (previousRegion.getEndPos() <= r.getEndPos()) {
                        previousRegion.setEndPos(r.getEndPos());
                        if (!previousRegion.getName().equalsIgnoreCase(r.getName())) {
                            previousRegion.setName(previousRegion.getName().concat("/").concat(r.getName()));
                        } else if (!previousRegion.getName().equals(r.getName())) {
                            List<String> nameSort = Arrays.asList(previousRegion.getName(), r.getName());
                            Collections.sort(nameSort);
                            previousRegion.setName(nameSort.get(0));
                        }
                        if (!previousRegion.getId().equalsIgnoreCase(r.getId())) {
                            previousRegion.setId(previousRegion.getId().concat("/").concat(r.getId()));
                        }
                    }
                } else {
                    merged.add(previousRegion);
                }
            }
        }
        merged.add(previousRegion);
        regions.clear();
        regions.addAll(merged);
    }
}
