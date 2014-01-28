package rpless.grass.math;

public class Matrix4fUtil {
    public static Matrix4f translate(float x, float y, float z) {
        Matrix4f mat = Matrix4f.identity();
        mat.set(3, 0, x);
        mat.set(3, 1, y);
        mat.set(3, 2, z);
        return mat;
    }
}