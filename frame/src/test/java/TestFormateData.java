import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author: suwy
 * @date: 2018/11/15
 * @decription:
 */
public class TestFormateData {

    @Test
    public void testFormateData() {
        List list = new ArrayList() {{
            for (int i = 0; i < 3; i++) {
                add(new HashMap<Object, Object>(){{
                    put("id", Collections.nCopies(2, "a"));
                    put("name", Collections.nCopies(5,  "b"));
                }});
            }
        }};
        System.out.println(list);

    }
}
