package ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import core.Position;
import core.Size;
import state.State;
import ui.Space;
import util.ImageUtil;

public abstract class UIContainer extends UIComponent {
    protected Color backgroundColor;
    protected List<UIComponent> children;
    protected Size windowSize;
    protected Alignment alignment;
    protected Size fixedSize;

    public UIContainer(Size windowSize) {
        super();
        this.windowSize = windowSize;
        alignment = new Alignment(Alignment.Position.START, Alignment.Position.START);
        margin = new Space(5);
        padding = new Space(5);
        children = new ArrayList<>();
        calculateSize();
        calculatePosition();
    }

    public void addUIComponent(UIComponent uiComponent) {
        children.add(uiComponent);
    }

    public void addUIComponentAll(UIComponent... uiComponents) {
        for (UIComponent uiComponent : uiComponents) {
            children.add(uiComponent);
        }
    }

    protected abstract Size calculateChildrenSize();

    protected abstract void calculateChildrenPosition();

    private void calculatePosition() {
        int x = padding.getLeft();
        if (alignment.getHorizontal() == Alignment.Position.CENTER) {
            x = windowSize.getWidth() / 2 - size.getWidth() / 2;
        }
        if (alignment.getHorizontal() == Alignment.Position.END) {
            x = windowSize.getWidth() - size.getWidth() - margin.getRight();
        }
        int y = padding.getTop();
        if (alignment.getVertical() == Alignment.Position.CENTER) {
            y = windowSize.getHeight() / 2 - size.getHeight() / 2;
        }
        if (alignment.getVertical() == Alignment.Position.END) {
            y = windowSize.getHeight() - size.getHeight() - margin.getBottom();
        }
        relativePosition = new Position(x, y);
        absolutePosition = new Position(x, y);
        calculateChildrenPosition();
    }

    private void calculateSize() {
        Size calculateChildrenSize = calculateChildrenSize();
        if (fixedSize != null) {
            size = fixedSize;
        } else {
            size = new Size(
                padding.getHorizontal() + calculateChildrenSize.getWidth(),
                padding.getVertical() + calculateChildrenSize.getHeight());
        }
    }

    protected void clearChildren() {
        children.clear();
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public BufferedImage getSprite() {
        BufferedImage image = ImageUtil.createCompatibleImage(size, Transparency.BITMASK);
        Graphics2D g = image.createGraphics();
        g.setColor(backgroundColor);
        g.fillRect(0, 0, size.getWidth(), size.getHeight());
        for (UIComponent child : children) {
            g.drawImage(
                child.getSprite(),
                (int)child.getRelativePosition().getX(),
                (int)child.getRelativePosition().getY(),
                null
            );
        }
        g.dispose();
        return image;
    }

    public void setAlignment(Alignment.Position vertical, Alignment.Position horizontal) {
        alignment = new Alignment(vertical, horizontal);
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setFixedSize(Size fixedSize) {
        this.fixedSize = fixedSize;
    }

    @Override
    public void update(State state) {
        children.forEach(component -> component.update(state));
        calculateSize();
        calculatePosition();
    }
}
