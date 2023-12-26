package map;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import core.Position;
import core.Size;
import core.Vector2D;
import entity.Entity;
import util.ImageUtil;
import util.MapUtil;

public class GameMap {
    public static final int MAP_LAYER_1 = 0;
    public static final int MAP_LAYER_2 = 1;
    public static final int MAP_LAYER_3 = 2;
    public static final int MAP_LAYER_4 = 3;
    public static final int NUMBER_OF_MAP_LAYER = 4;
    public static final int UNWALKABLE_TILE = 0;
    public static final int WALKABLE_TILE = 1;

    private List<int[][]> mapData;
    private List<BufferedImage> tileset;
    private String tileSetFileName;
    private Size stageSize;
    private Size tileSize;
    private int scale;
    private Rectangle tileRect;
    private Rectangle tileRect2;

    public GameMap() {
        mapData = new ArrayList<>();
        tileset = new ArrayList<>();
        scale = 4;
        tileRect = new Rectangle(-1, -1, 0, 0);
        tileRect2 = new Rectangle(-1, -1, 0, 0);
        buildGameMap();
        loadTileSet();
    }

    private void buildGameMap() {
        try {
            Map<String, String> map = MapUtil.loadMapFromFileName("map1.tmx");
            tileSetFileName = map.get(MapUtil.MAP_KEY_SOURCE);
            for (int i = 1; i <= NUMBER_OF_MAP_LAYER; i++) {
                // TODO 今IDは使われていない
                int id = Integer.parseInt(map.get(MapUtil.MAP_KEY_ID + i));
                int width = Integer.parseInt(map.get(MapUtil.MAP_KEY_WIDTH + i));
                int height = Integer.parseInt(map.get(MapUtil.MAP_KEY_HEIGHT + i));
                int tileWidth = Integer.parseInt(map.get(MapUtil.MAP_KEY_TILE_WIDTH));
                int tileHeight = Integer.parseInt(map.get(MapUtil.MAP_KEY_TILE_HEIGHT));
                stageSize = new Size(width, height);
                tileSize = new Size(tileWidth, tileHeight);
                String data = map.get(MapUtil.MAP_KEY_DATA + i);
                int[][] arrData = new int[height][width];
                String[] splitData = data.replace(" ", "").replace("\n", "").split(",");
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        arrData[y][x] = Integer.parseInt(splitData[y * width + x]);
                    }
                }
                mapData.add(arrData);
            }
        } catch (Exception e) {
            throw new IllegalStateException("ゲームマップの構築に失敗しました。", e);
        }
    }

    public int getHeight() {
        return stageSize.getHeight();
    }

    public List<int[][]> getMapData() {
        return mapData;
    }

    public int getMapDataCell(int x, int y, int layer) {
        return mapData.get(layer)[y][x];
    }

    private Points getCollisionPoints(Entity entity) {
        Vector2D vec = entity.getVector();
        Rectangle rect = entity.getCollisionBox();
        Points pos = new Points(-1);
        switch (entity.getDirection()) {
        case UP:
            pos.p1.setX(rect.x);
            pos.p1.setY(rect.y + vec.getY());
            pos.p2.setX(rect.x + rect.width);
            pos.p2.setY(rect.y + vec.getY());
            break;
        case DOWN:
            pos.p1.setX(rect.x);
            pos.p1.setY(rect.y + rect.height + vec.getY());
            pos.p2.setX(rect.x + rect.width);
            pos.p2.setY(rect.y + rect.height + vec.getY());
            break;
        case LEFT:
            pos.p1.setX(rect.x + vec.getX());
            pos.p1.setY(rect.y);
            pos.p2.setX(rect.x + vec.getX());
            pos.p2.setY(rect.y + rect.height);
            break;
        case RIGHT:
            pos.p1.setX(rect.x + rect.width + vec.getX());
            pos.p1.setY(rect.y);
            pos.p2.setX(rect.x + rect.width + vec.getX());
            pos.p2.setY(rect.y + rect.height);
            break;
        }
        return pos;
    }

    public Rectangle getTileRect() {
        return tileRect;
    }

    public Rectangle getTileRect2() {
        return tileRect2;
    }

    public List<BufferedImage> getTileSet() {
        return tileset;
    }

    public int getScale() {
        return scale;
    }

    public Size getStageSize() {
        return stageSize;
    }

    public Size getTileSize() {
        return tileSize;
    }

    public int getWidth() {
        return stageSize.getWidth();
    }

    public boolean isOutBoundsOfMap(int x, int y) {
        return x < 0 || x >= stageSize.getWidth() || y < 0 || y >= stageSize.getHeight();
    }

    public boolean isWalkable(Entity entity) {
        Points pos = getCollisionPoints(entity);
        double gridX1d = pos.p1.getX() / (tileSize.getWidth()  * scale);
        double gridY1d = pos.p1.getY() / (tileSize.getHeight() * scale);
        double gridX2d = pos.p2.getX() / (tileSize.getWidth()  * scale);
        double gridY2d = pos.p2.getY() / (tileSize.getHeight() * scale);
        int gridX1 = gridX1d < 0 ? -1 : (int)gridX1d;
        int gridY1 = gridY1d < 0 ? -1 : (int)gridY1d;
        int gridX2 = gridX2d < 0 ? -1 : (int)gridX2d;
        int gridY2 = gridY2d < 0 ? -1 : (int)gridY2d;
        if (isOutBoundsOfMap(gridX1, gridY1) && isOutBoundsOfMap(gridX2, gridY2)) {
            return false;
        }
        tileRect  = new Rectangle(gridX1 * (tileSize.getWidth() * scale), gridY1 * (tileSize.getHeight() * scale), tileSize.getWidth() * scale, tileSize.getHeight() * scale);
        tileRect2 = new Rectangle(gridX2 * (tileSize.getWidth() * scale), gridY2 * (tileSize.getHeight() * scale), tileSize.getWidth() * scale, tileSize.getHeight() * scale);
        if (mapData.get(MAP_LAYER_4)[gridY1][gridX1] == UNWALKABLE_TILE || 
            mapData.get(MAP_LAYER_4)[gridY2][gridX2] == UNWALKABLE_TILE) {
            return false;
        }
        return true;
    }

    private void loadTileSet() {
        try {
            BufferedImage image = ImageUtil.loadImage(MapUtil.TILE_SET_PATH + tileSetFileName);
            for (int y = 0; y < image.getHeight(null); y += tileSize.getHeight()) {
                for (int x = 0; x < image.getWidth(null); x += tileSize.getWidth()) {
                    tileset.add(image.getSubimage(x, y, tileSize.getWidth(), tileSize.getHeight()));
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("指定されたパスから正しくタイルセットが読み込めませんでした。 path: " + MapUtil.TILE_SET_PATH + tileSetFileName, e);
        }
    }

    private class Points {
        public Position p1;
        public Position p2;

        public Points() {
            p1 = new Position();
            p2 = new Position();
        }

        public Points(int p) {
            p1 = new Position(p, p);
            p2 = new Position(p, p);
        }

        public Points(int x1, int y1, int x2, int y2) {
            p1 = new Position(x1, y1);
            p2 = new Position(y1, y2);
        }
    }
}
