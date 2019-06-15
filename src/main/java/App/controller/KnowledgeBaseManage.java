package App.controller;

import App.dataModel.*;
import App.database.*;
import App.utile.FxmlUtile;
import App.utile.HyperlinkTableCell;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import App.dataModel.SuggestionData;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TreeView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class KnowledgeBaseManage {

    @FXML
    private ImageView modelImageView; //设计实例库中的模型方案图。

    @FXML
    private AnchorPane ruleAnchorPane;

    @FXML
    public TreeView<String> outfittingTreeView;

    //意见表增删改

    @FXML
    private TableColumn<SuggestionData, String> c35;
    @FXML
    private TableColumn<SuggestionData, String> c36;
    @FXML
    private TableColumn<SuggestionData, String> c37;
    @FXML
    private TableColumn<SuggestionData, String> c38;
    @FXML
    private TableColumn<SuggestionData, String> c39;
    @FXML
    private TableColumn<SuggestionData, String> c30;
    @FXML private TableColumn<SuggestionData, String> sugContentTC;
    @FXML private TableColumn<SuggestionData, String> sugFilePathTC;
    @FXML private TextArea sugContentTA;

    @FXML
    private TableView<SuggestionData> suggestionTable;

    @FXML
    private TextArea sproblemDescribeTextArea;
    @FXML
    private TextField soutfittingTypeTextField;

    @FXML
    private TextArea ssolutionDecribeTextArea;

    @FXML
    private ComboBox<String> schuanboTypeChoiceBox;

    @FXML
    private ComboBox<String> schuanDongChoiceBox;

    @FXML
    private TextField suggestionQueryTextField;

    //区域布局设计
    @FXML
    private TextField layoutQueryTextField;

    @FXML
    private TextField layoutTypeTextField;
    @FXML
    private TextField layoutNameTextField;

    @FXML
    private TextField depthTextFiled;
    @FXML
    private TextField layoutShipNumTextField;
    @FXML
    private TableView<LayoutData> layoutDesignTable;
    @FXML
    private TextField weightTextField;

    @FXML
    private TextField coefficientsTextField;
    @FXML
    private TextField lengthTextFiled;
    @FXML
    private TableColumn<LayoutData, String> l21;

    @FXML
    private TableColumn<LayoutData, String> l23;
    @FXML
    private TableColumn<LayoutData, String> l22;

    @FXML
    private TableColumn<LayoutData, String> l25;

    @FXML
    private TableColumn<LayoutData, String> l24;

    @FXML
    private TableColumn<LayoutData, String> l26;

    @FXML
    private TextArea layoutDesignTextArea;

    @FXML
    private ComboBox<String> layoutshipType;
    @FXML
    private TextField draughtTextField;
    @FXML
    private TextField widthTextFiled;

    //禁忌表增删改
    @FXML
    private TableView<TabooData> jinjiTable;
    @FXML
    private TableColumn<TabooData, String> c41;
    @FXML
    private TableColumn<TabooData, String> c42;
    @FXML
    private TableColumn<TabooData, String> c43;
    @FXML
    private TableColumn<TabooData, String> c44;
    @FXML
    private TableColumn<TabooData, String> c45;
    @FXML
    private TableColumn<TabooData, String> c46;
    @FXML
    private TableColumn<TabooData, String> c47;
    @FXML
    private TableColumn<TabooData, String> c48;
    @FXML
    private TableColumn<TabooData, String> c49;

    //禁忌
    @FXML
    private TextField tabooQueryTextField;

    @FXML
    private TextArea jinjiTextArea;

    @FXML
    private TextField jinjiNameTextField;
    @FXML
    private TextField jinjioutfittingTypeTextField;
    @FXML
    private ComboBox<String> jinjishipTypeChoiceBox;
    @FXML
    private ComboBox<String> jinjiLevelChoiceBox;

    //经验库变量
    @FXML
    private TextField experienceQueryTextField;

    @FXML
    private TableColumn<ExperienceData, String> e51;
    @FXML
    private TableColumn<ExperienceData, String> e53;
    @FXML
    private TableColumn<ExperienceData, String> e52;
    @FXML
    private TableColumn<ExperienceData, String> e55;
    @FXML private TableColumn<ExperienceData, String> expFilePathTC;
    @FXML
    private TextArea experienceTextArea1;

    @FXML
    private TableColumn<ExperienceData, String> e54;

    @FXML
    private TextField experienceNameTextField1;

    @FXML
    private TableView<ExperienceData> experienceTable1;

    @FXML
    private ComboBox<String> experienceshipType1;

    @FXML
    private TextField experienceOutfittingTypeTextField1;

    //规范表命名
    @FXML
    private TableView<CriterionData> guifanTable;
    @FXML
    private TableColumn<CriterionData, String> g31;
    @FXML
    private TableColumn<CriterionData, String> g32;
    @FXML
    private TableColumn<CriterionData, String> g33;
    @FXML
    private TableColumn<CriterionData, String> g34;
    @FXML
    private TableColumn<CriterionData, String> g35;
    @FXML
    private TableColumn<CriterionData, String> criterionClassificationSocietyTC;
    @FXML
    private TableColumn<CriterionData, String> criterionUrlTC;

    @FXML
    private TextField criterionQueryTextField;

    @FXML
    private TextArea guifanTextArea;

    @FXML
    private TextField guifanNameTextField;

    @FXML
    private TextField guifanOutfittingTypeTextField;

    @FXML
    private ComboBox<String> critShipTypeChoiceBox;

    @FXML
    private ComboBox<String> critClassificationSocietyChoiceBox;

    private ObservableList<String> jinjiLevelList = FXCollections.observableArrayList(null, "公司级", "部门级", "船级社级");
    private ObservableList<String> shipTypeList = FXCollections.observableArrayList(null, "油轮", "散货船", "集装箱船", "平台", "豪华游轮");
    private ObservableList<String> classificationSocietyList = FXCollections.observableArrayList(null, "CCS", "BV", "ABS", "DNV-GL", "LR");

    private String criterionFile;
    private String sugFile;
    private String expFile;

    @FXML
    private void initialize() {
        initTreeview();
        //添加区域布局设计
        layoutshipType.setItems(shipTypeList);
        l21.setCellValueFactory(cellData -> cellData.getValue().layoutDesignIDProperty());
        l22.setCellValueFactory(cellData -> cellData.getValue().layoutDesignNameProperty());
        l23.setCellValueFactory(cellData -> cellData.getValue().layoutDesignTypeProperty());
        l24.setCellValueFactory(cellData -> cellData.getValue().layoutShipTypeProperty());
        l25.setCellValueFactory(cellData -> cellData.getValue().shipWeightProperty());
        l26.setCellValueFactory(cellData -> cellData.getValue().layoutDesignContentProperty());

        layoutDesignTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showLayoutDesignDetails(newValue)
        );

        //船东船检意见表显示内容
        schuanboTypeChoiceBox.setItems(shipTypeList);
        schuanDongChoiceBox.setItems(classificationSocietyList);
        c36.setCellValueFactory(new PropertyValueFactory<>("sugId"));
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

        //禁忌表中添加显示禁忌内容
        jinjishipTypeChoiceBox.setItems(shipTypeList);
        jinjiLevelChoiceBox.setItems(jinjiLevelList);
        c41.setCellValueFactory(cellData -> cellData.getValue().jinjiIDProperty());
        c42.setCellValueFactory(cellData -> cellData.getValue().shipTypeProperty());
        c43.setCellValueFactory(cellData -> cellData.getValue().outfittingTypeProperty());
        c44.setCellValueFactory(cellData -> cellData.getValue().jinjiNameProperty());
        c45.setCellValueFactory(cellData -> cellData.getValue().jinjiContentProperty());
        c46.setCellValueFactory(cellData -> cellData.getValue().jkeyword1Property());
        c47.setCellValueFactory(cellData -> cellData.getValue().jkeyword2Property());
        c48.setCellValueFactory(cellData -> cellData.getValue().jkeyword3Property());
        c49.setCellValueFactory(cellData -> cellData.getValue().jjinjiLevelProperty());

//        showJinJiDetails(null);
        jinjiTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showJinJiDetails(newValue)
        );

        //经验表里添加经验内容
        experienceshipType1.setItems(shipTypeList);
        e51.setCellValueFactory(new PropertyValueFactory<>("expId"));
        e52.setCellValueFactory(new PropertyValueFactory<>("expShipType"));
        e53.setCellValueFactory(new PropertyValueFactory<>("expOutfittingRegion"));
        e54.setCellValueFactory(new PropertyValueFactory<>("expName"));
        e55.setCellValueFactory(new PropertyValueFactory<>("expContent"));
        expFilePathTC.setCellValueFactory(new PropertyValueFactory<>("expFilePath"));
        expFilePathTC.setCellFactory(param -> new HyperlinkTableCell<>());

        experienceTable1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ExperienceData>() {
            @Override
            public void changed(ObservableValue<? extends ExperienceData> observable, ExperienceData oldValue, ExperienceData newValue) {
                if (newValue != null) {
                    showExperienceDetails(newValue);
                    expFile = newValue.getExpFilePath();
                }
            }
        });

        MenuItem expItem1 = new MenuItem("打开文件");
        expItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().open(new File(expFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        MenuItem expItem2 = new MenuItem("打开文件夹");
        expItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().open(new File(new File(expFile).getParent()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ContextMenu expMenu = new ContextMenu();
        expMenu.getItems().addAll(expItem1, expItem2);
        experienceTable1.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                expMenu.show(experienceTable1, event.getScreenX(), event.getScreenY());
            }
        });
        experienceTable1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                expMenu.hide();
            }
        });

        //添加规范
        critShipTypeChoiceBox.setItems(shipTypeList);
        critClassificationSocietyChoiceBox.setItems(classificationSocietyList);
        g31.setCellValueFactory(new PropertyValueFactory<>("criId"));
        g32.setCellValueFactory(new PropertyValueFactory<>("criShipType"));
        criterionClassificationSocietyTC.setCellValueFactory(new PropertyValueFactory<>("criShipCompany"));
        g33.setCellValueFactory(new PropertyValueFactory<>("criOutfittingRegion"));
        g34.setCellValueFactory(new PropertyValueFactory<>("criName"));
        g35.setCellValueFactory(new PropertyValueFactory<>("criContent"));
        criterionUrlTC.setCellValueFactory(new PropertyValueFactory<>("criFilePath"));
        criterionUrlTC.setCellFactory(param -> new HyperlinkTableCell<>());

        guifanTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CriterionData>() {
            @Override
            public void changed(ObservableValue<? extends CriterionData> observable, CriterionData oldValue, CriterionData newValue) {
                if (newValue != null) {
                    showGuiFanDetails(newValue);
                    criterionFile = newValue.getCriFilePath();
                }
            }
        });

        MenuItem criterionItem1 = new MenuItem("打开文件");
        criterionItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().open(new File(criterionFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        MenuItem criterionItem2 = new MenuItem("打开文件夹");
        criterionItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().open(new File(new File(criterionFile).getParent()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ContextMenu criterionMenu = new ContextMenu();
        criterionMenu.getItems().addAll(criterionItem1, criterionItem2);
        guifanTable.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                criterionMenu.show(guifanTable, event.getScreenX(), event.getScreenY());
            }
        });
        guifanTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                criterionMenu.hide();
            }
        });
    }

    void refreshAllTable() {
        jinjiTable.setItems(TabooDatabase.getTabooDataList());
        suggestionTable.setItems(SuggestionDatabase.getSugDataList());
        experienceTable1.setItems(ExperienceDatabase.getExpDataList());
        guifanTable.setItems(CriterionDatabase.getCriterionDataList());
        layoutDesignTable.setItems((LayoutDatabase.getLayoutDataList()));
    }

    //用anchorpane加载
    private void loadItemFxml(String nameOfFxml) throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/" + nameOfFxml);
        AnchorPane anchorPane = loader.load();
        ruleAnchorPane.getChildren().clear();
        ruleAnchorPane.getChildren().add(anchorPane);
        AnchorPane.setLeftAnchor(anchorPane, 0.0);
        AnchorPane.setRightAnchor(anchorPane, 0.0);
        AnchorPane.setTopAnchor(anchorPane, 0.0);
        AnchorPane.setBottomAnchor(anchorPane, 0.0);
    }

    @FXML
    private void showOutfitItem() {
        outfittingTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
                System.out.println(newValue.getValue());
                switch (newValue.getValue()) {
                    case "锚设备":
                        try {
                            loadItemFxml("ItemAnchor.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "供油单元":
                        try {
                            loadItemFxml("ItemGongyoudanyuan.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });
    }

    //设计实例增加修改删除
    private AddLayoutDesign aldc = new AddLayoutDesign();

    @FXML
    private void handleLayoutQuery() throws SQLException {
        String keyword = layoutshipType.getValue() + layoutNameTextField.getText() + layoutTypeTextField.getText() + layoutShipNumTextField.getText() + lengthTextFiled.getText() + widthTextFiled.getText() + depthTextFiled.getText() + draughtTextField.getText() + coefficientsTextField.getText() + weightTextField.getText();
        layoutDesignTable.setItems(LayoutDatabase.query(keyword));
    }

    @FXML
    private void handleEditLayoutDesign() throws IOException {
        LayoutData selectedLayout = layoutDesignTable.getSelectionModel().getSelectedItem();
        aldc.showAddLayoutDesign(selectedLayout);
        layoutDesignTable.setItems(LayoutDatabase.getLayoutDataList());
    }

    @FXML
    private void showAddLayoutDesign() throws IOException {
        LayoutData tempLayoutData = new LayoutData();
        aldc.showAddLayoutDesign(tempLayoutData);
        layoutDesignTable.setItems(LayoutDatabase.getLayoutDataList());
    }

    @FXML
    private void handleResetLayoutDesign() {
        layoutDesignTable.setItems(LayoutDatabase.getLayoutDataList());
        showLayoutDesignDetails(null);
    }

    @FXML
    private void handleDeleteLayoutDesign() {
        LayoutData deletedLayoutData = layoutDesignTable.getSelectionModel().getSelectedItem();
        String deletedLayoutId = deletedLayoutData.getLayoutDesignID();
        LayoutDatabase.delete(deletedLayoutId);
        layoutDesignTable.setItems(LayoutDatabase.getLayoutDataList());
    }

    //实例详细显示
    private void showLayoutDesignDetails(LayoutData layoutData) {
        if (layoutData != null) {
            layoutNameTextField.setText(layoutData.getLayoutDesignName());
            layoutTypeTextField.setText(layoutData.getLayoutDesignType());
            layoutshipType.setValue(layoutData.getLayoutShipType());
            layoutShipNumTextField.setText(layoutData.getLayoutShipNum());
            lengthTextFiled.setText(layoutData.getShipLength());
            widthTextFiled.setText(layoutData.getShipWidth());
            depthTextFiled.setText(layoutData.getShipDepth());
            draughtTextField.setText(layoutData.getShipDraught());
            coefficientsTextField.setText(layoutData.getShipCoefficients());
            weightTextField.setText(layoutData.getShipWeight());
            layoutDesignTextArea.setText(layoutData.getLayoutDesignContent());
            layoutshipType.setValue(layoutData.getLayoutShipType());

            Image image = new Image("file:C:\\Users\\link0\\IdeaProjects\\outfitting\\src\\main\\resources\\App\\appView\\images\\cad.jpg");
            modelImageView.setImage(image);
        } else {
            layoutNameTextField.setText("");
            layoutTypeTextField.setText("");
            layoutshipType.setValue(null);
            layoutShipNumTextField.setText("");
            lengthTextFiled.setText("");
            widthTextFiled.setText("");
            depthTextFiled.setText("");
            draughtTextField.setText("");
            coefficientsTextField.setText("");
            weightTextField.setText("");
            layoutDesignTextArea.setText("");
            layoutshipType.setValue(null);
        }
    }

    //船东船检增加修改删除
    private AddSuggestion asc = new AddSuggestion();

    @FXML
    private void handleSuggestionQuery() throws SQLException {
        suggestionTable.setItems(SuggestionDatabase.query(
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
        suggestionTable.setItems(SuggestionDatabase.getSugDataList());
    }

    @FXML
    private void handleEditSuggestion() throws IOException {
        SuggestionData selectedSuggestion = suggestionTable.getSelectionModel().getSelectedItem();
        asc.showAddSuggestion(selectedSuggestion);
        suggestionTable.setItems(SuggestionDatabase.getSugDataList());
    }

    @FXML
    private void handleDeleteSuggestion() {
        SuggestionData deletedSugData = suggestionTable.getSelectionModel().getSelectedItem();
        String deletedSugDataId = deletedSugData.getSugId();
        SuggestionDatabase.delete(deletedSugDataId);
        suggestionTable.setItems(SuggestionDatabase.getSugDataList());
    }

    @FXML
    private void handleResetSuggestion() {
        suggestionTable.setItems(SuggestionDatabase.getSugDataList());
        showSugDetails(null);
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

    //禁忌增加修改删除
    private AddTaboo atc = new AddTaboo();

    @FXML
    private void handleTabooQuery() throws SQLException {
        String keyword = jinjishipTypeChoiceBox.getValue() + jinjiLevelChoiceBox.getValue() + jinjioutfittingTypeTextField.getText() + jinjiNameTextField.getText() + tabooQueryTextField.getText();
        jinjiTable.setItems(TabooDatabase.query(keyword));
    }

    @FXML
    private void handleResetJinji() {
        jinjiTable.setItems(TabooDatabase.getTabooDataList());
        showJinJiDetails(null);
    }

    @FXML
    private void handleDeleteJinJi() throws IOException {
        TabooData deletedTabooData = jinjiTable.getSelectionModel().getSelectedItem();
        String deletedJinJiDataId = deletedTabooData.getJinjiID();
        TabooDatabase.delete(deletedJinJiDataId);
        jinjiTable.setItems(TabooDatabase.getTabooDataList());
    }

    @FXML
    private void showAddJinJi() throws IOException {
        TabooData tempTabooData = new TabooData();
        atc.showAddTaboo(tempTabooData);
        jinjiTable.setItems(TabooDatabase.getTabooDataList());
    }

    @FXML
    private void handleEditJinJi() throws IOException {
        TabooData selectedJinJi = jinjiTable.getSelectionModel().getSelectedItem();
        atc.showAddTaboo(selectedJinJi);
        jinjiTable.setItems(TabooDatabase.getTabooDataList());
    }

    //在对应格子中显示选择的属性
    private void showJinJiDetails(TabooData tabooData) {
        if (tabooData != null) {
            jinjiNameTextField.setText(tabooData.getJinjiName());
            jinjioutfittingTypeTextField.setText(tabooData.getOutfittingType());
            jinjishipTypeChoiceBox.setValue(tabooData.getShipType());
            jinjiLevelChoiceBox.setValue(tabooData.getJjinjiLevel());
            jinjiTextArea.setText(tabooData.getJinjiContent());
        } else {
            jinjiNameTextField.setText("");
            jinjioutfittingTypeTextField.setText("");
            jinjishipTypeChoiceBox.setValue(null);
            jinjiLevelChoiceBox.setValue(null);
            jinjiTextArea.setText("");
        }
    }

    //经验修改，增加，删除
    private AddExperience aec = new AddExperience();

    @FXML
    private void handleExperienceQuery() throws SQLException {
        experienceTable1.setItems(ExperienceDatabase.query(
                experienceshipType1.getValue(),
                experienceOutfittingTypeTextField1.getText(),
                experienceNameTextField1.getText(),
                experienceQueryTextField.getText()
        ));
    }

    @FXML
    private void handleResetExperience() {
        experienceTable1.setItems(ExperienceDatabase.getExpDataList());
        showExperienceDetails(null);
    }

    @FXML
    private void handleDeleteExperience() {
        ExperienceData deletedExp = experienceTable1.getSelectionModel().getSelectedItem();
        String deletedExpId = deletedExp.getExpId();
        ExperienceDatabase.delete(deletedExpId);
        experienceTable1.setItems(ExperienceDatabase.getExpDataList());
    }

    @FXML
    private void showAddExperience() throws IOException {
        ExperienceData tempExperienceData = new ExperienceData();
        aec.showAddExperience(tempExperienceData);
        experienceTable1.setItems(ExperienceDatabase.getExpDataList());
    }

    @FXML
    private void handleEditExperience() throws Exception {
        ExperienceData selectedExperience = experienceTable1.getSelectionModel().getSelectedItem();
        aec.showAddExperience(selectedExperience);
        experienceTable1.setItems(ExperienceDatabase.getExpDataList());
    }

    //在对应格子中显示选择的属性
    private void showExperienceDetails(ExperienceData experienceData) {
        if (experienceData != null) {
            experienceNameTextField1.setText(experienceData.getExpName());
            experienceOutfittingTypeTextField1.setText(experienceData.getExpOutfittingRegion());
            experienceTextArea1.setText(experienceData.getExpContent());
            experienceshipType1.setValue(experienceData.getExpShipType());
        } else {
            experienceNameTextField1.setText("");
            experienceOutfittingTypeTextField1.setText("");
            experienceTextArea1.setText("");
            experienceshipType1.setValue(null);
        }
    }

    //规范搜索增加修改删除
    private AddCriterion acc = new AddCriterion();

    @FXML
    private void handleCriterionQuery() throws SQLException {
        guifanTable.setItems(CriterionDatabase.query(
                criterionQueryTextField.getText(),
                critShipTypeChoiceBox.getValue(),
                critClassificationSocietyChoiceBox.getValue(),
                guifanNameTextField.getText(),
                guifanOutfittingTypeTextField.getText()
        ));
    }

    @FXML
    private void handleResetGuiFan() {
        guifanTable.setItems(CriterionDatabase.getCriterionDataList());
        showGuiFanDetails(null);
    }

    @FXML
    private void handleDeleteGuiFan() {
        CriterionData deletedCrit = guifanTable.getSelectionModel().getSelectedItem();
        String deletedCritId = deletedCrit.getCriId();
        CriterionDatabase.delete(deletedCritId);
        guifanTable.setItems(CriterionDatabase.getCriterionDataList());
    }

    @FXML
    private void showAddGuiFan() throws IOException {
        CriterionData tempCriterionData = new CriterionData();
        acc.showAddGuiFan(tempCriterionData);
        guifanTable.setItems(CriterionDatabase.getCriterionDataList());
    }

    @FXML
    private void handleEditGuiFan() throws IOException {
        CriterionData selectedCrit = guifanTable.getSelectionModel().getSelectedItem();
        acc.showAddGuiFan(selectedCrit);
        guifanTable.setItems(CriterionDatabase.getCriterionDataList());
    }

    //在对应格子中显示选择的属性
    private void showGuiFanDetails(CriterionData guifanData) {
        if (guifanData != null) {
            guifanNameTextField.setText(guifanData.getCriName());
            critShipTypeChoiceBox.setValue(guifanData.getCriShipType());
            guifanOutfittingTypeTextField.setText(guifanData.getCriOutfittingRegion());
            critClassificationSocietyChoiceBox.setValue(guifanData.getCriShipCompany());
            guifanTextArea.setText(guifanData.getCriContent());
            System.out.println(guifanData.getCriFilePath());
        } else {
            guifanNameTextField.setText("");
            critShipTypeChoiceBox.setValue(null);
            guifanOutfittingTypeTextField.setText("");
            critClassificationSocietyChoiceBox.setValue(null);
            guifanTextArea.setText("");
        }
    }

    //初始化舾装件树
    private void initTreeview() {
        //船体舾装
        TreeItem<String> treeItem1 = new TreeItem<>("船体舾装");
        TreeItem<String> waiOutfitting = new TreeItem<>("外舾装");
        String[] waiItem = {"舵设备", "锚设备", "系泊设备", "救生设备", "起重设备", "拖拽设备", "舱口盖"};
        TreeItem<String> neiOutfitting = new TreeItem<>("内舾装");
        String[] neiItem = {"家具", "厨房设备", "冷库", "空调", "防火", "舱壁围壁", "天花板", "防火门", "舱壁隔热", "舱室甲板辅料", "舱室甲板基层敷料"};

        for (int i = 0; i < 7; i++) {
            TreeItem<String> item = new TreeItem<>(waiItem[i]);
            waiOutfitting.getChildren().add(item);
        }
        waiOutfitting.setExpanded(true);
        treeItem1.getChildren().add(waiOutfitting);

        for (int i = 0; i < 11; i++) {
            TreeItem<String> item = new TreeItem<String>(neiItem[i]);
            neiOutfitting.getChildren().add(item);
        }
        neiOutfitting.setExpanded(true);
        treeItem1.getChildren().add(neiOutfitting);
        treeItem1.setExpanded(true);

        //机舱舾装
        TreeItem<String> treeItem2 = new TreeItem<>("机舱舾装");
        TreeItem<String> guanOutfitting = new TreeItem<>("管装");
        String[] guanItem = {"蒸汽和热煤油管", "燃油管", "其他介质管"};
        TreeItem<String> jiOutfitting = new TreeItem<>("机装");
        String[] jiItem = {"主机", "供油单元", "滑油分油机", "燃油分油机", "螺旋桨", "锅炉"};
        TreeItem<String> tieOutfitting = new TreeItem<>("铁舾装");
        String[] tieItem = {"船用梯", "栏杆", "基座"};

        for (int i = 0; i < 3; i++) {
            TreeItem<String> item = new TreeItem<>(guanItem[i]);
            guanOutfitting.getChildren().add(item);
        }
        guanOutfitting.setExpanded(true);
        treeItem2.getChildren().add(guanOutfitting);

        for (int i = 0; i < 6; i++) {
            TreeItem<String> item = new TreeItem<>(jiItem[i]);
            jiOutfitting.getChildren().add(item);
        }
        jiOutfitting.setExpanded(true);
        treeItem2.getChildren().add(jiOutfitting);

        for (int i = 0; i < 3; i++) {
            TreeItem<String> item = new TreeItem<>(tieItem[i]);
            tieOutfitting.getChildren().add(item);
        }
        tieOutfitting.setExpanded(true);
        treeItem2.getChildren().add(tieOutfitting);
        treeItem2.setExpanded(true);

        //电气舾装
        TreeItem<String> treeItem3 = new TreeItem<>("电气舾装");
        TreeItem<String> dianOutfitting = new TreeItem<>("船舶电力系统");
        String[] dianItem = {"船用电源装置", "船舶电力网", "船用配电装置", "电缆托架", "船用电缆"};
        TreeItem<String> zhaoOutfitting = new TreeItem<>("船舶照明和信号灯");
        String[] zhaoItem = {"照明灯具", "探照灯", "号灯"};
        TreeItem<String> tongOutfitting = new TreeItem<>("通信和导航系统");
        String[] tongItem = {"船用电话", "无线电通信", "导航系统"};

        for (int i = 0; i < 5; i++) {
            TreeItem<String> item = new TreeItem<>(dianItem[i]);
            dianOutfitting.getChildren().add(item);
        }
        dianOutfitting.setExpanded(true);
        treeItem3.getChildren().add(dianOutfitting);

        for (int i = 0; i < 3; i++) {
            TreeItem<String> item = new TreeItem<>(zhaoItem[i]);
            zhaoOutfitting.getChildren().add(item);
        }
        zhaoOutfitting.setExpanded(true);
        treeItem3.getChildren().add(zhaoOutfitting);

        for (int i = 0; i < 3; i++) {
            TreeItem<String> item = new TreeItem<>(tongItem[i]);
            tongOutfitting.getChildren().add(item);
        }
        tongOutfitting.setExpanded(true);
        treeItem3.getChildren().add(tongOutfitting);
        treeItem3.setExpanded(true);

        //总舾装件选型树
        TreeItem<String> treeItem = new TreeItem<>("总体舾装件");
        treeItem.setExpanded(true);
        treeItem.getChildren().setAll(treeItem1, treeItem2, treeItem3);
        outfittingTreeView.setRoot(treeItem);
    }
}
