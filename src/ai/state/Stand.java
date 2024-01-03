package ai.state;

import ai.AITransition;
import entity.Entity;
import game.GameLoop;
import state.State;

public class Stand extends AIState {
    private int alive;

    @Override
    public AITransition initTransition() {
        return new AITransition("wander", ((state, eneity) -> alive >= GameLoop.ups * 3));
    }

    @Override
    public void update(State state, Entity entity) {
        alive++;
    }
}
