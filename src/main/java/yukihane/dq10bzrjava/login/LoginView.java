package yukihane.dq10bzrjava.login;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class LoginView implements FxmlView<LoginViewModel>, Initializable {

    @InjectViewModel
    private LoginViewModel viewModel;

    @FXML
    private WebView webView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewModel.initialize(webView.getEngine());
        viewModel.finishedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue == true) {
                Stage stage = (Stage) webView.getScene().getWindow();
                stage.close();
            }
        });
        viewModel.loadLoginPage();
    }

}
