package yukihane.dq10bzrjava.view.cell;

import yukihane.dq10bzrjava.entity.Entity;
import javafx.scene.control.ListCell;

/**
 * @author yuki
 * @param <T> 表示対象の型
 */
public class EntityCell<T extends Entity> extends ListCell<T> {

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        setText(item == null ? "" : item.getDisplayText());
    }

}
