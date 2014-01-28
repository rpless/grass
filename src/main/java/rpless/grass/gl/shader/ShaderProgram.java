package rpless.grass.gl.shader;

import rpless.grass.math.Matrix4f;
import rpless.grass.math.Matrix4fUtil;

import javax.media.opengl.GL4;
import java.util.Map;

public class ShaderProgram {
    private int handle;
    private Map<String, Integer> attributes, uniforms;

    ShaderProgram(int handle, Map<String, Integer> attributes, Map<String, Integer> uniforms) {
        this.handle = handle;
        this.attributes = attributes;
        this.uniforms = uniforms;
    }

    public void useProgram(GL4 gl) {
        gl.glUseProgram(handle);
    }

    public void disuseProgram(GL4 gl) {
        gl.glUseProgram(0);
    }

    public void delete(GL4 gl) {
        gl.glDeleteProgram(handle);
    }

    public int getAttributeLocation(String name) {
        return attributes.get(name);
    }

    public int getUniformLocation(String name) {
        return uniforms.get(name);
    }

    public void uniform(GL4 gl, String location, Matrix4f matrix) {
        gl.glUniformMatrix4fv(this.getUniformLocation(location), 1, false, matrix.toArray(), 0);
    }
}