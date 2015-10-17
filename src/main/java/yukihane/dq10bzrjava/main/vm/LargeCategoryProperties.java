package yukihane.dq10bzrjava.main.vm;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import yukihane.dq10bzrjava.entity.LargeCategory;

/**
 * 種類
 *
 * @author yuki
 */
public class LargeCategoryProperties {

    /**
     * 種類の選択肢.
     */
    final ReadOnlyListWrapper<yukihane.dq10bzrjava.entity.LargeCategory> values
        = new ReadOnlyListWrapper<>(FXCollections.observableArrayList());

    public ReadOnlyListProperty<yukihane.dq10bzrjava.entity.LargeCategory> valuesProperty() {
        return values.getReadOnlyProperty();
    }

    /**
     * 選択された種類.
     */
    final SimpleObjectProperty<yukihane.dq10bzrjava.entity.LargeCategory> selected
        = new SimpleObjectProperty<>();

    public SimpleObjectProperty<yukihane.dq10bzrjava.entity.LargeCategory> selectedProperty() {
        return selected;
    }

    public LargeCategory getSelected() {
        return selected.get();
    }

}
