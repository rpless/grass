package rpless.grass.gl.buffers;

import com.jogamp.common.nio.Buffers;

import javax.media.opengl.GL4;
import java.util.Collection;

public class Mesh {
    private NativeBuffer vertexBuffer;
    private int count;
    private Collection<MeshFormat> meshFormats;

    Mesh(NativeBuffer vertexBuffer, int count, Collection<MeshFormat> formats) {
        this.vertexBuffer = vertexBuffer;
        this.count = count;
        this.meshFormats = formats;
    }

    public void enable(GL4 gl) {
        int count = attributeCount();
        for (MeshFormat format : meshFormats) {
            format.enable(gl, count * Buffers.SIZEOF_FLOAT);
        }
    }

    private int attributeCount() {
        int sum = 0;
        for (MeshFormat format : meshFormats) {
            sum = sum + format.getSize();
        }
        return sum;
    }

    public void disable(GL4 gl) {
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
        for (MeshFormat format : meshFormats) {
            format.disable(gl);
        }
    }

    public void display(GL4 gl) {
        vertexBuffer.enable(gl);
        gl.glDrawArrays(GL4.GL_TRIANGLES, 0, count);
    }

    public void delete(GL4 gl) {
        vertexBuffer.delete(gl);
    }
}