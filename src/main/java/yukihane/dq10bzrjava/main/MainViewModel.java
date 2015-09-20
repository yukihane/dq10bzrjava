package yukihane.dq10bzrjava.main;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

public class MainViewModel implements ViewModel {

    private final ReadOnlyStringWrapper id = new ReadOnlyStringWrapper("");
    private final ReadOnlyStringWrapper characterName = new ReadOnlyStringWrapper("");

    public ReadOnlyStringProperty idProperty() {
        return id.getReadOnlyProperty();
    }

    public String getId() {
        return id.get();
    }
    
    public ReadOnlyStringProperty characterNameProperty() {
        return characterName.getReadOnlyProperty();
    }

    public String getCharacterName() {
        return characterName.get();
    }
}
