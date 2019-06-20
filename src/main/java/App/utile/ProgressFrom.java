package App.utile;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
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

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setBackground(Background.EMPTY);
        vBox.getChildren().addAll(progressIndicator, label);

        Scene scene = new Scene(vBox);
        scene.setFill(null);
        dialogStage.setScene(scene);

        Thread thread = new Thread(task);
        thread.setDaemon(true);  //true为守护线程；false为用户线程，默认为false。
        thread.start();
//        System.out.println(thread.getName());

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                cancelProgressBar();
            }
        });

    }

    public void activateProgressBar() {
//        System.out.println(Thread.currentThread().getName());
        dialogStage.showAndWait();
    }

    public void cancelProgressBar() {
        dialogStage.close();
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

}
