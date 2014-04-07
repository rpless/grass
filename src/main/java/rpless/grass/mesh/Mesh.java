package rpless.grass.mesh;

import com.jogamp.common.nio.Buffers;

import javax.media.opengl.GL4;
import java.util.Collection;

public abstract class Mesh {

    private Collection<MeshFormat> meshFormats;

    protected Mesh(Collection<MeshFormat> meshFormats) {
        this.meshFormats = meshFormats;
    }

    public abstract void display(GL4 gl);

    public abstract void delete(GL4 gl);

    public void enable(GL4 gl) {
        int count = attributeCount();
        for (MeshFormat format : meshFormats) {
            format.enable(gl, count * Buffers.SIZEOF_FLOAT);
        }
    }

    public void disable(GL4 gl) {
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
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