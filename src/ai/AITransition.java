package ai;

import entity.Entity;
import state.State;

public class AITransition {
    private String nextState;
    private AICondition condition;

    public AITransition(String nextState, AICondition condition) {
        this.nextState = nextState;
        this.condition = condition;
    }

    public boolean shouldTransition(State state, Entity entity) {
        return condition.isMet(state, entity);
    }

    public String getNextState() {
        return nextState;
    }
}
