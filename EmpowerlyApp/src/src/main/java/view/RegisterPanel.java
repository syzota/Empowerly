package view;

import controller.AuthController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;

public class RegisterPanel extends JPanel {

    private MainFrame mainFrame;
    private AuthController authController;
    private JTextField userField;
    private JPasswordField passField;
    private JTextField umurField;
    private BufferedImage backgroundImage;

    // warna pastel dari WelcomePanel
    private final Color bgColor = Color.decode("#F6F4F0");
    private final Color panelColor = Color.decode("#FAE7E9");
    private final Color textColor = Color.decode("#333333");
    private final Color fieldBgColor = Color.decode("#E6C1D2");
    
    private Font buttonFont;
    private Font titleFont;

    public RegisterPanel(MainFrame mainFrame) {
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
            System.out.println("Background image belum ditemukan. Path: " + new File("background.png").getAbsolutePath());
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

        // panel form dengan shadow dan PNG
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
        formPanel.setPreferredSize(new Dimension(370, 490));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // --- WELCOME GIF ---
        JLabel welcomeGif = new JLabel();
        welcomeGif.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            ImageIcon gifIcon = new ImageIcon("gif3.gif");
            int maxWidth = 200;
            int newHeight = (int) (gifIcon.getIconHeight() * (maxWidth / (double) gifIcon.getIconWidth()));
            Image scaledGif = gifIcon.getImage().getScaledInstance(maxWidth, newHeight, Image.SCALE_DEFAULT);
            welcomeGif.setIcon(new ImageIcon(scaledGif));
        } catch (Exception e) {
            welcomeGif.setText("Welcome!");
            welcomeGif.setFont(titleFont);
            welcomeGif.setForeground(textColor);
        }

        // --- Field dengan Placeholder ---
        userField = createStyledTextField("Username");
        passField = createStyledPasswordField("Password");
        umurField = createStyledTextField("Umur");

        JButton registerButton = createGlossyButton("Register");
        JButton backButton = createTextButton("Ada akun?");

        registerButton.addActionListener(e -> handleRegister());
        backButton.addActionListener(e -> mainFrame.showPanel(MainFrame.LOGIN_PANEL));

        formPanel.add(welcomeGif);
        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        formPanel.add(userField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(passField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(umurField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        formPanel.add(registerButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(backButton);

        add(formPanel);
    }

    // --- FIELD DENGAN PLACEHOLDER & GLOW EFFECT ---
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

        // efek glow pas fokus
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 160, 220), 3, true),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
            }

            @Override
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

                        // warna placeholder baru — lebih solid tapi tetep lembut
                        g2.setColor(new Color(60, 60, 60, 180)); // 👈 opacity 180 biar lebih terbaca
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
                @Override
                public void focusGained(FocusEvent e) {
                    field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 160, 220), 3, true),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                    ));
                }

                @Override
                public void focusLost(FocusEvent e) {
                    field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(240, 200, 220), 2, true),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                    ));
                }
            });
            return field;

    }

    // --- BUTTONS ---
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

                // background transparan lembut
                Color bgTop = new Color(255, 255, 255, 120);
                Color bgBottom = new Color(255, 255, 255, 50);
                GradientPaint gp = new GradientPaint(0, 0, bgTop, 0, getHeight(), bgBottom);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                // border halus transparan
                g2.setColor(new Color(255, 255, 255, 100));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);

                // teks
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 3;
                g2.setColor(new Color(100, 60, 120)); // ungu pastel
                g2.drawString(getText(), x, y);

                g2.dispose();
            }
        };
        button.setPreferredSize(new Dimension(250, 45));
        button.setMaximumSize(new Dimension(250, 45));
        button.setFont(buttonFont.deriveFont(Font.BOLD, 15f));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    // --- REGISTER HANDLER ---
    private void handleRegister() {
        String username = userField.getText();
        String password = new String(passField.getPassword());
        String umurStr = umurField.getText();

        if (username.isEmpty() || password.isEmpty() || umurStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int umur;
        try {
            umur = Integer.parseInt(umurStr);
            if (umur <= 0) throw new NumberFormatException();
            if (umur < 13) {
                JOptionPane.showMessageDialog(this, "Pendaftaran hanya untuk pengguna berusia 13 tahun ke atas!", "Batasan Umur", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Umur harus berupa angka positif!", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean success = authController.registerUser(username, password, umur);
        if (success) {
            JOptionPane.showMessageDialog(this, "Registrasi berhasil, silahkan login!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            mainFrame.showPanel(MainFrame.LOGIN_PANEL);
        } else {
            JOptionPane.showMessageDialog(this, "Registrasi gagal! Username sudah digunakan.", "Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clearFields() {
        if (userField != null) userField.setText("");
        if (passField != null) passField.setText("");
        if (umurField != null) umurField.setText("");
    }
}
