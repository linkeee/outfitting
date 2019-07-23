package App.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static App.database.DatabaseItem.connectDB;

public class DBTableCreate {

    private static String allCreateStatement = "CREATE TABLE `criterion` (\n" +
            "  `criId` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `criShipType` varchar(100) binary DEFAULT NULL,\n" +
            "  `criShipCompany` varchar(100) binary DEFAULT NULL,\n" +
            "  `criOutfittingRegion` varchar(100) binary DEFAULT NULL,\n" +
            "  `criName` varchar(100) binary DEFAULT NULL,\n" +
            "  `criContent` longtext binary,\n" +
            "  `criFilePath` varchar(200) binary DEFAULT NULL,\n" +
            "  `tfidf` longtext binary,\n" +
            "  PRIMARY KEY (`criId`),\n" +
            "  FULLTEXT KEY `criShipType` (`criShipType`,`criShipCompany`,`criOutfittingRegion`,`criName`,`criContent`,`criFilePath`) /*!50100 WITH PARSER `ngram` */ \n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;\n" +
            "\n" +
            "CREATE TABLE `experience` (\n" +
            "  `expId` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `expShipType` varchar(20) binary DEFAULT NULL,\n" +
            "  `expOutfittingRegion` varchar(20) binary DEFAULT NULL,\n" +
            "  `expName` varchar(20) binary DEFAULT NULL,\n" +
            "  `expContent` longtext binary,\n" +
            "  `expFilePath` varchar(200) binary DEFAULT NULL,\n" +
            "  `tfidf` longtext binary,\n" +
            "  PRIMARY KEY (`expId`),\n" +
            "  FULLTEXT KEY `expShipType` (`expShipType`,`expOutfittingRegion`,`expName`,`expContent`,`expFilePath`) /*!50100 WITH PARSER `ngram` */ \n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;\n" +
            "\n" +
            "CREATE TABLE `layout` (\n" +
            "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `outfitting_name` varchar(50) binary DEFAULT NULL,\n" +
            "  `ship_type` varchar(50) binary DEFAULT NULL,\n" +
            "  `ship_num` varchar(50) binary DEFAULT NULL,\n" +
            "  `ship_type_coefficient` varchar(50) binary DEFAULT NULL,\n" +
            "  `ship_load` varchar(50) binary DEFAULT NULL,\n" +
            "  `ship_length` varchar(50) binary DEFAULT NULL,\n" +
            "  `ship_width` varchar(50) binary DEFAULT NULL,\n" +
            "  `ship_depth` varchar(50) binary DEFAULT NULL,\n" +
            "  `ship_draught` varchar(50) binary DEFAULT NULL,\n" +
            "  `layout_content` longtext binary,\n" +
            "  `file_path` varchar(200) binary DEFAULT NULL,\n" +
            "  `tfidf` longtext binary,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  FULLTEXT KEY `layout_content` (`layout_content`) /*!50100 WITH PARSER `ngram` */ \n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;\n" +
            "\n" +
            "CREATE TABLE `manufacturer` (\n" +
            "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `outfitting_name` varchar(100) binary NOT NULL,\n" +
            "  `manufacturer_name` varchar(100) binary DEFAULT NULL,\n" +
            "  `outfitting_type` varchar(100) binary DEFAULT NULL,\n" +
            "  `param_scope` varchar(100) binary DEFAULT NULL,\n" +
            "  `remark` varchar(1000) binary DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  FULLTEXT KEY `outfitting_name` (`outfitting_name`,`manufacturer_name`,`outfitting_type`,`remark`) /*!50100 WITH PARSER `ngram` */ \n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;\n" +
            "\n" +
            "CREATE TABLE `paramandvalue` (\n" +
            "  `proj_id` int(100) NOT NULL,\n" +
            "  `version_name` varchar(100) binary NOT NULL,\n" +
            "  `param_id` int(100) NOT NULL,\n" +
            "  `outfitting_name` varchar(100) binary DEFAULT NULL,\n" +
            "  `param_name` varchar(100) binary NOT NULL,\n" +
            "  `param_type` tinyint(1) NOT NULL,\n" +
            "  `param_description` varchar(100) binary DEFAULT NULL,\n" +
            "  `param_value` varchar(100) binary DEFAULT NULL,\n" +
            "  PRIMARY KEY (`proj_id`,`version_name`,`param_id`),\n" +
            "  FULLTEXT KEY `version_name` (`version_name`,`outfitting_name`,`param_name`,`param_description`,`param_value`) /*!50100 WITH PARSER `ngram` */ \n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n" +
            "\n" +
            "CREATE TABLE `parameter` (\n" +
            "  `param_id` int(100) NOT NULL,\n" +
            "  `outfitting_name` varchar(100) binary DEFAULT NULL,\n" +
            "  `param_name` varchar(100) binary NOT NULL,\n" +
            "  `param_type` tinyint(1) NOT NULL,\n" +
            "  `param_description` varchar(100) binary DEFAULT NULL,\n" +
            "  `param_scope` varchar(30) binary DEFAULT NULL,\n" +
            "  PRIMARY KEY (`param_id`),\n" +
            "  FULLTEXT KEY `outfitting_name` (`outfitting_name`,`param_name`,`param_description`) /*!50100 WITH PARSER `ngram` */ \n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n" +
            "\n" +
            "CREATE TABLE `project` (\n" +
            "  `proj_id` int(100) NOT NULL AUTO_INCREMENT,\n" +
            "  `proj_name` varchar(100) binary NOT NULL,\n" +
            "  `proj_create_time` varchar(100) binary NOT NULL,\n" +
            "  `proj_modify_time` varchar(100) binary NOT NULL,\n" +
            "  `proj_creator` varchar(100) binary DEFAULT NULL,\n" +
            "  `proj_description` varchar(1000) binary DEFAULT NULL,\n" +
            "  PRIMARY KEY (`proj_id`),\n" +
            "  FULLTEXT KEY `proj_name` (`proj_name`,`proj_create_time`,`proj_modify_time`,`proj_creator`,`proj_description`) /*!50100 WITH PARSER `ngram` */ \n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;\n" +
            "\n" +
            "CREATE TABLE `selectedtype` (\n" +
            "  `proj_id` int(100) NOT NULL,\n" +
            "  `version_name` varchar(100) binary NOT NULL,\n" +
            "  `outfitting_name` varchar(100) binary NOT NULL,\n" +
            "  `param_name` varchar(100) binary NOT NULL,\n" +
            "  `param_description` varchar(100) binary DEFAULT NULL,\n" +
            "  `param_value` varchar(100) binary DEFAULT NULL,\n" +
            "  `manufacturer_name` varchar(100) binary DEFAULT NULL,\n" +
            "  `outfitting_type` varchar(100) binary DEFAULT NULL,\n" +
            "  `param_scope` varchar(30) binary DEFAULT NULL,\n" +
            "  `remark` varchar(1000) binary DEFAULT NULL,\n" +
            "  PRIMARY KEY (`proj_id`,`version_name`,`outfitting_name`),\n" +
            "  FULLTEXT KEY `outfitting_name` (`outfitting_name`,`param_name`,`param_description`,`manufacturer_name`,`outfitting_type`,`remark`) /*!50100 WITH PARSER `ngram` */ \n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n" +
            "\n" +
            "CREATE TABLE `suggestion` (\n" +
            "  `sugId` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `sugShipCompany` varchar(20) binary DEFAULT NULL,\n" +
            "  `sugShipType` varchar(20) binary DEFAULT NULL,\n" +
            "  `sugOutfittingRegion` varchar(50) binary DEFAULT NULL,\n" +
            "  `sugProblemDescribe` longtext binary,\n" +
            "  `sugSolutionDescribe` longtext binary,\n" +
            "  `sugContent` longtext binary,\n" +
            "  `sugFilePath` varchar(200) binary DEFAULT NULL,\n" +
            "  `tfidf` longtext binary,\n" +
            "  PRIMARY KEY (`sugId`),\n" +
            "  FULLTEXT KEY `sugShipCompany` (`sugShipCompany`,`sugShipType`,`sugOutfittingRegion`,`sugProblemDescribe`,`sugSolutionDescribe`,`sugContent`,`sugFilePath`) /*!50100 WITH PARSER `ngram` */ \n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;\n" +
            "\n" +
            "CREATE TABLE `user` (\n" +
            "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `user_jobNum` varchar(500) binary DEFAULT NULL,\n" +
            "  `user_name` varchar(500) binary DEFAULT NULL,\n" +
            "  `user_tel` varchar(500) binary DEFAULT NULL,\n" +
            "  `user_position` varchar(500) binary DEFAULT NULL,\n" +
            "  `user_role` varchar(500) binary DEFAULT NULL,\n" +
            "  `user_password` varchar(45) binary NOT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;\n" +
            "\n" +
            "CREATE TABLE `version` (\n" +
            "  `id` int(100) NOT NULL AUTO_INCREMENT,\n" +
            "  `proj_id` int(100) NOT NULL,\n" +
            "  `version_name` varchar(100) binary NOT NULL,\n" +
            "  `version_description` varchar(1000) binary DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;\n";

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
            "param_type int not null," +
            "param_description varchar(100)," +
            "param_scope varchar(30)," +
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
            "proj_id int not null primary key auto_increment," +
            "proj_name varchar(100) not null," +
            "proj_create_time varchar(100) not null," +
            "proj_modify_time varchar(100) not null," +
            "proj_creator varchar(100)," +
            "proj_description varchar(1000)," +
            "fulltext key (proj_name, proj_create_time, proj_modify_time, proj_creator, proj_description) with parser ngram" +
            ")ENGINE=InnoDB default charset=utf8;";

