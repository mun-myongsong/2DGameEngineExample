package gfx;

import java.awt.image.BufferedImage;
import java.util.List;

public class Animation {
    private BufferedImage currentImage;
    private List<BufferedImage> images;
    private long currentFrame;
    private long nextFrame;
    private int idx;
    private boolean loop;
    private boolean running;

    public Animation(List<BufferedImage> images, long nextFrame, boolean loop) {
        this.images = images;
        this.nextFrame = nextFrame;
        this.loop = loop;
        currentImage = images.get(0);
    }

    public BufferedImage getCurrentImage() {
		return currentImage;
	}

    public void start() {
        running = true;
    }

    public void stop() {
        running = false;
    }

	public void update() {
		if (running) {
			currentFrame++;
		} else {
			if (currentFrame != 0 && idx != 0) {
				currentFrame = 0;
				idx = 0;
			}
		}
        if (currentFrame == nextFrame) {
            currentFrame = 0;
            if (loop && idx == images.size() - 1) {
                idx = 0;
            } else {
                idx++;
            }
        }
        currentImage = images.get(idx);
    }
}
