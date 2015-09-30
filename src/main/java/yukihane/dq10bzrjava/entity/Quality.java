package yukihane.dq10bzrjava.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * できのよさ.
 *
 * @author yuki
 */
@ToString
@EqualsAndHashCode
public class Quality {

    @Getter
    private String text;

    @Getter
    private int min;

    @Getter
    private int max;
}
