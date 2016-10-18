package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jonathan on 10/14/2016.
 */
public enum PurityCondition {
    SAFE("Safe"),
    TREATABLE("Treatable"),
    UNSAFE("Unsafe");

    private String myString;
    private static final Map<String, PurityCondition> map;

    static{
        map = new HashMap<String, PurityCondition>();
        for(PurityCondition w: PurityCondition.values()) {
            map.put(w.getMyString(), w);
        }
    }

    PurityCondition(String string) {
        myString = string;
    }

    public String getMyString() {
        return myString;
    }

    public static PurityCondition findByKey(String s) {
        return map.get(s);
    }
}
