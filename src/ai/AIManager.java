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

    public void update(State state, Entity entity) {
        currentAIState.update(state, entity);
        if (currentAIState.shouldTransition(state, entity)) {
            transitionTo(currentAIState.getNextState());
        }
    }

    private void transitionTo(String nextState) {
        switch (nextState) {
        case "wander":
            display.DebugRenderer.messageMap.put("AISTATE", String.format("%s", "Wander"));
            currentAIState = new Wander();
            break;
        case "stand":
        default:
            display.DebugRenderer.messageMap.put("AISTATE", String.format("%s", "Stand"));
            currentAIState = new Stand();
        }
    }
}
