package rpless.grass.gl.shader;

import javax.media.opengl.GL4;

public class FragmentShader extends Shader {

    public FragmentShader(String source) {
        super(source);
    }

    @Override
    public int getType() {
        return GL4.GL_FRAGMENT_SHADER;
    }
}