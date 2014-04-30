package rpless.grass.gl.shader;

import com.jogamp.common.nio.Buffers;

import javax.media.opengl.GL3;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShaderProgramFactory {

    public static ShaderProgram makeShader(GL3 gl, Shader... shaders) {
        List<Shader> shaderList = Arrays.asList(shaders);
        int handle = gl.glCreateProgram();
        shaderList.forEach(shader -> {
            compileShader(gl, shader);
            gl.glAttachShader(handle, shader.getHandle());
        });

        gl.glLinkProgram(handle);
        if (!isLinked(gl, handle)) {
            printLinkerError(gl, handle);
        }
        shaderList.forEach(shader -> {
            gl.glDetachShader(handle, shader.getHandle());
            gl.glDeleteShader(shader.getHandle());
        });
        return new ShaderProgram(handle, getAttributeMap(gl, handle), getUniforms(gl, handle));
    }

    private static Map<String, Integer> getAttributeMap(GL3 gl, int handle) {
        Map<String, Integer> attributes = new HashMap<>();
        IntBuffer countBuffer = IntBuffer.allocate(1);
        gl.glGetProgramiv(handle, GL3.GL_ACTIVE_ATTRIBUTES, countBuffer);
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

    private static Map<String, Integer> getUniforms(GL3 gl, int handle) {
        Map<String, Integer> uniforms = new HashMap<>();
        IntBuffer countBuffer = IntBuffer.allocate(1);
        gl.glGetProgramiv(handle, GL3.GL_ACTIVE_UNIFORMS, countBuffer);
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

    private static void printLinkerError(GL3 gl, int handle) {
        IntBuffer logLengthBuffer = Buffers.newDirectIntBuffer(1);
        gl.glGetProgramiv(handle, GL3.GL_INFO_LOG_LENGTH, logLengthBuffer);
        int length = logLengthBuffer.get();

        ByteBuffer data = Buffers.newDirectByteBuffer(length);
        gl.glGetProgramInfoLog(handle, length, null, data);
        byte[] bytes = new byte[length];
        data.get(bytes);
        System.err.println(new String(bytes));
    }

    private static boolean isLinked(GL3 gl, int handle) {
        IntBuffer status = Buffers.newDirectIntBuffer(1);
        gl.glGetProgramiv(handle, GL3.GL_INFO_LOG_LENGTH, status);
        return status.get() == GL3.GL_TRUE;
    }

    private static void compileShader(GL3 gl, Shader shader) {
        int handle = gl.glCreateShader(shader.getType());
        gl.glShaderSource(handle, 1, new String[] {shader.getSource()}, null, 0);
        gl.glCompileShader(handle);
        if (!isCompiled(gl, handle)) {
            printCompileErrorLog(gl, handle, shader);
        }
        shader.setHandle(handle);
    }

    private static boolean isCompiled(GL3 gl, int handle) {
        IntBuffer status = Buffers.newDirectIntBuffer(1);
        gl.glGetShaderiv(handle, GL3.GL_COMPILE_STATUS, status);
        return status.get() == GL3.GL_TRUE;
    }

    private static void printCompileErrorLog(GL3 gl, int handle, Shader shader) {
        IntBuffer logLengthBuffer = Buffers.newDirectIntBuffer(1);
        gl.glGetShaderiv(handle, GL3.GL_INFO_LOG_LENGTH, logLengthBuffer);
        int length = logLengthBuffer.get();

        ByteBuffer data = Buffers.newDirectByteBuffer(length);
        gl.glGetShaderInfoLog(handle, length, null, data);
        byte[] bytes = new byte[length];
        data.get(bytes);
        System.err.println(shader + ": " + new String(bytes));
    }
}