package yukihane.dq10bzrjava.main.vm;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import yukihane.dq10bzrjava.entity.SmallCategory;

/**
 * 種類2
 *
 * @author yuki
 */
public class SmallCategoryProperties {

    /**
     * 種類2の選択肢.
     */
    final ReadOnlyListWrapper<SmallCategory> values
        = new ReadOnlyListWrapper<>(FXCollections.observableArrayList());

    public ReadOnlyListProperty<SmallCategory> valuesProperty() {
        return values.getReadOnlyProperty();
    }

    /**
     * 選択された種類2.
     */
    final SimpleObjectProperty<SmallCategory> selected
        = new SimpleObjectProperty<>();

    public SimpleObjectProperty<SmallCategory> selectedProperty() {
        return selected;
    }

    public SmallCategory getSelected() {
        return selected.get();
    }
}
