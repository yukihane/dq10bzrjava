package yukihane.dq10bzrjava.main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class MainView implements FxmlView<MainViewModel>, Initializable {

    @InjectViewModel
    private MainViewModel viewModel;

    @FXML
    private Label characterLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        characterLabel.textProperty().bind(viewModel.characterNameProperty());
    }

    @FXML
    public void loginAction() {
        viewModel.getLoginCommand().execute();
    }
}
