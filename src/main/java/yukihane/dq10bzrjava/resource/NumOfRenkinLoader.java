package yukihane.dq10bzrjava.resource;

import yukihane.dq10bzrjava.entity.NumOfRenkin;

/**
 * 錬金効果数ローダー.
 *
 * @author yuki
 */
public class NumOfRenkinLoader extends JsonFileLoader<NumOfRenkin> {

    @Override
    protected String getPath() {
        return "assets/numOfRenkinSet.json";
    }

    @Override
    protected Class<NumOfRenkin> getType() {
        return NumOfRenkin.class;
    }

}
