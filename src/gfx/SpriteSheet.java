package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.ImageUtil;

public class SpriteSheet {
    private int tileWidth;
    private int tileHeight;
    private BufferedImage sheet;
    private List<BufferedImage> sprites;

    public SpriteSheet(String fileName, int tileWidth, int tileHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        sprites = new ArrayList<>();
        loadImage(fileName);
        loadTextures();
    }

    private void loadImage(String fileName) {
        try {
            sheet = ImageUtil.loadImage(fileName);
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalStateException("スプライトシートの読み込みに失敗しました。", e);
        }
    }

    private void loadTextures() {
        for (int y = 0; y < sheet.getHeight(null); y += tileHeight) {
            for (int x = 0; x < sheet.getWidth(null); x += tileWidth) {
                sprites.add(sheet.getSubimage(x, y, tileWidth, tileHeight));
            }
        }
    }

    public BufferedImage getSprite(int idx) {
        return sprites.get(idx);
    }

    public List<BufferedImage> getTextures(int start, int end) {
        List<BufferedImage> textures = new ArrayList<>();
        for (int i = start; i < end; i++) {
            textures.add(getSprite(i));
        }
        return textures;
    }
}
