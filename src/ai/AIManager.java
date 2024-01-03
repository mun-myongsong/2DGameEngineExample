package ai;

import ai.state.AIState;
import ai.state.Stand;
import ai.state.Wander;
import entity.Entity;
import state.State;

public class AIManager {
    private AIState currentAIState;

    public AIManager() {
        transitionTo("stand");
    }

    public AIState getCurrentAIState() {
        return currentAIState;
    }

    private void transitionTo(String nextState) {
        switch (nextState) {
        case "wander":
            currentAIState = new Wander();
            break;
        case "stand":
        default:
            currentAIState = new Stand();
        }
    }

    public void update(State state, Entity entity) {
        currentAIState.update(state, entity);
        if (currentAIState.shouldTransition(state, entity)) {
            transitionTo(currentAIState.getNextState());
        }
    }
}
