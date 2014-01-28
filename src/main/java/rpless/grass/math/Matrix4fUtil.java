package rpless.grass.math;

public class Matrix4fUtil {

    public static Matrix4f translate(float x, float y, float z) {
        Matrix4f mat = Matrix4f.identity();
        mat.set(3, 0, x);
        mat.set(3, 1, y);
        mat.set(3, 2, z);
        return mat;
    }

    public static Matrix4f perspective(float fov, float aspect, float zNear, float zFar) {
        float scale = (float) Math.tan(Math.toRadians(fov / 2)) * zNear;
        float r = aspect * scale;
        return frustum(-r, r, -scale, scale, zNear, zFar);
    }

    public static Matrix4f frustum(float left, float right, float bottom, float top, float near, float far) {
        Matrix4f mat = new Matrix4f();
        mat.set(0, 0, (2 * near) / (right - left));
        mat.set(1, 1, (2 * near) / (top - bottom));
        mat.set(2, 0, (right + left) / (right - left));
        mat.set(2, 1, (top + bottom) / (top - bottom));
        mat.set(2, 2, -(far + near) / (far - near));
        mat.set(2, 3, -1);
        mat.set(3, 2, (-2 * far * near) / (far - near));
        return mat;
    }
}