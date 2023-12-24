package display;

import java.awt.Rectangle;
import java.util.Optional;

import core.Position;
import core.Size;
import entity.creature.Player;
import state.State;

public class GameCamera {
	private Optional<Player> focusObject;
	private Position position;
	private Size windowSize;
	private Rectangle viewBounds;

	public GameCamera(Size windowSize) {
		this.windowSize = windowSize;
		position = new Position();
		focusObject = Optional.empty();
		calculateViewBounds();
	}

	private void calculateViewBounds() {
		viewBounds = new Rectangle((int) position.getX(), (int) position.getY(), windowSize.getWidth(), windowSize.getHeight());
	}

	public void focusOn(Player player) {
		focusObject = Optional.of(player);
	}

	public boolean isInView(Player player) {
		return viewBounds.intersects(player.getX(), player.getY(), player.getWidth(), player.getHeight());
	}

	public Position getPosition() {
		return position;
	}

	public Size getWindowSize() {
		return windowSize;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void update(State state) {
		if (focusObject.isPresent()) {
            Position objectPosition = focusObject.get().getPosition();
            position.setX(objectPosition.getX() - windowSize.getWidth() / 2);
            position.setY(objectPosition.getY() - windowSize.getHeight() / 2);
        }
	}
}
