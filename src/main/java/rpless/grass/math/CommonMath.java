package rpless.grass.math;

public class CommonMath {
    /**
     * returns a new vector which is {@code v} rotated around {@code axis} by {@code alpha} degrees clockwise<p>
     * @param v The vector to rotate
     * @param axis The axis of rotation
     * @param alpha The angle of rotation
     * @return The new vector
     */
    public static Vector3f rotate(Vector3f v, Vector3f axis, float alpha)
    {
        //This formula is based on Rodrigues' rotation formula.
        alpha *= -1;
        Vector3f k = axis.normalize();
        Vector3f p2 = k.crossProduct(v).multiply((float)Math.sin(alpha));
        Vector3f p3 = k.multiply(k.dotProduct(v)).multiply((float) (1 - Math.cos(alpha)));
        Vector3f p1 = v.multiply((float)Math.cos(alpha));
        return p1.add(p2).add(p3);
    }
}
