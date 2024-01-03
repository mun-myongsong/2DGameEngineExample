package ai.state;

import java.util.List;

import ai.AITransition;
import controller.EnemyController;
import core.Position;
import entity.Entity;
import state.State;

public class Wander extends AIState {
    private Position target;

    public Wander() {
        super();
    }

    private boolean arrived(Entity entity) {
        return target != null && entity.getPosition().isInRangeOf(target);
    }

    @Override
    protected AITransition initTransition() {
        return new AITransition("stand", ((state, entity) -> arrived(entity)));
    }

    @Override
    public void update(State state, Entity entity) {
        EnemyController controller = (EnemyController)entity.getController();
        if (target == null) {
            target = state.getGameMap().findRandomPosition();
        }
        if (arrived(entity)) {
            controller.stop();
        }
        if (target != null) {
            controller.moveToTarget(target, entity.getPosition());
        }
    }
}
