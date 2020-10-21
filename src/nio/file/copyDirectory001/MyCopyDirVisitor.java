package nio.file.copyDirectory001;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Creating a custom FileVisitor implementation
 * The visitor pattern introduced in Java 1.7 is based on FileVisitor interface.
 * Instead of implementing this interface, this example extends SimpleFileVisitor which has default behavior to visit all files.
 */
public class MyCopyDirVisitor extends SimpleFileVisitor<Path> {

    private Path fromPath;
    private Path toPath;
    private StandardCopyOption copyOption;


    public MyCopyDirVisitor (Path fromPath, Path toPath, StandardCopyOption copyOption) {
        this.fromPath = fromPath;
        this.toPath = toPath;
        this.copyOption = copyOption;
    }

    public MyCopyDirVisitor(Path fromPath, Path toPath) {
        this(fromPath, toPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
        throws IOException
    {
        Path targetPath = toPath.resolve(fromPath.relativize(dir));
        if (!Files.exists(targetPath)) {
            Files.createDirectory(targetPath);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
        throws IOException
    {
        Files.copy(file, toPath.resolve(fromPath.relativize(file)), copyOption);
        return FileVisitResult.CONTINUE;
    }
}
