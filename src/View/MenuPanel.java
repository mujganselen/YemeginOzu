package View;

import Model.MenuItem;
import Model.OrderItem;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuPanel extends JPanel {
    private MainFrame mainFrame;
    private JPanel menuContainer;
    private int currentCategoryId;

    public MenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Header with back button
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Menu items grid
        menuContainer = new JPanel();
        menuContainer.setLayout(new GridLayout(0, 3, 15, 15));
        menuContainer.setBackground(Color.WHITE);
        menuContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(menuContainer);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 140, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton backButton = new JButton("◀ Kategoriler");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(255, 165, 0));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> mainFrame.backToCategories());

        JLabel titleLabel = new JLabel("🍽️ Menü");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);

        panel.add(backButton, BorderLayout.WEST);
        panel.add(titleLabel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton cartButton = new JButton("🛒 Sepete Git");
        cartButton.setFont(new Font("Arial", Font.BOLD, 18));
        cartButton.setPreferredSize(new Dimension(200, 50));
        cartButton.setBackground(new Color(34, 139, 34));
        cartButton.setForeground(Color.WHITE);
        cartButton.setFocusPainted(false);
        cartButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cartButton.addActionListener(e -> mainFrame.showCart());

        panel.add(cartButton);
        return panel;
    }

    public void loadMenuItems(int categoryId) {
        this.currentCategoryId = categoryId;
        menuContainer.removeAll();

        List<MenuItem> items = mainFrame.getRestaurantController()
                .getMenuItemsByCategory(categoryId);

        for (MenuItem item : items) {
            JPanel itemPanel = createMenuItemPanel(item);
            menuContainer.add(itemPanel);
        }

        menuContainer.revalidate();
        menuContainer.repaint();
    }

    private JPanel createMenuItemPanel(MenuItem item) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Item info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(item.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(new Color(139, 69, 19));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea descArea = new JTextArea(item.getDescription());
        descArea.setFont(new Font("Arial", Font.PLAIN, 12));
        descArea.setForeground(Color.GRAY);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setBackground(Color.WHITE);
        descArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel priceLabel = new JLabel(String.format("%.2f TL", item.getBasePrice()));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        priceLabel.setForeground(new Color(34, 139, 34));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(descArea);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(priceLabel);

        // Add button
        JButton addButton = new JButton("+ Sepete Ekle");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(new Color(255, 140, 0));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(e -> showCustomizationDialog(item));

        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.SOUTH);

        return panel;
    }

    private void showCustomizationDialog(MenuItem item) {
        CustomizationDialog dialog = new CustomizationDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                item,
                mainFrame.getRestaurantController(),
                mainFrame.getOrderController()
        );
        dialog.setVisible(true);
    }
}
