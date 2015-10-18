package yukihane.dq10bzrjava.resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * jsonファイルから設定値を読み込みます.
 *
 * @author yuki
 */
public abstract class JsonFileLoader<T> {

    public final List<T> load() {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final URL difficultySetUrl = getClass().getClassLoader().getResource(getPath());
            return mapper.readValue(difficultySetUrl, new TypeReference<List<T>>() {
            });
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected abstract String getPath();
}
