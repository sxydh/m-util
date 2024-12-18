package cn.net.bhe.mutil;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class FileServerUtilsTest {

    @Test
    void build() throws IOException {
        String root = FlUtils.combine(FlUtils.getRoot(), "ROOT");
        FlUtils.mkdir(root);

        FileServerUtils.FileServer fileServer = FileServerUtils.build(50);
        fileServer.start();
        fileServer.stop();

        fileServer = FileServerUtils.build("localhost", 50, root, "admin", "123");
        fileServer.start();
        fileServer.stop();
    }
}