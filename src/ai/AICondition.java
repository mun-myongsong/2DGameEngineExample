package ai;

import entity.Entity;
import state.State;

public interface AICondition {
    boolean isMet(State state, Entity entity);
}
