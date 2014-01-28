package rpless.grass.gl.shader;

import rpless.grass.gl.NativeObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class Shader extends NativeObject {

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

    public Shader(Path path) {
        this(getText(path));
    }

    public Shader(String source) {
        super(-1);
        this.source = source;
    }

    protected void setHandle(int handle) {
        super.setHandle(handle);
    }

    public String getSource() {
        return source;
    }

    public abstract int getType();
}