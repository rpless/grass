package rpless.grass.math;

import java.util.Arrays;

/**
 * A column major 4x4 matrix.
 */
public class Matrix4f {
    private static final int SIZE = 4;

    /**
     * @return Produces a 4x4 identity matrix.
     */
    public static Matrix4f identity() {
        Matrix4f matrix = new Matrix4f();
        for (int i = 0; i < SIZE; i++) {
            matrix.set(i, i, 1);
        }
        return matrix;
    }

    private float[] values;

    public Matrix4f() {
        values = new float[SIZE * SIZE];
    }

    public float get(int column, int row) {
        return values[column * SIZE + row];
    }

    public void set(int column, int row, float value) {
        values[column * SIZE + row] = value;
    }

    public void set(int column, Vector3f vector) {
        set(column, 0, vector.x());
        set(column, 1, vector.y());
        set(column, 2, vector.z());
    }

    public Matrix4f multiply(Matrix4f mat) {
        Matrix4f result = new Matrix4f();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    result.set(i, j, result.get(i, j) + (mat.get(i, k) * this.get(k, j)));
                }
            }
        }
        return result;
    }

    public Matrix4f transpose() {
        Matrix4f matrix = new Matrix4f();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrix.set(i, j, this.get(j, i));
            }
        }
        return matrix;
    }

    public float[] toArray() {
        return Arrays.copyOf(values, values.length);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < SIZE; i++) {
            builder.append("[");
            for (int j = 0; j < SIZE; j++) {
                builder.append(this.get(j, i)).append((j == (SIZE - 1)) ? "" : ",");
            }
            builder.append((i == (SIZE - 1)) ? "]]" : "],").append("\n");
        }
        return builder.toString();
    }
}