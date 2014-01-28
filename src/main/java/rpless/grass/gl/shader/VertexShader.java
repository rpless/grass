package rpless.grass.gl.shader;

import javax.media.opengl.GL4;

public class VertexShader extends Shader {

    public VertexShader(String source) {
        super(source);
    }

    @Override
    public int getType() {
        return GL4.GL_VERTEX_SHADER;
    }
}