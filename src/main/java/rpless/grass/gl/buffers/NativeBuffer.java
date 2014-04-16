package rpless.grass.gl.buffers;

import com.jogamp.common.nio.Buffers;
import rpless.grass.gl.NativeObject;

import javax.media.opengl.GL3;
import java.nio.Buffer;
import java.nio.IntBuffer;

public class NativeBuffer extends NativeObject {

    public static NativeBuffer createBuffer(GL3 gl, Buffer buffer, int target, int type, int renderingHint) {
        int handle = generateBufferHandle(gl);
        gl.glBindBuffer(target, handle);
        gl.glBufferData(target, buffer.capacity() * typeToSize(type), buffer, renderingHint);
        gl.glBindBuffer(target, handle);
        return new NativeBuffer(handle, target);
    }

    private static int generateBufferHandle(GL3 gl) {
        IntBuffer handleBuffer = Buffers.newDirectIntBuffer(1);
        gl.glGenBuffers(1, handleBuffer);
        return handleBuffer.get();
    }

    private int target;

    public NativeBuffer(int handle, int target) {
        super(handle);
        this.target = target;
    }

    public void enable(GL3 gl) {
        gl.glBindBuffer(target, getHandle());
    }

    public void disable(GL3 gl) {
        gl.glBindBuffer(target, 0);
    }

    public void delete(GL3 gl) {
        IntBuffer buffer = (IntBuffer) IntBuffer.allocate(1).put(getHandle()).rewind();
        gl.glDeleteBuffers(1, buffer);
    }
}