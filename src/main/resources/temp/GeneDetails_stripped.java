 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 * (at your option) any later version.
public class GeneDetails {
    private ArrayList<Exon> exons = new ArrayList<>();
    public void setSymbol(String geneSymbol) {
    }
    public void setId(String geneId) {
    }
    public void setChromosome(String chromosome) {
    }
    public void setStrand(String geneStrand) {
    }
    public void setTxStart(Integer geneTxStart) {
    }
    public void setTxEnd(Integer geneTxEnd) {
    }
    public void setCdsStart(Integer geneCdsStart) {
    }
    public void setCdsEnd(Integer geneCdsEnd) {
    }
    public void setTotalExons(Integer geneTotalExons) {
    }
    public void setTotalExons() {
        totalExons = exons.size();
    }
    public void setExons(ArrayList<Exon> geneExons) {
    }
    public void setExons(String starts, String ends) throws GeneExonsException {
        List<String> s = Arrays.asList(starts.split(","));
        List<String> e = Arrays.asList(ends.split(","));
        if (s.isEmpty() || e.isEmpty()) {
            throw new GeneExonsException("No exons found in strings passed to setExons method.");
        }
        if (s.size() != e.size()) {
            throw new GeneExonsException("Number of exons starts does not equal " + "number of exon ends from strings passed to setExons method.");
        }
        for (int i = 0; i < s.size(); i++) {
            if (strand.equals("-")) {
                order = s.size() - i;
            } else {
            }
            Exon exon = new Exon(Integer.parseInt(s.get(i)), Integer.parseInt(e.get(i)), order);
            exons.add(exon);
        }
    }
    public String getSymbol() {
    }
    public String getId() {
    }
    public String getStrand() {
    }
    public String getChromosome() {
    }
    public Integer getTxStart() {
    }
    public Integer getTxEnd() {
    }
    public Integer getCdsStart() {
    }
    public Integer getCdsEnd() {
    }
    public Integer getTotalExons() {
    }
    public ArrayList<Exon> getExons() {
    }
    public ArrayList<Exon> getCodingRegions() {
        ArrayList<Exon> codingExons = new ArrayList<>();
        for (Iterator<Exon> it = exons.iterator(); it.hasNext();) {
            Exon exon = it.next();
            if (exon.getEnd() < cdsStart) {
            } else if (exon.getStart() < cdsStart) {
                if (exon.getEnd() <= cdsEnd) {
                    codingExons.add(new Exon(cdsStart, exon.getEnd(), exon.getOrder()));
                } else {
                    codingExons.add(new Exon(cdsStart, cdsEnd, exon.getOrder()));
                }
            } else if (exon.getStart() < cdsEnd) {
                if (exon.getEnd() > cdsEnd) {
                    codingExons.add(new Exon(exon.getStart(), cdsEnd, exon.getOrder()));
                } else {
                    codingExons.add(new Exon(exon.getStart(), exon.getEnd(), exon.getOrder()));
                }
            } else {
            }
        }
    }
    public boolean isCoding() {
        if (cdsStart == null || cdsEnd == null) {
        }
    }
    public class Exon {
        Exon() {
            this(null, null, null);
        }
        Exon(Integer exonStart, Integer exonEnd) {
            this(exonStart, exonEnd, null);
        }
        Exon(Integer exonStart, Integer exonEnd, Integer exonOrder) {
        }
        public void setStart(Integer exonStart) {
        }
        public void setEnd(Integer exonEnd) {
        }
        public void setOrder(Integer exonOrder) {
        }
        public Integer getStart() {
        }
        public Integer getEnd() {
        }
        public Integer getOrder() {
        }
        public Integer getLength() {
            if (start != null && end != null) {
            } else {
            }
        }
        public boolean isCodingExon() {
            if (! isCoding()) {
            }
        }
        public Exon getExonCodingRegion() {
            if (! isCodingExon()) {
            }
            if (end < cdsStart) {
            } else if (start < cdsStart) {
                if (end <= cdsEnd) {
                    return new Exon(cdsStart, end, order);
                } else {
                    return new Exon(cdsStart, cdsEnd, order);
                }
            } else if (start < cdsEnd) {
                if (end > cdsEnd) {
                    return new Exon(start, cdsEnd, order);
                } else {
                    return new Exon(start, end, order);
                }
            } else {
            }
        }
    }
    public class GeneExonsException extends Exception {
        public GeneExonsException() {
            super();
        }
        public GeneExonsException(String message) {
            super(message);
        }
        public GeneExonsException(String message, Throwable cause) {
            super(message, cause);
        }
        public GeneExonsException(Throwable cause) {
            super(cause);
        }
    }
}
