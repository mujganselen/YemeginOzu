package View;

import Model.Order;
import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {
    private MainFrame mainFrame;

    public WelcomePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 250, 240));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(255, 250, 240));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(100, 50, 50, 50));

        JLabel logoLabel = new JLabel("🍽️");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 120));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Yemegin Ozu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(new Color(139, 69, 19));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Your Culinary Journey Begins");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        subtitleLabel.setForeground(new Color(105, 105, 105));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titlePanel.add(logoLabel);
        titlePanel.add(Box.createVerticalStrut(20));
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(subtitleLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonPanel.setBackground(new Color(255, 250, 240));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 150, 50));

        JButton dineInButton = createOrderTypeButton("🪑 Dine In", Order.OrderType.DINE_IN);
        JButton takeawayButton = createOrderTypeButton("📦 Takeaway", Order.OrderType.TAKEAWAY);

        buttonPanel.add(dineInButton);
        buttonPanel.add(takeawayButton);

        add(titlePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createOrderTypeButton(String text, Order.OrderType orderType) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setPreferredSize(new Dimension(300, 100));
        button.setBackground(new Color(255, 140, 0));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> mainFrame.startOrder(orderType));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 165, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 140, 0));
            }
        });

        return button;
    }
}