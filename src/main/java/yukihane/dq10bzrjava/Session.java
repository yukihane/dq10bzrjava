package yukihane.dq10bzrjava;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author yuki
 */
@ToString
public class Session {

    private static final Logger LOGGER = LoggerFactory.getLogger(Session.class);
    private static final String KEY_SESSION = "sessionId";
    private static final String KEY_DISPNAME = "displayName";

    private static final File APP_DIR = new File(System.getProperty("user.home"), ".dq10bzrjava");
    private static final File SESSION_FILE = new File(APP_DIR, "session.properties");

    private static final Session INSTANCE = new Session();

    @Getter
    @Setter
    private String sessionId = "";

    @Getter
    @Setter
    private String displayName = "";

    private Session() {
        LOGGER.debug("app dir: {}", APP_DIR.getAbsolutePath());
        APP_DIR.mkdir();
        if (!APP_DIR.exists()) {
            throw new RuntimeException("failed creating app directory. (" + APP_DIR + ")");
        }

        try (Reader reader = new FileReader(SESSION_FILE)) {
            Properties session = new Properties();
            session.load(reader);
            this.sessionId = session.getProperty(KEY_SESSION);
            this.displayName = session.getProperty(KEY_DISPNAME);
        } catch (FileNotFoundException ex) {
            LOGGER.info("Session file is not found: {}", SESSION_FILE);
        } catch (IOException ex) {
            LOGGER.error("file read error", ex);
        }
    }

    public static Session getInstance() {
        return INSTANCE;
    }

    public void save() {
        try (Writer writer = new FileWriter(SESSION_FILE)) {
            Properties session = new Properties();
            session.setProperty(KEY_SESSION, sessionId);
            session.setProperty(KEY_DISPNAME, displayName);
            session.store(writer, "dq10bzrjava session file");
        } catch (IOException ex) {
            LOGGER.error("file save error", ex);
        }
    }
}
