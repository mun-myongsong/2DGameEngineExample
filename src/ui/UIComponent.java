package ui;

import java.awt.image.BufferedImage;

import core.Position;
import core.Size;
import state.State;

public abstract class UIComponent {
    protected Position relativePosition;
    protected Position absolutePosition;
    protected Size size;
    protected Space margin;
    protected Space padding;

    public UIComponent() {
        relativePosition = new Position(0, 0);
        absolutePosition = new Position(0, 0);
        size = new Size(1, 1);
        margin = new Space(0);
        padding = new Space(0);
    }

    public Space getMargin() {
        return margin;
    }

    public Space getPadding() {
        return padding;
    }

    public Position getAbsolutePosition() {
        return absolutePosition;
    }

    public Position getRelativePosition() {
        return relativePosition;
    }

    public Size getSize() {
        return size;
    }

    public abstract BufferedImage getSprite();

    public void setMargin(Space margin) {
        this.margin = margin;
    }

    public void setPadding(Space padding) {
        this.padding = padding;
    }

    public void setAbsolutePosition(Position absolutePosition) {
        this.absolutePosition = absolutePosition;
    }

    public void setRelativePosition(Position relativePosition) {
        this.relativePosition = relativePosition;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public abstract void update(State state);
}
