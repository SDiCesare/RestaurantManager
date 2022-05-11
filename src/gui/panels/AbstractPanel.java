package gui.panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractPanel extends JPanel {

    private final BufferedImage backgroundImage;

    protected AbstractPanel() {
        super();
        try {
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream(this.getBackgroundAssets());
            if (stream == null) {
                throw new RuntimeException("Background image not find: " + getBackgroundAssets());
            }
            this.backgroundImage = ImageIO.read(stream);
        } catch (IOException ex) {
            throw new RuntimeException("Invalid Background assets: " + getBackgroundAssets());
        }
    }

    abstract String getBackgroundAssets();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
