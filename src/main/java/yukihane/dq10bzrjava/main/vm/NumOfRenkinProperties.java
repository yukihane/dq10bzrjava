package yukihane.dq10bzrjava.main.vm;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import yukihane.dq10bzrjava.entity.NumOfRenkin;
import yukihane.dq10bzrjava.entity.Quality;

/**
 * 錬金効果数に関連するプロパティ.
 *
 * @author yuki
 */
public class NumOfRenkinProperties {

    /**
     * できのよさの選択肢.
     */
    final ReadOnlyListWrapper<NumOfRenkin> values
        = new ReadOnlyListWrapper<>(FXCollections.observableArrayList());

    public ReadOnlyListProperty<NumOfRenkin> valuesProperty() {
        return values.getReadOnlyProperty();
    }

    /**
     * 選択されたできのよさ.
     */
    final SimpleObjectProperty<NumOfRenkin> selected
        = new SimpleObjectProperty<>();

    public SimpleObjectProperty<NumOfRenkin> selectedProperty() {
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
