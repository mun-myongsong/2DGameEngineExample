package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import core.Position;
import core.Size;
import core.Vector2D;
import entity.creature.Direction;
import gfx.Animation;
import gfx.SpriteSheet;
import input.Keyboard;
import map.GameMap;
import state.State;

public abstract class Entity {
    protected Position position;
    protected Size size;
    protected Vector2D vector;
    protected Keyboard keyboard;
    protected Map<Direction, Animation> animations;
    protected Animation currentAnimation;
    protected Direction direction;
    protected Rectangle collisionBox;
    protected double walkSpeed;

    public Entity(Keyboard keyboard, Position position, Size size, String fileName, int tileWidth, int tileHeight) {
        this.position = position;
        this.size = size;
        this.keyboard = keyboard;
        vector = new Vector2D();
        SpriteSheet spriteSheet = new SpriteSheet(fileName, tileWidth, tileHeight);
        registerAnimations(spriteSheet);
        currentAnimation = animations.get(Direction.DOWN);
        direction = Direction.STOP;
        collisionBox = new Rectangle((int)position.getX(), (int)position.getY(), size.getWidth(), size.getHeight());
        walkSpeed = 3;
    }

    private void changeAnimation(Direction direction) {
		currentAnimation.start();
        if (!currentAnimation.equals(animations.get(direction))) {
            currentAnimation = animations.get(direction);
        }
    }

    public BufferedImage getCurrentImage() {
        return currentAnimation.getCurrentImage();
    }

    public Direction getDirection() {
        return direction;
    }

    public Map<Direction, Animation> getAnimations() {
        return animations;
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    public Rectangle getCollisionBox() {
        return collisionBox;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public int getHeight() {
        return size.getHeight();
    }

	public Position getPosition() {
        return position;
    }

    public Size getSize() {
        return size;
    }

    public Vector2D getVector() {
        return vector;
    }

    public int getWidth() {
        return size.getWidth();
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    private void handleAnimation() {
        switch (direction) {
        case UP:
            changeAnimation(Direction.UP);
            break;
        case LEFT:
            changeAnimation(Direction.LEFT);
            break;
        case DOWN:
            changeAnimation(Direction.DOWN);
            break;
        case RIGHT:
            changeAnimation(Direction.RIGHT);
            break;
        case STOP:
            currentAnimation.stop();
        default:
            break;
        }
        currentAnimation.update();
    }

    private void handleCollision(State state) {
        GameMap map = state.getGameMap();
        collisionBox.x += vector.getX();
        collisionBox.y += vector.getY();
        switch (direction) {
        case UP:
            vector.setVector(0, -walkSpeed);
            break;
        case LEFT:
            vector.setVector(-walkSpeed, 0);
            break;
        case DOWN:
            vector.setVector(0, walkSpeed);
            break;
        case RIGHT:
            vector.setVector(walkSpeed, 0);
            break;
        case STOP:
        default:
            vector.setVector(0, 0);
            break;
        }
        if (!map.isWalkable(this)) {
            vector.setVector(0, 0);
        }
        position.add(vector);
    }

    public boolean isWalkable() {
        return (vector.getX() != 0 || vector.getY() != 0);
    }

    protected void registerAnimations(SpriteSheet spriteSheet) {
        animations = new HashMap<>();
    }

    public void update(State state) {
        handleAnimation();
        handleCollision(state);
    }
}
