package game;

import java.awt.event.KeyEvent;

import core.Size;
import display.Display;
import input.Keyboard;
import input.Mouse;
import state.PlayState;
import state.StartState;
import state.State;

public class Game {
    private GameLoop gameLoop;
    private Display display;
    private Keyboard keyboard;
    private Mouse mouse;
    private State state;

    public Game(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
        keyboard = new Keyboard();
        mouse = new Mouse();
        display = new Display(keyboard, mouse, "2D Game Engine Example", new Size(1920, 1080));
        // state = new PlayState(display.getCanvasSize(), this);
        state = new StartState(display.getCanvasSize(), this);
    }

    public Display getDisplay() {
		return display;
	}

	public Keyboard getKeyboard() {
        return keyboard;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void render() {
        display.render(state);
    }

    public void setState(State state) {
        this.state = state;
    }

    public void stop() {
        gameLoop.stop();
    }

    public void update() {
        if (keyboard.isJustPressedKey(KeyEvent.VK_ESCAPE)) {
            stop();
        }
        state.update();
    }
}
