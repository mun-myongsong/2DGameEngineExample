package game;

import java.awt.event.KeyEvent;

import core.Size;
import display.Display;
import input.Keyboard;
import state.PlayState;
import state.State;

public class Game {
    private GameLoop gameLoop;
    private Display display;
    private Keyboard keyboard;
    private State state;

    public Game(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
        keyboard = new Keyboard();
        display = new Display(keyboard, "Web Diver", new Size(1920, 1080));
        state = new PlayState(this);
    }

    public Display getDisplay() {
		return display;
	}

	public Keyboard getKeyboard() {
        return keyboard;
    }

    public void render() {
        display.render(state);
    }

    private void stop() {
        display.setVisible(false);
        display.dispose();
        gameLoop.stop();
    }

    public void update() {
        if (keyboard.isJustPressedKey(KeyEvent.VK_ESCAPE)) {
            stop();
        }
        state.update();
    }
}
