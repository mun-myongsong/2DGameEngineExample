package core;

public class Vector2D {
    private double x;
    private double y;

    public Vector2D() {
        this(0, 0);
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2D copyOf(Vector2D vector) {
        return new Vector2D(vector.getX(), vector.getY());
    }

    public static Vector2D directionBetweenPositions(Position start, Position end) {
        Vector2D direction = new Vector2D(start.getX() - end.getX(), start.getY() - end.getY());
        direction.normalize();
        return direction;
    }

    public static double dotProduct(Vector2D v1, Vector2D v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public void multiply(double speed) {
        x *= speed;
        y *= speed;
    }

    public void normalize() {
        double length = length();
        x = x == 0 ? 0 : x/length;
        y = y == 0 ? 0 : y/length;
    }

    public void rotateByDegrees(int degrees) {
        double radians  = Math.toRadians(degrees);
        double cosAngle = Math.cos(radians);
        double sinAngle = Math.sin(radians);
        double rotatedX = cosAngle * x - sinAngle * y;
        double rotatedY = sinAngle * x + cosAngle * y;
        x = rotatedX;
        y = rotatedY;
    }

    public void setVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "VECTOR: (X: " + x + ", Y: " + y + ")";
    }
}
