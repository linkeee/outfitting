package App.utile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtile {

    /**
     * 以一定格式输出当前日期和时间。
     * @param format 指定的格式。
     * @return 字符串格式返回。
     */
    public static String nowDateFormat(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 以"yyyy-MM-dd HH:mm:ss" 的格式输出当前日期和时间。
     * @return 字符串
     */
    public static String nowDateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return sdf.format(date);
    }
}
