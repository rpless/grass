package rpless.grass;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Data {
    public static final FloatBuffer vertexData = FloatBuffer.wrap(new float[] {
            0.0f, 0.0f, 0.0f, 1.0f,
            1f, 0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1f, 1.0f,
            1f, 0.0f, 1f, 1.0f
    });

    public static final ShortBuffer indexData = ShortBuffer.wrap(new short[] {
            0, 1, 2, 1, 2, 3
    });
}