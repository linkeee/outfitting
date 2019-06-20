package App.utile;

import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.control.*;

public class EditingCell<T> extends TableCell<T, String> {
    private TextField textField;

    public EditingCell() {
    }

    @Override
    public void commitEdit(String inputValue) {
        if (!isEditing() && !inputValue.equals(this.getItem())) {
            TableView<T> tableView = this.getTableView();
            if (tableView != null) {
                TableColumn<T, String> column = this.getTableColumn();
                TableColumn.CellEditEvent<T, String> event =
                        new TableColumn.CellEditEvent<>(
                                tableView,
                                new TablePosition<>(tableView, this.getIndex(), column),
                                TableColumn.editCommitEvent(),
                                inputValue);
                Event.fireEvent(column, event);

            }
        }
        super.commitEdit(inputValue);
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

        // 双击后，无输入退出编辑，设置cell的显示状态
        setText(getString());
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
                // 初始化刷新表格时，将cell内容设置为getString()方法返回的字符串
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
        // 监听cell中的textField，编辑状态下有输入时，失焦后将输入提交给commitEdit()方法
        textField.focusedProperty().addListener(
                (ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) -> {
                    if (!arg2) {
                        commitEdit(textField.getText());
                        System.out.println("createTextField" + textField.getText());
                    }
                }
        );
    }

    private String getString() {
        return (getItem() == null || getItem().equals("")) ? "双击添加" : getItem();
    }
}
