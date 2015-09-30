package yukihane.dq10bzrjava.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 錬金効果.
 *
 * @author yuki
 */
@ToString
@EqualsAndHashCode
public class RenkinType {

    @Getter
    private int id;

    @Getter
    private double max;

    @Getter
    private double min;

    @Getter
    private String name;

    @Getter
    private int scale;

    @Getter
    private double step;
}
