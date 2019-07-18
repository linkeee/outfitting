package App.utile;

import App.dataModel.UserData;
import App.database.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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

    public static List<String> roleList = new ArrayList<>(Arrays.asList("超级管理员", "管理员", "用户"));
    public static List<String> paramType = new ArrayList<>(Arrays.asList("已知", "待求"));
    public static String parameterTableName = "jproject.parameter";

    public static List<String> getRoleList() {
        List<UserData> list = UserDb.getAllUser();
        Set<String> set = new LinkedHashSet<>();
        for (UserData userData : list) {
            set.add(userData.getRole());
        }
        set.remove("");
        set.remove(null);
        return new ArrayList<>(set);
    }

    public static List<String> getPositionList() {
        List<UserData> list = UserDb.getAllUser();
        Set<String> set = new LinkedHashSet<>();
        for (UserData userData : list) {
            set.add(userData.getPosition());
        }
        set.remove("");
        set.remove(null);
        return new ArrayList<>(set);
    }

    /**
     * 船舶类型list
     *
     * @return
     */
    public static List<String> getShipTypeList() {
        List<String> list1 = ExperienceDb.getShipTypeList();
        List<String> list2 = SuggestionDb.getShipTypeList();
        List<String> list3 = CriterionDb.getShipTypeList();
        List<String> list4 = LayoutDb.getShipTypeList();
        Set<String> set = new LinkedHashSet<>();
        set.addAll(list1);
        set.addAll(list2);
        set.addAll(list3);
        set.addAll(list4);
        set.remove("");
        set.remove(null);
        return new ArrayList<>(set);
    }

    /**
     * 船级社List
     *
     * @return
     */
    public static List<String> getChuanjisheList() {
        List<String> list = CriterionDb.getChuanJiSheList();
        Set<String> set = new LinkedHashSet<>(list);
        set.remove("");
        set.remove(null);
        return new ArrayList<>(set);
    }

    /**
     * 船东公司
     *
     * @return
     */
    public static List<String> getShipOwnerCompany() {
        List<String> list = SuggestionDb.getChuandongCompanyList();
        Set<String> set = new LinkedHashSet<>(list);
        set.remove("");
        set.remove(null);
        return new ArrayList<>(set);
    }

    /**
     * 舾装件名称list
     *
     * @return
     */
    public static List<String> getOutfittingName() {
        List<String> list1 = ParameterDb.getOutfittingItems();
        List<String> list2 = LayoutDb.getOutfittingItems();
        Set<String> set = new LinkedHashSet<>();
        set.addAll(list1);
        set.addAll(list2);
        set.remove("");
        set.remove(null);
        return new ArrayList<>(set);
    }

    public static List<String> getStopWords() {
        List<String> stopWords = new ArrayList<>();
        File file = new File("..\\..\\..\\resources\\stopwords.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String tempLine;
            while ((tempLine = reader.readLine()) != null) {
                if (!stopWords.contains(tempLine))
                    stopWords.add(tempLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stopWords;
    }

}
