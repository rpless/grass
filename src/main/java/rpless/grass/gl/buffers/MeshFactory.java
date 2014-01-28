package rpless.grass.gl.buffers;

import com.jogamp.common.nio.Buffers;

import javax.media.opengl.GL4;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Collection;

public class MeshFactory {

    public Mesh createMesh(GL4 gl, FloatBuffer buffer, MeshFormat... formats) {
        return this.createMesh(gl, buffer, Arrays.asList(formats));
    }

    public Mesh createMesh(GL4 gl, FloatBuffer buffer, Collection<MeshFormat> formats) {
        int vertexBufferHandle = createVertexBuffer(gl, buffer);
        return new Mesh(vertexBufferHandle, buffer.capacity(), formats);
    }

    private int createVertexBuffer(GL4 gl, FloatBuffer buffer) {
        return createBuffer(gl, buffer, GL4.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT, GL4.GL_STATIC_DRAW);
    }

    private int createBuffer(GL4 gl, Buffer buffer, int target, int typeSize, int renderingHint) {
        int handle = this.generateBufferHandle(gl);
        gl.glBindBuffer(target, handle);
        gl.glBufferData(target, buffer.capacity() * typeSize, buffer, renderingHint);
        gl.glBindBuffer(target, handle);
        return handle;
    }

    private int generateBufferHandle(GL4 gl) {
        IntBuffer handleBuffer = Buffers.newDirectIntBuffer(1);
        gl.glGenBuffers(1, handleBuffer);
        return handleBuffer.get();
    }
}