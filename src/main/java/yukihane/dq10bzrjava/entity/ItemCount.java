package yukihane.dq10bzrjava.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import yukihane.dq10remote.communication.dto.bazaar.ItemCountValueList;

/**
 * アイテムの実体.
 *
 * @author yuki
 */
@ToString
@EqualsAndHashCode
public class ItemCount implements Entity {

    @Getter
    private final String itemName;

    @Getter
    private final String webItemId;

    public ItemCount(String itemName, String webItemId) {
        this.itemName = itemName;
        this.webItemId = webItemId;
    }

    public static ItemCount from(ItemCountValueList t) {
        return new ItemCount(t.getItemName(), t.getWebItemId());
    }

    @Override
    public String getDisplayText() {
        return getItemName();
    }

}
