package org.test;

import com.yunde.frame.tools.UnicodeKit;
import org.junit.jupiter.api.Test;

/**
 * @author laisy
 * @date 2019/4/19
 * @description
 */
public class TestUnicodeKit {
    @Test
    public void run() {
        String source = "多发发打发法撒旦发生发对方";
        String target = UnicodeKit.encode(source);
        System.out.println(target);
        System.out.println(UnicodeKit.decode(target));
    }
}