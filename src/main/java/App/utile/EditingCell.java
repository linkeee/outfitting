package App.utile;

import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.control.*;

public class EditingCell<T> extends TableCell<T, String> {
    private TextField textField;

    public EditingCell() {
    }

    @Override
    public void commitEdit(String newValue) {
        if (!isEditing() && !newValue.equals(this.getItem())) {
            TableView<T> tableView = this.getTableView();
            if (tableView != null) {
                TableColumn<T, String> column = this.getTableColumn();
                TableColumn.CellEditEvent<T, String> event =
                        new TableColumn.CellEditEvent<>(
                                tableView,
                                new TablePosition<>(tableView, this.getIndex(), column),
                                TableColumn.editCommitEvent(),
                                newValue);
                Event.fireEvent(column, event);
            }
        }
        super.commitEdit(newValue);
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private void createTextField() {
        if (getString().equals(Constant.doubleClickCellTip)) {
            textField = new TextField(null);
        } else {
            textField = new TextField(getString());
        }
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.focusedProperty().addListener(
                (ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) -> {
                    if (!arg2) {
                        commitEdit(textField.getText());
                    }
                }
        );
    }

    private String getString() {
        return getItem() == null ? "双击添加" : getItem();
    }
}
