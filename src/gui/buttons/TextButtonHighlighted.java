package gui.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A button that change his text color when passed on with the mouse.
 * It's a JLabel that has no more functionality for icon, and can change his text color whenever the users
 * passed on it with the mouse.
 */
public class TextButtonHighlighted extends JLabel implements MouseListener {

    private final Color baseForeground;
    private final Color brightForeground;

    private ActionListener action;

    /**
     * Create a TextButtonHighlighted with the two foreground color
     *
     * @param baseForeground:   The basic foreground color
     * @param brightForeground: The foreground used when mouse is over it
     */
    public TextButtonHighlighted(Color baseForeground, Color brightForeground) {
        super();
        this.baseForeground = baseForeground;
        this.brightForeground = brightForeground;
        this.addMouseListener(this);
        this.setForeground(baseForeground);
    }

    /**
     * Defines the Action that the Label is doing when clicked on
     *
     * @param action The action to define for this Label
     */
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
    public void setIcon(Icon icon) {

    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setForeground(this.brightForeground);
        Component root = SwingUtilities.getRoot(((Component) e.getSource()));
        root.repaint();
        root.revalidate();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setForeground(this.baseForeground);
        Component root = SwingUtilities.getRoot(((Component) e.getSource()));
        root.repaint();
        root.revalidate();
    }
}
