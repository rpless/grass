package rpless.grass.mesh;

import rpless.grass.gl.buffers.NativeBuffer;

import javax.media.opengl.GL4;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.Collection;

public class MeshFactory {

    public static Mesh createMesh(GL4 gl, FloatBuffer vertexData, ShortBuffer indexData, MeshFormat... formats) {
        return createMesh(gl, vertexData, indexData, Arrays.asList(formats));
    }

    public static Mesh createMesh(GL4 gl, FloatBuffer vertexData, ShortBuffer indexData, Collection<MeshFormat> formats) {
        NativeBuffer vertexBuffer = NativeBuffer.createBuffer(gl, vertexData, GL4.GL_ARRAY_BUFFER, GL4.GL_FLOAT, GL4.GL_STATIC_DRAW);
        NativeBuffer indexBuffer = NativeBuffer.createBuffer(gl, indexData, GL4.GL_ELEMENT_ARRAY_BUFFER, GL4.GL_UNSIGNED_SHORT, GL4.GL_STATIC_DRAW);
        return new Mesh(vertexBuffer, indexBuffer, vertexData.capacity(), formats);
    }
}