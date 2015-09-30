package yukihane.dq10bzrjava.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 錬金効果数
 *
 * @author yuki
 */
@ToString
@EqualsAndHashCode
public class NumOfRenkin {

    @Getter
    private String text;

    @Getter
    private int min;

    @Getter
    private int max;
}
