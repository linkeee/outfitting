package App.utile;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * 返回一个FXMLLoader。
 */
public class FxmlUtile {
    public FXMLLoader getFxmlLoader(String fxmlUrl) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(fxmlUrl));
        return loader;
    }

    public static void setStyle(Button btn, Button... buttons) {
        btn.setStyle("-fx-background-color: rgb(0, 180, 255)");
        for (Button button : buttons) button.setStyle("-fx-background-color: rgb(0, 150, 255)");
    }
}