    /**
     * 项目参数库：
     * <p>
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
    private static String sql11 = "CREATE TABLE IF NOT EXISTS paramandvalue(" +
            "proj_id int(100) not null," +
            "version_name varchar(100) not null," +
            "param_id int(100) not null," +
            "outfitting_name varchar(100)," +
            "param_name varchar(100) not null," +
            "param_type int not null," +
            "param_description varchar(100)," +
            "param_value varchar(100)," +
            "primary key(proj_id, version_name, param_id)," +
            "fulltext key (version_name, outfitting_name, param_name, param_description, param_value) with parser ngram" +
            ")ENGINE=InnoDB default charset=utf8;";

    /**
     * 版本库
     */
    private static String sql12 = "create table if not exists version(" +
            "id int not null primary key auto_increment," +
            "proj_id int(100) not null," +
            "version_name varchar(100) not null," +
            "version_description varchar(1000)" +
            ")ENGINE=InnoDB default charset=utf8;";

    /**
     * 设备供应商库
     *
     */
    private static String sql13 = "create table if not exists manufacturer(" +
            "id int not null primary key auto_increment," +
            "outfitting_name varchar(100) not null," +
            "manufacturer_name varchar(100)," +
            "outfitting_type varchar(100)," +
            "param_scope varchar(30)," +
            "remark varchar(1000)," +
            "fulltext key (outfitting_name, manufacturer_name, outfitting_type, remark) with parser ngram" +
            ")ENGINE=InnoDB default charset=utf8;";

    private static String sql14 = "CREATE TABLE IF NOT EXISTS selectedType(" +
            "proj_id int(100) not null," +
            "version_name varchar(100) not null," +
            "outfitting_name varchar(100)," +
            "param_name varchar(100) not null," +
            "param_description varchar(100)," +
            "param_value varchar(100)," +
            "manufacturer_name varchar(100)," +
            "outfitting_type varchar(100)," +
            "param_scope varchar(30)," +
            "remark varchar(1000)," +
            "primary key(proj_id, version_name, outfitting_name)," +
            "fulltext key (outfitting_name, param_name, param_description, manufacturer_name, outfitting_type, remark) with parser ngram" +
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
        list.add(sql12);
        list.add(sql13);
        list.add(sql14);

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
