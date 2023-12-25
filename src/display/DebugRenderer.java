package display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.LinkedHashMap;
import java.util.Map;

import core.Position;
import core.Size;
import entity.creature.Player;
import game.GameLoop;
import map.GameMap;
import state.PlayState;
import state.State;

public class DebugRenderer {
    public static Map<String, String> messageMap = new LinkedHashMap<>();

    public void renderDebug(Graphics2D g, State state) {
        renderMapDataCollisionBox(g, state);
        renderDebugCollisionBox(g, state);
        renderDebugGameInfo(g, state);
    }

    private void renderDebugCollisionBox(Graphics2D g, State state) {
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(2));
        if (state instanceof PlayState playState) {
            Position cp = state.getGameCamera().getPosition();
            Player p = playState.getPlayer();
            int scale = state.getGameMap().getScale();
            playState.getEntities().stream().forEach(entity -> {
                g.drawRect(
                    (int)(entity.getCollisionBox().getX() - cp.getX()), (int)(entity.getCollisionBox().getY() - cp.getY()),
                    (int)entity.getCollisionBox().getWidth() * scale, (int)entity.getCollisionBox().getHeight() * scale);
            });
            g.drawRect(
                (int)(p.getCollisionBox().getX() - cp.getX()), (int)(p.getCollisionBox().getY() - cp.getY()),
                (int)p.getCollisionBox().getWidth() * scale, (int)p.getCollisionBox().getHeight() * scale);
        }
    }

    private void renderMapDataCollisionBox(Graphics2D g, State state) {
        Color color = Color.RED;
        GameMap gm = state.getGameMap();
        Size tileSize = gm.getTileSize();
        Position cp = state.getGameCamera().getPosition();
        int scale = gm.getScale();
        int[][] mapData = gm.getMapData().get(GameMap.MAP_LAYER_4);
        for (int y = 0; y < mapData.length; y++) {
            for (int x = 0; x < mapData[y].length; x++) {
                color = mapData[y][x] == 0 ? Color.YELLOW : Color.GREEN;
                g.setColor(color);
                g.drawRect(x * tileSize.getWidth() * scale - (int) cp.getX(), y * tileSize.getHeight() * scale - (int) cp.getY(),
                           tileSize.getWidth() * scale, tileSize.getHeight() * scale);
            }
        }
        if (state instanceof PlayState playState) {
            if (gm.getTileRect().x != -1) {
                Player player = playState.getPlayer();
                g.setColor(player.isWalkable() ? Color.BLUE : Color.RED);
                g.setStroke(new BasicStroke(3));
                g.drawRect(gm.getTileRect().x - (int) cp.getX(), gm.getTileRect().y - (int) cp.getY(), tileSize.getWidth() * scale, tileSize.getHeight() * scale);
                g.drawRect(gm.getTileRect2().x - (int) cp.getX(), gm.getTileRect2().y - (int) cp.getY(), tileSize.getWidth() * scale, tileSize.getHeight() * scale);
            }
        }
    }

    private static void renderDebugGameInfo(Graphics2D g, State state) {
        messageMap.put("RAM", String.format("RAM: %.3fMB", (((double)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000) / 1000)));
        messageMap.put("FPS", String.format("FPS: %d", GameLoop.fpsCount));
        messageMap.put("UPS", String.format("UPS: %d", GameLoop.upsCount));
        messageMap.put("LOOP", String.format("1S : %d", GameLoop.loopCount));
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        if (state instanceof PlayState pState) {
            Player p = pState.getPlayer();
            GameMap gm = state.getGameMap();
            Size tileSize = gm.getTileSize();
            int scale = gm.getScale();
			messageMap.put("POS", String.format("%s", "Player: " + p.getPosition()));
            int gridX = ((int) p.getCollisionBox().getX() / (tileSize.getWidth() * scale));
            int gridY = ((int) p.getCollisionBox().getY() / (tileSize.getHeight() * scale));
			messageMap.put("GIRD", String.format("%s", "(X: " + gridX + " Y: " + gridY + ")"));
            messageMap.put("DIR", String.format("%s", p.getDirection()));
        }
        int line = 1;
        for (String key : messageMap.keySet()) {
            g.setColor(Color.GRAY);
            g.drawString(messageMap.get(key), 2, line * 30 + 2);
            g.setColor(Color.WHITE);
            g.drawString(messageMap.get(key), 0, line * 30);
            line++;
        }
    }
}
