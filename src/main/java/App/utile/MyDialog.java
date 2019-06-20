package App.utile;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;

import java.util.Optional;

/**
 * 弹出各种对话框。
 */
public class MyDialog {

    /**
     * 仅用于提示一些信息，无其他作用。
     *
     * headerText可以为空，只提示信息。
     *
     * @param headerText
     * @param contentText
     */
    public static void information(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示/information");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    /**
     * 仅用于警告一些信息，无其他作用。
     *
     * headerText可以为空，只警告信息。
     *
     * @param headerText
     * @param contentText
     */
    public static void warning(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("警告/warning");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    /**
     * 仅用于提示出现错误，无其他作用。
     *
     * headerText可以为空，只警告信息。
     *
     * @param headerText
     * @param contentText
     */
    public static void error(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("错误/error");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    /**
     * 用于确认操作，用户确认后操作才生效。
     *
     * headerText可以为空，只警告信息。
     *
     * @param headerText
     * @param contentText
     */
    public static Optional<ButtonType> confirmation(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认/confirmation");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.initModality(Modality.APPLICATION_MODAL);
        return alert.showAndWait();
    }

    public static Optional<String> inputText(String headerText) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("输入/input");
        dialog.setHeaderText(headerText);

        ButtonType confirm = new ButtonType("确认", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("取消", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(confirm, cancel);

        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setPadding(new Insets(0));
        dialog.getDialogPane().setPadding(new Insets(10));
        dialog.getDialogPane().setContent(textArea);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirm) {
                return textArea.getText();
            }
            return null;
        });

        return dialog.showAndWait();
    }

}
