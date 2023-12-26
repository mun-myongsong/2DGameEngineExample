package ai.state;

import ai.AITransition;
import entity.Entity;
import state.State;

public abstract class AIState {
    private AITransition transition;

    public AIState() {
        transition = initTransition();
    }

    protected abstract AITransition initTransition();

    public String getNextState() {
        return transition.getNextState();
    }

    public boolean shouldTransition(State state, Entity entity) {
        return transition.shouldTransition(state, entity);
    }

    public abstract void update(State state, Entity entity);
}
