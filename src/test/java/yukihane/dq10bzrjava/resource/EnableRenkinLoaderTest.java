/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yukihane.dq10bzrjava.resource;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import yukihane.dq10bzrjava.entity.EnableRenkin;

/**
 *
 * @author yuki
 */
public class EnableRenkinLoaderTest {

    @Test
    public void testLoad() {
        List<EnableRenkin> res = new EnableRenkinLoader().load();
        assertFalse("ロードができている", res.isEmpty());
        assertEquals("101", res.get(0).getKey());
    }

    public static void main(String[] args) {
        List<EnableRenkin> res = new EnableRenkinLoader().load();
    }
}
