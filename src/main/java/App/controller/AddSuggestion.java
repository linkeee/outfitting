package App.controller;

import java.io.IOException;

import App.dataModel.SuggestionData;
import App.database.SuggestionDb;
import App.function.FileReader;
import App.utile.Constant;
import App.utile.FxmlUtile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddSuggestion {

    @FXML
    private TextArea solutionDescribeTextArea;

    @FXML
    private TextArea problemDescribeTextArea;

    @FXML
    private ComboBox<String> shipTypeChoiceBox;

    @FXML
    private ComboBox<String> shipCompanyChoiceBox;

    @FXML
    private TextField outfittingTextField;

    @FXML
    private Hyperlink sugFilePathHyperLink;

    @FXML
    private TextArea sugFileContentTextArea;

    @FXML
    private Label addSugLabel;

    private static class AddSuggestionInstance {
        private static final AddSuggestion Instance = new AddSuggestion();
    }

    public static AddSuggestion getInstance() {
        return AddSuggestionInstance.Instance;
    }

    private SuggestionData suggestionData = new SuggestionData();
    private String editSuggestionId;

    private ObservableList<String> classificationSocietyList = FXCollections.observableArrayList(null, "CCS", "BV", "ABS", "DNV-GL", "LR");

    @FXML
    void initialize() {
        shipTypeChoiceBox.setItems(FXCollections.observableArrayList(Constant.getShipTypeList()));
        shipCompanyChoiceBox.setItems(FXCollections.observableArrayList(Constant.getShipOwnerCompany()));
    }

    //将选择的船东意见信息添加到修改界面中
    private void setSuggestionData(SuggestionData suggestionData) {
        this.editSuggestionId = suggestionData.getSugId();

        if (editSuggestionId != null) {
            addSugLabel.setText("修改");
            outfittingTextField.setText(suggestionData.getSugOutfittingRegion());
            problemDescribeTextArea.setText(suggestionData.getSugProblemDescribe());
            solutionDescribeTextArea.setText(suggestionData.getSugSolutionDescribe());
            shipTypeChoiceBox.setValue(suggestionData.getSugShipType());
            shipCompanyChoiceBox.setValue(suggestionData.getSugShipCompany());
            sugFilePathHyperLink.setText(suggestionData.getSugFilePath());
            sugFileContentTextArea.setText(suggestionData.getSugContent());
        } else {
            addSugLabel.setText("添加");
        }
    }

    @FXML
    public void handleOk(ActionEvent event) throws IOException {

        suggestionData.setSugShipCompany(shipCompanyChoiceBox.getValue());
        suggestionData.setSugShipType(shipTypeChoiceBox.getValue());
        suggestionData.setSugOutfittingRegion(outfittingTextField.getText());
        suggestionData.setSugProblemDescribe(problemDescribeTextArea.getText());
        suggestionData.setSugSolutionDescribe(solutionDescribeTextArea.getText());
        suggestionData.setSugFilePath(sugFilePathHyperLink.getText());
        suggestionData.setSugContent(sugFileContentTextArea.getText());

        if (editSuggestionId != null) {
            SuggestionDb.update(suggestionData, editSuggestionId);
            closeAddSuggestion(event);
        } else {
            SuggestionDb.insert(suggestionData);
            closeAddSuggestion(event);
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        closeAddSuggestion(event);
    }

    @FXML
    void handleDragDropped(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;
        if (dragboard.hasFiles()) {
            System.out.println(dragboard.getFiles());
            String[] temp = dragboard.getFiles().toString().split("\\[");
            sugFilePathHyperLink.setText(temp[temp.length - 1].split("]")[0]);
            sugFileContentTextArea.setText(FileReader.readFileContent(temp[temp.length - 1].split("]")[0]));
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    void handleDragOver(DragEvent event) {
        if (event.getGestureSource() != sugFileContentTextArea && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    //显示意见添加页面
    void showAddSuggestion(SuggestionData suggestionData) throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/AddSuggestion.fxml");
        BorderPane borderPane = loader.load();
        Scene scene = new Scene(borderPane);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);//WINDOW_MODAL表示一次只能打开一个窗口进行操作
        stage.setScene(scene);

        AddSuggestion addSuggestion = loader.getController();
        editSuggestionId = suggestionData.getSugId();
        addSuggestion.setSuggestionData(suggestionData);

        stage.showAndWait();
    }

    //关闭界面
    private void closeAddSuggestion(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}
