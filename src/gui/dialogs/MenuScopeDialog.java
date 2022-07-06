package gui.dialogs;

import restaurant.MenuScope;

import javax.swing.*;

/**
 * A Dialog for modifying and creating MenuScope used in {@link gui.panels.ChefPanel}
 * */
public class MenuScopeDialog extends JDialog {

    private MenuScope scope;
    private boolean shouldSave;

    /**
     * A Constructor of the class MenuScopeDialog,
     * Create a new {@link MenuScope MenuScope} that can be edited.
     *
     * @param id    The id of the new MenuScope
     * @param frame The parent to which is locked this JDialog
     */
    public MenuScopeDialog(int id, JFrame frame) {
        this(new MenuScope(id, "", 0), frame);
    }

    /**
     * The Constructor of the class MenuScopeDialog.
     * Created a JDialog in which a {@link MenuScope MenuScope} can be edited.
     *
     * @param scope The MenuScope to edit
     * @param frame The parent to which is locked this JDialog
     */
    public MenuScopeDialog(MenuScope scope, JFrame frame) {
        super(frame, "Add Menu Scope");
        this.scope = scope;
        this.shouldSave = false;
        this.setSize(350, 230);
        this.setModal(true);
        JLabel nameLabel = new JLabel("Name");
        JLabel costLabel = new JLabel("Cost");
        JTextField nameField = new JTextField();
        JTextField costField = new JTextField();
        if (scope != null) {
            nameField.setText(scope.getName());
            costField.setText(String.valueOf(scope.getCost() / 100.f));
        }
        this.setLayout(null);
        nameField.setBounds(10, 40, 300, 30);
        nameLabel.setBounds(nameField.getX(), nameField.getY() - 25, 100, 25);
        costField.setBounds(nameField.getX(), 40 + nameField.getHeight() + nameField.getY(), nameField.getWidth(), nameField.getHeight());
        costLabel.setBounds(costField.getX(), costField.getY() - nameLabel.getHeight(), nameLabel.getWidth(), nameLabel.getHeight());
        this.add(nameLabel);
        this.add(nameField);
        this.add(costLabel);
        this.add(costField);
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener((e) -> {
            this.shouldSave = true;
            this.scope.setName(nameField.getText());
            this.scope.setCost((int) (Float.parseFloat(costField.getText()) * 100));
            this.setVisible(false);
            this.dispose();
        });
        saveButton.setBounds(nameField.getX(), costField.getY() + costField.getHeight() + 10, 100, 30);
        JButton deleteButton = new JButton("Cancel");
        deleteButton.addActionListener((e) -> {
            this.setVisible(false);
            this.dispose();
        });
        deleteButton.setBounds(saveButton.getX() + saveButton.getWidth() + 10, saveButton.getY(), saveButton.getWidth(), saveButton.getHeight());
        this.add(saveButton);
        this.add(deleteButton);
        this.setLocationRelativeTo(frame);
        this.setResizable(false);
    }

    /**
     * Shows this {@link MenuScopeDialog MenuScopeDialog} and wait for a return value.
     *
     * @return The {@link MenuScope MenuScope} inserted by the user. Null if the process is cancelled.
     */
    public MenuScope showDialog() {
        this.setVisible(true);
        return this.shouldSave ? this.scope : null;
    }

}
