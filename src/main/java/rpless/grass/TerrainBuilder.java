package rpless.grass;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class TerrainBuilder {

    private final int width, depth;
    private final float size;

    public TerrainBuilder(float size, int width, int depth) {
        this.size = size;
        this.width = width;
        this.depth = depth;
    }

    public FloatBuffer generateTerrain() {
        FloatBuffer buffer = FloatBuffer.allocate(width * depth * 16);
        for (int i = 1; i <= width; i++) {
            for (int j = 1; j <= depth; j++) {
                buffer.put(generateTriangle(i, j));
            }
        }
       return ((FloatBuffer) buffer.rewind());
    }

    private float[] generateTriangle(int i, int j) {
        float x = i * size;
        float z = j * size;
        return new float[] {
            0f, 0f, 0f, 1f,
            x, 0f, 0f, 1f,
            0f, 0f, z, 1f,
            x, 0f, z, 1f
        };
    }

    public ShortBuffer generateIndices() {
        ShortBuffer buffer = ShortBuffer.allocate(width * depth * 6);
        for (short i = 0; i < (width * depth); i++) {
            short base = (short) (i * 4);
            buffer.put(new short[] {
                    base, (short) (base + 1), (short) (base + 2),
                    (short) (base + 1), (short) (base + 2), (short) (base + 3)
            });
        }
        return ((ShortBuffer) buffer.rewind());
    }
}