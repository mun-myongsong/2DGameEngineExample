package controller;

import core.Position;

public class EnemyController implements EntityController {
    private boolean up;
    private boolean down;
    private boolean right;
    private boolean left;

    public boolean isRequestKeyAction() {
        return false;
	}

    public boolean isRequestKeyDown() {
        return down;
    }

    public boolean isRequestKeyLeft() {
        return left;
    }

    public boolean isRequestKeyRight() {
        return right;
    }

    public boolean isRequestKeyUp() {
        return up;
    }

    public void moveToTarget(Position target, Position current) {
        double deltaX = target.getX() - current.getX();
        double deltaY = target.getY() - current.getY();
        up    = deltaY < 0 && Math.abs(deltaY) > Position.PROXIMITY_RANGE;
        right = deltaX > 0 && Math.abs(deltaX) > Position.PROXIMITY_RANGE;
        down  = deltaY > 0 && Math.abs(deltaY) > Position.PROXIMITY_RANGE;
        left  = deltaX < 0 && Math.abs(deltaX) > Position.PROXIMITY_RANGE;
    }

    public void stop() {
        up = down = left = right = false;
    }
}
