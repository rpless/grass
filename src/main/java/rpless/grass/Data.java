package rpless.grass;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Data {
    private static final TerrainBuilder builder = new TerrainBuilder(0.05f, 1, 1);
    public static final FloatBuffer vertexData = builder.generateTerrain();
    public static final ShortBuffer indexData = builder.generateIndices();
}