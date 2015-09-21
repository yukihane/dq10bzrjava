package yukihane.dq10bzrjava.main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;
import yukihane.dq10don.communication.dto.login.CharacterList;

public class MainView implements FxmlView<MainViewModel>, Initializable {

    @InjectViewModel
    private MainViewModel viewModel;

    @FXML
    private Label idLabel;

    @FXML
    private ComboBox<CharacterList> characterComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idLabel.textProperty().bind(viewModel.idProperty());
        characterComboBox.itemsProperty().bind(viewModel.getCharactersProperty());
        characterComboBox.setCellFactory(new Callback<ListView<CharacterList>, ListCell<CharacterList>>() {
            @Override
            public ListCell<CharacterList> call(ListView<CharacterList> param) {
                return new ListCell<CharacterList>() {
                    @Override
                    protected void updateItem(CharacterList item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getCharacterName()
                                    + " (" + item.getSmileUniqueNo() + ")");
                        }
                    }
                };
            }
        });
        characterComboBox.setConverter(new StringConverter<CharacterList>() {

            @Override
            public String toString(CharacterList object) {
                if (object == null) {
                    return null;
                }
                return object.getCharacterName();
            }

            @Override
            public CharacterList fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    public void loginAction() {
        viewModel.getLoginCommand().execute();
    }
}
