package display;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import core.Size;
import input.Keyboard;
import input.Mouse;
import state.State;

public class Display extends JFrame {
    private Canvas canvas;
    private Keyboard keyboard;
    private Mouse mouse;
    private Renderer renderer;
    private DebugRenderer debugRenderer;

    public Display(Keyboard keyboard, Mouse mouse, String title, Size windowSize) {
        super(title);
        this.keyboard = keyboard;
        this.mouse = mouse;
        renderer = new Renderer();
        debugRenderer = new DebugRenderer();
        createFrame(windowSize);
        createCanvas(windowSize);
        add(canvas);
        pack();
        setVisible(true);
        canvas.createBufferStrategy(2);
        addKeyListener(this.keyboard);
        canvas.addKeyListener(this.keyboard);
        canvas.addMouseListener(this.mouse);
        canvas.addMouseMotionListener(this.mouse);
        canvas.setFocusable(true);
    }

    private void createCanvas(Size windowSize) {
        GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        canvas = new Canvas(config);
        Dimension dimension = new Dimension(windowSize.getWidth(), windowSize.getHeight());
        canvas.setPreferredSize(dimension);
        canvas.setMaximumSize(dimension);
        canvas.setMinimumSize(dimension);
        canvas.setBackground(Color.BLACK);
    }

    private void createFrame(Size windowSize) {
        setSize(windowSize.getWidth(), windowSize.getHeight());
        setUndecorated(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public Size getCanvasSize() {
        return new Size(canvas.getWidth(), canvas.getHeight());
    }

	public void render(State state) {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(2);
            return;
        }
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        renderer.render(g, state);
        debugRenderer.renderDebug(g, state);
        g.dispose();
        bs.show();
    }
}
