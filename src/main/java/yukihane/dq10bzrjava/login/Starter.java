package yukihane.dq10bzrjava.login;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author yuki
 */
public class Starter extends Application {

    public static void main(String... args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Hello World Application");

        ViewTuple<LoginView, LoginViewModel> viewTuple
                = FluentViewLoader.fxmlView(LoginView.class).load();

        Parent root = viewTuple.getView();
        stage.setScene(new Scene(root));

        stage.show();
    }

}
