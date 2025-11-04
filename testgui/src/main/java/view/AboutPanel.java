package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class AboutPanel extends JPanel {

    private MainFrame mainFrame;
    private ImageIcon backgroundGif;

    private final Color textColor = Color.decode("#333333");
    private final Color bgColor = Color.decode("#F6F4F0");
    private final Color panelColor = Color.decode("#FAE7E9");

    public AboutPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        loadBackgroundImage();
        initComponents();
    }

    private void loadBackgroundImage() {
        try {
            // pakai ImageIcon biar GIF bisa jalan
            backgroundGif = new ImageIcon("aboutt.gif"); 
            if (backgroundGif.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.out.println("⚠️ GIF gagal dimuat. Path: " + new File("aboutt.gif").getAbsolutePath());
            } else {
                System.out.println("✅ GIF berhasil dimuat: " + new File("aboutt.gif").getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println("⚠️ Background GIF belum ditemukan. Path: " + new File("aboutt.gif").getAbsolutePath());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundGif != null && backgroundGif.getImage() != null) {
            // ambil ukuran gambar asli
            Image img = backgroundGif.getImage();
            int imgWidth = img.getWidth(this);
            int imgHeight = img.getHeight(this);

            // jaga rasio biar gak gepeng
            double imgAspect = (double) imgWidth / imgHeight;
            int drawWidth = getWidth();
            int drawHeight = (int) (drawWidth / imgAspect);

            if (drawHeight < getHeight()) {
                drawHeight = getHeight();
                drawWidth = (int) (drawHeight * imgAspect);
            }

            int x = (getWidth() - drawWidth) / 2;
            int y = (getHeight() - drawHeight) / 2;

            g.drawImage(img, x, y, drawWidth, drawHeight, this);
        } else {
            g.setColor(bgColor);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private JButton createStartButton(String text, Font font) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // bayangan
                g2.setColor(new Color(0, 0, 0, 40));
                g2.fillRoundRect(4, 4, getWidth() - 4, getHeight() - 4, 25, 25);

                // gradasi
                Color topColor = new Color(230, 30, 140);
                Color bottomColor = new Color(180, 0, 100);
                GradientPaint gp = new GradientPaint(0, 0, topColor, 0, getHeight(), bottomColor);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, 25, 25);

                // efek glossy
                g2.setColor(new Color(255, 255, 255, 80));
                g2.fillRoundRect(0, 0, getWidth(), getHeight() / 2, 25, 25);

                // outline
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, getWidth() - 5, getHeight() - 5, 25, 25);

                // teks
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

    private void initComponents() {
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        JButton startButton = createStartButton("START", new Font("OCR A Extended", Font.BOLD, 18));
        startButton.addActionListener(e -> mainFrame.showPanel(MainFrame.WELCOME_PANEL));

        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
