package state;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.Position;
import core.Size;
import entity.Entity;
import entity.creature.Player;
import game.Game;

public class PlayState extends State {
    private Player player;
    private List<Entity> entities;

    public PlayState(Game game) {
        super(game);
        player = new Player(
            "Tester",
            keyboard,
            new Position(5 * gameMap.getTileSize().getWidth() * gameMap.getScale(), 3 * gameMap.getTileSize().getHeight() * gameMap.getScale()),
            new Size(gameMap.getTileSize().getWidth(), gameMap.getTileSize().getHeight()),
            "/character/cyber/judyscuba.png",
            32, 32);    //TODO キャラクタのみタイルサイズが32の為
        generateEntity(10);
        gameCamera.focusOn(player);
    }

    // TODO
    private void generateEntity(int num) {
        entities = new ArrayList<>();
        for (int i = 0; i < num; i++) {
        }
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
