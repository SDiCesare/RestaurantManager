package gui.panels;

import gui.MainFrame;
import gui.buttons.ImageButtonHighlighted;
import gui.buttons.TextButtonHighlighted;
import restaurant.*;
import restaurant.Menu;

import javax.swing.*;
import java.awt.*;

/**
 * A Panel for the Cashier work.
 * It can display the  Receipt of the table, and can save them into a file.
 */
public class CashierPanel extends AbstractPanel {

    private Menu menu;
    private Order currentOrder;
    private ReceiptPanel receiptPanel;
    private JScrollPane receiptPanelScrollPane;
    private JLabel receiptTotalLabel;

    /**
     * Creates a new CashierPanel based on the given menu
     *
     * @param menu: The menu of the restaurant
     */
    public CashierPanel(Menu menu) {
        super();
        this.setLayout(null);
        this.menu = menu;
        this.currentOrder = OrderUtil.getOrderFromTable(1, menu);
        ImageButtonHighlighted backToMenuButton = new ImageButtonHighlighted("assets/arrow.png", "assets/grayArrow.png");
        backToMenuButton.setBounds(80, 65, 50, 30);
        backToMenuButton.setAction((e) -> {
            Component root = SwingUtilities.getRoot(((Component) e.getSource()));
            if (root instanceof MainFrame) {
                ((MainFrame) root).setContent(new MainPanel());
            }
        });
        this.receiptPanel = new ReceiptPanel();
        Order[] orders = OrderUtil.getCompletedOrders(menu);
        if (orders.length > 0) {
            this.currentOrder = OrderUtil.getOrderFromTable(orders[0].getTableNumber(), this.menu);
        } else {
            this.currentOrder = OrderUtil.getOrderFromTable(1, this.menu);
        }
        String[] tables = new String[orders.length];
        for (int i = 0; i < tables.length; i++) {
            tables[i] = "Table " + orders[i].getTableNumber();
        }
        JComboBox<String> tableList = new JComboBox<>(tables);
        tableList.setBounds(150, 65, 300, 35);
        tableList.setFont(tableList.getFont().deriveFont(tableList.getHeight() - 10.0f));
        tableList.addActionListener((e) -> {
            JComboBox<String> box = (JComboBox) e.getSource();
            int selectedIndex = box.getSelectedIndex();
            this.currentOrder = OrderUtil.getOrderFromTable(orders[selectedIndex].getTableNumber(), this.menu);
            this.receiptTotalLabel.setText(String.format("Receipt Total: %.2f$", this.currentOrder.getTotal() / 100.f));
            refreshReceiptPanel();
        });
        TextButtonHighlighted printReceiptButton = new TextButtonHighlighted(Color.BLACK, Color.LIGHT_GRAY);
        printReceiptButton.setAction((e) -> {
            if (OrderUtil.availableOrder(this.currentOrder)) {
                OrderUtil.stampAndDeleteOrder(this.currentOrder);
                int tableNumber = this.currentOrder.getTableNumber();
                this.currentOrder = new Order();
                this.currentOrder.setTableNumber(tableNumber);
                this.receiptTotalLabel.setText("Receipt Total: 0.00$");
                refreshReceiptPanel();
            }
        });
        printReceiptButton.setBounds(200, 670, 200, 30);
        printReceiptButton.setText("Print Receipt");
        printReceiptButton.setFont(printReceiptButton.getFont().deriveFont(30.0f));
        this.receiptTotalLabel = new JLabel(String.format("Receipt Total: %.2f", this.currentOrder.getTotal() / 100.f));
        this.receiptTotalLabel.setFont(receiptTotalLabel.getFont().deriveFont(20.0f));
        this.receiptTotalLabel.setBounds(90, 615, 200, 30);
        this.receiptPanelScrollPane = new JScrollPane(receiptPanel);
        this.receiptPanelScrollPane.setOpaque(false);
        this.receiptPanelScrollPane.getViewport().setOpaque(false);
        this.receiptPanelScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.receiptPanelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.receiptPanelScrollPane.setBounds(100, 150, 390, 450);
        this.receiptPanelScrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(this.receiptPanelScrollPane);
        this.add(this.receiptTotalLabel);
        this.add(tableList);
        this.add(printReceiptButton);
        this.add(backToMenuButton);
        refreshReceiptPanel();
    }

    private void refreshReceiptPanel() {
        this.receiptPanel = new ReceiptPanel();
        this.receiptPanelScrollPane.setViewportView(this.receiptPanel);
        this.repaint();
        this.revalidate();
    }

    @Override
    public String getBackgroundAssets() {
        return "assets/cashierBackground.png";
    }

    private class ReceiptPanel extends JPanel {

        private ReceiptPanel() {
            super();
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.setBackground(new Color(0, 0, 0, 0));
            this.setOpaque(false);
            this.setPreferredSize(new Dimension(350, 50 * menu.entries()));
            this.setMinimumSize(this.getPreferredSize());
            for (Dish dish : currentOrder.getCookedDishes()) {
                DishPanel panel = new DishPanel(dish);
                panel.setBackground(this.getBackground());
                panel.setOpaque(true);
                this.add(panel);
            }
        }

    }

    /**
     * This class displays a MenuScope in a {@link JPanel JPanel} divided in 2 {@link JLabel JLabel}
     * The firs label has the name of the scope displayed in.
     * The second label has the cost of the scope displayed in, and formatted as .2f$
     * This way of display a MenuScope is for the {@link ChefPanel ChefPanel} class.
     */
    private static class DishPanel extends JPanel {

        private final Dish dish;

        /**
         * The Constructor of MenuScopePanel
         * This Panel represents a MenuScope with 2 {@link JLabel JLabel} with the
         * name and the cost of the MenuScope
         *
         * @param dish The MenuScope to display in this panel
         */
        private DishPanel(Dish dish) {
            super();
            this.dish = dish;
            this.setMaximumSize(new Dimension(350, 50));
            this.setMinimumSize(this.getMaximumSize());
            this.setPreferredSize(this.getMaximumSize());
            this.setSize(this.getMaximumSize());
            this.setLayout(null);
            JLabel nameLabel = new JLabel(getFormattedName(dish.getScope().getName()));
            nameLabel.setFont(nameLabel.getFont().deriveFont(15.0f));
            nameLabel.setForeground(Color.BLACK);
            nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
            nameLabel.setVerticalAlignment(SwingConstants.CENTER);
            nameLabel.setBounds(0, 0, 200, 50);
            this.add(nameLabel);
            float cost = (dish.getScope().getCost() * dish.getQuantity()) / 100.f;
            JLabel costLabel = new JLabel(String.format("%.2f$ X%d       %.2f$", dish.getScope().getCost() / 100.f, dish.getQuantity(), cost));
            costLabel.setFont(nameLabel.getFont());
            costLabel.setForeground(Color.BLACK);
            costLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            costLabel.setVerticalAlignment(SwingConstants.CENTER);
            costLabel.setBounds(nameLabel.getWidth(), 0, 350 - nameLabel.getWidth(), 50);
            this.add(costLabel);
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
