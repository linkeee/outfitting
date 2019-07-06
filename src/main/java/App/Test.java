package App;

import App.dataModel.ParameterData;
import javafx.scene.control.SplitPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        String temp = "78,     98";
        System.out.println(temp.trim());

        System.out.println(temp.split("[,，]", 2)[1].trim());
    }
}
