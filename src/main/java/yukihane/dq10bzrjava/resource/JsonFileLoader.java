package yukihane.dq10bzrjava.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * jsonファイルから設定値を読み込みます.
 *
 * @author yuki
 * @param <T> 読み込む型.
 */
public abstract class JsonFileLoader<T> {

    public final List<T> load() {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final URL difficultySetUrl = getClass().getClassLoader().getResource(getPath());
            final CollectionType collectionType
                = mapper.getTypeFactory().constructCollectionType(List.class, getType());
            return mapper.readValue(difficultySetUrl, collectionType);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected abstract String getPath();

    protected abstract Class<T> getType();
}
