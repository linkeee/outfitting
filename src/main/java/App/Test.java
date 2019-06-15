package App;

import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("abc");
        set.add("bca");
        set.add("abc");
        System.out.println(set.size());
    }
}
