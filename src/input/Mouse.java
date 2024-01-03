package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import core.Position;

public class Mouse implements MouseListener, MouseMotionListener {
    private Position mousePosition;
    private boolean mouseClicked;
    private boolean mouseReleased;
    private boolean mousePressed;

    public Mouse() {
        mousePosition = new Position(-1, -1);
    }

    public void cleanUpInputEvents() {
        mouseClicked = false;
        mouseReleased = false;
        mousePressed = false;
    }

    public Position getMousePosition() {
        return mousePosition;
    }

    public boolean isMouseClicked() {
        return mouseClicked;
    }

    public boolean isMouseReleased() {
        return mouseReleased;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosition.setX(e.getPoint().getX());
        mousePosition.setY(e.getPoint().getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition.setX(e.getPoint().getX());
        mousePosition.setY(e.getPoint().getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = e.getButton() == MouseEvent.BUTTON1;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseClicked = true;
            mousePressed = false;
            mouseReleased = true;
        }
    }
}
