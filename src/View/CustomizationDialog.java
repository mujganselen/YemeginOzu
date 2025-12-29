package View;

import Controller.RestaurantController;
import Controller.OrderController;
import Model.*;
import Model.MenuItem;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CustomizationDialog extends JDialog {
    private MenuItem menuItem;
    private OrderItem orderItem;
    private RestaurantController restaurantController;
    private OrderController orderController;

    private JPanel ingredientsPanel;
    private JLabel totalLabel;
    private JSpinner quantitySpinner;

    public CustomizationDialog(JFrame parent, MenuItem menuItem,
                               RestaurantController restaurantController,
                               OrderController orderController) {
        super(parent, "Customize: " + menuItem.getName(), true);
        this.menuItem = menuItem;
        this.restaurantController = restaurantController;
        this.orderController = orderController;
        this.orderItem = new OrderItem(menuItem);

        initUI();
        setSize(500, 600);
        setLocationRelativeTo(parent);
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // Top panel - item info
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        // Center - ingredients
        ingredientsPanel = new JPanel();
        ingredientsPanel.setLayout(new BoxLayout(ingredientsPanel, BoxLayout.Y_AXIS));
        ingredientsPanel.setBackground(Color.WHITE);
        ingredientsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        loadIngredients();

        JScrollPane scrollPane = new JScrollPane(ingredientsPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Ingredients"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 248, 220));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel nameLabel = new JLabel(menuItem.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descLabel = new JLabel(menuItem.getDescription());
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setForeground(Color.GRAY);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel priceLabel = new JLabel(String.format("Base Price: %.2f TL", menuItem.getBasePrice()));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        priceLabel.setForeground(new Color(34, 139, 34));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(descLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(priceLabel);

        return panel;
    }

    private void loadIngredients() {
        ingredientsPanel.removeAll();

        // Default ingredients
        JLabel defaultLabel = new JLabel("Default Ingredients:");
        defaultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        defaultLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ingredientsPanel.add(defaultLabel);
        ingredientsPanel.add(Box.createVerticalStrut(10));

        for (Ingredient ing : menuItem.getDefaultIngredients()) {
            JPanel ingPanel = createIngredientPanel(ing, true);
            ingredientsPanel.add(ingPanel);
            ingredientsPanel.add(Box.createVerticalStrut(5));
        }

        ingredientsPanel.add(Box.createVerticalStrut(15));

        // Extra ingredients
        JLabel extraLabel = new JLabel("Extra Ingredients:");
        extraLabel.setFont(new Font("Arial", Font.BOLD, 14));
        extraLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ingredientsPanel.add(extraLabel);
        ingredientsPanel.add(Box.createVerticalStrut(10));

        List<Ingredient> allIngredients = restaurantController.getAllIngredients();
        for (Ingredient ing : allIngredients) {
            if (!menuItem.getDefaultIngredients().contains(ing)) {
                JPanel ingPanel = createIngredientPanel(ing, false);
                ingredientsPanel.add(ingPanel);
                ingredientsPanel.add(Box.createVerticalStrut(5));
            }
        }
    }

    private JPanel createIngredientPanel(Ingredient ingredient, boolean isDefault) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(450, 40));
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel nameLabel = new JLabel(ingredient.getName());
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        String priceText = ingredient.getExtraPrice() > 0 ?
                String.format(" (+%.2f TL)", ingredient.getExtraPrice()) : "";
        JLabel priceLabel = new JLabel(priceText);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 12));
        priceLabel.setForeground(new Color(34, 139, 34));

        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected(isDefault);
        checkBox.setBackground(Color.WHITE);

        if (isDefault) {
            checkBox.addActionListener(e -> {
                if (!checkBox.isSelected()) {
                    orderItem.removeIngredient(ingredient);
                } else {
                    orderItem.getRemovedIngredients().remove(ingredient);
                }
                updateTotal();
            });
        } else {
            checkBox.addActionListener(e -> {
                if (checkBox.isSelected()) {
                    orderItem.addIngredient(ingredient);
                } else {
                    orderItem.getAddedIngredients().remove(ingredient);
                }
                updateTotal();
            });
        }

        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);

        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(checkBox, BorderLayout.EAST);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Quantity selector
        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        quantityPanel.setBackground(Color.WHITE);

        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setFont(new Font("Arial", Font.BOLD, 14));

        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        quantitySpinner.setFont(new Font("Arial", Font.PLAIN, 14));
        ((JSpinner.DefaultEditor) quantitySpinner.getEditor()).getTextField().setEditable(false);
        quantitySpinner.addChangeListener(e -> updateTotal());

        quantityPanel.add(qtyLabel);
        quantityPanel.add(quantitySpinner);

        // Total and buttons
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(Color.WHITE);

        totalLabel = new JLabel(String.format("Total: %.2f TL", orderItem.getTotalPrice()));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setForeground(new Color(34, 139, 34));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.addActionListener(e -> dispose());

        JButton addButton = new JButton("Add to Cart");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(new Color(255, 140, 0));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> addToCart());

        rightPanel.add(totalLabel);
        rightPanel.add(Box.createHorizontalStrut(20));
        rightPanel.add(cancelButton);
        rightPanel.add(addButton);

        panel.add(quantityPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    private void updateTotal() {
        int quantity = (Integer) quantitySpinner.getValue();
        orderItem.setQuantity(quantity);
        totalLabel.setText(String.format("Total: %.2f TL", orderItem.getTotalPrice()));
    }

    private void addToCart() {
        orderController.addToCart(orderItem);
        JOptionPane.showMessageDialog(this,
                "Item added to cart",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}