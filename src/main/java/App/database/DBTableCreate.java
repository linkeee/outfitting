package App.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static App.database.DatabaseItem.connectDB;

public class DBTableCreate {

    private static String s1 = "CREATE TABLE IF NOT EXISTS `criterion` (\n" +
            "  `criId` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `criShipType` varchar(100) binary DEFAULT NULL,\n" +
            "  `criShipCompany` varchar(100) binary DEFAULT NULL,\n" +
            "  `criOutfittingRegion` varchar(100) binary DEFAULT NULL,\n" +
            "  `criName` varchar(100) binary DEFAULT NULL,\n" +
            "  `criContent` longtext binary,\n" +
            "  `criFilePath` varchar(200) binary DEFAULT NULL,\n" +
            "  `tfidf` longtext binary,\n" +
            "  PRIMARY KEY (`criId`),\n" +
            "  FULLTEXT KEY `criShipType` (`criShipType`,`criShipCompany`,`criOutfittingRegion`,`criName`,`criContent`,`criFilePath`) /*!50100 WITH PARSER `ngram` */\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;";

    private static String s2 = "CREATE TABLE IF NOT EXISTS `experience` (\n" +
            "  `expId` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `expShipType` varchar(20) binary DEFAULT NULL,\n" +
            "  `expOutfittingRegion` varchar(20) binary DEFAULT NULL,\n" +
            "  `expName` varchar(20) binary DEFAULT NULL,\n" +
            "  `expContent` longtext binary,\n" +
            "  `expFilePath` varchar(200) binary DEFAULT NULL,\n" +
            "  `tfidf` longtext binary,\n" +
            "  PRIMARY KEY (`expId`),\n" +
            "  FULLTEXT KEY `expShipType` (`expShipType`,`expOutfittingRegion`,`expName`,`expContent`,`expFilePath`) /*!50100 WITH PARSER `ngram` */\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;";

    private static String s3 = "CREATE TABLE IF NOT EXISTS `layout` (\n" +
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
            "  FULLTEXT KEY `layout_content` (`layout_content`) /*!50100 WITH PARSER `ngram` */\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;";

    private static String s4 = "CREATE TABLE IF NOT EXISTS `manufacturer` (\n" +
            "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `outfitting_name` varchar(100) binary NOT NULL,\n" +
            "  `manufacturer_name` varchar(100) binary DEFAULT NULL,\n" +
            "  `outfitting_type` varchar(100) binary DEFAULT NULL,\n" +
            "  `param_scope` varchar(100) binary DEFAULT NULL,\n" +
            "  `remark` varchar(1000) binary DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  FULLTEXT KEY `outfitting_name` (`outfitting_name`,`manufacturer_name`,`outfitting_type`,`remark`) /*!50100 WITH PARSER `ngram` */\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;";

    private static String s5 = "CREATE TABLE IF NOT EXISTS `paramandvalue` (\n" +
            "  `proj_id` int(100) NOT NULL,\n" +
            "  `version_name` varchar(100) binary NOT NULL,\n" +
            "  `param_id` int(100) NOT NULL,\n" +
            "  `outfitting_name` varchar(100) binary DEFAULT NULL,\n" +
            "  `param_name` varchar(100) binary NOT NULL,\n" +
            "  `param_type` tinyint(1) NOT NULL,\n" +
            "  `param_description` varchar(100) binary DEFAULT NULL,\n" +
            "  `param_value` varchar(100) binary DEFAULT NULL,\n" +
            "  PRIMARY KEY (`proj_id`,`version_name`,`param_id`),\n" +
            "  FULLTEXT KEY `version_name` (`version_name`,`outfitting_name`,`param_name`,`param_description`,`param_value`) /*!50100 WITH PARSER `ngram` */\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

    private static String s6 = "CREATE TABLE IF NOT EXISTS `parameter` (\n" +
            "  `param_id` int(100) NOT NULL,\n" +
            "  `outfitting_name` varchar(100) binary DEFAULT NULL,\n" +
            "  `param_name` varchar(100) binary NOT NULL,\n" +
            "  `param_type` tinyint(1) NOT NULL,\n" +
            "  `param_description` varchar(100) binary DEFAULT NULL,\n" +
            "  `param_scope` varchar(30) binary DEFAULT NULL,\n" +
            "  PRIMARY KEY (`param_id`),\n" +
            "  FULLTEXT KEY `outfitting_name` (`outfitting_name`,`param_name`,`param_description`) /*!50100 WITH PARSER `ngram` */\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

