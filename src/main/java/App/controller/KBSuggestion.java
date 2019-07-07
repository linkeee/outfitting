package App.controller;

import App.dataModel.SuggestionData;
import App.database.SuggestionDb;
import App.utile.HyperlinkTableCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class KBSuggestion {

    @FXML
    private ComboBox<String> schuanDongChoiceBox;
    @FXML
    private TableView<SuggestionData> suggestionTable;
    @FXML
    private TableColumn<?, ?> c30;
    @FXML
    private TableColumn<?, ?> c35;
    @FXML
    private TextArea ssolutionDecribeTextArea;
    @FXML
    private TextField suggestionQueryTextField;
    @FXML
    private TableColumn<?, ?> c37;
    @FXML
    private TableColumn<?, ?> c39;
    @FXML
    private TableColumn<?, ?> c38;
    @FXML
    private TableColumn<SuggestionData, String> sugFilePathTC;
    @FXML
    private TextArea sugContentTA;
    @FXML
    private TableColumn<?, ?> sugContentTC;
    @FXML
    private TextArea sproblemDescribeTextArea;
    @FXML
    private TextField soutfittingTypeTextField;
    @FXML
    private ComboBox<String> schuanboTypeChoiceBox;

    private String sugFile;

    //船东船检增加修改删除
    private final AddSuggestion asc = AddSuggestion.getInstance();

    @FXML
    private void handleSuggestionQuery() throws SQLException {
        suggestionTable.setItems(SuggestionDb.query(
                schuanDongChoiceBox.getValue(),
                schuanboTypeChoiceBox.getValue(),
                soutfittingTypeTextField.getText(),
                suggestionQueryTextField.getText()
        ));
    }

    @FXML
    private void showAddSuggestion() throws IOException {
        SuggestionData tempSuggestion = new SuggestionData();
        asc.showAddSuggestion(tempSuggestion);
        suggestionTable.setItems(SuggestionDb.getSugDataList());
        refreshCB();
    }

    @FXML
    private void handleEditSuggestion() throws IOException {
        SuggestionData selectedSuggestion = suggestionTable.getSelectionModel().getSelectedItem();
        asc.showAddSuggestion(selectedSuggestion);
        suggestionTable.setItems(SuggestionDb.getSugDataList());
        refreshCB();
    }

    @FXML
    private void handleDeleteSuggestion() {
        SuggestionData deletedSugData = suggestionTable.getSelectionModel().getSelectedItem();
        String deletedSugDataId = deletedSugData.getSugId();
        SuggestionDb.delete(deletedSugDataId);
        suggestionTable.setItems(SuggestionDb.getSugDataList());
        refreshCB();
    }

    @FXML
    private void handleResetSuggestion() {
        suggestionTable.setItems(SuggestionDb.getSugDataList());
        showSugDetails(null);
        refreshCB();
    }

    private void refreshCB() {
        schuanboTypeChoiceBox.setItems(FXCollections.observableArrayList(SuggestionDb.getShipTypeList()));
        schuanDongChoiceBox.setItems(FXCollections.observableArrayList(SuggestionDb.getChuandongCompanyList()));
    }

    @FXML
    void initialize() {
        //船东船检意见表显示内容
        refreshCB();
        c37.setCellValueFactory(new PropertyValueFactory<>("sugShipType"));
        c38.setCellValueFactory(new PropertyValueFactory<>("sugOutfittingRegion"));
        c39.setCellValueFactory(new PropertyValueFactory<>("sugProblemDescribe"));
        c30.setCellValueFactory(new PropertyValueFactory<>("sugSolutionDescribe"));
        c35.setCellValueFactory(new PropertyValueFactory<>("sugShipCompany"));
        sugContentTC.setCellValueFactory(new PropertyValueFactory<>("sugContent"));
        sugFilePathTC.setCellValueFactory(new PropertyValueFactory<>("sugFilePath"));
        sugFilePathTC.setCellFactory(param -> new HyperlinkTableCell<>());

        suggestionTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SuggestionData>() {
            @Override
            public void changed(ObservableValue<? extends SuggestionData> observable, SuggestionData oldValue, SuggestionData newValue) {
                if (newValue != null) {
                    showSugDetails(newValue);
                    sugFile = newValue.getSugFilePath();
                }
            }
        });

        suggestionTable.setItems(SuggestionDb.getSugDataList());

        MenuItem sugItem1 = new MenuItem("打开文件");
        sugItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().open(new File(sugFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        MenuItem sugItem2 = new MenuItem("打开文件夹");
        sugItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().open(new File(new File(sugFile).getParent()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ContextMenu sugMenu = new ContextMenu();
        sugMenu.getItems().addAll(sugItem1, sugItem2);
        suggestionTable.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                sugMenu.show(suggestionTable, event.getScreenX(), event.getScreenY());
            }
        });
        suggestionTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sugMenu.hide();
            }
        });

        suggestionTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showSugDetails(newValue));
    }

    private void showSugDetails(SuggestionData suggestionData) {
        if (suggestionData != null) {
            soutfittingTypeTextField.setText(suggestionData.getSugOutfittingRegion());
            sproblemDescribeTextArea.setText(suggestionData.getSugProblemDescribe());
            ssolutionDecribeTextArea.setText(suggestionData.getSugSolutionDescribe());
            sugContentTA.setText(suggestionData.getSugContent());
            schuanboTypeChoiceBox.setValue(suggestionData.getSugShipType());
            schuanDongChoiceBox.setValue(suggestionData.getSugShipCompany());
        } else {
            soutfittingTypeTextField.setText("");
            sproblemDescribeTextArea.setText("");
            ssolutionDecribeTextArea.setText("");
            sugContentTA.setText("");
            schuanboTypeChoiceBox.setValue(null);
            schuanDongChoiceBox.setValue(null);
        }
    }

}
