package rpless.grass.gl.shader;

import javax.media.opengl.GL4;
import java.nio.file.Path;

public class VertexShader extends Shader {

    public VertexShader(Path path) {
        super(path);
    }

    public VertexShader(String source) {
        super(source);
    }

    @Override
    public int getType() {
        return GL4.GL_VERTEX_SHADER;
    }
}