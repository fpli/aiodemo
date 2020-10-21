package nio.file.copyDirectory001;

import java.io.File;
import java.io.IOException;

public class CopyUtilTest {

    public static void main (String[] args) throws IOException {
        CopyUtil.copyDirectoryContent(new File("d:\\temp"), new File("d:\\temp-copy"));
    }
}
