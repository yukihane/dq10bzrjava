package yukihane.dq10bzrjava.resource;

import yukihane.dq10bzrjava.entity.Quality;

/**
 * できのよさをファイルから読む.
 * @author yuki
 */
public class QualityLoader extends JsonFileLoader<Quality> {

    @Override
    protected String getPath() {
        return "assets/qualitySet.json";
    }

    @Override
    protected Class<Quality> getType() {
        return Quality.class;
    }

}
