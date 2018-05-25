package pppdatadownloader.rinex;

import java.util.HashMap;

public class ParseMap {
    private static final HashMap<String, int[]> map = new HashMap<>();

    public ParseMap() {
        map.put("TIME OF FIRST OBS", new int[]{1,2,3,4,5,6});
        map.put("TIME OF LAST OBS", new int[]{1,2,3,4,5,6});
    }

    public static int[] getParseInstruction(String key){
        return map.get(key);
    }
}


