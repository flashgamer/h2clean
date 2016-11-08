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

    /**
     * Constructor for each PurityCondition.
     * @param string String representation of each PurityCondition.
     */
    PurityCondition(String string) {
        myString = string;
    }

    /**
     * Gets the String representation of each PurityCondition.
     * @return the String representation fo each PurityCondition.
     */
    public String getMyString() {
        return myString;
    }

    /**
     * Finds the String representation of each PurityCondition
     * in the map.
     * @param s the String key to find.
     * @return A String representation of the PurityCondition.
     */
    public static PurityCondition findByKey(String s) {
        return map.get(s);
    }
}
