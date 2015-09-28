package yukihane.dq10bzrjava.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Difficulty {

    @Getter
    private String text;

    @Getter
    private int parameter;
}
