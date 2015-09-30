package yukihane.dq10bzrjava.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 難易度.
 * @author yuki
 */
@ToString
@EqualsAndHashCode
public class Difficulty {

    @Getter
    private String text;

    @Getter
    private int parameter;
}