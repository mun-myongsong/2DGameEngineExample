package ui.clickable;

import java.awt.Color;
import java.awt.image.BufferedImage;

import core.Size;
import ui.UIContainer;
import ui.UIText;
import ui.VerticalContainer;
import state.State;

public class UIButton extends UIClickable {
    private UIContainer container;
    private UIText label;
    private Runnable clickEvent;

    public UIButton(String label, Runnable clickEvent) {
        this.label = new UIText(label);
        this.clickEvent = clickEvent;
        container = new VerticalContainer(new Size(0, 0));
        container.addUIComponent(this.label);
        container.setFixedSize(new Size(150, 30));
    }

    @Override
    public BufferedImage getSprite() {
        return container.getSprite();
    }

    @Override
    public void onClick() {
        clickEvent.run();
    }

    @Override
    public void update(State state) {
        super.update(state);
        container.update(state);
        size = container.getSize();
        Color color = Color.GRAY;
        if (hasFocus) {
            color = Color.LIGHT_GRAY;
        }
        if (isPressed) {
            color = Color.DARK_GRAY;
        }
        container.setBackgroundColor(color);
    }
}
