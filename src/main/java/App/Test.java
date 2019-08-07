package App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
    public static void main(String[] args) throws IOException {
//        String s = "";
//        try {
//            Runtime.getRuntime().exec("cmd.exe mysqld --install");
//            Runtime.getRuntime().exec("cmd.exe net start mysql");
//            Runtime.getRuntime().exec("cmd.exe mysql -uroot -psa --execute \"create database jproject1;\"");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String ss = "mysqld --initialize --console";
        String s = "mysqld --install";
        String s1 = "cmd.exe net start mysql";
        String s2 = "mysql -uroot -psa --execute \"create database jproject1;\"";
//        executeCMDCommand(ss);
//        executeCMDCommand(s);
//        String ssss = executeCMDCommand(s1);
//        System.out.println(ssss);
        Runtime.getRuntime().exec("mysqld --install");
        Runtime.getRuntime().exec("net start mysql");
        Runtime.getRuntime().exec("mysql -uroot -psa --execute \"create database jproject;\"");
//        executeCMDCommand(s2);
    }

    public static String executeCMDCommand(String cmdCommand)
    {
        StringBuilder stringBuilder = new StringBuilder();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmdCommand);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), "GBK"));
            String line = null;
            while((line=bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line+"\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String excuteBatFile(String file, boolean isCloseWindow)
    {
        String cmdCommand = null;
        if(isCloseWindow)
        {
            cmdCommand = "cmd.exe /c "+file;
        }else
        {
            cmdCommand = "cmd.exe /k "+file;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmdCommand);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), "GBK"));
            String line = null;
            while((line=bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line+"\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
