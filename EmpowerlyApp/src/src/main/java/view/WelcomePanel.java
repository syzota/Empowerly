package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;

public class WelcomePanel extends JPanel {

    private MainFrame mainFrame;
    private BufferedImage backgroundImage;

    private final Color bgColor = Color.decode("#F6F4F0");
    private final Color panelColor = Color.decode("#FAE7E9");
    private final Color textColor = Color.decode("#333333");

    public WelcomePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        loadBackgroundImage();
        initComponents();
    }

    private void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(new File("background.png"));
        } catch (Exception e) {
            System.out.println("⚠️ Background image belum ditemukan. Path: " + new File("background.png").getAbsolutePath());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(bgColor);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        JPanel centerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                int shadowOffset = 6;
                int arc = 30;

                g2d.setColor(new Color(0, 0, 0, 60));
                g2d.fillRoundRect(shadowOffset, shadowOffset, getWidth() - shadowOffset, getHeight() - shadowOffset, arc, arc);

                try {
                    Image img = ImageIO.read(new File("panel.png"));
                    g2d.drawImage(img, 0, 0, getWidth() - shadowOffset, getHeight() - shadowOffset, this);
                } catch (Exception e) {
            
                    g2d.setColor(panelColor);
                    g2d.fillRoundRect(0, 0, getWidth() - shadowOffset, getHeight() - shadowOffset, arc, arc);
                }

                g2d.dispose();
            }
        };


        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setPreferredSize(new Dimension(350, 450));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        Font titleFont = new Font("OCR A Extended", Font.BOLD, 28);
        Font buttonFont = new Font("OCR A Extended", Font.BOLD, 18);
        Font labelFont = new Font("OCR A Extended", Font.BOLD, 16);

        JLabel welcomeGif = new JLabel();
        welcomeGif.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            ImageIcon gifIcon = new ImageIcon("welcome.gif");
            int maxWidth = 200;
            int newHeight = (int) (gifIcon.getIconHeight() * (maxWidth / (double) gifIcon.getIconWidth()));
            Image scaledGif = gifIcon.getImage().getScaledInstance(maxWidth, newHeight, Image.SCALE_DEFAULT);
            welcomeGif.setIcon(new ImageIcon(scaledGif));
        } catch (Exception e) {
            welcomeGif.setText("Welcome!");
            welcomeGif.setFont(titleFont);
            welcomeGif.setForeground(textColor);
        }

        JButton loginButton = createMenuButton("Login", buttonFont);
        JButton registerButton = createMenuButton("Register", buttonFont);
        JButton keluarButton = createMenuButton("Keluar", buttonFont);

        loginButton.addActionListener(e -> mainFrame.showPanel(MainFrame.LOGIN_PANEL));
        registerButton.addActionListener(e -> mainFrame.showPanel(MainFrame.REGISTER_PANEL));
        keluarButton.addActionListener(e -> System.exit(0));

        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(welcomeGif);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        centerPanel.add(loginButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPanel.add(registerButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPanel.add(keluarButton);

        add(centerPanel);
    }

    private JButton createMenuButton(String text, Font font) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(0, 0, 0, 40));
                g2.fillRoundRect(4, 4, getWidth() - 4, getHeight() - 4, 25, 25);
                
                Color topColor = new Color(230, 30, 140);
                Color bottomColor = new Color(180, 0, 100);
                GradientPaint gp = new GradientPaint(0, 0, topColor, 0, getHeight(), bottomColor);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 25, 25);

                g2.setColor(new Color(255, 255, 255, 80));
                g2.fillRoundRect(0, 0, getWidth(), getHeight() / 2, 25, 25);

                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, getWidth() - 5, getHeight() - 5, 25, 25);

                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 4;
                g2.setColor(Color.WHITE);
                g2.drawString(getText(), x, y);

                g2.dispose();
            }
        };

        button.setPreferredSize(new Dimension(250, 60));
        button.setMaximumSize(new Dimension(250, 60));
        button.setMinimumSize(new Dimension(250, 60));
        button.setFont(font);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 200, 220));
                button.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 180, 210));
                button.repaint();
            }
        });

        return button;
    }
}
