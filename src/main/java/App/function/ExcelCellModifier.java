package App.function;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

import static App.function.ExcelCellReader.getCell;
import static App.function.ExcelCellReader.getCellValue;

public class ExcelCellModifier {

    /**
     * 对excel中指定的cell进行填充数据。
     *
     * @param excelPath  excel文件路径。
     * @param sheetName  excel中表单名称。
     * @param rowIndex   行索引。
     * @param colIndex   列索引。
     * @param cellDouble 需要填入的double类型的数据。
     * @throws Exception
     */
    public static void modifier(String excelPath, String sheetName, int rowIndex, int colIndex, double cellDouble) throws Exception {
        InputStream inputStream = new FileInputStream(excelPath);
        Workbook workbook;
        switch (excelPath.split("\\.")[excelPath.split("\\.").length - 1]) {
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
                throw new Exception("不能识别的文件类型: " + excelPath.split("\\.")[excelPath.split("\\.").length - 1]);
        }
        Sheet sheet = workbook.getSheet(sheetName);
        Row row = sheet.getRow(rowIndex);
        Cell cell;
        if (row.getCell(colIndex) != null) {
            cell = row.getCell(colIndex);
            cell.setCellValue(cellDouble);
        } else {
            cell = row.createCell(colIndex);
            cell.setCellValue(cellDouble);
        }

        OutputStream outputStream = new FileOutputStream(excelPath);
        outputStream.flush();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    /**
     * 修改excel中String类型的cell。
     *
     * @param excelPath  excel文件路径。
     * @param sheetName  excel中表单名称。
     * @param rowIndex   行索引。
     * @param colIndex   列索引。
     * @param cellString 需要填入的String类型数据。
     * @throws Exception
     */
    public static void modifier(String excelPath, String sheetName, int rowIndex, int colIndex, String cellString) throws Exception {
        InputStream inputStream = new FileInputStream(excelPath);
        Workbook workbook;
        switch (excelPath.split("\\.")[excelPath.split("\\.").length - 1]) {
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
                throw new Exception("不能识别的文件类型: " + excelPath.split("\\.")[excelPath.split("\\.").length - 1]);
        }
        Sheet sheet = workbook.getSheet(sheetName);
        Row row = sheet.getRow(rowIndex);
        Cell cell;
        if (row.getCell(colIndex) != null) {
            cell = row.getCell(colIndex);
            cell.setCellValue(cellString);
        } else {
            cell = row.createCell(colIndex);
            cell.setCellValue(cellString);
        }

        OutputStream outputStream = new FileOutputStream(excelPath);
        outputStream.flush();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
