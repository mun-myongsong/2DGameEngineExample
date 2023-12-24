package map;

import java.awt.image.BufferedImage;

import gfx.Animation;

public class Tile {
    private Animation animation;
    private int tileWidth;
    private int tileHeight;
    private boolean walkable;

    public Tile() {
        walkable = true;
    }

    private Tile(Animation animation, int tileWidth, int tileHeight boolean walkable) {
        this.animation = animation;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.walkable = walkable;
    }

    // FIXME 必要なければ削除
    public static Tile copyOf(Tile tile) {
        return new Tile(tile.getSprite(), tile.getTileIndex(), tile.getTileName(), tile.isWalkable());
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public int getTileIndex() {
        return tileIndex;
    }

    public String getTileName() {
        return tileName;
    }

    public boolean isWalkable() {
        return walkable;
    }
}
