package yukihane.dq10bzrjava.resource;

import yukihane.dq10bzrjava.entity.Difficulty;

/**
 * できのよさをファイルからロードします.
 *
 * @author yuki
 */
public class DifficultyLoader extends JsonFileLoader<Difficulty> {

    @Override
    protected String getPath() {
        return "assets/difficultySet.json";
    }

}
