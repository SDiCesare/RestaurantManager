package gui.panels;

import gui.MainFrame;
import gui.buttons.ImageButtonHighlighted;
import gui.buttons.TextButtonHighlighted;
import restaurant.*;
import restaurant.Menu;

import javax.swing.*;
import java.awt.*;

/**
 * A Panel with the Waiter functionalities.
 * Can Create and Send order to te kitchen for 10 tables
 * */
public class WaiterPanel extends AbstractPanel {

    private Menu menu;
    private Order currentOrder;
    private OrderPanel orderPanel;

    /**
     * Creates a WaiterPanel with the specified Menu
     *
     * @param menu: The Restaurant Menu
     * */
    public WaiterPanel(Menu menu) {
        super();
        this.setLayout(null);
        ImageButtonHighlighted backToMenuButton = new ImageButtonHighlighted("assets/arrow.png", "assets/lightArrow.png");
        backToMenuButton.setFont(backToMenuButton.getFont().deriveFont(50.0f));
        backToMenuButton.setBounds(80, 65, 80, 30);
        backToMenuButton.setAction((e) -> {
            Component root = SwingUtilities.getRoot(((Component) e.getSource()));
            if (root instanceof MainFrame) {
                ((MainFrame) root).setContent(new MainPanel());
            }
        });
        this.menu = menu;
        this.currentOrder = new Order();//OrderUtil.getOrderFromTable(1, this.menu);
        this.currentOrder.setTableNumber(1);
        this.orderPanel = new OrderPanel(true);
        String[] tables = new String[10];
        for (int i = 0; i < tables.length; i++) {
            tables[i] = "Table " + (i + 1);
        }
        JComboBox<String> tableList = new JComboBox<>(tables);
        tableList.setBounds(150, 65, 300, 35);
        tableList.setFont(tableList.getFont().deriveFont(tableList.getHeight() - 10.0f));
        JScrollPane pane = new JScrollPane(orderPanel);
        tableList.addActionListener((e) -> {
            JComboBox<String> box = (JComboBox) e.getSource();
            this.currentOrder = new Order();//OrderUtil.getOrderFromTable(box.getSelectedIndex() + 1, this.menu);
            this.currentOrder.setTableNumber(box.getSelectedIndex() + 1);
            refreshOrderPanel(pane, true);
        });
        JCheckBox showSelected = new JCheckBox("Show Selected");
        showSelected.setSelected(false);
        showSelected.setToolTipText("Show only The Chosen Dish");
        showSelected.addActionListener(e -> {
            Object source = e.getSource();
            if (source instanceof JCheckBox) {
                boolean selected = ((JCheckBox) source).isSelected();
                refreshOrderPanel(pane, !selected);
            }
        });
        showSelected.setOpaque(true);
        showSelected.setBackground(new Color(230, 230, 230));
        showSelected.setFont(showSelected.getFont().deriveFont(10.0f));
        showSelected.setBounds(tableList.getX(), tableList.getY() + tableList.getHeight() + 10, 100, 20);
        pane.setOpaque(false);
        pane.getViewport().setOpaque(false);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pane.setBounds(100, 150, 390, 500);
        //pane.setBorder(BorderFactory.createEmptyBorder());
        TextButtonHighlighted sendOrderButton = new TextButtonHighlighted(Color.BLACK, Color.WHITE);
        sendOrderButton.setText("Send Order To Kitchen");
        sendOrderButton.setFont(sendOrderButton.getFont().deriveFont(30.0f));
        sendOrderButton.setBounds(120, 670, 330, 30);
        sendOrderButton.setAction((e) -> {
            int result = JOptionPane.showConfirmDialog(this, "Send the order to the kitchen?", "Sending Order", JOptionPane.YES_NO_OPTION);
            if (result == 0) {
                OrderUtil.sendOrder(this.currentOrder, this.menu);
                int tableNumber = this.currentOrder.getTableNumber();
                this.currentOrder = new Order();
                this.currentOrder.setTableNumber(tableNumber);
                this.orderPanel.resetOrder();
                refreshOrderPanel(pane, true);
                //OrderUtil.saveOrder(this.currentOrder);
            }
        });
        sendOrderButton.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.DARK_GRAY));
        this.add(backToMenuButton);
        this.add(tableList);
        this.add(showSelected);
        this.add(pane);
        this.add(sendOrderButton);
    }

    private void refreshOrderPanel(JScrollPane pane, boolean showAll) {
        this.orderPanel = new OrderPanel(showAll);
        pane.setViewportView(this.orderPanel);
        this.repaint();
        this.revalidate();
    }

    @Override
    String getBackgroundAssets() {
        return "assets/orderBackground.png";
    }

    /**
     * A {@link JPanel} that displays the order taken from the Waiter
     */
    private class OrderPanel extends JPanel {

        /**
         * Creates an OrderPanel with the possibility to shows only the dish with a quantity > 0
         *
         * @param showAll: The flag that decides if the Panel should show only the dish with a quantity > 0
         *                 True: all the Dish are displayed
         *                 False: only the Dish with quantity > 0 are Displayed
         */
        private OrderPanel(boolean showAll) {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            Color transparent = new Color(0, 0, 0, 0);
            this.setBackground(new Color(230, 230, 230));
            //this.setOpaque(false);
            this.setPreferredSize(new Dimension(350, 50 * menu.entries()));
            this.setMinimumSize(this.getPreferredSize());
            for (MenuScope scope : menu.getMenu()) {
                DishPanel panel;
                if (currentOrder.contains(scope)) {
                    panel = new DishPanel(currentOrder.get(scope));
                } else if (showAll) {
                    panel = new DishPanel(scope);
                } else {
                    continue;
                }
                panel.setBackground(transparent);
                panel.setOpaque(true);
                this.add(panel);
            }
        }

        private void resetOrder() {
            Component[] components = this.getComponents();
            for (Component c : components) {
                if (c instanceof DishPanel) {
                    DishPanel panel = (DishPanel) c;
                    panel.dish.setQuantity(0);
                }
            }
        }
    }

    /**
     * A JPanel that displays a Dish for the Waiter
     */
    private class DishPanel extends JPanel {

        private Dish dish;

        /**
         * Create a new DishPanel with the specified MenuScope and a quantity of 0
         *
         * @param scope: The {@link MenuScope} of the Panel
         */
        private DishPanel(MenuScope scope) {
            this(new Dish(scope));
        }

        /**
         * Creates a new DishPanel with the specified dish
         *
         * @param dish: The dish represented in this Panel
         */
        private DishPanel(Dish dish) {
            super();
            this.dish = dish;
            this.setMaximumSize(new Dimension(350, 50));
            this.setMinimumSize(this.getMaximumSize());
            this.setPreferredSize(this.getMaximumSize());
            this.setSize(this.getMaximumSize());
            this.setLayout(null);
            JLabel nameLabel = new JLabel(getFormattedName(this.dish.getScope().getName()));
            nameLabel.setFont(nameLabel.getFont().deriveFont(15.0f));
            nameLabel.setBounds(0, 0, 200, 50);
            JLabel quantityLabel = new JLabel(String.valueOf(this.dish.getQuantity()));
            quantityLabel.setFont(nameLabel.getFont());
            quantityLabel.setBounds(nameLabel.getWidth() + nameLabel.getX(), nameLabel.getY(), 50, 50);
            TextButtonHighlighted incrementQuantity = new TextButtonHighlighted(Color.BLACK, Color.WHITE);
            incrementQuantity.setAction((e) -> {
                this.dish.incrementQuantity(1);
                if (!currentOrder.contains(this.dish)) {
                    currentOrder.add(this.dish);
                }
                quantityLabel.setText(String.valueOf(this.dish.getQuantity()));
                WaiterPanel.this.repaint();
            });
            incrementQuantity.setText("+");
            incrementQuantity.setFont(incrementQuantity.getFont().deriveFont(30.0f));
            incrementQuantity.setBounds(quantityLabel.getWidth() + quantityLabel.getX() + 20, quantityLabel.getY(), 30, 50);
            TextButtonHighlighted decrementQuantity = new TextButtonHighlighted(Color.BLACK, Color.WHITE);
            decrementQuantity.setAction((e) -> {
                if (this.dish.getQuantity() == 0) {
                    return;
                }
                this.dish.decrementQuantity(1);
                if (this.dish.getQuantity() == 0) {
                    currentOrder.remove(this.dish);
                }
                quantityLabel.setText(String.valueOf(this.dish.getQuantity()));
                WaiterPanel.this.repaint();
            });
            decrementQuantity.setText("-");
            decrementQuantity.setFont(incrementQuantity.getFont());
            decrementQuantity.setBounds(incrementQuantity.getX() + incrementQuantity.getWidth(), incrementQuantity.getY() - 3, incrementQuantity.getWidth(), incrementQuantity.getHeight());
            this.add(nameLabel);
            this.add(quantityLabel);
            this.add(incrementQuantity);
            this.add(decrementQuantity);
            this.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY));
        }

        /**
         * Format a MenuScope name to fit into a 25 character width space
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

    }
}
