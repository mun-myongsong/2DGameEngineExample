package entity.creature;

import java.awt.Rectangle;

import core.Position;
import core.Size;
import game.GameLoop;
import gfx.Animation;
import gfx.SpriteSheet;
import input.Keyboard;
import map.GameMap;
import state.State;

public class Enemy extends Creature {
    public Enemy(GameMap gameMap, String name, Keyboard keyboard, Position position, Size size, String fileName, int tileWidth, int tileHeight) {
        super(gameMap, name, keyboard, position, size, fileName, tileWidth, tileHeight);
        collisionBox = new Rectangle((int)position.getX() + 25, (int)position.getY() + 25, 4, 8);
    }

    @Override
    protected void determiningStatus() {
    }

    @Override
    protected void registerAnimations(SpriteSheet spriteSheet) {
        super.registerAnimations(spriteSheet);
        animations.put(Direction.DOWN , new Animation(spriteSheet.getTextures(0,  4 ), (long) (0.3 * GameLoop.ups), true));
        animations.put(Direction.RIGHT, new Animation(spriteSheet.getTextures(4,  8 ), (long) (0.3 * GameLoop.ups), true));
        animations.put(Direction.LEFT , new Animation(spriteSheet.getTextures(8,  12), (long) (0.3 * GameLoop.ups), true));
        animations.put(Direction.UP   , new Animation(spriteSheet.getTextures(12, 16), (long) (0.3 * GameLoop.ups), true));
	}

    @Override
    public void update(State state) {
        super.update(state);
    }
}
