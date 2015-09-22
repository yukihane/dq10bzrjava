package yukihane.dq10bzrjava.entity;

import lombok.Getter;
import lombok.ToString;
import yukihane.dq10remote.communication.dto.bazaar.LargeCategoryValueList;

/**
 * バザー検索の「種類」.
 *
 * @author yuki
 */
@ToString
public class LargeCategory {

    @Getter
    private final String largeCategoryName;

    @Getter
    private final int largeCategoryId;

    @Getter
    private final boolean isSmallCategory;

    @Getter
    private final int smallCategoryId;

    public static LargeCategory from(LargeCategoryValueList lc) {
        return new LargeCategory(
            lc.getLargeCategoryName(),
            lc.getLargeCategoryId(),
            lc.getIsSmallCategory(),
            lc.getSmallCategoryId());
    }

    public LargeCategory(String largeCategoryName, int largeCategoryId, boolean isSmallCategory, int smallCategoryId) {
        this.largeCategoryName = largeCategoryName;
        this.largeCategoryId = largeCategoryId;
        this.isSmallCategory = isSmallCategory;
        this.smallCategoryId = smallCategoryId;
    }
}
