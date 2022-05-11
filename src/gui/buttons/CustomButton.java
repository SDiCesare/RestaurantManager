package gui.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//TODO Documentation and Change the name
public class CustomButton extends JLabel implements MouseListener {

    private final Color baseForeground;
    private final Color brightForeground;

    private ActionListener action;

    public CustomButton(Color baseForeground, Color brightForeground) {
        super();
        this.baseForeground = baseForeground;
        this.brightForeground = brightForeground;
        this.addMouseListener(this);
        this.setForeground(baseForeground);
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
