package gui.panels;

import gui.MainFrame;
import gui.buttons.ImageButtonHighlighted;
import gui.buttons.TextButtonHighlighted;
import restaurant.*;
import restaurant.Menu;

import javax.swing.*;
import java.awt.*;

public class CookPanel extends AbstractPanel {

    private final Menu menu;
    private Order currentOrder;

    private JScrollPane orderPanelScrollPane;
    private OrderPanel orderPanel;

    public CookPanel(Menu menu) {
        super();
        this.setLayout(null);
        ImageButtonHighlighted backToMenuButton = new ImageButtonHighlighted("assets/arrow.png", "assets/lightArrow.png");
        backToMenuButton.setFont(backToMenuButton.getFont().deriveFont(50.0f));
        backToMenuButton.setBounds(80, 65, 50, 30);
        backToMenuButton.setAction((e) -> {
            OrderUtil.saveOrder(this.currentOrder);
            Component root = SwingUtilities.getRoot(((Component) e.getSource()));
            if (root instanceof MainFrame) {
                ((MainFrame) root).setContent(new MainPanel());
            }
        });
        this.menu = menu;
        //this.currentOrder = OrderUtil.getOrderFromTable(1, this.menu);
        //TODO Make accessible only the panel with orders
        Order[] orders = OrderUtil.getActiveOrders(menu);
        if (orders.length > 0) {
            this.currentOrder = OrderUtil.getOrderFromTable(orders[0].getTableNumber(), this.menu);
        } else {
            this.currentOrder = OrderUtil.getOrderFromTable(1, this.menu);
        }
        this.orderPanel = new OrderPanel();
        String[] tables = new String[orders.length];
        for (int i = 0; i < tables.length; i++) {
            tables[i] = "Table " + orders[i].getTableNumber();
        }
        JComboBox<String> tableList = new JComboBox<>(tables);
        tableList.setBounds(150, 65, 300, 35);
        tableList.setFont(tableList.getFont().deriveFont(tableList.getHeight() - 10.0f));
        orderPanelScrollPane = new JScrollPane(orderPanel);
        tableList.addActionListener((e) -> {
            JComboBox<String> box = (JComboBox) e.getSource();
            OrderUtil.saveOrder(this.currentOrder);
            int selectedIndex = box.getSelectedIndex();
            this.currentOrder = OrderUtil.getOrderFromTable(orders[selectedIndex].getTableNumber(), this.menu);
            refreshOrderPanel();
        });
        orderPanelScrollPane.setOpaque(false);
        orderPanelScrollPane.getViewport().setOpaque(false);
        orderPanelScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        orderPanelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        orderPanelScrollPane.setBounds(100, 150, 390, 500);
        //pane.setBorder(BorderFactory.createEmptyBorder());
        this.add(backToMenuButton);
        this.add(tableList);
        this.add(orderPanelScrollPane);
    }

    private void refreshOrderPanel() {
        this.orderPanel = new OrderPanel();
        orderPanelScrollPane.setViewportView(this.orderPanel);
        this.repaint();
        this.revalidate();
    }

    @Override
    String getBackgroundAssets() {
        return "assets/orderBackground.png";
    }

    private class OrderPanel extends JPanel {

        private OrderPanel() {
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            Color transparent = new Color(0, 0, 0, 0);
            this.setBackground(new Color(230, 230, 230));
            //this.setOpaque(false);
            this.setPreferredSize(new Dimension(350, 50 * menu.entries()));
            this.setMinimumSize(this.getPreferredSize());
            for (MenuScope scope : menu.getMenu()) {
                if (currentOrder.contains(scope)) {
                    DishPanel panel = new DishPanel(currentOrder.get(scope));
                    panel.setBackground(transparent);
                    panel.setOpaque(true);
                    this.add(panel);
                }
            }
        }

    }

    private class DishPanel extends JPanel {

        private DishPanel(MenuScope scope) {
            this(new Dish(scope));
        }

        private DishPanel(Dish dish) {
            super();
            this.setMaximumSize(new Dimension(350, 50));
            this.setMinimumSize(this.getMaximumSize());
            this.setPreferredSize(this.getMaximumSize());
            this.setSize(this.getMaximumSize());
            this.setLayout(null);
            JLabel nameLabel = new JLabel(getFormattedName(dish.getScope().getName()));
            nameLabel.setFont(nameLabel.getFont().deriveFont(15.0f));
            nameLabel.setBounds(0, 0, 200, 50);
            JLabel quantityLabel = new JLabel(String.valueOf(dish.getQuantity()));
            quantityLabel.setFont(nameLabel.getFont());
            quantityLabel.setBounds(nameLabel.getWidth() + nameLabel.getX(), nameLabel.getY(), 50, 50);
            TextButtonHighlighted sendDishButton = new TextButtonHighlighted(Color.BLACK, Color.WHITE);
            sendDishButton.setAction((e) -> {
                currentOrder.cook(dish);
                refreshOrderPanel();
            });
            sendDishButton.setText("Send Dish ->");
            sendDishButton.setFont(nameLabel.getFont());
            sendDishButton.setBounds(quantityLabel.getWidth() + quantityLabel.getX() + 10, quantityLabel.getY(), 150, 50);
            this.add(nameLabel);
            this.add(quantityLabel);
            this.add(sendDishButton);
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
