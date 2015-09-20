package yukihane.dq10bzrjava.login;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class LoginView implements FxmlView<LoginViewModel>, Initializable {

    @InjectViewModel
    private LoginViewModel viewModel;

    @FXML
    private WebView webView;

    @FXML
    private TextField urlField;

    @FXML
    private Button openButton;

    private WebEngine engine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(location);

        engine = webView.getEngine();
        Worker<Void> worker = engine.getLoadWorker();

        worker.stateProperty().addListener(
                (ObservableValue<? extends Worker.State> observable,
                        Worker.State oldValue, Worker.State newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED) {
                        String url = engine.getLocation();
                        if (url != null && !url.isEmpty()) {
                            urlField.setText(url);
                        }
                    }
                });

        EventHandler<ActionEvent> handler = (event) -> {
            loadUrl();
        };

        urlField.setOnAction(handler);
        openButton.setOnAction(handler);
    }

    private void loadUrl() {
        String url = urlField.getText();
        if (Objects.nonNull(url) && !url.trim().isEmpty()) {
            engine.load(url);
        }
    }

}
