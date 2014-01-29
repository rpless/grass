package rpless.grass;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Data {
    public static final FloatBuffer vertexData = FloatBuffer.wrap(new float[] {
            // Front face
            -1.0f, -1.0f,  1.0f, 1.0f,  1.0f,  1.0f,  1.0f,  1.0f,
            1.0f, -1.0f,  1.0f, 1.0f,  1.0f,  1.0f,  1.0f,  1.0f,
            1.0f,  1.0f,  1.0f, 1.0f,  1.0f,  1.0f,  1.0f,  1.0f,
            -1.0f,  1.0f,  1.0f, 1.0f,  1.0f,  1.0f,  1.0f,  1.0f,

            // Back face
            -1.0f, -1.0f, -1.0f, 1.0f,  1.0f,  0.0f,  0.0f,  1.0f,
            -1.0f,  1.0f, -1.0f, 1.0f,  1.0f,  0.0f,  0.0f,  1.0f,
            1.0f,  1.0f, -1.0f, 1.0f,  1.0f,  0.0f,  0.0f,  1.0f,
            1.0f, -1.0f, -1.0f, 1.0f,  1.0f,  0.0f,  0.0f,  1.0f,

            // Top face
            -1.0f,  1.0f, -1.0f, 1.0f,  0.0f,  1.0f,  0.0f,  1.0f,
            -1.0f,  1.0f,  1.0f, 1.0f,  0.0f,  1.0f,  0.0f,  1.0f,
            1.0f,  1.0f,  1.0f, 1.0f,  0.0f,  1.0f,  0.0f,  1.0f,
            1.0f,  1.0f, -1.0f, 1.0f,  0.0f,  1.0f,  0.0f,  1.0f,

            // Bottom face
            -1.0f, -1.0f, -1.0f, 1.0f,  0.0f,  0.0f,  1.0f,  1.0f,
            1.0f, -1.0f, -1.0f, 1.0f,  0.0f,  0.0f,  1.0f,  1.0f,
            1.0f, -1.0f,  1.0f, 1.0f,  0.0f,  0.0f,  1.0f,  1.0f,
            -1.0f, -1.0f,  1.0f, 1.0f,  0.0f,  0.0f,  1.0f,  1.0f,

            // Right face
            1.0f, -1.0f, -1.0f, 1.0f,  1.0f,  1.0f,  0.0f,  1.0f,
            1.0f,  1.0f, -1.0f, 1.0f,  1.0f,  1.0f,  0.0f,  1.0f,
            1.0f,  1.0f,  1.0f, 1.0f,  1.0f,  1.0f,  0.0f,  1.0f,
            1.0f, -1.0f,  1.0f, 1.0f,  1.0f,  1.0f,  0.0f,  1.0f,

            // Left face
            -1.0f, -1.0f, -1.0f, 1.0f,  1.0f,  0.0f,  1.0f,  1.0f,
            -1.0f, -1.0f,  1.0f, 1.0f,  1.0f,  0.0f,  1.0f,  1.0f,
            -1.0f,  1.0f,  1.0f, 1.0f,  1.0f,  0.0f,  1.0f,  1.0f,
            -1.0f,  1.0f, -1.0f, 1.0f,  1.0f,  0.0f,  1.0f,  1.0f
    });

    public static final ShortBuffer indexData = ShortBuffer.wrap(new short[] {
            0,  1,  2,      0,  2,  3,    // front
            4,  5,  6,      4,  6,  7,    // back
            8,  9,  10,     8,  10, 11,   // top
            12, 13, 14,     12, 14, 15,   // bottom
            16, 17, 18,     16, 18, 19,   // right
            20, 21, 22,     20, 22, 23    // left
    });
}