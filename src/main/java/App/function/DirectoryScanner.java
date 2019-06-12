package App.function;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryScanner {
    public static ObservableList<String> scanner(String directoryPath) {
        List<String> list = new ArrayList<>();
        File file = new File(directoryPath);
        File[] tempList = file.listFiles();
        assert tempList != null;
        for (File aTempList : tempList) {
            if (aTempList.isFile()) {
                list.add("\t" + "   " + "文件:" + "\t" + aTempList.getName());
            }
            if (aTempList.isDirectory()) {
                list.add("\t文件夹:" + "\t" + aTempList.getName());
            }
        }
        return FXCollections.observableArrayList(list);
    }
}
