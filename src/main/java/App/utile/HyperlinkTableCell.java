package App.utile;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 带有HyperLink的TableCell。
 * @param <T>
 */
public class HyperlinkTableCell<T> extends TableCell<T, String> {
//    private Consumer<T> onaction;   //hyperlink点击事件
//    private T entity;   //目标实体

    private final Hyperlink hyperlink;

    public HyperlinkTableCell() {
//        this.onaction = callback;
        hyperlink = new Hyperlink();
        hyperlink.setUnderline(true);
        hyperlink.setOnAction(event -> {
            try {
//                String regex = "'([^']*)'";
//                String str = hyperlink.toString();
//                Matcher matcher = Pattern.compile(regex).matcher(str);
//                System.out.println(matcher.group());
                String[] temp = hyperlink.toString().split("'");
                Desktop.getDesktop().open(new File(temp[1]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
//        if (getIndex() > -1 && getTableView().getItems().size() > getIndex()) {
//            entity = getTableView().getItems().get(getIndex());
//        }
        if (StringUtils.isBlank(item)) {
            hyperlink.setText("请点击“修改”上传文件文件");
//            hyperlink.setOnAction(event -> onaction.accept(entity));
        } else {
            hyperlink.setText(item);
//            if (getTableView().getItems().size() > getIndex()) {
//                setGraphic(new Label("not uploaded"));
//            }
        }

        setGraphic(hyperlink);
        setGraphic(empty? null:hyperlink);
    }
}
