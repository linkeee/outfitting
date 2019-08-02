package App;
import App.utile.FxmlUtile;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception{
        Main.stage = stage;
        showLoginWindows();
    }

    //显示登录界面
    public void showLoginWindows() throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/LoginWindows.fxml");
        BorderPane login = loader.load();
        Scene scene = new Scene(login);
        stage.setTitle("Welcome to sign in!");
        stage.setScene(scene);
        stage.getIcons().add(new Image("App/appView/images/used/javaIcon.png"));
        stage.show();
    }

    //关闭登录界面
    public void closeLoginWindows(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
