package rpless.grass.math;

public class Vector3f {

    public static Vector3f of(float x, float y, float z) {
        return new Vector3f(x, y, z);
    }

    public static Vector3f zero() {
        return Vector3f.of(0, 0, 0);
    }

    // An epsilon for comparing floats.
    private static float EPSILON = 0.000001f;

    private float x, y, z;

    Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float z() {
        return z;
    }

    public Vector3f add(Vector3f v) {
        return new Vector3f(x + v.x(), y + v.y(), z + v.z());
    }

    public Vector3f subtract(Vector3f v) {
        return new Vector3f(x - v.x(), y - v.y(), z - v.z());
    }

    public Vector3f negate() {
        return new Vector3f(-x, -y, -z);
    }

    public Vector3f multiply(float scale) {
        return new Vector3f(x * scale, y * scale, z * scale);
    }

    public Vector3f normalize() {
        return this.multiply(1.0f / this.magnitude());
    }

    public float magnitude() {
        return (float) Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public float dotProduct(Vector3f v) {
        return (this.x * v.x()) + (this.y * v.y()) + (this.z * v.z());
    }

    public Vector3f crossProduct(Vector3f v) {
        float nx = (this.y() * v.z()) - (this.z() * v.y());
        float ny = (this.z() * v.x()) - (this.x() * v.z());
        float nz = (this.x() * v.y()) - (this.y() * v.x());
        return new Vector3f(nx, ny, nz);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Vector3f) {
            Vector3f v = (Vector3f) o;
            return withinEpsilon(this.x(), v.x()) && withinEpsilon(this.y(), v.y()) && withinEpsilon(this.z(), v.z());
        } else {
            return false;
        }
    }

    private boolean withinEpsilon(float f1, float f2) {
        return Math.abs(f1 - f2) < EPSILON;
    }

    @Override
    public String toString() {
        return "[" + this.x() + ", " + this.y() + ", " + this.z() + "]";
    }
}