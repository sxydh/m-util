package cn.net.bhe.mutil;

import org.junit.jupiter.api.Test;

class ZipUtilsTest {

    @Test
    void comp() throws Exception {
        ZipUtils.comp("AddrUtils.CHN_ARR");
    }

    @Test
    void deComp() throws Exception {
        String deComp = ZipUtils.deComp("AddrUtils.CHN_ARR");
        System.out.println(deComp);
    }
}