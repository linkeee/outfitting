package App.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static App.database.DatabaseItem.connectDB;

public class DBTableCreate {

    private static String sql1 = "CREATE TABLE IF NOT EXISTS criterion(" +
            "criId varchar(10) not null primary key," +
            "criShipType varchar(20)," +
            "criShipCompany varchar(20)," +
            "criOutfittingRegion varchar(20)," +
            "criName varchar(20)," +
            "criContent longtext," +
            "criFilePath varchar(100)," +
            "fulltext key (criId, criShipType, criShipCompany, criOutfittingRegion, criName, criContent, criFilePath) with parser ngram" +
            ")ENGINE=InnoDB default charset=utf8;";

    private static String sql2 = "CREATE TABLE IF NOT EXISTS experience(" +
            "expId varchar(10) not null primary key," +
            "expShipType varchar(20)," +
            "expOutfittingRegion varchar(20)," +
            "expName varchar(20)," +
            "expContent longtext," +
            "expFilePath varchar(200)," +
            "fulltext key (expId, expShipType, expOutfittingRegion, expName, expContent, expFilePath) with parser ngram" +
            ")ENGINE=InnoDB default charset=utf8;";

    private static String sql3 = "CREATE TABLE IF NOT EXISTS layout(" +
            "layoutId varchar(10) not null primary key," +
            "layoutName varchar(30)," +
            "layoutType varchar(20)," +
            "layoutShipType varchar(20)," +
            "layoutShipNum varchar(10)," +
            "layoutContent varchar(20000)," +
            "layoutShipLength varchar(10)," +
            "layoutShipWidth varchar(10)," +
            "layoutShipDepth varchar(10)," +
            "layoutShipDraught varchar(10)," +
            "layoutShipCoefficient varchar(10)," +
            "layoutShipWeight varchar(10)," +
            "fulltext key (layoutId, layoutName, layoutType, layoutShipType, layoutShipNum, layoutContent, layoutShipLength, layoutShipWidth, layoutShipDepth, layoutShipDraught, layoutShipCoefficient, layoutShipWeight) with parser ngram" +
            ")ENGINE=InnoDB default charset=utf8;";

    private static String sql4 = "CREATE TABLE IF NOT EXISTS suggestion(" +
            "sugId varchar(10) not null primary key," +
            "sugShipCompany varchar(20)," +
            "sugShipType varchar(20)," +
            "sugOutfittingRegion varchar(50)," +
            "sugProblemDescribe longtext," +
            "sugSolutionDescribe longtext," +
            "sugContent longtext," +
            "sugFilePath varchar(200)," +
            "fulltext key (sugId, sugShipCompany, sugShipType, sugOutfittingRegion, sugProblemDescribe, sugSolutionDescribe, sugContent, sugFilePath) with parser ngram" +
            ")ENGINE=InnoDB default charset=utf8;";

    private static String sql5 = "CREATE TABLE IF NOT EXISTS taboo(" +
            "tabooId varchar(10) not null primary key," +
            "shipType varchar(20)," +
            "outfittingType varchar(20)," +
            "tabooKeyword1 varchar(50)," +
            "tabooKeyword2 varchar(50)," +
            "tabooKeyword3 varchar(50)," +
            "tabooLevel varchar(30)," +
            "tabooName varchar(30)," +
            "tabooContent varchar(20000)," +
            "fulltext key (tabooId, shipType, outfittingType, tabooKeyword1, tabooKeyword2, tabooKeyword3, tabooLevel, tabooName, tabooContent) with parser ngram" +
            ")ENGINE=InnoDB default charset=utf8;";

    private static String sql6 = "CREATE TABLE IF NOT EXISTS user(" +
            "jobNum varchar(500) NOT NULL PRIMARY KEY," +
            "name varchar(500)," +
            "tel varchar(500)," +
            "position varchar(500)," +
            "role varchar(500)," +
            "FULLTEXT KEY (name, tel, jobNum, position, role) WITH PARSER ngram" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

//    private static String sql7 = "CREATE TABLE IF NOT EXISTS ruleItem(" +
//            "ruleId varchar(10) not null primary key," +
//            "itemName varchar(30)," +
//            "itemHyperLink varchar(100)," +
//            "outfittingType varchar(50)," +
//            "anchorType varchar(50)," +
//            "manufacturer varchar(50)," +
//            "gongyouliang varchar(50)," +
//            "zhongyouOutputPressure varchar(50)," +
//            "zhongyouOutputTemperature varchar(50)," +
//            "zhongyouOutputViscosity varchar(50)," +
//            "fulltext key (ruleId, itemName, itemHyperLink, outfittingType, anchorType, manufacturer, gongyouliang, zhongyouOutputPressure, zhongyouOutputTemperature, zhongyouOutputViscosity) with parser ngram" +
//            ")ENGINE=InnoDB default charset=utf8;";

