package yukihane.dq10bzrjava.main.vm;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import yukihane.dq10bzrjava.entity.ItemCount;

/**
 * 「アイテム名」
 *
 * @author yuki
 */
public class ItemCountProperties {

    /**
     * アイテム名の選択肢.
     */
    final ReadOnlyListWrapper<ItemCount> values
        = new ReadOnlyListWrapper<>(FXCollections.observableArrayList());

    public ReadOnlyListProperty<ItemCount> valuesProperty() {
        return values.getReadOnlyProperty();
    }

    /**
     * 選択されたアイテム名.
     */
    final SimpleObjectProperty<ItemCount> selected
        = new SimpleObjectProperty<>();

    public SimpleObjectProperty<ItemCount> selectedProperty() {
        return selected;
    }

    /**
     * アイテム名が選択不可能か.
     */
    final ReadOnlyBooleanWrapper disabled
        = new ReadOnlyBooleanWrapper(true);

    public ReadOnlyBooleanProperty disabledProperty() {
        return disabled.getReadOnlyProperty();
    }

}
