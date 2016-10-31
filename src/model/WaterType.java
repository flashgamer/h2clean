package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jonathan on 10/14/2016.
 */
public enum WaterType {
    BOTTLED("Bottled"),
    WELL("Well"),
    STREAM("Stream"),
    LAKE("Lake"),
    SPRING("Spring"),
    OTHER("Other");

    private String myString;
    private static final Map<String, WaterType> map;

    static{
        map = new HashMap<String, WaterType>();
        for(WaterType w: WaterType.values()) {
            map.put(w.getMyString(), w);
        }
    }

    WaterType(String string) {
        myString = string;
    }

    /**
     * Gets the string with the water type information
     * @return String representation of water type
     */
    public String getMyString() {
        return myString;
    }

    /**
     * Allows you to get the water type  of a report if you know the key of
     * the report
     * @param s Key for a certain report
     * @return water type of a specific report with a known key
     */
    public static WaterType findByKey(String s) {
        return map.get(s);
    }

}