    /**
     * 参数库
     */
    private static String sql8 = "CREATE TABLE IF NOT EXISTS parameter(" +
            "param_id int(100) not null primary key auto_increment," +
            "outfitting_name varchar(100)," +
            "param_name varchar(100) not null," +
            "param_type tinyint(1) not null," +
            "param_description varchar(100)," +
            "fulltext key (outfitting_name, param_name, param_description) with parser ngram" +
            ")ENGINE=InnoDB default charset=utf8;";

//    /**
//     * 公式库
//     */
//    private static String sql9 = "CREATE TABLE IF NOT EXISTS equation(" +
//            "equation_id int(100) not null primary key auto_increment," +
//            "equation_left int(100) not null," +
//            "equation_right varchar(200) not null," +
//            "equation_description varchar(100)," +
//            "rule_param varchar(100)," +
//            "rule_param_description varchar(100)," +
//            "rule_left int(100)," +
//            "rule_right int(100)," +
//            "rule_other varchar(100)," +
//            "fulltext key (equation_right, equation_description, rule_param, rule_param_description, rule_other) with parser ngram" +
//            ")ENGINE=InnoDB default charset=utf8;";

    /**
     * 项目库
     * 项目自增ID、项目名称（命名规则）、创建时间&修改时间（系统自动确定）、负责人、备注。
     */
    private static String sql10 = "CREATE TABLE IF NOT EXISTS project(" +
            "proj_id int(100) not null primary key auto_increment," +
            "proj_name varchar(100) not null," +
            "proj_create_time varchar(100) not null," +
            "proj_modify_time varchar(100) not null," +
            "proj_creator varchar(100)," +
            "proj_description varchar(1000)," +
            "fulltext key (proj_name, proj_create_time, proj_modify_time, proj_creator, proj_description) with parser ngram" +
            ")ENGINE=InnoDB default charset=utf8;";

    /**
     * 项目参数库：
     *
     * 项目参数ID 自增
     * 项目ID（定位）
     * 版本号（定位，用V+项目修改时间表示）
     * 舾装件名称
     * 参数名称
     * 参数类型
     * 参数描述
     * 参数值
     * 备注
     */
    private static String sql11 = "CREATE TABLE IF NOT EXISTS projParamAndValue(" +
            "id int(100) not null primary key auto_increment," +
            "proj_id int(100) not null," +
            "version varchar(100) not null," +
            "param_id int(100) not null," +
            "outfitting_name varchar(100)," +
            "param_name varchar(100) not null," +
            "param_type tinyint(1) not null," +
            "param_description varchar(100)," +
            "param_value varchar(100)," +
            "remark varchar(1000)," +
            "fulltext key (version, outfitting_name, param_name, param_description, param_value, remark) with parser ngram" +
            ")ENGINE=InnoDB default charset=utf8;";

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add(sql1);
        list.add(sql2);
        list.add(sql3);
        list.add(sql4);
        list.add(sql5);
        list.add(sql6);
//        list.add(sql7);
        list.add(sql8);
//        list.add(sql9);
        list.add(sql10);
        list.add(sql11);

        for (String sql : list) {
            executeSql(sql);
        }


//        String keyword1 = "平台";
//        String keyword2 = "";
//        String keyword3 = null;
//        String keyword4 = "";
//        String keyword5 = "";
//        try {
//            query(keyword1, keyword2, keyword3, keyword4, keyword5);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public static void query(String content, String shipType, String ccs, String name, String region) throws SQLException {
        Connection connection = connectDB();
        PreparedStatement preparedStatement;

        if (ccs == null) {
            ccs = "";
        }

        String sql = "SELECT * FROM jproject.criterion where criterionContent like ? and shipType like ? and criterionClassificationSociety like ? and criterionName like ? and outfittingType like ?";
//        String sql1 = "select * from criterion where match (criterionId, shipType, criterionClassificationSociety, outfittingType, criterionName, criterionContent, criterionUrl) against (?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + content + "%");
        preparedStatement.setString(2, "%" + shipType + "%");
        preparedStatement.setString(3, "%" + ccs + "%");
        preparedStatement.setString(4, "%" + name + "%");
        preparedStatement.setString(5, "%" + region + "%");

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            print("ID: " + resultSet.getString(1) + '\t' + "Name: " + resultSet.getString(5));
        }
    }

    private static void print(String str) {
        System.out.println(str);
    }

    private static void executeSql(String sql) {
        Connection connection = connectDB();
        try {
            Statement statement = connection.createStatement();
            statement.executeLargeUpdate(sql);
            System.out.println("Execute successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getErrorCode() + "\n" + e.getSQLState());
        }
    }

}
