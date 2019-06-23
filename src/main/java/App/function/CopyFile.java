package App.function;

import App.utile.Docker;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CopyFile {

    /**
     * 将指定的文件复制到当前文件夹，并重新命名。
     * @param fromPathWithName 目标文件的绝对地址+文件名。
     * @throws IOException
     * @return 生成的新文件的名称。
     */
    public static String copyFile(String fromPathWithName) throws IOException {
        File templateFile = new File(fromPathWithName);//被复制的文件

        File templateFileFolder = new File(templateFile.getParent());//目标文件夹
        if (!templateFileFolder.exists()) templateFileFolder.mkdirs();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String time = sdf.format(date);

        String newFilePath = templateFileFolder + "设备估算书" + time + ".xlsx";
        File newFile = new File(newFilePath);
        if (!newFile.exists()) newFile.createNewFile();

        copyFileContent(templateFile, newFile);
        String newName = "设备估算书" + time + ".xlsx";
        Docker.put("newTemplateFileName", newName);
        return newName;
    }

    private static void copyFileContent(File fromFile, File toFile) throws IOException {
        FileInputStream inputStream = new FileInputStream(fromFile);
        FileOutputStream outputStream = new FileOutputStream(toFile);
        byte[] b = new byte[1024];
        int n = 0;
        while ((n = inputStream.read(b)) != -1) {
            outputStream.write(b, 0, n);
        }
        inputStream.close();
        outputStream.close();
    }
}
