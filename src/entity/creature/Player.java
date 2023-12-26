package entity.creature;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import core.Position;
import core.Size;
import core.Vector2D;
import game.GameLoop;
import gfx.Animation;
import gfx.SpriteSheet;
import input.Keyboard;
import map.GameMap;
import state.State;

public class Player extends Creature {
    public Player(GameMap gameMap, String name, Keyboard keyboard, Position position, Size size, String fileName, int tileWidth, int tileHeight) {
        super(gameMap, name, keyboard, position, size, fileName, tileWidth, tileHeight);
        collisionBox = new Rectangle((int)position.getX() + 25, (int)position.getY() + 25, 4 * gameMap.getScale(), 8 * gameMap.getScale());
    }

    @Override
    protected void determiningStatus() {
        maxhp = 30;
        maxmp = 10;
        hp = maxhp;
        mp = maxmp;
        // TODO nextlevel;
        offens = 2;
        defens = 2;
        speed = 2;
        guts = 2;
        vitality = 2;
        iq = 2;
        luck = 2;
    }

    private void handleInput() {
        direction = Direction.STOP;
        if (keyboard.isPressedKey(KeyEvent.VK_W)) {
            direction = Direction.UP;
        }
        if (keyboard.isPressedKey(KeyEvent.VK_A)) {
            direction = Direction.LEFT;
        }
        if (keyboard.isPressedKey(KeyEvent.VK_S)) {
            direction = Direction.DOWN;
        }
        if (keyboard.isPressedKey(KeyEvent.VK_D)) {
            direction = Direction.RIGHT;
        }
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
        handleInput();
    }
}
