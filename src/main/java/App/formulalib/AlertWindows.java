package App.formulalib;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

/**
 * 自定义的错误信息弹窗
 */
class AlertWindows {
    /**
     * 警告公式中含有未定义的变量名
     *
     * @param undefinedVariableList 未定义的变量的列表
     */
    AlertWindows(List<String> undefinedVariableList) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("未定义的变量名");
        alert.setHeaderText("公式的计算代数式中含有未定义的变量名！请添加变量后重试");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.print(undefinedVariableList);
        String printText = sw.toString();

        Label label = new Label("未定义的变量:");
        TextArea textArea = new TextArea(printText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane expContent = new GridPane();
        expContent.setPrefHeight(100);
        expContent.setPrefWidth(400);
        expContent.setVgap(20);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);
        alert.getDialogPane().setContent(expContent);
        alert.showAndWait();
    }

    /**
     * @param e               异常对象
     * @param errorType       要提示的错误类型
     * @param tipsInformation 解决错误的提示
     */
    AlertWindows(@NotNull Exception e, String errorType, String tipsInformation) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("错误信息");
        alert.setHeaderText(errorType);
        alert.setContentText(tipsInformation);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("异常堆栈信息:");
        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);
        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

    /**
     * 创建一个不带HeaderText部分的弹窗
     *
     * @param title   弹窗标题
     * @param content 弹窗展示内容
     */
    AlertWindows(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    AlertWindows(LogicalException e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(e.getTitle());
        alert.setHeaderText(null);
        alert.setContentText(e.getContent());
        alert.showAndWait();
    }

    /**
     * 创建一个带有HeaderText的弹窗
     *
     * @param title   弹窗标题
     * @param header  弹窗内容标题
     * @param content 弹窗展示内容
     */
    AlertWindows(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    static boolean newConfirmWindows(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
