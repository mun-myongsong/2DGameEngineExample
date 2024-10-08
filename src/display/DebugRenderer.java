package display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import core.Position;
import core.Size;
import entity.creature.Enemy;
import entity.creature.Player;
import game.GameLoop;
import map.GameMap;
import state.PlayState;
import state.State;

public class DebugRenderer {
    public void renderDebug(Graphics2D g, State state) {
        renderMapDataCollisionBox(g, state);
        renderDebugCollisionBox(g, state);
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
                    (int)entity.getCollisionBox().getWidth(), (int)entity.getCollisionBox().getHeight());
                if (entity instanceof Enemy enemy) {
                    g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
                    g.setColor(Color.GRAY);
                    g.drawString(enemy.getCurrentState(), (int)(entity.getX() - cp.getX() + 2), (int)(entity.getY() - 8 - cp.getY()));
                    g.setColor(Color.WHITE);
                    g.drawString(enemy.getCurrentState(), (int)(entity.getX() - cp.getX()), (int)(entity.getY() - 10 - cp.getY()));
                    g.setColor(Color.RED);
                }
            });
            g.drawRect(
                (int)(p.getCollisionBox().getX() - cp.getX()), (int)(p.getCollisionBox().getY() - cp.getY()),
                (int)p.getCollisionBox().getWidth(), (int)p.getCollisionBox().getHeight());
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
}
