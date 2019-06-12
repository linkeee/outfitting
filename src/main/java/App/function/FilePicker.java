package App.function;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FilePicker {

    //-----------------------------------------------------------------
//    @Override
//    public void start(Stage stage) {
//        try {
//            showPickerWindow();
//            String s = getDroppedLabel().toString();
//            System.out.println(s);
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
    //--------------------------------------------------------------------

    private Label droppedLabel = new Label("");
    private String pathName = null;
    private String content = null;

    public Label getDroppedLabel() {
        return droppedLabel;
    }

    public String getPathName() {
        return pathName;
    }

    public String getContent() {
        return content;
    }

    public void showPickerWindow() {
        Label label1 = new Label("请将文件拖放至窗口内");
        Label label2 = new Label("文件绝对路径：");
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        Button okButton = new Button("OK");
        Button cancelButton = new Button("cancel");
        HBox buttonPool = new HBox(okButton, cancelButton);
        buttonPool.setAlignment(Pos.CENTER);
        buttonPool.setSpacing(30);
        VBox dragTarget = new VBox();
        dragTarget.getChildren().addAll(label1, label2, droppedLabel, textArea, buttonPool);
        textArea.prefHeightProperty().bind(dragTarget.heightProperty());
        textArea.prefWidthProperty().bind(dragTarget.widthProperty());

        dragTarget.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != dragTarget
                && event.getDragboard().hasFiles()) {
                    //allow for both copying and moving, whatever user chooses
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        dragTarget.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard dragboard = event.getDragboard();
                boolean success = false;
                if (dragboard.hasFiles()) {
                    droppedLabel.setText(dragboard.getFiles().toString());
                    String[] temp = droppedLabel.toString().split("\\[");
                    pathName = temp[temp.length-1].split("]")[0];
//                    System.out.println(pathName);
                    content = FileReader.readFileContent(pathName);
                    textArea.setText(content);
                    success = true;
                }
                /*
                let the source know whether the string was successfully
                transferred and used
                 */
                event.setDropCompleted(success);
                event.consume();
            }
        });

        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                closePickerWindow(event);
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pathName = null;
                content = null;
                closePickerWindow(event);
            }
        });

        Stage stage = new Stage();
        StackPane root = new StackPane();
        root.getChildren().add(dragTarget);
        Scene scene = new Scene(root, 500, 250);
        stage.setTitle("Drag Window");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void closePickerWindow(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
