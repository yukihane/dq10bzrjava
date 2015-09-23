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
public class SmallCategory {

    @Getter
    private final int smallCategoryId;

    @Getter
    private final String smallCategoryName;

    public SmallCategory(int smallCategoryId, String smallCategoryName) {
        this.smallCategoryId = smallCategoryId;
        this.smallCategoryName = smallCategoryName;
    }

    public static SmallCategory from(SmallCategoryValueList t) {
        return new SmallCategory(t.getSmallCategoryId(), t.getSmallCategoryName());
    }

}
