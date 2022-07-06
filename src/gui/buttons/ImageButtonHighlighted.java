package gui.buttons;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * A button that change his image when passed on with the mouse.
 * It's a JLabel that has no more functionality for text, and can change his Icon whenever the users
 * passed on it with the mouse.
 * */
public class ImageButtonHighlighted extends JLabel implements MouseListener {

    private Icon icon;
    private Icon highlightedIcon;
    private ActionListener action;

    /**
     * Crate an ImageButtonHighlighted with two image in the resources
     *
     * @param imageName The path of the image
     * @param lightedImageName The path of the highlighted image
     * */
    public ImageButtonHighlighted(String imageName, String lightedImageName) {
        super();
        BufferedImage image = null;
        BufferedImage highlightedImage = null;
        try {
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream(imageName);
            if (stream == null) {
                throw new RuntimeException("Button image not found: " + imageName);
            }
            image = ImageIO.read(stream);
            stream = this.getClass().getClassLoader().getResourceAsStream(lightedImageName);
            if (stream == null) {
                throw new RuntimeException("Button image not found: " + lightedImageName);
            }
            highlightedImage = ImageIO.read(stream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.icon = new ImageIcon(image);
        this.highlightedIcon = new ImageIcon(highlightedImage);
        this.setIcon(icon);
        this.addMouseListener(this);
    }

    /**
     * Defines the Action that the Label is doing when clicked on
     *
     * @param action The action to define for this Label
     * */
    public void setAction(ActionListener action) {
        this.action = action;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (action != null) {
            action.actionPerformed(new ActionEvent(e.getSource(), 0, "MouseClicked"));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void setText(String text) {

    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setIcon(highlightedIcon);
        Component root = SwingUtilities.getRoot(((Component) e.getSource()));
        root.repaint();
        root.revalidate();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setIcon(this.icon);
        Component root = SwingUtilities.getRoot(((Component) e.getSource()));
        root.repaint();
        root.revalidate();
    }
}
