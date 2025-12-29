package View;

import Model.Category;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CategoryPanel extends JPanel {
    private MainFrame mainFrame;
    private JPanel categoryContainer;

    public CategoryPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Category Grid
        categoryContainer = new JPanel();
        categoryContainer.setLayout(new GridLayout(0, 2, 20, 20));
        categoryContainer.setBackground(Color.WHITE);
        categoryContainer.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JScrollPane scrollPane = new JScrollPane(categoryContainer);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with cart button
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 140, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Categories");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);

        panel.add(titleLabel, BorderLayout.WEST);
        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton cartButton = new JButton("View Cart (" +
                mainFrame.getOrderController().getCartItemCount() + ")");
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

    public void loadCategories() {
        categoryContainer.removeAll();

        List<Category> categories = mainFrame.getRestaurantController().getAllCategories();

        for (Category category : categories) {
            JButton categoryButton = createCategoryButton(category);
            categoryContainer.add(categoryButton);
        }

        categoryContainer.revalidate();
        categoryContainer.repaint();
    }

    private JButton createCategoryButton(Category category) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.setPreferredSize(new Dimension(300, 150));
        button.setBackground(new Color(255, 248, 220));
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 140, 0), 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);

        // Icon based on category
        String icon = getCategoryIcon(category.getName());
        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 50));

        JLabel nameLabel = new JLabel(category.getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        nameLabel.setForeground(new Color(139, 69, 19));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textPanel.add(iconLabel);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(nameLabel);

        button.add(textPanel, BorderLayout.CENTER);

        button.addActionListener(e -> mainFrame.showMenu(category.getId()));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 235, 205));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 248, 220));
            }
        });

        return button;
    }

    private String getCategoryIcon(String categoryName) {
        if (categoryName.contains("Main")) return "🍖";
        if (categoryName.contains("Starter")) return "🥗";
        if (categoryName.contains("Dessert")) return "🍰";
        if (categoryName.contains("Drink")) return "🥤";
        return "🍽️";
    }
}