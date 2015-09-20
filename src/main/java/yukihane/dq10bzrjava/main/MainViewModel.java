package yukihane.dq10bzrjava.main;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import java.util.function.Supplier;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

public class MainViewModel implements ViewModel {

    private final ReadOnlyStringWrapper id = new ReadOnlyStringWrapper("");
    private final ReadOnlyStringWrapper characterName = new ReadOnlyStringWrapper("");

    private final Command loginCommand;

    public MainViewModel() {
        loginCommand = new DelegateCommand(new Supplier<Action>() {
            @Override
            public Action get() {
                openLoginWindow();
                return null;
            }
        });
    }

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

    public Command getLoginCommand() {
        return loginCommand;
    }

    private void openLoginWindow() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
