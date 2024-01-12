package state;

import core.Size;
import game.Game;
import ui.state.start.UIStartStateMenu;

public class StartState extends State {
    public StartState(Size windowSize, Game game) {
        super(windowSize, game);
    }

    @Override
    public void initializeUI(Size windowSize) {
        UIStartStateMenu menu = new UIStartStateMenu(windowSize, game);
        uiContainers.add(menu);
    }
}
