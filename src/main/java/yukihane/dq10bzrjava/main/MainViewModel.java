package yukihane.dq10bzrjava.main;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.utils.commands.Action;
import de.saxsys.mvvmfx.utils.commands.Command;
import de.saxsys.mvvmfx.utils.commands.DelegateCommand;
import de.saxsys.mvvmfx.utils.notifications.NotificationObserver;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import yukihane.dq10bzrjava.Constants;
import yukihane.dq10bzrjava.login.LoginView;
import yukihane.dq10don.communication.HappyService;
import yukihane.dq10don.communication.dto.login.CharacterList;

public class MainViewModel implements ViewModel {

    private final ReadOnlyStringWrapper id = new ReadOnlyStringWrapper("");
    private final ReadOnlyListWrapper<CharacterList> characters;

    private final Command loginCommand;

    private HappyService service;

    public MainViewModel() {
        ObservableList<CharacterList> charalist = FXCollections.observableArrayList();
        characters = new ReadOnlyListWrapper<>(charalist);
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

    public ReadOnlyListProperty<CharacterList> getCharactersProperty() {
        return characters;
    }

    public ObservableList<CharacterList> getCharacters() {
        return characters.get();
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

        NotificationObserver observer = (String string, Object... os) -> {
            service = (HappyService) os[0];
            CharacterList character = (CharacterList) os[1];
        };

        MvvmFX.getNotificationCenter().subscribe(
                viewTuple.getViewModel(),
                Constants.LOGIN_COMPLETED,
                observer);
    }

}
