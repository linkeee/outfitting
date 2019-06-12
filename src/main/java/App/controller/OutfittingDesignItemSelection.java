package App.controller;

import App.utile.FxmlUtile;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class OutfittingDesignItemSelection {

    @FXML
    private BorderPane outfittingItemSelectionBorderPane;

    @FXML
    private TreeView<String> outfittingItemSelectionTreeView;

    @FXML
    private AnchorPane outfittingItemSelectionAnchorPane;

    @FXML
    void initialize() {
        initTreeview();
        loadOutfittingItem();
    }

    @FXML
    void loadOutfittingItem() {
        outfittingItemSelectionTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
                System.out.println(newValue.getValue());
                switch (newValue.getValue()) {
                    case "锚设备":
                        try {
                            loadItemFxml("SelectionItemAnchor.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "供油单元":
                        try {
                            loadItemFxml("SelectionItemGongyoudanyuan.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });
    }

    //用anchorpane加载
    private void loadItemFxml(String nameOfFxmlDoc) throws IOException {
        FxmlUtile fxmlUtile = new FxmlUtile();
        FXMLLoader loader = fxmlUtile.getFxmlLoader("App/appView/" + nameOfFxmlDoc);
        AnchorPane anchorPane = loader.load();
        outfittingItemSelectionAnchorPane.getChildren().clear();
        outfittingItemSelectionAnchorPane.getChildren().add(anchorPane);
        AnchorPane.setLeftAnchor(anchorPane, 0.0);
        AnchorPane.setRightAnchor(anchorPane, 0.0);
        AnchorPane.setTopAnchor(anchorPane, 0.0);
        AnchorPane.setBottomAnchor(anchorPane, 0.0);
    }

    //初始化舾装件树
    private void initTreeview () {
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
        outfittingItemSelectionTreeView.setRoot(treeItem);
    }

}
