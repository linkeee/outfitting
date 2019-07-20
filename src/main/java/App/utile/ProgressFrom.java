package App.utile;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 连接数据库时，显示加载中动画。
 */
public class ProgressFrom {

    private Stage dialogStage;

    public ProgressFrom(final Task<?> task, String promptMessage) {
        dialogStage = new Stage();
        ProgressIndicator progressIndicator = new ProgressIndicator();

        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        //progress bar
        Label label = new Label(promptMessage);
        label.setFont(Font.font("宋体", FontWeight.BOLD, 18));
        label.setTextFill(Color.BLUE);
        progressIndicator.setProgress(-1F);
        progressIndicator.progressProperty().bind(task.progressProperty());
        progressIndicator.setMinSize(80, 80);

        Button button = new Button("后台运行");

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(Background.EMPTY);
        vBox.getChildren().addAll(progressIndicator, label, button);

        Scene scene = new Scene(vBox);
        scene.setFill(null);
        dialogStage.setScene(scene);

        Thread thread = new Thread(task);
        thread.setName("jobThread");
        thread.setDaemon(true);  //true为守护线程；false为用户线程，默认为false。
        thread.start();

        task.setOnSucceeded(event -> cancelProgressBar());

        button.setOnMouseClicked(event -> {
//            thread.stop();
            cancelProgressBar();
        });
    }

    public void activateProgressBar() {
        dialogStage.showAndWait();
    }

    public void cancelProgressBar() {
        dialogStage.close();
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

}
