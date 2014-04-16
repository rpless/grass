package rpless.grass.mesh;

import rpless.grass.gl.buffers.NativeBuffer;

import javax.media.opengl.GL3;
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
    public void enable(GL3 gl) {
        vertexBuffer.enable(gl);
        indexBuffer.enable(gl);
        super.enable(gl);
    }

    public void display(GL3 gl) {
        gl.glDrawElements(GL3.GL_TRIANGLES, count, GL3.GL_UNSIGNED_SHORT, 0);
    }

    public void delete(GL3 gl) {
        vertexBuffer.delete(gl);
        indexBuffer.delete(gl);
    }
}
