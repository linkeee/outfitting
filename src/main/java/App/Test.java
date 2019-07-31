package App;

import java.math.BigDecimal;

public class Test {
    public static void main(String[] args) {
        double d = 11.636324232;
        BigDecimal b = new BigDecimal(d);
        d = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(d);
    }
}
