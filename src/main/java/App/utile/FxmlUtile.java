package App.utile;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * 返回一个FXMLLoader。
 */
public class FxmlUtile {

    private final static FxmlUtile Instance = new FxmlUtile();

    public static FxmlUtile getInstance() {
        return Instance;
    }

    public FXMLLoader getFxmlLoader(String fxmlUrl) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(fxmlUrl));
        return loader;
    }

    public static void setStyle(Button btn, Button... buttons) {
        btn.setStyle("-fx-background-color: white; -fx-text-fill: black");
        for (Button button : buttons) button.setStyle("-fx-background-color: rgb(16, 110, 190); -fx-text-fill: white");
    }
}
