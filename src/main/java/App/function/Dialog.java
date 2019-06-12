package App.function;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.options.Option;

import java.util.Optional;

/**
 * 弹出各种对话框。
 */
public class Dialog {

    /**
     * 仅用于提示一些信息，无其他作用。
     *
     * headerText可以为空，只提示信息。
     *
     * @param title
     * @param headerText
     * @param contentText
     */
    public static void information(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
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
     * @param title
     * @param headerText
     * @param contentText
     */
    public static void warning(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
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
     * @param title
     * @param headerText
     * @param contentText
     */
    public static void error(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
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
     * @param title
     * @param headerText
     * @param contentText
     */
    public static Optional<ButtonType> confirmation(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.initModality(Modality.APPLICATION_MODAL);
        return alert.showAndWait();
    }

}
