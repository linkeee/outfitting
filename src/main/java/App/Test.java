package App;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("111", true);
        System.out.println(map.get("111").equals(1));
    }
}
