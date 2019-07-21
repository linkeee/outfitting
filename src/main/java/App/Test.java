package App;

import App.dataModel.ParamAndValueData;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Test {
    public static void main(String[] args) throws IOException {
        List<ParamAndValueData> list = new ArrayList<>();
        list.add(new ParamAndValueData());
        list.add(new ParamAndValueData());
        for (ParamAndValueData p : list) {
//            p.setParam_name("aaaaaaaaaaaaaaaaaaaaaaa");
        }
        for (ParamAndValueData p1 : list) {
            System.out.println(p1.getParam_name());
        }
    }
}
