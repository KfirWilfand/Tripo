package model;

import java.util.Map;

public class Dictionary {
    Map<String, Map<String, Double>> perExOccur ;
    Map<String, Map<String, Double>> promoOccur ;

    public Dictionary(Map<String, Map<String, Double>> perExOccur, Map<String, Map<String, Double>> promoOccur) {
        this.perExOccur = perExOccur;
        this.promoOccur = promoOccur;
    }

    public Map<String, Map<String, Double>> getPerExOccur() {
        return perExOccur;
    }

    public Map<String, Map<String, Double>> getPromoOccur() {
        return promoOccur;
    }
}
