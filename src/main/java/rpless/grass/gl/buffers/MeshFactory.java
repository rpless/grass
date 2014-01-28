package rpless.grass.gl.buffers;

import com.jogamp.common.nio.Buffers;

import javax.media.opengl.GL4;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.Collection;

public class MeshFactory {

    public static Mesh createMesh(GL4 gl, FloatBuffer buffer, MeshFormat... formats) {
        return createMesh(gl, buffer, Arrays.asList(formats));
    }

    public static Mesh createMesh(GL4 gl, FloatBuffer buffer, Collection<MeshFormat> formats) {
        NativeBuffer vertexBuffer = NativeBuffer.createBuffer(gl, buffer, GL4.GL_ARRAY_BUFFER, GL4.GL_FLOAT, GL4.GL_STATIC_DRAW);
        return new Mesh(vertexBuffer, buffer.capacity(), formats);
    }
}