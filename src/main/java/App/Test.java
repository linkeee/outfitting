package App;

import App.dataModel.ParameterData;
import javafx.scene.control.SplitPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("fseif", null);
        map.put("jli", null);

        System.out.println(map.get("fseif"));
        System.out.println(map.get("jli"));
    }
}
