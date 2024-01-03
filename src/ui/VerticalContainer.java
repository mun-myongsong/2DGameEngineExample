package ui;

import core.Position;
import core.Size;

public class VerticalContainer extends UIContainer {
    public VerticalContainer(Size windowSize) {
        super(windowSize);
    }

    @Override
    protected void calculateChildrenPosition() {
        int currentY = padding.getTop();
        for (UIComponent child : children) {
            currentY += child.getMargin().getTop();
            child.setRelativePosition(new Position(padding.getLeft(), currentY));
            child.setAbsolutePosition(new Position(padding.getLeft() + (int)absolutePosition.getX(), currentY + (int)absolutePosition.getY()));
            currentY += child.getSize().getHeight();
            currentY += child.getMargin().getBottom();
        }
    }

    @Override
    protected Size calculateChildrenSize() {
        int combinedChildH = 0;
        int widestChildW = 0;
        for (UIComponent child : children) {
            combinedChildH += child.getSize().getHeight() + child.getMargin().getVertical();
            if (child.getSize().getWidth() > widestChildW) {
                widestChildW = child.getSize().getWidth();
            }
        }
        return new Size(widestChildW, combinedChildH);
    }
}
