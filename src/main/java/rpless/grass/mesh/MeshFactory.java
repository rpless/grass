package rpless.grass.mesh;

import rpless.grass.gl.buffers.NativeBuffer;

import javax.media.opengl.GL3;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.Collection;

public class MeshFactory {

    public static Mesh createMesh(GL3 gl, FloatBuffer vertexData, MeshFormat... formats) {
        NativeBuffer buffer = NativeBuffer.createBuffer(gl, vertexData, GL3.GL_ARRAY_BUFFER, GL3.GL_FLOAT, GL3.GL_STATIC_DRAW);
        return new ArrayMesh(buffer, vertexData.capacity(), Arrays.asList(formats));
    }

    public static Mesh createMesh(GL3 gl, FloatBuffer vertexData, ShortBuffer indexData, MeshFormat... formats) {
        return createMesh(gl, vertexData, indexData, Arrays.asList(formats));
    }

    public static Mesh createMesh(GL3 gl, FloatBuffer vertexData, ShortBuffer indexData, Collection<MeshFormat> formats) {
        NativeBuffer vertexBuffer = NativeBuffer.createBuffer(gl, vertexData, GL3.GL_ARRAY_BUFFER, GL3.GL_FLOAT, GL3.GL_STATIC_DRAW);
        NativeBuffer indexBuffer = NativeBuffer.createBuffer(gl, indexData, GL3.GL_ELEMENT_ARRAY_BUFFER, GL3.GL_UNSIGNED_SHORT, GL3.GL_STATIC_DRAW);
        return new IndexedMesh(vertexBuffer, indexBuffer, indexData.capacity(), formats);
    }
}