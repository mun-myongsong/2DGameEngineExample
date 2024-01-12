package ui.state.start;

import core.Size;
import game.Game;
import state.PlayState;
import ui.Alignment;
import ui.UIText;
import ui.VerticalContainer;
import ui.clickable.UIButton;

public class UIStartStateMenu extends VerticalContainer {
    private Game game;
    private UIText title;
    private UIButton start;
    private UIButton setting;
    private UIButton exit;

    public UIStartStateMenu(Size windowSize, Game game) {
        super(windowSize);
        this.game = game;
        setAlignment(Alignment.Position.CENTER, Alignment.Position.CENTER);
        title = new UIText("2D Game Engine Example");
        start = new UIButton("開始", () -> game.setState(new PlayState(game.getDisplay().getCanvasSize(), game)));
        setting = new UIButton("設定", () -> {});
        exit = new UIButton("終了", () -> game.stop());
        addUIComponentAll(title, start, setting, exit);
    }
}
