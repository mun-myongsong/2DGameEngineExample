package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import controller.EnemyController;
import controller.EntityController;
import core.Position;
import core.Size;
import core.Vector2D;
import entity.creature.Direction;
import gfx.Animation;
import gfx.SpriteSheet;
import input.Keyboard;
import map.GameMap;
import state.PlayState;
import state.State;

public abstract class Entity {
    private static final int DISTANCE_TO_ENTITY = 100;

    protected GameMap gameMap;
    protected Position position;
    protected Size size;
    protected Vector2D vector;
    protected Keyboard keyboard;
    protected Map<Direction, Animation> animations;
    protected Animation currentAnimation;
    protected Direction direction;
    protected Rectangle collisionBox;
    protected double walkSpeed;
    protected EntityController controller;

    public Entity(GameMap gameMap, Keyboard keyboard, Position position, Size size, String fileName, int tileWidth, int tileHeight) {
        this.gameMap = gameMap;
        this.position = position;
        this.size = size;
        this.keyboard = keyboard;
        vector = new Vector2D();
        SpriteSheet spriteSheet = new SpriteSheet(fileName, tileWidth, tileHeight);
        registerAnimations(spriteSheet);
        currentAnimation = animations.get(Direction.DOWN);
        direction = Direction.STOP;
        collisionBox = new Rectangle((int)position.getX(), (int)position.getY(),
                size.getWidth() * gameMap.getScale(), size.getHeight() * gameMap.getScale());
        walkSpeed = 3;
    }

    protected void changeAnimation(Direction direction) {
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

    public EntityController getController() {
        return controller;
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

    protected void handleCollision(State state) {
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
        if (!map.isWalkable(this) || !isWalkable(state)) {
            vector.setVector(0, 0);
        }
        position.add(vector);
    }

    private boolean isCollision(Entity entity) {
        Rectangle rect = new Rectangle(
                collisionBox.x + (int)vector.getX(), collisionBox.y + (int)vector.getY(),
                collisionBox.width, collisionBox.height);
        display.DebugRenderer.messageMap.put("ENTITYPOSITION", String.format("Enemy: %s", entity.getPosition()));
        return entity.getCollisionBox().intersects(rect);
    }

    public boolean isWalkable() {
        return (vector.getX() != 0 || vector.getY() != 0);
    }

    protected boolean isWalkable(State state) {
        if (state instanceof PlayState playState) {
            List<Entity> filterList = playState.getEntities().stream()
                .filter(entity -> Math.abs(getX() - entity.getX()) < DISTANCE_TO_ENTITY && Math.abs(getY() - entity.getY()) < DISTANCE_TO_ENTITY)
                .collect(Collectors.toList());
            for (int i = 0; i < filterList.size(); i++) {
                if (isCollision(filterList.get(i))) {
                    return false;
                } else {
                    display.DebugRenderer.messageMap.remove("ENTITYPOSITION");
                }
            }
        }
        return true;
    }

    protected void registerAnimations(SpriteSheet spriteSheet) {
        animations = new HashMap<>();
    }

    public void update(State state) {
        handleAnimation();
        handleCollision(state);
    }
}
