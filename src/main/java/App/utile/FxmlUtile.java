package App.utile;

import javafx.fxml.FXMLLoader;

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
}
