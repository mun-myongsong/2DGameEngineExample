package core;

public class Size {
    private int height;
    private int width;

    public Size() {
        width = 0;
        height = 0;
    }

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Size(Size size) {
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "SIZE: (W: " + width + ", H: " + height + ")";
    }
}
