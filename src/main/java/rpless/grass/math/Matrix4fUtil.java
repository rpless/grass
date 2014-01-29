package rpless.grass.math;

public class Matrix4fUtil {

    public static Matrix4f translate(float x, float y, float z) {
        Matrix4f mat = Matrix4f.identity();
        mat.set(3, 0, x);
        mat.set(3, 1, y);
        mat.set(3, 2, z);
        return mat;
    }

    public static Matrix4f rotateX(float angle) {
        float theta = (float) Math.toRadians(angle);
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);
        Matrix4f rot = Matrix4f.identity();

        rot.set(1, 1, cosTheta);
        rot.set(2, 1, -sinTheta);
        rot.set(1, 2, sinTheta);
        rot.set(2, 2, cosTheta);
        return rot;
    }

    public static Matrix4f rotateY(float angle) {
        float theta = (float) Math.toRadians(angle);
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);
        Matrix4f rot = Matrix4f.identity();
        rot.set(0, 0, cosTheta);
        rot.set(2, 0, sinTheta);
        rot.set(0, 2, -sinTheta);
        rot.set(2, 2, cosTheta);
        return rot;
    }

    public static Matrix4f rotateZ(float angle) {
        float theta = (float) Math.toRadians(angle);
        float cosTheta = (float) Math.cos(theta);
        float sinTheta = (float) Math.sin(theta);
        Matrix4f rot = Matrix4f.identity();
        rot.set(0, 0, cosTheta);
        rot.set(1, 0, -sinTheta);
        rot.set(0, 1, sinTheta);
        rot.set(1, 1, cosTheta);
        return rot;
    }

    public static Matrix4f rotate(Vector3f axis, float angle) {
        Vector3f nAxis = axis.normalize();
        float rAngle = (float) Math.toRadians(angle);
        float fCos = (float) Math.cos(rAngle);
        float fInvCos = 1 - fCos;
        float fSin = (float) Math.sin(rAngle);
        float xSquared = nAxis.x() * nAxis.x();
        float ySquared = nAxis.y() * nAxis.y();
        float zSquared = nAxis.z() * nAxis.z();

        Matrix4f rot = Matrix4f.identity();
        rot.set(0, 0, xSquared + ((1 - xSquared) * fCos));
        rot.set(0, 1, (nAxis.x() * nAxis.y() * fInvCos + (nAxis.z() * fSin)));
        rot.set(0, 2, nAxis.x() * nAxis.z() * fInvCos + (nAxis.y() * fSin));

        rot.set(1, 0, nAxis.x() * nAxis.y() * fInvCos - (nAxis.z() * fSin));
        rot.set(1, 1, ySquared + ((1 - ySquared) * fCos));
        rot.set(1, 2, nAxis.y() * nAxis.z() * fInvCos + (nAxis.x() * fSin));

        rot.set(2, 0, nAxis.x() * nAxis.z() * fInvCos + (nAxis.y() * fSin));
        rot.set(2, 1, nAxis.y() * nAxis.z() * fInvCos - (nAxis.x() * fSin));
        rot.set(2, 2, zSquared + ((1 - zSquared) * fCos));
        return rot;
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