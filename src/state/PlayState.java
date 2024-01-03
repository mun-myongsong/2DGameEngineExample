package state;

import core.Position;
import core.Size;
import entity.creature.Player;
import game.Game;
import ui.HorizontalContainer;
import ui.UIText;
import ui.UIContainer;
import ui.VerticalContainer;

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
        container.setAlignment(new ui.Alignment(ui.Alignment.Position.CENTER, ui.Alignment.Position.CENTER));
        container.setPadding(new ui.Space(5));
        container.addUIComponent(new ui.clickable.UIButton("Menu", () -> System.out.println("Menu")));
        container.addUIComponent(new ui.clickable.UIButton("Options", () -> System.out.println("Options")));
        container.addUIComponent(new ui.clickable.UIButton("Exit", () -> System.exit(0)));
        uiContainers.add(container);
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void update() {
        super.update();
        player.update(this);
    }
}
