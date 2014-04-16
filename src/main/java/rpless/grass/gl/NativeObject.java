package rpless.grass.gl;

import com.jogamp.common.nio.Buffers;

import javax.media.opengl.GL3;

public abstract class NativeObject {

    /**
     * @param type An OpenGL type, such as GL.GL_FLOAT
     * @return the size of the type in bytes
     */
    public static int typeToSize(int type) {
        switch (type) {
            case GL3.GL_FLOAT: return Buffers.SIZEOF_FLOAT;
            case GL3.GL_UNSIGNED_SHORT: return Buffers.SIZEOF_SHORT;
            default: throw new IllegalArgumentException("The given type is not recognized.");
        }
    }

    private int handle;

    protected NativeObject(int handle) {
        this.handle = handle;
    }

    protected void setHandle(int handle) {
        this.handle = handle;
    }

    public int getHandle() {
        return handle;
    }
}