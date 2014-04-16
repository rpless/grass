package rpless.grass.gl.shader;

import rpless.grass.gl.NativeObject;
import rpless.grass.math.Matrix4f;
import rpless.grass.math.Matrix4fUtil;

import javax.media.opengl.GL3;
import java.util.Map;

public class ShaderProgram extends NativeObject {
    private Map<String, Integer> attributes, uniforms;

    ShaderProgram(int handle, Map<String, Integer> attributes, Map<String, Integer> uniforms) {
        super(handle);
        this.attributes = attributes;
        this.uniforms = uniforms;
    }

    public void useProgram(GL3 gl) {
        gl.glUseProgram(getHandle());
    }

    public void disuseProgram(GL3 gl) {
        gl.glUseProgram(0);
    }

    public void delete(GL3 gl) {
        gl.glDeleteProgram(getHandle());
    }

    public int getAttributeLocation(String name) {
        return attributes.get(name);
    }

    public int getUniformLocation(String name) {
        return uniforms.get(name);
    }

    public void uniform(GL3 gl, String location, Matrix4f matrix) {
        gl.glUniformMatrix4fv(this.getUniformLocation(location), 1, false, matrix.toArray(), 0);
    }
}