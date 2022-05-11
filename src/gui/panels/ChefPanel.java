package gui.panels;

import gui.MainFrame;
import gui.buttons.CustomButton;
import gui.dialogs.MenuScopeDialog;
import restaurant.Menu;
import restaurant.MenuScope;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ChefPanel extends AbstractPanel {

    private Menu menu;
    private MenuPanel menuPanel;

    /**
     * The Constructor of the class ChefPanel.
     * Creates an Editable List of {@link MenuScope MenuScopes} for the Chef
     * The default size of this Panel are 594x771
     *
     * @param menu The Menu to display and modify
     */
    public ChefPanel(Menu menu) {
        super();
        this.setLayout(null);
        CustomButton addScopeButton = new CustomButton(Color.BLACK, Color.WHITE);
        addScopeButton.setText("+");
        addScopeButton.setFont(addScopeButton.getFont().deriveFont(50.0f));
        addScopeButton.setBounds(450, 65, 50, 30);
        addScopeButton.setAction((e) -> {
            Component root = SwingUtilities.getRoot(((Component) e.getSource()));
            MenuScopeDialog menuScopeDialog = new MenuScopeDialog(this.menu.getLastID(), ((JFrame) root));
            this.addMenuScope(menuScopeDialog.showDialog());
        });
        CustomButton backToMenuButton = new CustomButton(Color.BLACK, Color.WHITE);
        backToMenuButton.setText("←");
        backToMenuButton.setFont(addScopeButton.getFont());
        backToMenuButton.setBounds(80, 65, 50, 30);
        backToMenuButton.setAction((e) -> {
            Component root = SwingUtilities.getRoot(((Component) e.getSource()));
            if (root instanceof MainFrame) {
                ((MainFrame) root).setContent(new MainPanel());
            }
        });
        this.menu = menu;
        this.menuPanel = new MenuPanel();
        JScrollPane pane = new JScrollPane(menuPanel);
        pane.setOpaque(false);
        pane.getViewport().setOpaque(false);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pane.setBounds(100, 150, 390, 570);
        pane.setBorder(BorderFactory.createEmptyBorder());
        this.add(pane);
        this.add(backToMenuButton);
        this.add(addScopeButton);
    }

    /**
     * Adds a {@link MenuScope MenuScope} to the Menu associated with this object
     *
     * @param scope The MenuScope to add
     */
    private void addMenuScope(MenuScope scope) {
        if (scope == null) {
            return;
        }
        this.menu.add(scope);
        this.menuPanel.addMenuScope(scope);
    }

    @Override
    String getBackgroundAssets() {
        return "assets/menuBackground.png";
    }

    /**
     * This class displays a Menu in a {@link JPanel JPanel} as a list of {@link MenuScopePanel MenuScopePanel}
     * This class should be putted in a {@link JScrollPane JScrollPane}
     */
    private class MenuPanel extends JPanel {

        /**
         * The Constructor of the class MenuPanel
         * Creates a List of {@link MenuScopePanel MenuScopePanel} ordered by a {@link BoxLayout BoxLayout} in a vertical way.
         */
        private MenuPanel() {
            super();
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.setBackground(new Color(0, 0, 0, 0));
            this.setOpaque(false);
            this.setPreferredSize(new Dimension(350, 50 * menu.entries()));
            this.setMinimumSize(this.getPreferredSize());
            for (MenuScope scope : menu.getMenu()) {
                MenuScopePanel panel = new MenuScopePanel(scope);
                panel.setBackground(this.getBackground());
                panel.setOpaque(true);
                this.add(panel);
            }
        }

        /**
         * Updates a {@link MenuScope MenuScope} inside this MenuPanel
         *
         * @param scope The MenuScope to update
         */
        private void updateMenuScope(MenuScope scope) {
            int index = 0;
            for (Component c : this.getComponents()) {
                if (c instanceof MenuScopePanel && ((MenuScopePanel) c).scope.equals(scope)) {
                    this.remove(c);
                    MenuScopePanel panel = new MenuScopePanel(scope);
                    panel.setBackground(this.getBackground());
                    panel.setOpaque(true);
                    this.add(panel, index);
                    break;
                }
                index++;
            }
            this.revalidate();
            this.repaint();
        }

        /**
         * Adds a {@link MenuScope MenuScope} to this MenuPanel
         *
         * @param scope The MenuScope to add
         */
        private void addMenuScope(MenuScope scope) {
            this.setPreferredSize(new Dimension(350, 50 * menu.entries()));
            this.setMinimumSize(this.getPreferredSize());
            MenuScopePanel panel = new MenuScopePanel(scope);
            panel.setBackground(this.getBackground());
            panel.setOpaque(true);
            this.add(panel);
            this.revalidate();
            this.repaint();
        }

        /**
         * Removes a {@link MenuScope MenuScope} from this MenuPanel
         *
         * @param scope The MenuScope to remove
         */
        private void removeMenuScope(MenuScope scope) {
            for (Component c : this.getComponents()) {
                if (c instanceof MenuScopePanel && ((MenuScopePanel) c).scope.equals(scope)) {
                    this.remove(c);
                    break;
                }
            }
            this.revalidate();
            this.repaint();
        }

    }

    /**
     * This class displays a MenuScope in a {@link JPanel JPanel} divided in 2 {@link JLabel JLabel}
     * The firs label has the name of the scope displayed in.
     * The second label has the cost of the scope displayed in, and formatted as .2f$
     * This way of display a MenuScope is for the {@link ChefPanel ChefPanel} class.
     */
    private class MenuScopePanel extends JPanel {

        private final MenuScope scope;

        /**
         * The Constructor of MenuScopePanel
         * This Panel represents a MenuScope with 2 {@link JLabel JLabel} with the
         * name and the cost of the MenuScope
         *
         * @param scope The MenuScope to display in this panel
         */
        private MenuScopePanel(MenuScope scope) {
            super();
            this.scope = scope;
            this.setMaximumSize(new Dimension(350, 50));
            this.setMinimumSize(this.getMaximumSize());
            this.setPreferredSize(this.getMaximumSize());
            this.setSize(this.getMaximumSize());
            this.setLayout(null);
            JLabel nameLabel = new JLabel(getFormattedName(scope.getName()));
            nameLabel.setFont(nameLabel.getFont().deriveFont(15.0f));
            nameLabel.setForeground(Color.BLACK);
            nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
            nameLabel.setVerticalAlignment(SwingConstants.CENTER);
            nameLabel.setBounds(0, 0, 250, 50);
            this.add(nameLabel);
            float cost = scope.getCost() / 100.f;
            JLabel costLabel = new JLabel(String.format("%.2f$", cost));
            costLabel.setFont(nameLabel.getFont());
            costLabel.setForeground(Color.BLACK);
            costLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            costLabel.setVerticalAlignment(SwingConstants.CENTER);
            costLabel.setBounds(nameLabel.getWidth(), 0, 100, 50);
            this.add(costLabel);
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        JPopupMenu menu = new JPopupMenu();
                        JMenuItem editScope = new JMenuItem("Edit");
                        editScope.addActionListener((a) -> {
                            Component root = SwingUtilities.getRoot(((Component) e.getSource()));
                            MenuScopeDialog menuScopeDialog = new MenuScopeDialog(scope, ((JFrame) root));
                            MenuScope scp = menuScopeDialog.showDialog();
                            ChefPanel.this.menuPanel.updateMenuScope(scp);
                        });
                        JMenuItem removeScope = new JMenuItem("Remove");
                        removeScope.addActionListener((a) -> {
                            ChefPanel.this.menu.remove(scope);
                            ChefPanel.this.menuPanel.removeMenuScope(scope);
                        });
                        menu.add(editScope);
                        menu.add(removeScope);
                        menu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            });
        }


        /**
         * Format a MenuScope name to fit into a 16 character width space
         *
         * @param name The name of the MenuScope
         * @return A formatted name
         */
        private String getFormattedName(String name) {
            final int limit = 25;
            if (name.length() <= limit) {
                return name;
            }
            String sign = "";
            String out = "<html>";
            while (name.contains(" ")) {
                int i = name.indexOf(" ");
                String sub = name.substring(0, i);
                if (sign.length() + sub.length() <= limit) {
                    sign += sub + " ";
                    out += sub + " ";
                } else {
                    sign = sub + " ";
                    out += "<br>" + sub + " ";
                }
                name = name.substring(i + 1);
            }
            if (out.contains("<br>")) {
                if (out.substring(out.lastIndexOf("<br>")).length() + name.length() > limit) {
                    out += "<br>" + name;
                } else {
                    out += " " + name;
                }
            } else {
                if (sign.length() + name.length() > limit) {
                    out += "<br>" + name;
                } else {
                    out += " " + name;
                }
            }
            out = out.replace("  ", " ").replace(" <br>", "<br>");
            return out + "</html>";
        }

        @Override
        public void setBackground(Color bg) {
            super.setBackground(bg);
            for (Component c : this.getComponents()) {
                c.setBackground(bg);
            }
        }

        @Override
        public void setOpaque(boolean isOpaque) {
            super.setOpaque(isOpaque);
            for (Component c : this.getComponents()) {
                if (c instanceof JLabel) {
                    ((JLabel) c).setOpaque(isOpaque);
                }
            }
        }
    }
}
