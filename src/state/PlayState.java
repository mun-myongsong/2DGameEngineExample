package state;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.Position;
import core.Size;
import entity.Entity;
import entity.creature.Enemy;
import entity.creature.Player;
import game.Game;

public class PlayState extends State {
    private Player player;
    private List<Entity> entities;
    private Random random;

    public PlayState(Game game) {
        super(game);
        player = new Player(
            "Tester",
            keyboard,
            new Position(5 * gameMap.getTileSize().getWidth() * gameMap.getScale(), 3 * gameMap.getTileSize().getHeight() * gameMap.getScale()),
            new Size(gameMap.getTileSize().getWidth(), gameMap.getTileSize().getHeight()),
            "/character/cyber/judyscuba.png",
            32, 32);    //TODO キャラクタのみタイルサイズが32の為
        random = new Random();
        generateEntity(10);
        gameCamera.focusOn(player);
    }

    // TODO
    private void generateEntity(int num) {
        entities = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            entities.add(
                new Enemy(
                    "Enemy" + i,
                    keyboard,
                    new Position(random.nextInt(gameMap.getHeight()) * gameMap.getTileSize().getWidth() * gameMap.getScale(), random.nextInt(gameMap.getWidth()) * gameMap.getTileSize().getHeight() * gameMap.getScale()),
                    new Size(gameMap.getTileSize().getWidth(), gameMap.getTileSize().getHeight()),
                    "/character/cyber/davidmartinez.png",
                    32, 32    //TODO キャラクタのみタイルサイズが32の為
                )
            );
        }
    }

    public List<Entity> getEntities() {
        return entities;
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
