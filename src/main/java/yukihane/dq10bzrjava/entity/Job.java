package yukihane.dq10bzrjava.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 装備可能職業
 *
 * @author yuki
 */
@ToString
@EqualsAndHashCode
public class Job {

    @Getter
    private String id;

    @Getter
    private String name;
}
