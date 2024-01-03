package display;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import core.Position;
import core.Size;
import entity.creature.Player;
import map.GameMap;
import state.PlayState;
import state.State;

public class Renderer {
    public void render(Graphics2D g, State state) {
        renderGameMap(g, state);
        renderGameObjects(g, state);
        renderUI(g, state);
    }

    private void renderGameMap(Graphics2D g, State state) {
        GameMap gameMap = state.getGameMap();
        List<BufferedImage> tileset = gameMap.getTileSet();
        Size stageSize = gameMap.getStageSize();
        GameCamera camera = state.getGameCamera();
        for (int i = 0; i < GameMap.NUMBER_OF_MAP_LAYER - 1; i++) {
            for (int y = 0; y < stageSize.getHeight(); y++) {
                for (int x = 0; x < stageSize.getWidth(); x++) {
                    int id = gameMap.getMapDataCell(x, y, i);
                    if (id >= 1) {
                        Size tileSize = gameMap.getTileSize();
                        int scale = gameMap.getScale();
                        Position cp = camera.getPosition();
                        g.drawImage(
                            tileset.get(id - 1),
                            x * tileSize.getWidth() * scale - (int) cp.getX(), y * tileSize.getHeight() * scale - (int) cp.getY(),
                            tileSize.getWidth() * scale, tileSize.getHeight() * scale,
                            null);
                    }
                }
            }
        }
    }

    private void renderGameObjects(Graphics2D g, State state) {
        GameMap gameMap = state.getGameMap();
        if (state instanceof PlayState playState) {
            GameCamera camera = state.getGameCamera();
            Position cp = camera.getPosition();
            Player player = playState.getPlayer();
            playState.getEntities().stream().forEach(entity -> {
                g.drawImage(
                    entity.getCurrentImage(),
                    (int) entity.getX() - (int) cp.getX(),
                    (int) entity.getY() - (int) cp.getY(),
                    gameMap.getTileSize().getWidth() * gameMap.getScale(),
                    gameMap.getTileSize().getHeight() * gameMap.getScale(),
                    null);
            });
            g.drawImage(
                player.getCurrentImage(),
                (int) player.getX() - (int) cp.getX(),
                (int) player.getY() - (int) cp.getY(),
                gameMap.getTileSize().getWidth() * gameMap.getScale(),
                gameMap.getTileSize().getHeight() * gameMap.getScale(),
                null);
        }
    }

    private void renderUI(Graphics2D g, State state) {
        state.getUiContainers().forEach(uiContainer -> g.drawImage(
            uiContainer.getSprite(),
            (int)uiContainer.getRelativePosition().getX(),
            (int)uiContainer.getRelativePosition().getY(),
            null
        ));
    }
}
