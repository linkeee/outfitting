package App.utile;

import App.dataModel.ParameterData;
import App.database.ParameterDb;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.util.List;

public class JxlFromDbToExcel {
    public static void toExcel(String filePath, String sheetName) {
        try {
            WritableWorkbook wwb;

            // 创建可写入的Excel工作簿
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            //以fileName为文件名来创建一个Workbook
            wwb = Workbook.createWorkbook(file);

            // 创建工作表
            WritableSheet ws = wwb.createSheet("App.utile.Test Shee 1", 0);

            //查询数据库中所有的数据
            List<ParameterData> list = ParameterDb.getParameterList();
            //要插入到的Excel表格的行号，默认从0开始
            Label labelId = new Label(0, 0, "编号");//表示第
            Label labelName = new Label(1, 0, "姓名");
            Label labelDescription = new Label(2, 0, "描述");

            ws.addCell(labelId);
            ws.addCell(labelName);
            ws.addCell(labelDescription);
            for (int i = 0; i < list.size(); i++) {

                Label labelId_i = new Label(0, i + 1, list.get(i).getParam_id() + "");
                Label labelName_i = new Label(1, i + 1, list.get(i).getParam_name());
                Label labelDescription_i = new Label(3, i + 1, list.get(i).getParam_description() + "");
                ws.addCell(labelId_i);
                ws.addCell(labelName_i);
                ws.addCell(labelDescription_i);
            }

            //写进文档
            wwb.write();
            // 关闭Excel工作簿对象
            System.out.println("数据导出成功!");
            wwb.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}