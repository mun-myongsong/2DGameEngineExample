package ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import core.Size;
import state.State;
import util.ImageUtil;

public class UIText extends UIComponent {
    private String text;
    private int fontSize;
    private int fontStyle;
    private String fontFamily;
    private Color color;
    private boolean dropShadow;
    private int dropShadowOffset;
    private Color shadowColor;
    private Font font;

    public UIText(String text) {
        this.text = text;
        fontSize = 24;
        fontStyle = Font.BOLD;
        fontFamily = Font.MONOSPACED;
        color = Color.WHITE;
        dropShadow = true;
        dropShadowOffset = 2;
        shadowColor = new Color(140, 140, 140);
    }

    private void createFont() {
        font = new Font(fontFamily, fontStyle, fontSize);
    }

    private void calculateSize() {
        FontMetrics fontMetrics = new Canvas().getFontMetrics(font);
        size = new Size(
            fontMetrics.stringWidth(text) + padding.getHorizontal(),
            fontMetrics.getHeight() + padding.getVertical()
        );
    }

    @Override
    public BufferedImage getSprite() {
        BufferedImage image = (BufferedImage)ImageUtil.createCompatibleImage(size, Transparency.BITMASK);
        Graphics2D g = image.createGraphics();
        g.setFont(font);
        if (dropShadow) {
            g.setColor(shadowColor);
            g.drawString(text, padding.getLeft() + dropShadowOffset, fontSize + padding.getTop() + dropShadowOffset);
        }
        g.setColor(color);
        g.drawString(text, padding.getLeft(), fontSize + padding.getTop());
        g.dispose();
        return image;
    }

    @Override
    public void update(State state) {
        createFont();
        calculateSize();
    }

    public void setText(String text) {
        this.text = text;
    }
}
