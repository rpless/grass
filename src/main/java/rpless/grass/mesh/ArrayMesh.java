package rpless.grass.mesh;

import rpless.grass.gl.buffers.NativeBuffer;

import javax.media.opengl.GL3;
import java.util.Collection;

public class ArrayMesh extends Mesh {
    private NativeBuffer vertexBuffer;
    private int count;

    public ArrayMesh(NativeBuffer vertexBuffer, int count, Collection<MeshFormat> meshFormats) {
        super(meshFormats);
        this.vertexBuffer = vertexBuffer;
        this.count = count;
    }

    @Override
    public void enable(GL3 gl) {
        vertexBuffer.enable(gl);
        super.enable(gl);
    }

    public void display(GL3 gl) {
        gl.glDrawArrays(GL3.GL_TRIANGLES, 0, count);
    }

    public void delete(GL3 gl) {
        vertexBuffer.delete(gl);
    }
}