package yukihane.dq10bzrjava.login;

import de.saxsys.mvvmfx.ViewModel;
import java.util.Objects;
import javafx.scene.web.WebEngine;

public class LoginViewModel implements ViewModel {

    private static final String LOGIN_URL = "https://secure.square-enix.com/oauth/oa/oauthlogin?client_id=happy&redirect_uri=https%3A%2F%2Fhappy.dqx.jp%2Fcapi%2Flogin%2Fsecurelogin%2F&response_type=code&yl=1";

    private WebEngine engine;

    void initialize(WebEngine engine) {
        this.engine = Objects.requireNonNull(engine);
    }

    public void loadLoginPage() {
        engine.load(LOGIN_URL);
    }

}
