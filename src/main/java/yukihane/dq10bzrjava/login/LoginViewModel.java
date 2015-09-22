package yukihane.dq10bzrjava.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.saxsys.mvvmfx.ViewModel;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.concurrent.Worker.State;
import javafx.scene.web.WebEngine;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yukihane.dq10bzrjava.Constants;
import yukihane.dq10remote.communication.HappyService;
import yukihane.dq10remote.communication.HappyServiceFactory;
import yukihane.dq10remote.communication.dto.login.CharacterList;
import yukihane.dq10remote.communication.dto.login.LoginDto;
import yukihane.dq10remote.exception.HappyServiceException;

public class LoginViewModel implements ViewModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginViewModel.class);

    private static final String LOGIN_URL = "https://secure.square-enix.com/oauth/oa/oauthlogin?client_id=happy&redirect_uri=https%3A%2F%2Fhappy.dqx.jp%2Fcapi%2Flogin%2Fsecurelogin%2F&response_type=code&yl=1";

    private WebEngine engine;

    /**
     * ログイン処理が終了し, 画面を閉じるべきタイミングであればtrue.
     */
    private ReadOnlyBooleanWrapper finished = new ReadOnlyBooleanWrapper(false);

    public ReadOnlyBooleanProperty finishedProperty() {
        return finished.getReadOnlyProperty();
    }

    public boolean isFinished() {
        return finished.get();
    }

    void initialize(WebEngine engine) {
        this.engine = Objects.requireNonNull(engine);
        engine.getLoadWorker().stateProperty().addListener(
                (obs, oldValue, newValue) -> {
                    if (newValue == State.SUCCEEDED) {

                        String location = engine.getLocation();
                        if (!"https://happy.dqx.jp/capi/login/securelogin/".equals(location)) {
                            return;
                        }

                        try {
                            TransformerFactory transformerFactory = TransformerFactory
                            .newInstance();
                            Transformer transformer = transformerFactory.newTransformer();
                            StringWriter stringWriter = new StringWriter();
                            transformer.transform(new DOMSource(engine.getDocument()),
                                    new StreamResult(stringWriter));
                            String xml = stringWriter.getBuffer().toString();
                            loginCompleted(xml);
                        } catch (TransformerException | IOException e) {
                            LOGGER.error("error occured", e);
                        }

                    }
                });
    }

    public void loadLoginPage() {
        engine.load(LOGIN_URL);
    }

    private void loginCompleted(String xml) throws IOException {
        Pattern p = Pattern.compile("<PRE.+?>(.+)</PRE>");
        Matcher m = p.matcher(xml);
        if (m.find()) {
            String jsontext = m.group(1);
            ObjectMapper mapper = new ObjectMapper();
            LoginDto res = mapper.readValue(jsontext, LoginDto.class);

            HappyService service = HappyServiceFactory.getService(res.getSessionId());
            try {
                CharacterList character = res.getCharacterList().get(0);
                service.characterSelect(res.getCharacterList().get(0).getWebPcNo());
                publish(Constants.LOGIN_COMPLETED, service, res.getSessionId(), character);
            } catch (HappyServiceException ex) {
                LOGGER.error("login error", ex);
            }

            finished.set(true);
        }

    }
}
