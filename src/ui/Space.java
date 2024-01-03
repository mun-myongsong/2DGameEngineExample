package ui;

public class Space {
    private int top;
    private int bottom;
    private int left;
    private int right;

    public Space(int space) {
        this(space, space);
    }

    public Space(int horizontal, int vertical) {
        this(vertical, vertical, horizontal, horizontal);
    }

    public Space(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public int getHorizontal() {
        return left + right;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }

    public int getVertical() {
        return top + bottom;
    }
}
