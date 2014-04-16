package rpless.grass.gl.shader;

import javax.media.opengl.GL3;
import java.nio.file.Path;

public class FragmentShader extends Shader {

    public FragmentShader(Path path) {
        super(path);
    }

    public FragmentShader(String source) {
        super(source);
    }

    @Override
    public int getType() {
        return GL3.GL_FRAGMENT_SHADER;
    }
}