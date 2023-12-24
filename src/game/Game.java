package game;

import core.Size;
import display.Display;
import input.Keyboard;
import state.PlayState;
import state.State;

public class Game {
    private Display display;
    private Keyboard keyboard;
    private State state;

    public Game() {
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

    public void update() {
        state.update();
    }
}
