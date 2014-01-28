package rpless.grass.gl.buffers;

import javax.media.opengl.GL4;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Collection;

public class Mesh {
    private int vertexBufferHandle, count;
    private Collection<MeshFormat> meshFormats;

    Mesh(int vertexBufferHandle, int count, Collection<MeshFormat> formats) {
        this.vertexBufferHandle = vertexBufferHandle;
        this.count = count;
        this.meshFormats = formats;
    }

    public void enable(GL4 gl) {
        for (MeshFormat format : meshFormats) {
            format.enable(gl, 0);
        }
    }

    public void disable(GL4 gl) {
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
        for (MeshFormat format : meshFormats) {
            format.disable(gl);
        }
    }

    public void display(GL4 gl) {
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vertexBufferHandle);
        gl.glDrawArrays(GL4.GL_TRIANGLES, 0, count);
    }

    public void delete(GL4 gl) {
        IntBuffer buffer = (IntBuffer) IntBuffer.allocate(1).put(vertexBufferHandle).rewind();
        gl.glDeleteBuffers(1, buffer);
    }
}