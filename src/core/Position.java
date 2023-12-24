package core;

public class Position {
    private double x;
    private double y;

    public Position() {
        x = 0;
        y = 0;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position position) {
        x = position.getX();
        y = position.getY();
    }

    public void add(Vector2D vector) {
        x += vector.getX();
        y += vector.getY();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "POSITION: (X: " + x + ", Y: " + y + ")";
    }
}
