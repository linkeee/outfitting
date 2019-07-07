package App.utile;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constant {
    /**
     * TableView可双击修改列为空时的提示信息。
     */
    public static String doubleClickCellTip = "双击添加";

    /**
     * 数据库中版本表的表名。
     */
    public static String versionTableName = "jproject.version";

    /**
     * 数据库中参数参数值表名。
     */
    public static String paramValueTableName = "jproject.paramandvalue";

    /**
     * 舾装件生产商表的表名
     */
    public static String manufacturerTableName = "jproject.manufacturer";

    /**
     * 已选择的设备型号清单表的表名
     */
    public static String selectedTypeTableName = "jproject.selectedtype";

    /**
     * 船舶类型List
     */
    public static List<String> shipTypeList = new ArrayList<>(Arrays.asList(null, "油轮", "散货船", "集装箱船", "平台", "豪华游轮"));

    /**
     * 船级社List
     */
    public static List<String> chuanjisheList = new ArrayList<>(Arrays.asList(null, "CCS", "BV", "ABS", "DNV-GL", "LR"));

    public static List<String> positionList = new ArrayList<>(Arrays.asList(null, "部长", "主任", "主管", "设计人员"));
    public static List<String> roleList = new ArrayList<>(Arrays.asList(null, "超级管理员", "管理员", "用户"));

}
