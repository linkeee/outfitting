package App.utile;

import java.util.HashMap;
import java.util.Map;

public class Docker {
    static Map<String, Object> map = new HashMap<>();

    public static Object get(String key) {
        return map.get(key);
    }

    public static void put(String key, Object obj) {
        map.put(key, obj);
    }

    public static void remove(String key) {
        map.remove(key);
    }

    public static boolean containKey(String key) {
        return map.containsKey(key);
    }

    public static boolean containValue(Object obj) {
        return map.containsValue(obj);
    }
}
