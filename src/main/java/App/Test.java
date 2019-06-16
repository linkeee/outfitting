package App;

import App.dataModel.ParamAndValueData;
import App.dataModel.TestData;
import App.dataModel.VersionData;
import App.database.ParamAndValueDatabase;
import App.database.ParameterDatabase;
import App.database.VersionDatabase;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> list = VersionDatabase.getVersionNameListOfProj(1);
        for (String s : list) {
            System.out.println(s);
        }

        System.out.println("-------------------------------------------------------");

//        List<VersionData> list1 = VersionDatabase.getVersionDataListOfProj(1);
//        for (VersionData v : list1) {
//            System.out.println(v.getVersion_name());
//        }

//        List<ParamAndValueData> pav = ParamAndValueDatabase.getParamByProjAndVersion(1, "v1");
//        for (ParamAndValueData pavd : pav) {
//            System.out.println(pavd);
//        }

        List<VersionData> list1 = VersionDatabase.getVersionDataListOfProj(1);
        for (VersionData v : list1) {
            System.out.println("---------------------------"+v.getVersion_name());
        }

//        List<String> list2 = new ArrayList<>();
//        list2.add("1"); list2.add("2"); list2.add("3");
//
//        List<String> test = new ArrayList<>();
//        test.addAll(list2);
//
//        for (String s : test) {
//            System.out.println(s);
//        }

//        List<String> param_value_list;
//
//        param_value_list = list2;
//
//        for (String s : param_value_list) {
//            System.out.println(s);
//        }

    }
}
