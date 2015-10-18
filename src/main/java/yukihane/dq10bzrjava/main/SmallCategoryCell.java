package yukihane.dq10bzrjava.main;

import javafx.scene.control.ListCell;
import yukihane.dq10bzrjava.entity.SmallCategory;

/**
 *
 * @author yuki
 */
public class SmallCategoryCell extends ListCell<SmallCategory> {

    @Override
    protected void updateItem(SmallCategory item, boolean empty) {
        super.updateItem(item, empty);
        setText(item == null ? "" : item.getSmallCategoryName());
    }
}
