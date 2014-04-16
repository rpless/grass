package rpless.grass.gl.shader;

import javax.media.opengl.GL3;
import java.nio.file.Path;

public class GeometryShader extends Shader {

    public GeometryShader(Path path) {
        super(path);
    }

    public GeometryShader(String source) {
        super(source);
    }

    @Override
    public int getType() {
        return GL3.GL_GEOMETRY_SHADER;
    }
}