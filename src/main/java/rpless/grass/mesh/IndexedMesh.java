package rpless.grass.mesh;

import rpless.grass.gl.buffers.NativeBuffer;

import javax.media.opengl.GL4;
import java.util.Collection;

public class IndexedMesh extends Mesh {
    private NativeBuffer vertexBuffer, indexBuffer;
    private int count;

    IndexedMesh(NativeBuffer vertexBuffer, NativeBuffer indexBuffer, int count, Collection<MeshFormat> formats) {
        super(formats);
        this.vertexBuffer = vertexBuffer;
        this.indexBuffer = indexBuffer;
        this.count = count;
    }

    @Override
    public void enable(GL4 gl) {
        vertexBuffer.enable(gl);
        indexBuffer.enable(gl);
        super.enable(gl);
    }

    public void display(GL4 gl) {
        gl.glDrawElements(GL4.GL_TRIANGLES, count, GL4.GL_UNSIGNED_SHORT, 0);
    }

    public void delete(GL4 gl) {
        vertexBuffer.delete(gl);
        indexBuffer.delete(gl);
    }
}
