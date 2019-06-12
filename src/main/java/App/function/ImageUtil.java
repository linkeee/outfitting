package App.function;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageUtil {

    /**
     * 根据传入图片地址读取图片，并返回该图片的字节流形式。
     *
     * @param imagePath
     * @return
     */
    public static FileInputStream getImageByte(String imagePath) {
        FileInputStream imageByte = null;
        try {
            imageByte = new FileInputStream(new File(imagePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return imageByte;
    }

//    public static void read
}
