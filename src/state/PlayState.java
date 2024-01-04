package state;

import java.awt.Color;

import core.Position;
import core.Size;
import display.Display;
import entity.Entity;
import entity.creature.Player;
import game.Game;
import game.GameLoop;
import ui.Alignment;
import ui.UIContainer;
import ui.VerticalContainer;
import ui.clickable.UIButton;
import ui.debug.UIDebugInfo;

public class PlayState extends State {
    private Player player;

    public PlayState(Size windowSize, Game game) {
        super(game);
        player = new Player(
            gameMap,
            "Tester",
            keyboard,
            new Position(320, 192),
            new Size(gameMap.getTileSize().getWidth(), gameMap.getTileSize().getHeight()),
            "/character/cyber/judyscuba.png",
            32, 32);    //TODO キャラクタのみタイルサイズが32の為
        generateEntity(10);
        gameCamera.focusOn(player);
        initializeUI(windowSize);
    }

    private void initializeUI(Size windowSize) {
        UIContainer container = new VerticalContainer(windowSize);
        container.setAlignment(Alignment.Position.END, Alignment.Position.START);
        container.addUIComponent(new UIButton("DEBUG", () -> Display.debug = !Display.debug));
        uiContainers.add(container);
        UIDebugInfo uiDebugInfo = new UIDebugInfo(windowSize);
        uiDebugContainers.add(uiDebugInfo);
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void update() {
        super.update();
        player.update(this);
        entities.forEach(entity -> entity.update(this));
    }
}
