package App.function;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelCellReader {
    /**
     * 将Cell中的数据以字符串格式返回。
     *
     * @param excelCell excel中的Cell。
     * @return 返回字符串。
     */
    public static String getCellValueToString(Cell excelCell) {
        String cellValue = "";
        if (excelCell == null) {
            return cellValue;
        }
        switch (excelCell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: // 数字
                //short s = cell.getCellStyle().getDataFormat();
                if (HSSFDateUtil.isCellDateFormatted(excelCell)) {// 处理日期格式、时间格式
                    SimpleDateFormat sdf = null;
                    // 验证short值
                    if (excelCell.getCellStyle().getDataFormat() == 14) {
                        sdf = new SimpleDateFormat("yyyy/MM/dd");
                    } else if (excelCell.getCellStyle().getDataFormat() == 21) {
                        sdf = new SimpleDateFormat("HH:mm:ss");
                    } else if (excelCell.getCellStyle().getDataFormat() == 22) {
                        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    } else {
                        throw new RuntimeException("日期格式错误!!!");
                    }
                    Date date = excelCell.getDateCellValue();
                    cellValue = sdf.format(date);
                } else if (excelCell.getCellStyle().getDataFormat() == 0) {//处理数值格式
                    excelCell.setCellType(Cell.CELL_TYPE_STRING);
                    cellValue = String.valueOf(excelCell.getRichStringCellValue().getString());
                }
                break;
            case Cell.CELL_TYPE_STRING: // 字符串
                cellValue = String.valueOf(excelCell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                cellValue = String.valueOf(excelCell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: // 公式
                cellValue = String.valueOf(excelCell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: // 空值
                cellValue = null;
                break;
            case Cell.CELL_TYPE_ERROR: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * 将cell中的数据以Object obj的格式返回。
     * 返回的obj为数据的原格式。即原来cell中若为String，则返回的obj的格式也为String。
     *
     * @param excelCell excel中的cell。
     * @return Object。
     */
    public static Object getCellValue(Cell excelCell) {
        Object o = null;

        try { //字符串
            o = excelCell.getStringCellValue();
            return o;
        } catch (Exception e) {
            try { //数字
                o = excelCell.getNumericCellValue();
                return o;
            } catch (Exception e1) {
                try { // boolean
                    o = excelCell.getBooleanCellValue();
                    return o;
                } catch (Exception e2) {
                    try { //公式
                        o = excelCell.getCellFormula();
                        return o;
                    } catch (Exception e3) {
                        try { //富文本
                            o = excelCell.getRichStringCellValue().toString();
                            return o;
                        } catch (Exception e4) { //错误值
                            o = excelCell.getErrorCellValue();
                            return "Bad value: " + o;
                        }
                    }
                }
            }
        }

//        int cellType = excelCell.getCellType();
//        switch (cellType) {
//            case Cell.CELL_TYPE_BLANK:
//                o = "";
//                break;
//            case Cell.CELL_TYPE_BOOLEAN:
//                o = excelCell.getBooleanCellValue();
//                break;
//            case Cell.CELL_TYPE_ERROR:
//                o = "Bad value!";
//                break;
//            case Cell.CELL_TYPE_NUMERIC:
//                o = excelCell.getNumericCellValue();
//                break;
//            case Cell.CELL_TYPE_FORMULA:
//                try {
//                    o = excelCell.getCellFormula();
//                } catch (IllegalStateException e) {
//                    try {
//                        o = excelCell.getRichStringCellValue().toString();
//                    } catch (IllegalStateException e1) {
//                        o = excelCell.getErrorCellValue();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//            default:
//                o = excelCell.getRichStringCellValue().getString();
//    }
//        return o;
    }

    /**
     * 输入参数后返回一个excel中Cell类型的数据。
     *
     * @param excelPath excel文件路径。
     * @param sheetName excel中表单名称。
     * @param rowIndex  行索引。
     * @param colIndex  列索引。
     * @return 返回Cell类型。
     * @throws Exception 抛出异常。
     */
    public static Cell getCell(String excelPath, String sheetName, int rowIndex, int colIndex) throws Exception {
        InputStream inputStream;
        Workbook workbook;
        switch (excelPath.split("\\.")[excelPath.split("\\.").length - 1]) {
            case "xlsx":
                inputStream = new FileInputStream(excelPath);
                workbook = new XSSFWorkbook(inputStream);
                break;
            case "XLSX":
                inputStream = new FileInputStream(excelPath);
                workbook = new XSSFWorkbook(inputStream);
                break;
            case "xls":
                inputStream = new FileInputStream(excelPath);
                workbook = new HSSFWorkbook(inputStream);
                break;
            case "XLS":
                inputStream = new FileInputStream(excelPath);
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
        } else {
            cell = row.createCell(colIndex);
        }

        OutputStream outputStream = new FileOutputStream(excelPath);
        outputStream.flush();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

        return cell;
    }
}
