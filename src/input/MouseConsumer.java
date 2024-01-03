package input;

import state.State;

public interface MouseConsumer {
    void onClick(State state);
    void onDrag(State state);
    void onRelease(State state);
}
