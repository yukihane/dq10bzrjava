package yukihane.dq10bzrjava.entity;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author yuki
 */
@ToString
@EqualsAndHashCode
public class EnableRenkin {

    @Getter
    private String key;

    @Getter
    private List<String> values;

}
