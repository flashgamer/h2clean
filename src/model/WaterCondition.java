package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jonathan on 10/14/2016.
 */
public enum WaterCondition {

    WASTE("Waste"),
    TREATABLE_CLEAR("Treatable-Clear"),
    TREATABLE_MUDDY("Treatable-Muddy"),
    POTABLE("Potable");


    private String myString;
    private static final Map<String, WaterCondition> map;

    static{
        map = new HashMap<String, WaterCondition>();
        for(WaterCondition w: WaterCondition.values()) {
            map.put(w.getMyString(), w);
        }
    }

    WaterCondition(String string) {
        myString = string;
    }

    public String getMyString() {
        return myString;
    }

    public static WaterCondition findByKey(String s) {
        return map.get(s);
    }
}
