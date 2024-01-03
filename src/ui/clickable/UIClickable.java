package ui.clickable;

import java.awt.Rectangle;

import core.Position;
import state.State;
import ui.UIComponent;

public abstract class UIClickable extends UIComponent {
    protected boolean hasFocus;
    protected boolean isPressed;

    @Override
    public void update(State state) {
        Position mousePosition = state.getMouse().getMousePosition();
        hasFocus = getBounds().contains((int)mousePosition.getX(), (int)mousePosition.getY());
        isPressed = hasFocus && state.getMouse().isMousePressed();
        if (hasFocus && state.getMouse().isMouseClicked()) {
            onClick();
        }
    }

    private Rectangle getBounds() {
        return new Rectangle(
            (int)absolutePosition.getX(),
            (int)absolutePosition.getY(),
            size.getWidth(),
            size.getHeight()
        );
    }

    protected abstract void onClick();
}
