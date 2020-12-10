package nio.file.copyDirectory001;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.EnumSet;

/**
 * This example demonstrate how to copy entire directory contents to another directory. The top level destination directory can have a different name.
 * It's based on Java 1.7 Java IO visitor pattern to recursively visit all files in a file tree.
 */
public class CopyUtil {

    public static void copyDirectoryContent(File sourceFolder, File destinationFolder) throws IOException {
        if (sourceFolder.isDirectory()) {

            if (destinationFolder.exists() && destinationFolder.isFile()) {
                throw new IllegalArgumentException(
                        "Destination exists but is not a folder: "
                                + destinationFolder
                                .getAbsolutePath());
            }

            if (!destinationFolder.exists()) {
                Files.createDirectory(destinationFolder.toPath());
            }

            for (File file : sourceFolder.listFiles()) {
                if (file.isDirectory()) {
                    copyDirectory(file, destinationFolder);
                } else {
                    copyFile(file, destinationFolder);
                }
            }
        }
    }

    public static void copyDirectory(File fromFile, File toParentFile) throws IOException {
        Path from = fromFile.toPath();
        Path to = Paths.get(toParentFile.getAbsolutePath() + File.separatorChar + fromFile .getName());

        Files.walkFileTree(from, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, new MyCopyDirVisitor(from, to));
    }

    public static void copyFile(File toCopy, File mainDestination) throws IOException {
        if (!mainDestination.exists()) {
            mainDestination.mkdirs();
        }
        Path to = Paths.get(mainDestination.getAbsolutePath() + File.separatorChar + toCopy.getName());

        Files.copy(toCopy.toPath(), to, StandardCopyOption.REPLACE_EXISTING);
    }
}