package yukihane.dq10bzrjava.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import yukihane.dq10remote.communication.dto.bazaar.SmallCategoryValueList;

/**
 * バザー検索の「種類2」
 *
 * @author yuki
 */
@EqualsAndHashCode
@ToString
public class SmallCategory implements Entity {

    @Getter
    private final String smallCategoryName;

    @Getter
    private final int smallCategoryId;

    public SmallCategory(String smallCategoryName, int smallCategoryId) {
        this.smallCategoryName = smallCategoryName;
        this.smallCategoryId = smallCategoryId;
    }

    public static SmallCategory from(SmallCategoryValueList t) {
        return new SmallCategory(t.getSmallCategoryName(), t.getSmallCategoryId());
    }

    @Override
    public String getDisplayText() {
        return getSmallCategoryName();
    }

}
