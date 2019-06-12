package App;

import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.stream.Collectors;

public class HeadingInComboBoxDemo extends Application {

    private final int WEAK_INDEX = -1;
    private final int STRONG_INDEX = -2;

    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        root.setSpacing(15);
        root.setPadding(new Insets(25));
        root.setAlignment(Pos.TOP_LEFT);
        Scene sc = new Scene(root, 600, 600);
        stage.setScene(sc);
        stage.show();

        final ObservableList<Level> items = FXCollections.observableArrayList();
        for (int i = 1; i < 11; i++) {
            if (i == 1) {
                items.add(new Level(WEAK_INDEX));
            } else if (i == 6) {
                items.add(new Level(STRONG_INDEX));
            }
            items.add(new Level(i));
        }

        final ComboBox<Level> comboBox = new ComboBox<Level>(items) {
            protected javafx.scene.control.Skin<?> createDefaultSkin() {
                return new ComboBoxListViewSkin<Level>(this) {
                    @Override
                    protected boolean isHideOnClickEnabled() {
                        return false;
                    }
                };
            }
        };
        comboBox.setPrefWidth(150);
        comboBox.setItems(items);
        comboBox.setCellFactory(new Callback<ListView<Level>, ListCell<Level>>() {
            @Override
            public ListCell<Level> call(ListView<Level> param) {
                return new ListCell<Level>() {
                    private CheckBox cb = new CheckBox();
                    private BooleanProperty booleanProperty;

                    {
                        cb.setOnAction(e->getListView().getSelectionModel().select(getItem()));
                    }
                    @Override
                    protected void updateItem(Level item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            if (item.getLevel() == WEAK_INDEX || item.getLevel() == STRONG_INDEX) {
                                Label lbl = new Label(item.getLevel() == WEAK_INDEX ? "Weak" : "Strong");
                                lbl.setStyle("-fx-font-size:14px;-fx-font-weight:bold;");
                                setGraphic(lbl);
                                setText(null);
                            } else {
                                if (booleanProperty != null) {
                                    cb.selectedProperty().unbindBidirectional(booleanProperty);
                                }
                                booleanProperty = item.selectedProperty();
                                cb.selectedProperty().bindBidirectional(booleanProperty);
                                setGraphic(cb);
                                setText(item.getLevel() + "");
                            }
                        } else {
                            setGraphic(null);
                            setText(null);
                        }
                    }
                };
            }
        });

        comboBox.setButtonCell(new ListCell<Level>() {
            @Override
            protected void updateItem(Level item, boolean empty) {
                super.updateItem(item, empty);
                String selected = comboBox.getItems().stream().filter(i -> i.isSelected())
                        .map(i -> i.getLevel()).sorted()
                        .map(i -> i + "").collect(Collectors.joining(","));
                setText(selected);
            }
        });

        Text txt = new Text();
        Button show = new Button("Show Selected");
        show.setOnAction(e->{
            StringBuilder sb = new StringBuilder();
            comboBox.getItems().stream().filter(Level::isSelected).forEach(item->{
                sb.append(item.getLevel()).append("\n");
            });
            txt.setText(sb.toString());
        });

        root.getChildren().addAll(comboBox,show,txt);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    class Level {
        private IntegerProperty level = new SimpleIntegerProperty();
        private BooleanProperty selected = new SimpleBooleanProperty();

        public Level(int level) {
            setLevel(level);
        }

        public int getLevel() {
            return level.get();
        }

        public IntegerProperty levelProperty() {
            return level;
        }

        public void setLevel(int level) {
            this.level.set(level);
        }

        public boolean isSelected() {
            return selected.get();
        }

        public BooleanProperty selectedProperty() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected.set(selected);
        }
    }
}
