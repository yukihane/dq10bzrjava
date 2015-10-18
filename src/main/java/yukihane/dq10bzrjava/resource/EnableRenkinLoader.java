package yukihane.dq10bzrjava.resource;

import yukihane.dq10bzrjava.entity.EnableRenkin;

/**
 *
 * @author yuki
 */
public class EnableRenkinLoader extends JsonFileLoader<EnableRenkin> {

    @Override
    protected String getPath() {
        return "assets/enableRenkinSet.json";
    }

    @Override
    protected Class<EnableRenkin> getType() {
        return EnableRenkin.class;
    }

}
