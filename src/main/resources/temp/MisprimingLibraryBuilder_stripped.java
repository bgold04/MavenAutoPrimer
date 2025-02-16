 * Copyright (C) 2014 David A. Parry <d.a.parry@leeds.ac.uk>
 * (at your option) any later version.
public class MisprimingLibraryBuilder {
    public static final HashMap<String, String> buildToMisprime = new HashMap<>();
    static {
        initializeBuildsToMisprime();
    }
    public String getMisprimingLibrary(String build) {
        if (buildToMisprime.containsKey(build)) {
            return buildToMisprime.get(build);
        } else {
        }
    }
    public static void initializeBuildsToMisprime() {
        buildToMisprime.put("hg", "human");
        buildToMisprime.put("panTro", "human");
        buildToMisprime.put("gorGor", "human");
        buildToMisprime.put("ponAbe", "human");
        buildToMisprime.put("nomLeu", "human");
        buildToMisprime.put("rheMac", "human");
        buildToMisprime.put("papAnu", "human");
        buildToMisprime.put("saiBol", "human");
        buildToMisprime.put("calJac", "human");
        buildToMisprime.put("tarSyr", "human");
        buildToMisprime.put("micMur", "human");
        buildToMisprime.put("tupBel", "human");
        buildToMisprime.put("mm", "rodent");
        buildToMisprime.put("rn", "rodent");
        buildToMisprime.put("dipOrd", "rodent");
        buildToMisprime.put("criGri", "rodent");
        buildToMisprime.put("hetGla", "rodent");
        buildToMisprime.put("cavPor", "rodent");
        buildToMisprime.put("oryCun", "rodent");
        buildToMisprime.put("speTri", "rodent");
        buildToMisprime.put("ochPri", "rodent");
        buildToMisprime.put("dm", "drosophila");
        buildToMisprime.put("droSim", "drosophila");
        buildToMisprime.put("droSec", "drosophila");
        buildToMisprime.put("droYak", "drosophila");
        buildToMisprime.put("droEre", "drosophila");
        buildToMisprime.put("droAna", "drosophila");
        buildToMisprime.put("droPer", "drosophila");
        buildToMisprime.put("droVir", "drosophila");
        buildToMisprime.put("droMoj", "drosophila");
        buildToMisprime.put("droGri", "drosophila");
        buildToMisprime.put("anoGam", "drosophila");
        buildToMisprime.put("apiMel", "drosophila");
    }
}
