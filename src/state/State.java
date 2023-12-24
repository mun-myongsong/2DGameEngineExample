package state;

import display.GameCamera;
import game.Game;
import input.Keyboard;
import map.GameMap;

public abstract class State {
    protected Game game;
    protected Keyboard keyboard;
    protected GameMap gameMap;
    protected GameCamera gameCamera;

    public State(Game game) {
        this.game = game;
        keyboard = game.getKeyboard();
        gameMap = new GameMap();
        gameCamera = new GameCamera(game.getDisplay().getCanvasSize());
    }

    public GameCamera getGameCamera() {
		return gameCamera;
	}

	public GameMap getGameMap() {
        return gameMap;
    }

    public void update() {
        gameCamera.update(this);
        keyboard.update();
    }
}
