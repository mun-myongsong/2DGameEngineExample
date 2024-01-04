package ui.debug;

import java.awt.Color;

import core.Size;
import entity.creature.Player;
import game.GameLoop;
import map.GameMap;
import state.PlayState;
import state.State;
import ui.VerticalContainer;
import ui.UIText;

public class UIDebugInfo extends VerticalContainer {
    private UIText ram = new UIText("RAM");
    private UIText fps = new UIText("FPS");
    private UIText ups = new UIText("UPS");
    private UIText loop = new UIText("LOOP");
    private UIText pos = new UIText("POS");
    private UIText grid = new UIText("GRID");
    private UIText dir = new UIText("DIR");

    public UIDebugInfo(Size windowSize) {
        super(windowSize);
        setBackgroundColor(new Color(0, 0, 0, 0));
        addUIComponentAll(ram, fps, ups, loop, pos, grid, dir);
    }

    public void update(State state) {
        super.update(state);
        ram.setText(String.format("RAM: %.3fMB",
                    (((double)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000) / 1000)));
        fps.setText(String.format("FPS: %d", GameLoop.fpsCount));
        ups.setText(String.format("UPS: %d", GameLoop.upsCount));
        loop.setText(String.format("1S : %d", GameLoop.loopCount));
        if (state instanceof PlayState pState) {
            Player p = pState.getPlayer();
            GameMap gm = state.getGameMap();
            Size tileSize = gm.getTileSize();
            int scale = gm.getScale();
			pos.setText(String.format("%s", "Player: " + p.getPosition()));
            int gridX = ((int) p.getCollisionBox().getX() / (tileSize.getWidth() * scale));
            int gridY = ((int) p.getCollisionBox().getY() / (tileSize.getHeight() * scale));
			grid.setText(String.format("%s", "(X: " + gridX + " Y: " + gridY + ")"));
            dir.setText(String.format("%s", p.getDirection()));
        }
    }
}
