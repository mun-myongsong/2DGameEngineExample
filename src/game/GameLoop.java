package game;

public class GameLoop implements Runnable {
    public static int fpsCount = 0;
    public static int upsCount = 0;
    public static long loopCount = 0;
    public static double fps = 120.0;
    public static double ups = 120.0;
    private boolean running;
    private double targetUpsFrame = 1000000000.0 / ups;
    private double targetFpsFrame = 1000000000.0 / fps;
    private Game game;

    public GameLoop() {
        running = true;
        game = new Game(this);
    }

    private void render() {
        game.render();
    }

    @Override
    public void run() {
        int fpsCnt = 0;
        double fpsDelta = 0;
        long timer = 0;
        long last = System.nanoTime();
        long loopCnt = 0;
        int upsCnt = 0;
        double upsDelta = 0;
        while (running) {
            long now = System.nanoTime();
            upsDelta += (now - last) / targetUpsFrame;
            fpsDelta += (now - last) / targetFpsFrame;
            timer += now - last;
            last = now;
            while (upsDelta >= 1) {
                update();
                upsDelta--;
                upsCnt++;
            }
            while (fpsDelta >= 1) {
                render();
                fpsDelta--;
                fpsCnt++;
            }
            loopCnt++;
            if (timer >= 1000000000) {
                fpsCount = fpsCnt;
                upsCount = upsCnt;
                loopCount = loopCnt;
                timer = fpsCnt = upsCnt = 0;
                loopCnt = 0;
            }
        }
        System.exit(0);
    }

    public void stop() {
        running = false;
    }

    private void update() {
        game.update();
    }
}