    private static String s7 = "CREATE TABLE IF NOT EXISTS `project` (\n" +
            "  `proj_id` int(100) NOT NULL AUTO_INCREMENT,\n" +
            "  `proj_name` varchar(100) binary NOT NULL,\n" +
            "  `proj_create_time` varchar(100) binary NOT NULL,\n" +
            "  `proj_modify_time` varchar(100) binary NOT NULL,\n" +
            "  `proj_creator` varchar(100) binary DEFAULT NULL,\n" +
            "  `proj_description` varchar(1000) binary DEFAULT NULL,\n" +
            "  PRIMARY KEY (`proj_id`),\n" +
            "  FULLTEXT KEY `proj_name` (`proj_name`,`proj_create_time`,`proj_modify_time`,`proj_creator`,`proj_description`) /*!50100 WITH PARSER `ngram` */\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;";

    private static String s8 = "CREATE TABLE IF NOT EXISTS `selectedtype` (\n" +
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
            "  FULLTEXT KEY `outfitting_name` (`outfitting_name`,`param_name`,`param_description`,`manufacturer_name`,`outfitting_type`,`remark`) /*!50100 WITH PARSER `ngram` */\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

    private static String s9 = "CREATE TABLE IF NOT EXISTS `suggestion` (\n" +
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
            "  FULLTEXT KEY `sugShipCompany` (`sugShipCompany`,`sugShipType`,`sugOutfittingRegion`,`sugProblemDescribe`,`sugSolutionDescribe`,`sugContent`,`sugFilePath`) /*!50100 WITH PARSER `ngram` */\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;";

    private static String s10 = "CREATE TABLE IF NOT EXISTS `user` (\n" +
            "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `user_jobNum` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,\n" +
            "  `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,\n" +
            "  `user_tel` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,\n" +
            "  `user_position` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,\n" +
            "  `user_role` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,\n" +
            "  `user_password` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE KEY `user_name_UNIQUE` (`user_name`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;\n";

//    private static String s11 = "CREATE TABLE IF NOT EXISTS `version` (\n" +
//            "              `id` int(100) NOT NULL AUTO_INCREMENT,\n" +
//            "              `proj_id` int(100) NOT NULL,\n" +
//            "              `version_name` varchar(100) binary NOT NULL,\n" +
//            "              `version_description` varchar(1000) binary DEFAULT NULL,\n" +
//            "              PRIMARY KEY (`id`)\n" +
//            "            ) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;";

    private static String s11 = "CREATE TABLE IF NOT EXISTS `version` (\n" +
            "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `proj_id` int(100) NOT NULL,\n" +
            "  `version_name` varchar(100) binary NOT NULL,\n" +
            "  `version_description` varchar(1000) binary DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;";

    private static String s12 = "INSERT INTO user (user_name, user_role, user_password) VALUES ('root', '超级管理员', 'root') ON DUPLICATE KEY UPDATE user_role='超级管理员', user_password='root';";

    public static void main(String[] args) {
        createAllTableIfNotExists();
    }

    public static void createAllTableIfNotExists() {
        executeSql(s1);
        executeSql(s2);
        executeSql(s3);
        executeSql(s4);
        executeSql(s5);
        executeSql(s6);
        executeSql(s7);
        executeSql(s8);
        executeSql(s9);
        executeSql(s10);
        executeSql(s11);
        executeSql(s12);
    }

    private static void executeSql(String sql) {
        Connection connection = connectDB();
        try {
            Statement statement = null;
            if (connection != null) {
                statement = connection.createStatement();
            }
            if (statement != null) {
//                statement.execute(sql);
                statement.executeLargeUpdate(sql);
            }
            System.out.println("Execute successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getErrorCode() + "\n" + e.getSQLState());
        }
    }

}
