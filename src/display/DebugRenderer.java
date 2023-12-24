package display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import core.Position;
import core.Size;
import entity.creature.Player;
import game.GameLoop;
import map.GameMap;
import state.PlayState;
import state.State;

public class DebugRenderer {
    public void renderDebug(Graphics2D g, State state) {
        renderMapDataCollisionBox(g, state);
        renderDebugCollisionBox(g, state);
        renderDebugGameInfo(g, state);
    }

    private void renderDebugCollisionBox(Graphics2D g, State state) {
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(2));
        if (state instanceof PlayState pState) {
            Position cp = state.getGameCamera().getPosition();
            Player p = pState.getPlayer();
            int scale = state.getGameMap().getScale();
            g.drawRect(
                (int) (p.getCollisionBox().getX() - cp.getX()), (int) (p.getCollisionBox().getY() - cp.getY()),
                (int) p.getCollisionBox().getWidth() * scale, (int) p.getCollisionBox().getHeight() * scale);
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
        if (state instanceof PlayState pState) {
            if (gm.getTileRect().x != -1) {
                Player player = pState.getPlayer();
                g.setColor(player.isWalkable() ? Color.BLUE : Color.RED);
                g.setStroke(new BasicStroke(3));
                g.drawRect(gm.getTileRect().x - (int) cp.getX(), gm.getTileRect().y - (int) cp.getY(), tileSize.getWidth() * scale, tileSize.getHeight() * scale);
                g.drawRect(gm.getTileRect2().x - (int) cp.getX(), gm.getTileRect2().y - (int) cp.getY(), tileSize.getWidth() * scale, tileSize.getHeight() * scale);
            }
        }
    }

    private void renderDebugGameInfo(Graphics2D g, State state) {
        g.setColor(Color.RED);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        g.drawString(String.format("RAM: %.3fMB", ((double)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000) / 1000), 5, 30);
        g.drawString(String.format("FPS: %d", GameLoop.fpsCount), 5, 60);
        g.drawString(String.format("UPS: %d", GameLoop.upsCount), 5, 90);
        g.drawString(String.format("1S : %d", GameLoop.loopCount), 5, 120);
        if (state instanceof PlayState pState) {
            Player p = pState.getPlayer();
            GameMap gm = state.getGameMap();
            Size tileSize = gm.getTileSize();
            int scale = gm.getScale();
			g.drawString(String.format("%s", "Player: " + p.getPosition()), 5, 150);
            int gridX = ((int) p.getCollisionBox().getX() / (tileSize.getWidth() * scale));
            int gridY = ((int) p.getCollisionBox().getY() / (tileSize.getHeight() * scale));
			g.drawString(String.format("%s", "(X: " + gridX + " Y: " + gridY + ")"), 5, 180);
            g.drawString(String.format("%s", p.getDirection()), 5, 210);
        }
    }
}
