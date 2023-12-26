package entity.creature;

import java.awt.Rectangle;

import ai.AIManager;
import controller.EnemyController;
import core.Position;
import core.Size;
import display.DebugRenderer;
import entity.creature.Direction;
import game.GameLoop;
import gfx.Animation;
import gfx.SpriteSheet;
import input.Keyboard;
import map.GameMap;
import state.State;

public class Enemy extends Creature {
    private AIManager aiManager;

    public Enemy(GameMap gameMap, String name, Keyboard keyboard, Position position, Size size, String fileName, int tileWidth, int tileHeight) {
        super(gameMap, name, keyboard, position, size, fileName, tileWidth, tileHeight);
        aiManager = new AIManager();
        collisionBox = new Rectangle((int)position.getX() + 25, (int)position.getY() + 25, 4 * gameMap.getScale(), 8 * gameMap.getScale());
        controller = new EnemyController();
    }

    @Override
    protected void determiningStatus() {
    }

    @Override
    protected void handleCollision(State state) {
        GameMap map = state.getGameMap();
        collisionBox.x += vector.getX();
        collisionBox.y += vector.getY();
        vector.setVector(0, 0);
        if (controller.isRequestKeyUp()) {
            changeAnimation(Direction.UP);
            vector.setVector(0, -walkSpeed);
        }
        if (controller.isRequestKeyDown()) {
            changeAnimation(Direction.DOWN);
            vector.setVector(0, walkSpeed);
        }
        if (controller.isRequestKeyLeft()) {
            changeAnimation(Direction.LEFT);
            vector.setVector(-walkSpeed, 0);
        }
        if (controller.isRequestKeyRight()) {
            changeAnimation(Direction.RIGHT);
            vector.setVector(walkSpeed, 0);
        }
        // TODO
        // if (!map.isWalkable(this) || !isWalkable(state)) {
        //     vector.setVector(0, 0);
        // }
        position.add(vector);
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
        aiManager.update(state, this);
    }
}
