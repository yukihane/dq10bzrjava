package yukihane.dq10bzrjava.login;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

public class LoginView implements FxmlView<LoginViewModel>, Initializable {

    @InjectViewModel
    private LoginViewModel viewModel;

    @FXML
    private WebView webView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewModel.initialize(webView.getEngine());
        viewModel.loadLoginPage();
    }

}
