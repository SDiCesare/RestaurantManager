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

//TODO Documentation
public class ImageButtonHighlighted extends JLabel implements MouseListener {

    private Icon icon;
    private Icon highlightedIcon;
    private ActionListener action;

    public ImageButtonHighlighted(String i1, String i2) {
        super();
        BufferedImage image = null;
        BufferedImage highlightedImage = null;
        try {
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream(i1);
            if (stream == null) {
                throw new RuntimeException("Button image not found: " + i1);
            }
            image = ImageIO.read(stream);
            stream = this.getClass().getClassLoader().getResourceAsStream(i2);
            if (stream == null) {
                throw new RuntimeException("Button image not found: " + i2);
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
