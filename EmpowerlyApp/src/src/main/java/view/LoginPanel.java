package view;

import controller.AuthController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class LoginPanel extends JPanel {

    private MainFrame mainFrame;
    private AuthController authController;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private BufferedImage backgroundImage;

    // warna pastel & font
    private final Color bgColor = Color.decode("#F6F4F0");
    private final Color panelColor = Color.decode("#FAE7E9");
    private final Color accentColor = Color.decode("#9BCCB5");
    private final Color textColor = Color.decode("#333333");
    private final Color fieldBgColor = Color.decode("#E6C1D2");
    private Font buttonFont;
    private Font titleFont;

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.authController = new AuthController();

        loadBackgroundImage();
        loadFonts();
        initUI();
        clearFields();
    }

    private void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(new File("background.png"));
        } catch (Exception e) {
            System.out.println("⚠Background image belum ditemukan: " + e.getMessage());
        }
    }

    private void loadFonts() {
        
            buttonFont = new Font("OCR A Extended", Font.BOLD, 18);
            titleFont = new Font("OCR A Extended", Font.BOLD, 28);
        
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

    private void initUI() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        JPanel formPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                int shadowSize = 10;
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(shadowSize, shadowSize, getWidth() - shadowSize, getHeight() - shadowSize, 30, 30);
                try {
                    Image img = ImageIO.read(new File("panel.png"));
                    g2d.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    g2d.setColor(panelColor);
                    g2d.fillRoundRect(0, 0, getWidth() - shadowSize, getHeight() - shadowSize, 30, 30);
                }
                g2d.dispose();
            }
        };
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setPreferredSize(new Dimension(370, 440));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel loginGif = new JLabel();
        loginGif.setAlignmentX(Component.CENTER_ALIGNMENT);
            ImageIcon gifIcon = new ImageIcon("pixel.gif");
            int maxWidth = 180;
            int newHeight = (int) (gifIcon.getIconHeight() * (maxWidth / (double) gifIcon.getIconWidth()));
            Image scaledGif = gifIcon.getImage().getScaledInstance(maxWidth, newHeight, Image.SCALE_DEFAULT);
            loginGif.setIcon(new ImageIcon(scaledGif));

        usernameField = createStyledTextField("Username");
        passwordField = createStyledPasswordField("Password");

        JButton loginButton = createGlossyButton("Masuk");
        JButton registerButton = createTextButton("Belum ada akun?");
        JButton backButton = createTextButton("Kembali");

        loginButton.addActionListener(this::handleLogin);
        registerButton.addActionListener(e -> mainFrame.showPanel(MainFrame.REGISTER_PANEL));
        backButton.addActionListener(e -> mainFrame.showPanel(MainFrame.WELCOME_PANEL));

        formPanel.add(loginGif);
        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        formPanel.add(usernameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(passwordField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        formPanel.add(loginButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(registerButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(backButton);

        add(formPanel);
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(100, 100, 100, 200));
                    g2.setFont(getFont().deriveFont(Font.ITALIC));
                    g2.drawString(placeholder, 10, getHeight() / 2 + g2.getFontMetrics().getAscent() / 3 - 2);
                    g2.dispose();
                }
            }
        };
        field.setBackground(fieldBgColor);
        field.setForeground(textColor);
        field.setCaretColor(textColor);
        field.setFont(buttonFont.deriveFont(Font.BOLD, 16f));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(240, 200, 220), 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 160, 220), 3, true),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
            }

            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(240, 200, 220), 2, true),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
            }
        });
        return field;
    }

    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getPassword().length == 0 && !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(60, 60, 60, 180));
                    g2.setFont(getFont().deriveFont(Font.ITALIC, 15f));
                    g2.drawString(placeholder, 10, getHeight() / 2 + g2.getFontMetrics().getAscent() / 3 - 2);
                    g2.dispose();
                }
            }
        };
        field.setBackground(fieldBgColor);
        field.setForeground(textColor);
        field.setCaretColor(textColor);
        field.setFont(buttonFont.deriveFont(Font.BOLD, 16f));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(240, 200, 220), 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 160, 220), 3, true),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
            }

            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(240, 200, 220), 2, true),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
            }
        });
        return field;
    }

    private JButton createGlossyButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color topColor = new Color(230, 30, 140);
                Color bottomColor = new Color(180, 0, 100);
                GradientPaint gp = new GradientPaint(0, 0, topColor, 0, getHeight(), bottomColor);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                g2.setColor(new Color(255, 255, 255, 90));
                g2.fillRoundRect(0, 0, getWidth(), getHeight() / 2, 25, 25);

                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 25, 25);

                FontMetrics fm = g2.getFontMetrics(getFont());
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 4;
                g2.setColor(Color.WHITE);
                g2.drawString(getText(), x, y);

                g2.dispose();
            }
        };
        button.setPreferredSize(new Dimension(250, 50));
        button.setMaximumSize(new Dimension(250, 50));
        button.setFont(buttonFont);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    private JButton createTextButton(String text) {
    JButton button = new JButton(text) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color bgTop = new Color(255, 255, 255, 120);
            Color bgBottom = new Color(255, 255, 255, 50);
            GradientPaint gp = new GradientPaint(0, 0, bgTop, 0, getHeight(), bgBottom);
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            g2.setColor(new Color(255, 255, 255, 100));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);

            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = (getHeight() + fm.getAscent()) / 2 - 3;
            g2.setColor(new Color(105, 17, 149));
            g2.drawString(getText(), x, y);
            g2.dispose();
        }
    };

    button.setPreferredSize(new Dimension(250, 35));
    button.setMaximumSize(new Dimension(250, 35));

    button.setFont(buttonFont.deriveFont(Font.BOLD, 13f));

    button.setContentAreaFilled(false);
    button.setFocusPainted(false);
    button.setBorderPainted(false);
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    button.setAlignmentX(Component.CENTER_ALIGNMENT);

    button.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

    return button;
}

    private void handleLogin(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object[] loginResult = authController.validateLogin(username, password);
        if (loginResult != null) {
            int id_user = Integer.parseInt(loginResult[0].toString());
            String role = (String) loginResult[1];
            String status = (String) loginResult[2];
            mainFrame.handleLoginSuccess(id_user, role, status);
        } else {
            JOptionPane.showMessageDialog(this, "Username atau password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clearFields() {
        if (usernameField != null) usernameField.setText("");
        if (passwordField != null) passwordField.setText("");
    }
}
