package rpless.grass.mesh;

import javax.media.opengl.GL3;

public class MeshFormat {
    private int index, size, type, offset;
    private boolean normalized;

    public MeshFormat(int index, int size, int type, int offset, boolean normalized) {
        this.index = index;
        this.size = size;
        this.type = type;
        this.offset = offset;
        this.normalized = normalized;
    }

    public void enable(GL3 gl, int stride) {
        gl.glEnableVertexAttribArray(index);
        gl.glVertexAttribPointer(index, size, type, normalized, stride, offset);
    }

    public void disable(GL3 gl) {
        gl.glDisableVertexAttribArray(index);
    }

    int getSize() {
        return size;
    }
}