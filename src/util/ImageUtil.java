package util;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.Size;

public class ImageUtil {
    public static BufferedImage createCompatibleImage(Size size, int transparency) {
        GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        return config.createCompatibleImage(size.getWidth(), size.getHeight(), transparency);
    }

    public static BufferedImage loadImage(String path) throws IOException, IllegalArgumentException {
        try {
            Image imageFromDisk = ImageIO.read(ImageUtil.class.getResource(path));
            BufferedImage compatibleImage = createCompatibleImage(
                new Size(imageFromDisk.getWidth(null), imageFromDisk.getHeight(null)), Transparency.BITMASK);
            Graphics2D graphics = compatibleImage.createGraphics();
            graphics.drawImage(imageFromDisk, 0, 0, null);
            graphics.dispose();
            return compatibleImage;
        } catch (IOException e) {
            throw new IOException("指定されたパスからイメージを読み込む事が出来ませんでした。 path: " + path, e);
        } catch (IllegalArgumentException e) {
            throw new IOException("指定されたパスが存在しませんでした。 path: " + path, e);
        }
    }
}
