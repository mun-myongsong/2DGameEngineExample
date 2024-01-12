package state;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.Position;
import core.Size;
import display.Display;
import display.GameCamera;
import entity.Entity;
import entity.creature.Enemy;
import game.Game;
import input.Keyboard;
import input.Mouse;
import input.MouseConsumer;
import map.GameMap;
import ui.UIContainer;
import ui.debug.UIDebugInfo;

public abstract class State {
    protected Game game;
    protected Keyboard keyboard;
    protected Mouse mouse;
    protected GameMap gameMap;
    protected GameCamera gameCamera;
    protected MouseConsumer activeConsumer;
    protected Random random;
    protected List<Entity> entities;
    protected List<UIContainer> uiContainers;
    protected List<UIContainer> uiDebugContainers;
    protected UIDebugInfo uiDebugInfo;

    public State(Size windowSize, Game game) {
        this.game = game;
        keyboard = game.getKeyboard();
        mouse = game.getMouse();
        gameMap = new GameMap();
        gameCamera = new GameCamera(game.getDisplay().getCanvasSize());
        random = new Random();
        entities = new ArrayList<>();
        uiContainers = new ArrayList<>();
        uiDebugContainers = new ArrayList<>();
        initializeUI(windowSize);
    }

    protected void generateEntity(int num) {
        for (int i = 0; i < num; i++) {
            int randomX = random.nextInt(gameMap.getWidth()) * gameMap.getTileSize().getWidth() * gameMap.getScale();
            int randomY = random.nextInt(gameMap.getHeight()) * gameMap.getTileSize().getHeight() * gameMap.getScale();
            entities.add(
                new Enemy(
                    gameMap,
                    "Enemy" + i,
                    keyboard,
                    new Position(randomX, randomY),
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

    public GameCamera getGameCamera() {
		return gameCamera;
	}

	public GameMap getGameMap() {
        return gameMap;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public List<UIContainer> getUiContainers() {
        return uiContainers;
    }

    public List<UIContainer> getUiDebugContainers() {
        return uiDebugContainers;
    }

    public abstract void initializeUI(Size windowSize);

    public void setActiveConsumer(MouseConsumer activeConsumer) {
        this.activeConsumer = activeConsumer;
    }

    public void update() {
        keyboard.update();
        entities.forEach(entity -> entity.update(this));
        uiContainers.forEach(uiContainer -> uiContainer.update(this));
        if (Display.debug) {
            uiDebugContainers.forEach(uiContainer -> uiContainer.update(this));
        }
        gameCamera.update(this);
        mouse.cleanUpInputEvents();
    }
}
