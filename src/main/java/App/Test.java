package App;

import App.dataModel.ParamAndValueData;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Test {
    public static void main(String[] args) throws IOException {
        List<String> list = new ArrayList<>();
        list.add("ijfsl");
        list.add("lfis");
        list.add("fnkil");
        list.replaceAll(String::toUpperCase);
        System.out.println(list.toString());
    }
}
