package rpless.grass;


import rpless.grass.math.Matrix4f;
import rpless.grass.math.Vector3f;

public class Camera {

    private Vector3f position, view, right, up;
    private float xRotation, yRotation, zRotation;

    /**
     * Instantiates a camera at the origin of the world space.
     * The camera is pointing at (0, 0, -1), which is to say in the negative direction on the Z-Axis.
     */
    public Camera() {
        position = Vector3f.zero();
        view = Vector3f.of(0, 0, -1.0f);
        right = Vector3f.of(1.0f, 0, 0);
        up = Vector3f.of(0, 1.0f, 0);
        xRotation = 0;
        yRotation = 0;
        zRotation = 0;
    }

    public void move(Vector3f v) {
        position = position.add(v);
    }

    public void moveForward(float distance) {
        this.move(view.multiply(distance));
    }

    public void strafeRight(float distance) {
        this.move(right.multiply(distance));
    }

    public void moveUp(float distance) {
        this.move(up.multiply(distance));
    }

    /**
     * @param angle An angle in degrees
     */
    public void rotateX(float angle) {
        xRotation += angle;

        Vector3f u = up.multiply((float) Math.sin(Math.toRadians(angle)));
        view = view.multiply((float) Math.cos(Math.toRadians(angle))).add(u).normalize();

        up = view.crossProduct(right).negate();
    }

    public void rotateY(float angle) {
        yRotation += angle;

        Vector3f u = right.multiply((float) Math.sin(Math.toRadians(angle)));
        view = view.multiply((float) Math.cos(Math.toRadians(angle))).subtract(u).normalize();

        right = view.crossProduct(up);
    }

    public void rotateZ(float angle) {
        zRotation += angle;

        Vector3f u = up.multiply((float) Math.sin(Math.toRadians(angle)));
        right = right.multiply((float) Math.cos(Math.toRadians(angle))).add(u).normalize();

        up = view.crossProduct(right).negate();
    }

    /**
     * @return Returns a matrix that represents where the camera is and what is looking at.
     */
    public Matrix4f lookAt() {
        Vector3f center = position.add(view);
        Vector3f f = center.subtract(position).normalize();
        Vector3f uNorm = up.normalize();
        Vector3f s = f.crossProduct(uNorm);
        Vector3f u = s.crossProduct(f);

        Matrix4f rotation = Matrix4f.identity();
        rotation.set(0, s);
        rotation.set(1, u);
        rotation.set(2, f.negate());

        Matrix4f translation = Matrix4f.identity();
        translation.set(3, position.negate());

        return rotation.transpose().multiply(translation);
    }
}