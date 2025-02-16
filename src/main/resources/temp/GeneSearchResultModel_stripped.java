 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 * (at your option) any later version.
public class GeneSearchResultModel {
    private LinkedHashSet<GeneDetails> found = new LinkedHashSet();
    private LinkedHashSet<String> notFound = new LinkedHashSet();
    private LinkedHashSet<String> nonCodingTargets = new LinkedHashSet();
    GeneSearchResult(LinkedHashSet<GeneDetails> hits, LinkedHashSet<String> misses, LinkedHashSet<String> noncoding) {
    }
    GeneSearchResult(LinkedHashSet<GeneDetails> hits, LinkedHashSet<String> misses, LinkedHashSet<String> noncoding, GeneCoordinatesFetcher g) {
    }
    public void setFound(LinkedHashSet<GeneDetails> f) {
    }
    public void setNotFound(LinkedHashSet<String> n) {
    }
    public void setNonCoding (LinkedHashSet<String> n) {
    }
    public void setGeneSearcher(GeneCoordinatesFetcher g) {
    }
    public LinkedHashSet<GeneDetails> getFound() {
    }
    public LinkedHashSet<String> getNotFound() {
    }
    public LinkedHashSet<String> getNonCodingTargets() {
    }
    public GeneCoordinatesFetcher getGeneSearcher() {
    }
}
