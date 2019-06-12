package App.function;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

public class ExcelFiller {

    /**
     * 将map中的数据填入模板文件中对应的位置。
     * @param excelPathWithName 模板文件的绝对路径+文件名。
     * @param map {parameter:value}
     * @throws Exception
     */
    public static void filler(String excelPathWithName, Map<String, Double> map) throws Exception {
        InputStream inputStream = new FileInputStream(excelPathWithName);
        Workbook workbook;
        switch (excelPathWithName.split("\\.")[excelPathWithName.split("\\.").length - 1]) {
            case "xlsx":
                workbook = new XSSFWorkbook(inputStream);
                break;
            case "XLSX":
                workbook = new XSSFWorkbook(inputStream);
                break;
            case "xls":
                workbook = new HSSFWorkbook(inputStream);
                break;
            case "XLS":
                workbook = new HSSFWorkbook(inputStream);
                break;
            default:
                throw new Exception("不能识别的文件类型: " + excelPathWithName.split("\\.")[excelPathWithName.split("\\.").length - 1]);
        }
        int sheetNum = workbook.getNumberOfSheets();
        int symbolColIndex = 2;
        int resultColIndex;
        for (int sheetIndex = 0; sheetIndex < sheetNum; sheetIndex++) {
            if ("输入参数".equals(workbook.getSheetName(sheetIndex))) {
                resultColIndex = 4;
            } else {
                resultColIndex = 5;
            }
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            int rowNums = sheet.getLastRowNum();
            for (int rowIndex = 0; rowIndex <= rowNums; rowIndex++) {
                try {
                    ExcelCellReader.getCellValue(ExcelCellReader.getCell(excelPathWithName, workbook.getSheetName(sheetIndex), rowIndex, symbolColIndex));
                } catch (Exception e) {
                    continue;
                }
                Object object = ExcelCellReader.getCellValue(ExcelCellReader.getCell(excelPathWithName, workbook.getSheetName(sheetIndex), rowIndex, symbolColIndex));
                System.out.println(object + " has been caught!");
                if (map.containsKey(object)) {
                    ExcelCellModifier.modifier(excelPathWithName, workbook.getSheetName(sheetIndex), rowIndex, resultColIndex, map.get(object));
                    System.out.println(object + " --> " + map.get(object));
                }
            }
        }
        workbook.close();
        inputStream.close();
    }
}
