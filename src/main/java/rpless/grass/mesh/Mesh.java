package rpless.grass.mesh;

import com.jogamp.common.nio.Buffers;

import javax.media.opengl.GL3;
import java.util.Collection;

public abstract class Mesh {

    private Collection<MeshFormat> meshFormats;

    protected Mesh(Collection<MeshFormat> meshFormats) {
        this.meshFormats = meshFormats;
    }

    public abstract void display(GL3 gl);

    public abstract void delete(GL3 gl);

    public void enable(GL3 gl) {
        int count = attributeCount();
        for (MeshFormat format : meshFormats) {
            format.enable(gl, count * Buffers.SIZEOF_FLOAT);
        }
    }

    public void disable(GL3 gl) {
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
        for (MeshFormat format : meshFormats) {
            format.disable(gl);
        }
    }

    private int attributeCount() {
        int sum = 0;
        for (MeshFormat format : meshFormats) {
            sum = sum + format.getSize();
        }
        return sum;
    }
}