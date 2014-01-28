package rpless.grass.gl.shader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class Shader {

    private static String getText(Path path) {
        StringBuilder builder = new StringBuilder();
        try {
            for (String line : Files.readAllLines(path, Charset.defaultCharset())) {
                builder.append(line).append("\n");
            }
            return builder.append("\n").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String source;
    private int handle;

    public Shader(Path path) {
        this(getText(path));
    }

    public Shader(String source) {
        this.source = source;
        this.handle = -1;
    }

    public String getSource() {
        return source;
    }

    public abstract int getType();

    int getHandle() {
        return handle;
    }

    void setHandle(int handle) {
        this.handle = handle;
    }
}