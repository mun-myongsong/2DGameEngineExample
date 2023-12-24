import game.GameLoop;

public class Launcher {
    private Launcher() {
        Thread thread = new Thread(new GameLoop());
        thread.start();
    }

    public static void main(String[] args) {
        new Launcher();
    }
}
