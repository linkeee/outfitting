package App.database;

import java.sql.*;

public class DatabaseItem {

    /**
     * 初始化数据库
     *
     * @return
     */
    public static Connection connectDB() {
        final Connection conn;
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
//            String driver = "org.sqlite.JDBC";
//            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);

//            String url = "jdbc:mysql://223.104.213.207:3306/jproject?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT&useSSL=false";

            String url = "jdbc:mysql://localhost:3306/jproject?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT&useSSL=false";
//            String url = "jdbc:sqlite:jproject.db";
//            String url = "jdbc:mysql://localhost:3306/jproject?characterEncoding=UTF-8";
            String user = "root";
            String password = "sa";
            conn = DriverManager.getConnection(url, user, password);

            return conn;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭数据库
     *
     * @param ps
     * @param sm
     * @param conn
     */
    static void closeDatabase(PreparedStatement ps, Statement sm, Connection conn) {
        if (ps != null)
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        if (conn != null)
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        if (sm != null)
            try {
                sm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
