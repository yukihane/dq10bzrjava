package yukihane.dq10bzrjava.main;

import javafx.scene.control.ListCell;
import yukihane.dq10bzrjava.entity.LargeCategory;

/**
 * 「種類」リストセル.
 * <a href="http://docs.oracle.com/javase/jp/8/javafx/api/javafx/scene/control/Cell.html">javadoc</a>
 * を参考に作成.
 *
 * @author yuki
 */
public class LargeCategoryCell extends ListCell<LargeCategory> {

    @Override
    protected void updateItem(LargeCategory item, boolean empty) {
        super.updateItem(item, empty);
        setText(item == null ? "" : item.getLargeCategoryName());
    }

}
