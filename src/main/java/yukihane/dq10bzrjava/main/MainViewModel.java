package yukihane.dq10bzrjava.main;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import yukihane.dq10bzrjava.login.LoginView;

public class MainViewModel implements ViewModel {

    private final ReadOnlyStringWrapper id = new ReadOnlyStringWrapper("");
    private final ReadOnlyStringWrapper characterName = new ReadOnlyStringWrapper("");

    private final Command loginCommand;

    public MainViewModel() {
        loginCommand = new DelegateCommand(() -> new Action() {
            @Override
            protected void action() throws Exception {
                openLoginWindow();
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
        ViewTuple viewTuple = FluentViewLoader.fxmlView(LoginView.class).load();

        Parent root = viewTuple.getView();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

}
