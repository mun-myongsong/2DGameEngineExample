package ui;

import core.Position;
import core.Size;

public class HorizontalContainer extends UIContainer {
    public HorizontalContainer(Size windowSize) {
        super(windowSize);
    }

    @Override
    protected void calculateChildrenPosition() {
        int currentX = padding.getLeft();
        for (UIComponent child : children) {
            currentX += child.getMargin().getLeft();
            child.setRelativePosition(new Position(currentX, padding.getTop()));
            child.setAbsolutePosition(new Position(currentX + (int)absolutePosition.getX(), padding.getTop() + (int)absolutePosition.getY()));
            currentX += child.getSize().getWidth();
            currentX += child.getMargin().getRight();
        }
    }

    @Override
    protected Size calculateChildrenSize() {
        int combinedChildW = 0;
        int tallestChildH = 0;
        for (UIComponent child : children) {
            combinedChildW += child.getSize().getWidth() + child.getMargin().getHorizontal();
            if (child.getSize().getHeight() > tallestChildH) {
                tallestChildH = child.getSize().getHeight();
            }
        }
        return new Size(combinedChildW, tallestChildH);
    }
}
