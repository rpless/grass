package rpless.grass.gl.buffers;

import javax.media.opengl.GL4;

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

    public void enable(GL4 gl, int stride) {
        gl.glEnableVertexAttribArray(index);
        gl.glVertexAttribPointer(index, size, type, normalized, stride, offset);
    }

    public void disable(GL4 gl) {
        gl.glDisableVertexAttribArray(index);
    }
}