package rpless.grass;


import rpless.grass.math.CommonMath;
import rpless.grass.math.Matrix4f;
import rpless.grass.math.Vector3f;

public class Camera {

    private Vector3f position, view, right, lockedUp, rotatedUp;
    private float xRotation, yRotation, zRotation;

    /**
     * Instantiates a camera at the origin of the world space.
     * The camera is pointing at (0, 0, -1), which is to say in the negative direction on the Z-Axis.
     */
    public Camera() {
        position = Vector3f.zero();
        view = Vector3f.of(0, 0, -1.0f);
        lockedUp = Vector3f.of(0, 1.0f, 0);
        rotatedUp = Vector3f.of(0, 1.0f, 0);
        right = lockedUp.crossProduct(view);
        xRotation = 0;
        yRotation = 0;
        zRotation = 0;
    }

    public void move(Vector3f v) {
        position = position.add(v);
    }

    public void moveForward(float distance) {
        this.move(view.normalize().multiply(distance));
    }

    public void strafeRight(float distance) {
        this.move(right.normalize().multiply(distance));
    }

    public void moveUp(float distance) {
        this.move(lockedUp.normalize().multiply(distance));
    }

    public void roll(float alpha) {
        this.right = CommonMath.rotate(right, view, alpha);
        this.lockedUp = CommonMath.rotate(lockedUp, view, alpha);
        if (!rotatedUp.equals(view)) {
            rotatedUp = CommonMath.rotate(rotatedUp, view, alpha);
        }
    }

    public void pitch(float alpha) {
        this.view = CommonMath.rotate(view, right, alpha);
        this.lockedUp = CommonMath.rotate(lockedUp, right, alpha);
    }

    public void yaw(float alpha) {
        if (!view.equals(rotatedUp)) this.view = CommonMath.rotate(view, rotatedUp, alpha);
        if (!right.equals(rotatedUp)) this.right = CommonMath.rotate(right, rotatedUp, alpha);
        if (!lockedUp.equals(rotatedUp)) this.lockedUp = CommonMath.rotate(lockedUp, rotatedUp, alpha);
    }

    /**
     * @return Returns a matrix that represents where the camera is and what is looking at.
     */
    public Matrix4f lookAt() {
        Vector3f center = position.add(view);
        Vector3f f = center.subtract(position).normalize();
        Vector3f uNorm = lockedUp.normalize();
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