package yukihane.dq10bzrjava.resource;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import yukihane.dq10bzrjava.entity.Quality;

public class QualityLoaderTest {

    @Test
    public void testLoad() {
        final List<Quality> res = new QualityLoader().load();
        assertNotEquals(0, res.size());
    }

}
