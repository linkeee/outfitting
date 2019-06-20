package App;

import App.dataModel.ParamAndValueData;
import App.dataModel.TestData;
import App.dataModel.VersionData;
import App.database.ParamAndValueDatabase;
import App.database.ParameterDatabase;
import App.database.VersionDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("111", true);
        System.out.println(map.get("111").equals(1));
    }
}
