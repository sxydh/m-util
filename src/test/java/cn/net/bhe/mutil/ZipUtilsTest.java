package cn.net.bhe.mutil;

import org.junit.jupiter.api.Test;

class ZipUtilsTest {

    @Test
    void comp() throws Exception {
        ZipUtils.comp("StrUtils.PHONE_CODE");
    }

    @Test
    void deComp() throws Exception {
        String deComp = ZipUtils.deComp("AddrUtils.CHN_LIST");
        System.out.println(deComp);
    }
}