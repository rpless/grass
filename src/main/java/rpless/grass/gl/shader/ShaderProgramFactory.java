package rpless.grass.gl.shader;

import com.jogamp.common.nio.Buffers;

import javax.media.opengl.GL4;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShaderProgramFactory {

    public ShaderProgram makeShader(GL4 gl, Shader... shaders) {
        List<Shader> shaderList = Arrays.asList(shaders);
        int handle = gl.glCreateProgram();
        for (Shader shader : shaderList) {
            compileShader(gl, shader);
            gl.glAttachShader(handle, shader.getHandle());
        }

        gl.glLinkProgram(handle);
        if (!isLinked(gl, handle)) {
            printLinkerError(gl, handle);
        }
        for (Shader shader : shaderList) {
            gl.glDetachShader(handle, shader.getHandle());
            gl.glDeleteShader(shader.getHandle());
        }
        return new ShaderProgram(handle, getAttributeMap(gl, handle), getUniforms(gl, handle));
    }

    private Map<String, Integer> getAttributeMap(GL4 gl, int handle) {
        Map<String, Integer> attributes = new HashMap<>();
        IntBuffer countBuffer = IntBuffer.allocate(1);
        gl.glGetProgramiv(handle, GL4.GL_ACTIVE_ATTRIBUTES, countBuffer);
        int limit = countBuffer.get();
        for (int i = 0; i < limit; i++) {
            IntBuffer length = IntBuffer.allocate(1);
            ByteBuffer buffer = ByteBuffer.allocate(100);
            gl.glGetActiveAttrib(handle, i, 100, length, IntBuffer.allocate(1), IntBuffer.allocate(1), buffer);
            byte[] byteArray = new byte[buffer.remaining()];
            buffer.get(byteArray);
            String name = new String(byteArray).trim();
            attributes.put(name, gl.glGetAttribLocation(handle, name));
        }
        return attributes;
    }

    private Map<String, Integer> getUniforms(GL4 gl, int handle) {
        Map<String, Integer> uniforms = new HashMap<>();
        IntBuffer countBuffer = IntBuffer.allocate(1);
        gl.glGetProgramiv(handle, GL4.GL_ACTIVE_UNIFORMS, countBuffer);
        int limit = countBuffer.get();
        for (int i = 0; i < limit; i++) {
            IntBuffer length = IntBuffer.allocate(1);
            ByteBuffer buffer = ByteBuffer.allocate(100);
            gl.glGetActiveUniformName(handle, i, 100, length, buffer);
            byte[] byteArray = new byte[buffer.remaining()];
            buffer.get(byteArray);
            String name = new String(byteArray).trim();
            uniforms.put(name, gl.glGetUniformLocation(handle, name));
        }
        return uniforms;
    }

    private void printLinkerError(GL4 gl, int handle) {
        IntBuffer logLengthBuffer = Buffers.newDirectIntBuffer(1);
        gl.glGetProgramiv(handle, GL4.GL_INFO_LOG_LENGTH, logLengthBuffer);
        int length = logLengthBuffer.get();

        ByteBuffer data = Buffers.newDirectByteBuffer(length);
        gl.glGetProgramInfoLog(handle, length, null, data);
        byte[] bytes = new byte[length];
        data.get(bytes);
        System.err.println(new String(bytes));
    }

    private boolean isLinked(GL4 gl, int handle) {
        IntBuffer status = Buffers.newDirectIntBuffer(1);
        gl.glGetProgramiv(handle, GL4.GL_INFO_LOG_LENGTH, status);
        return status.get() == GL4.GL_TRUE;
    }

    private void compileShader(GL4 gl, Shader shader) {
        int handle = gl.glCreateShader(shader.getType());
        gl.glShaderSource(handle, 1, new String[] {shader.getSource()}, null, 0);
        gl.glCompileShader(handle);
        if (!isCompiled(gl, handle)) {
            printCompileErrorLog(gl, handle);
        }
        shader.setHandle(handle);
    }

    private boolean isCompiled(GL4 gl, int handle) {
        IntBuffer status = Buffers.newDirectIntBuffer(1);
        gl.glGetShaderiv(handle, GL4.GL_COMPILE_STATUS, status);
        return status.get() == GL4.GL_TRUE;
    }

    private void printCompileErrorLog(GL4 gl, int handle) {
        IntBuffer logLengthBuffer = Buffers.newDirectIntBuffer(1);
        gl.glGetShaderiv(handle, GL4.GL_INFO_LOG_LENGTH, logLengthBuffer);
        int length = logLengthBuffer.get();

        ByteBuffer data = Buffers.newDirectByteBuffer(length);
        gl.glGetShaderInfoLog(handle, length, null, data);
        byte[] bytes = new byte[length];
        data.get(bytes);
        System.err.println(new String(bytes));
    }
}