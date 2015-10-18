package yukihane.dq10bzrjava.main.vm;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import yukihane.dq10bzrjava.entity.Quality;

/**
 *
 * @author yuki
 */
public class QualityProperties {

    /**
     * できのよさの選択肢.
     */
    final ReadOnlyListWrapper<Quality> values
        = new ReadOnlyListWrapper<>(FXCollections.observableArrayList());

    public ReadOnlyListProperty<Quality> valuesProperty() {
        return values.getReadOnlyProperty();
    }

    /**
     * 選択されたできのよさ.
     */
    final SimpleObjectProperty<Quality> selected
        = new SimpleObjectProperty<>();

    public SimpleObjectProperty<Quality> selectedProperty() {
        return selected;
    }

    /**
     * できのよさが選択不可能か.
     */
    final ReadOnlyBooleanWrapper disabled
        = new ReadOnlyBooleanWrapper(true);

    public ReadOnlyBooleanProperty disabledProperty() {
        return disabled.getReadOnlyProperty();
    }
}
