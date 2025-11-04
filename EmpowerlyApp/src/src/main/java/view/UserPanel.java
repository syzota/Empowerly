package view;

import main.DatabaseConnection;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.URL;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class UserPanel extends BasePanel {

    private MainFrame mainFrame;
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private CardLayout contentCardLayout;
    private List<JButton> sidebarButtons = new ArrayList<>();
    private int userId;
    private final Color WARNA_BACKGROUND_UTAMA = new Color(255, 255, 255, 0);
    private final Color WARNA_PANEL_CONTENT = new Color(255, 255, 255, 100);
    private final Color TOMBOL = Color.decode("#DF1C89");
    private final Color buttonTextColor = Color.WHITE;
    Font customFont = new Font("OCR A Extended", Font.BOLD, 14);
    
    private static final String MATERI = "MATERI";
    private static final String FORUM = "FORUM";
    private static final String KOMENTAR = "KOMENTAR";
    
    private DefaultTableModel materiTableModel;
    private JTable materiTable;
    private JTextField txtInfoJudul;
    private JTextField txtInfoTipe;
    private JTextField txtInfoKonten;
    private JButton btnInfoLihatKonten;

    public UserPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        System.out.println("[DEBUG UserPanel] Constructor UserPanel dipanggil.");
    }

    public void loadData(int userId) {
        System.out.println("[DEBUG UserPanel] loadData dipanggil. User ID: " + userId);
        this.userId = userId;
        removeAll();
        sidebarButtons.clear();
        createSidebar();
        createContentArea();
        revalidate();
        repaint();
        System.out.println("[DEBUG UserPanel] loadData selesai. Panel di-refresh.");
    }

    private void createSidebar() {
    sidebarPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon icon = new ImageIcon("2.png"); 
            g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    };
    sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
    sidebarPanel.setPreferredSize(new Dimension(220, 720));
    sidebarPanel.setOpaque(false);
    sidebarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 15));
    sidebarPanel.add(Box.createRigidArea(new Dimension(0, 30)));
    
    try {
        ImageIcon originalIcon = new ImageIcon("logo.png"); 
        Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel logoLabel = new JLabel(scaledIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebarPanel.add(logoLabel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20))); 
    } catch (Exception e) {
        System.out.println("[DEBUG] Logo gagal dimuat: " + e.getMessage());
    }

    sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20))); 
        
        addSidebarButton("Materi", MATERI);
        addSidebarButton("Forum", FORUM);
        addSidebarButton("Komentar", KOMENTAR);
        sidebarPanel.add(Box.createVerticalGlue());
        
        JButton logoutButton = createSidebarButton("Keluar", "LOGOUT");
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                mainFrame,                  
                "Yakin ingin keluar?",      
                "Konfirmasi Logout",                      
                JOptionPane.YES_NO_OPTION,          
                JOptionPane.QUESTION_MESSAGE               
            );

            if (confirm == JOptionPane.YES_OPTION) {
                mainFrame.handleLogout(); 
            }

        });
        sidebarPanel.add(logoutButton);

        add(sidebarPanel, BorderLayout.WEST);
    }

    private void addSidebarButton(String text, String panelName) {
        JButton button = createSidebarButton(text, panelName);
        button.addActionListener(e -> {
            if (contentCardLayout != null && contentPanel != null) {
                contentCardLayout.show(contentPanel, panelName);
                setActiveButton(button);
            }
        });
        sidebarButtons.add(button);
        if (sidebarPanel != null) {
             sidebarPanel.add(button);
        }
    }

private JButton createSidebarButton(String text, String panelName) {
    JButton button = new JButton(text) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color topColor = new Color(128, 240, 242);  
            Color bottomColor = new Color(96, 224, 225);
            GradientPaint gp = new GradientPaint(0, 0, topColor, 0, getHeight(), bottomColor);
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

            g2.setColor(new Color(255, 255, 255, 70));
            g2.fillRoundRect(0, 0, getWidth(), getHeight() / 2, 20, 20);
            
            g2.setColor(new Color(0, 0, 0, 20));
            g2.fillRoundRect(3, 3, getWidth() - 6, getHeight() - 6, 20, 20);

            g2.setColor(new Color(255, 255, 255, 200));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 20, 20);

            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = (getHeight() + fm.getAscent()) / 2 - 4;
            g2.setColor(new Color(20, 30, 40));
            g2.drawString(getText(), x, y);

            g2.dispose();
        }
    };

    button.setPreferredSize(new Dimension(180, 45));
    button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
    button.setFont(new Font("OCR A Extended", Font.BOLD, 13));
    button.setContentAreaFilled(false);
    button.setFocusPainted(false);
    button.setBorderPainted(false);
    button.setOpaque(false);
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    button.setAlignmentX(Component.CENTER_ALIGNMENT);

    button.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(new Color(160, 245, 245));
            button.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setBackground(new Color(96, 224, 225));
            button.repaint();
        }
    });
    
    button.addActionListener(e -> {
        if (contentCardLayout != null && contentPanel != null) {
            contentCardLayout.show(contentPanel, panelName);
            setActiveButton(button);
        }
    });

    sidebarButtons.add(button);
    if (sidebarPanel != null) {
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(button);
    }

    return button;
}

    private void setActiveButton(JButton activeButton) {
        for (JButton btn : sidebarButtons) {
            btn.setForeground(Color.GRAY);
            btn.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
        }
        activeButton.setForeground(Color.BLACK);
        activeButton.setFont(new Font("OCR A Extended", Font.BOLD, 14));
    }

private void createContentArea() {
    contentCardLayout = new CardLayout();
    contentPanel = new JPanel(contentCardLayout);
    contentPanel.setOpaque(false);
    
    JPanel overlayPanel = new JPanel(new BorderLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            g2.setColor(new Color(255, 255, 255)); 
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    };
    overlayPanel.setOpaque(false);
    overlayPanel.add(contentPanel, BorderLayout.CENTER);

    JLabel backgroundLabel = new JLabel(new ImageIcon("gifwall.gif")) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image img = ((ImageIcon) getIcon()).getImage();
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    };
    backgroundLabel.setLayout(new BorderLayout());
    backgroundLabel.add(overlayPanel, BorderLayout.CENTER);

    add(backgroundLabel, BorderLayout.CENTER);
    revalidate();
    repaint();

    JPanel materiPanel = createUserMateriPanel();
    JPanel forumPanel = createUserForumPanel();
    JPanel komentarPanel = createUserKomentarPanel();

    contentPanel.add(materiPanel, MATERI);
    contentPanel.add(forumPanel, FORUM);
    contentPanel.add(komentarPanel, KOMENTAR);

    if (!sidebarButtons.isEmpty()) {
        contentCardLayout.show(contentPanel, MATERI);
        setActiveButton(sidebarButtons.get(0));
    }
}

    private JPanel createUserMateriPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(WARNA_BACKGROUND_UTAMA);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        ImageIcon icon = new ImageIcon(getClass().getResource("materials.png"));
        System.out.println(getClass().getResource("materials.png"));
        Image img = icon.getImage();

        JLabel titleLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                g2.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                g2.dispose();
                
                int imgWidth = img.getWidth(this);
                int imgHeight = img.getHeight(this);
                double aspect = (double) imgWidth / imgHeight;

                int drawWidth = getWidth();
                int drawHeight = (int) (drawWidth / aspect);
                if (drawHeight > getHeight()) {
                    drawHeight = getHeight();
                    drawWidth = (int) (drawHeight * aspect);
                }

                int x = (getWidth() - drawWidth) / 2;
                int y = (getHeight() - drawHeight) / 2;
                g2.drawImage(img, x, y, drawWidth, drawHeight, this);

            }
        };
        titleLabel.setOpaque(false);
        titleLabel.setPreferredSize(new Dimension(220, 60));
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        JPanel contentSplitPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        contentSplitPanel.setBackground(WARNA_BACKGROUND_UTAMA);
        contentSplitPanel.add(createUserMateriInfoPanel());
        contentSplitPanel.add(createUserMateriTablePanel());
        mainPanel.add(contentSplitPanel, BorderLayout.CENTER);
        if (materiTable != null) {
            materiTable.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting() && materiTable.getSelectedRow() != -1) {
                    if (txtInfoJudul != null && txtInfoTipe != null && txtInfoKonten != null && btnInfoLihatKonten != null && materiTableModel != null) {
                        int selectedRow = materiTable.getSelectedRow();
                        if (selectedRow < materiTableModel.getRowCount()) {
                            txtInfoJudul.setText(materiTableModel.getValueAt(selectedRow, 1).toString());
                            txtInfoTipe.setText(materiTableModel.getValueAt(selectedRow, 2).toString());
                            txtInfoKonten.setText(materiTableModel.getValueAt(selectedRow, 4).toString());
                            btnInfoLihatKonten.setEnabled(true);
                        } else {
                            System.out.println("[DEBUG UserPanel] Indeks baris tidak valid saat klik tabel materi.");
                        }
                    }
                }
            });
        } else {
            System.err.println("[DEBUG UserPanel] ERROR: materiTable null saat menambahkan listener!");
        }
        loadMateriTableData();
        return mainPanel;
    }

    private JPanel createUserMateriInfoPanel() {
        JPanel infoPanel = new JPanel();
        GroupLayout layout = new GroupLayout(infoPanel);
        infoPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        infoPanel.setBackground(WARNA_PANEL_CONTENT);
        infoPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), null,
            TitledBorder.LEFT, TitledBorder.TOP, new Font("OCR A Extended", Font.BOLD, 14)));
        JLabel lblJudul = new JLabel("Judul Materi:");
        txtInfoJudul = new JTextField("Pilih materi di tabel"); txtInfoJudul.setEditable(false);
        JLabel lblTipe = new JLabel("Tipe:");
        txtInfoTipe = new JTextField(); txtInfoTipe.setEditable(false);
        JLabel lblKonten = new JLabel("Konten:");
        txtInfoKonten = new JTextField(); txtInfoKonten.setEditable(false);
        btnInfoLihatKonten = new JButton("Lihat Konten");
        btnInfoLihatKonten.setBackground(TOMBOL); btnInfoLihatKonten.setForeground(buttonTextColor);
        btnInfoLihatKonten.setFont(new Font("OCR A Extended", Font.BOLD, 12)); btnInfoLihatKonten.setEnabled(false);
        btnInfoLihatKonten.addActionListener(e -> {
            String url = txtInfoKonten.getText();
            if (url != null && !url.isEmpty() && (url.startsWith("http://") || url.startsWith("https://"))) {
                try {
                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                        Desktop.getDesktop().browse(new URI(url));
                    } else { JOptionPane.showMessageDialog(this, "Tidak bisa membuka browser.", "Error", JOptionPane.ERROR_MESSAGE); }
                } catch (Exception ex) { ex.printStackTrace(); JOptionPane.showMessageDialog(this, "Gagal membuka link: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
            } else { JOptionPane.showMessageDialog(this, "Konten bukan link yang valid.", "Peringatan", JOptionPane.WARNING_MESSAGE); }
        });
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(lblJudul).addComponent(txtInfoJudul).addComponent(lblTipe).addComponent(txtInfoTipe)
            .addComponent(lblKonten).addComponent(txtInfoKonten).addComponent(btnInfoLihatKonten, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(lblJudul).addComponent(txtInfoJudul, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(10)
            .addComponent(lblTipe).addComponent(txtInfoTipe, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(10)
            .addComponent(lblKonten).addComponent(txtInfoKonten, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(20)
            .addComponent(btnInfoLihatKonten, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        return infoPanel;
    }

    private JPanel createUserMateriTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout(10, 10));
        tablePanel.setBackground(WARNA_PANEL_CONTENT);
        tablePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), null,
            TitledBorder.LEFT, TitledBorder.TOP, new Font("OCR A Extended", Font.BOLD, 14)));
        String[] columnNames = {"ID Materi", "Judul", "Tipe", "Kategori", "Konten"};
        
         if (materiTableModel == null) {
            materiTableModel = new DefaultTableModel(columnNames, 0) {
                @Override public boolean isCellEditable(int r, int c){ return false; }
            };
         }
         if (materiTable == null) {
            materiTable = new JTable(materiTableModel);
         } else {
             materiTable.setModel(materiTableModel);
         }
        setupTableStyle(materiTable, WARNA_BACKGROUND_UTAMA);
        JScrollPane scrollPane = new JScrollPane(materiTable);
        setupScrollPaneStyle(scrollPane, materiTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private void loadMateriTableData() {
        if (materiTableModel == null) return;
        materiTableModel.setRowCount(0);
        Connection conn = DatabaseConnection.getInstance().getConnection();;
        String sql = "SELECT m.id_materi, m.judul, m.tipe, k.nama_kategori, m.konten FROM materi m JOIN kategori k ON m.id_kategori = k.id_kategori";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                materiTableModel.addRow(new Object[]{ rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5) });
            }
             System.out.println("[DEBUG UserPanel] Data materi dimuat untuk user, Jumlah baris: " + materiTableModel.getRowCount());
        } catch (SQLException e) { e.printStackTrace(); JOptionPane.showMessageDialog(this, "Gagal memuat data materi: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException npe) {
             System.err.println("[DEBUG UserPanel] NullPointerException saat loadMateriTableData. Mungkin tabel belum dibuat?");
             npe.printStackTrace();
        }
    }

private JPanel createUserForumPanel() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.setBackground(WARNA_BACKGROUND_UTAMA);

    ImageIcon icon = new ImageIcon(getClass().getResource("forum.png"));
    Image img = icon.getImage();

    JLabel titleLabel = new JLabel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            int imgWidth = img.getWidth(this);
            int imgHeight = img.getHeight(this);
            double aspect = (double) imgWidth / imgHeight;

            int drawWidth = getWidth();
            int drawHeight = (int) (drawWidth / aspect);

            if (drawHeight > getHeight()) {
                drawHeight = getHeight();
                drawWidth = (int) (drawHeight * aspect);
            }

            int x = (getWidth() - drawWidth) / 2;
            int y = (getHeight() - drawHeight) / 2;

            g2.drawImage(img, x, y, drawWidth, drawHeight, this);
            g2.dispose();
        }
    };
    titleLabel.setOpaque(false);
    titleLabel.setPreferredSize(new Dimension(220, 60));
    panel.add(titleLabel, BorderLayout.NORTH);

    // --- Tabel forum ---
    String[] columnNames = {"ID Forum", "Judul", "Deskripsi", "Admin"};
    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    JTable table = new JTable(tableModel);
    Connection conn = DatabaseConnection.getInstance().getConnection();

    String sql = "SELECT f.id_forum, f.judul, f.deskripsi, u.username " +
                 "FROM forum f JOIN user u ON f.id_user = u.id_user";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            tableModel.addRow(new Object[]{
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4)
            });
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    setupTableStyle(table, WARNA_BACKGROUND_UTAMA);

    JScrollPane scrollPane = new JScrollPane(table);
    setupScrollPaneStyle(scrollPane, table);
    panel.add(scrollPane, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    buttonPanel.setBackground(WARNA_BACKGROUND_UTAMA);

    JButton viewDetailsButton = new JButton("Lihat Detail Forum & Komentar");
    viewDetailsButton.setBackground(TOMBOL);
    viewDetailsButton.setForeground(buttonTextColor);
    viewDetailsButton.setFont(customFont);
    buttonPanel.add(viewDetailsButton);
    panel.add(buttonPanel, BorderLayout.SOUTH);

    viewDetailsButton.addActionListener(e -> {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih forum...", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idForum = (int) tableModel.getValueAt(row, 0);
        String judulForum = (String) tableModel.getValueAt(row, 1);
        String deskripsiForum = (String) tableModel.getValueAt(row, 2);

        JDialog dialog = new JDialog(mainFrame, judulForum, true);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(WARNA_BACKGROUND_UTAMA);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        ImageIcon imageIcon = new ImageIcon("head.png"); 

        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        topPanel.setOpaque(false);
        topPanel.add(imageLabel);

        dialog.add(topPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel judulLabel = new JLabel(judulForum);
        judulLabel.setFont(new Font("OCR A Extended", Font.BOLD, 18));
        judulLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(judulLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JLabel deskripsiLabel = new JLabel("<html>" + deskripsiForum + "</html>");
        deskripsiLabel.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
        deskripsiLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(deskripsiLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        String[] commentCols = {"ID Komen", "Pengirim", "Isi Komentar"};
        DefaultTableModel commentModel = new DefaultTableModel(commentCols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        JTable commentTable = new JTable(commentModel);
        setupTableStyle(commentTable, WARNA_BACKGROUND_UTAMA);
        
        commentTable.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID Komen
        commentTable.getColumnModel().getColumn(0).setMinWidth(50);
        commentTable.getColumnModel().getColumn(0).setMaxWidth(50);

        commentTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Pengirim
        commentTable.getColumnModel().getColumn(1).setMinWidth(80);
        commentTable.getColumnModel().getColumn(1).setMaxWidth(150);

        commentTable.getColumnModel().getColumn(2).setPreferredWidth(430); // Isi Komentar
        commentTable.getColumnModel().getColumn(2).setMinWidth(200);
        commentTable.getColumnModel().getColumn(2).setMaxWidth(Integer.MAX_VALUE);

        JScrollPane commentScrollPane = new JScrollPane(commentTable);
        commentScrollPane.setPreferredSize(new Dimension(580, 200));
        commentScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        commentScrollPane.getVerticalScrollBar().setBackground(TOMBOL);
        commentScrollPane.getVerticalScrollBar().setForeground(TOMBOL);
        contentPanel.add(commentScrollPane);

        String sqlComments = "SELECT k.id_komen, u.username, k.isi " +
                             "FROM komen k JOIN user u ON k.id_user = u.id_user " +
                             "WHERE k.id_forum = ?";
        try (PreparedStatement pstmtComments = conn.prepareStatement(sqlComments)) {
            pstmtComments.setInt(1, idForum);
            ResultSet rsComments = pstmtComments.executeQuery();
            while (rsComments.next()) {
                commentModel.addRow(new Object[]{
                    rsComments.getInt(1),
                    rsComments.getString(2),
                    rsComments.getString(3)
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JScrollPane centerScroll = new JScrollPane(contentPanel);
        centerScroll.setBorder(null);
        centerScroll.getVerticalScrollBar().setUnitIncrement(16);
        centerScroll.getVerticalScrollBar().setBackground(TOMBOL);
        centerScroll.getVerticalScrollBar().setForeground(TOMBOL);
        dialog.add(centerScroll, BorderLayout.CENTER);

        JPanel commentInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        commentInputPanel.setBackground(Color.WHITE);
        JTextField txtKomentar = new JTextField(30);
        JButton btnTambahKomen = new JButton("Tambah Komentar");
        btnTambahKomen.setBackground(TOMBOL);
        btnTambahKomen.setForeground(buttonTextColor);
        btnTambahKomen.setFont(customFont);
        commentInputPanel.add(new JLabel("Komentar Anda:"));
        commentInputPanel.add(txtKomentar);
        commentInputPanel.add(btnTambahKomen);

        btnTambahKomen.addActionListener(ev -> {
            String isiKomentar = txtKomentar.getText();
            if (isiKomentar.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Komentar kosong.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String sqlInsert = "INSERT INTO komen (isi, id_forum, id_user) VALUES (?, ?, ?)";
            try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                pstmtInsert.setString(1, isiKomentar);
                pstmtInsert.setInt(2, idForum);
                pstmtInsert.setInt(3, this.userId);
                pstmtInsert.executeUpdate();

                ResultSet rsKey = pstmtInsert.getGeneratedKeys();
                if (rsKey.next()) {
                    commentModel.addRow(new Object[]{
                        rsKey.getInt(1),
                        "Anda (User: " + this.userId + ")",
                        isiKomentar
                    });
                    txtKomentar.setText("");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Gagal: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(commentInputPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(mainFrame);
        dialog.setVisible(true);
    });

    return panel;
}

private JPanel createUserKomentarPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(WARNA_BACKGROUND_UTAMA);

        ImageIcon icon = new ImageIcon(getClass().getResource("komen.png"));
        Image img = icon.getImage();

        JLabel titleLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                int imgWidth = img.getWidth(this);
                int imgHeight = img.getHeight(this);
                double aspect = (double) imgWidth / imgHeight;

                int drawWidth = getWidth();
                int drawHeight = (int) (drawWidth / aspect);

                if (drawHeight > getHeight()) {
                    drawHeight = getHeight();
                    drawWidth = (int) (drawHeight * aspect);
                }

                int x = (getWidth() - drawWidth) / 2;
                int y = (getHeight() - drawHeight) / 2;

                g2.drawImage(img, x, y, drawWidth, drawHeight, this);
                g2.dispose();
            }

        };
        titleLabel.setOpaque(false);
        titleLabel.setPreferredSize(new Dimension(220, 60));

        panel.add(titleLabel, BorderLayout.NORTH);

String[] columnNames = {"ID Komen", "Isi Komentar", "Forum", "Status"};
DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
    @Override
    public boolean isCellEditable(int r, int c) {
        return false;
    }
    
};

JTable table = new JTable(tableModel);
Connection conn = DatabaseConnection.getInstance().getConnection();

String sql = "SELECT k.id_komen, k.isi, f.judul AS judul_forum FROM komen k JOIN forum f ON k.id_forum = f.id_forum WHERE k.id_user = ?";

try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
    pstmt.setInt(1, this.userId);
    ResultSet rs = pstmt.executeQuery();
    while (rs.next()) {
        tableModel.addRow(new Object[]{
            rs.getInt(1),
            rs.getString(2),
            rs.getString(3),
            "Terkirim"
        });
    }
} catch (SQLException e) {
    e.printStackTrace();
}

setupTableStyle(table, WARNA_BACKGROUND_UTAMA);
JScrollPane scrollPane = new JScrollPane(table);
setupScrollPaneStyle(scrollPane, table);
panel.add(scrollPane, BorderLayout.CENTER);

// Tombol aksi
JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
buttonPanel.setBackground(WARNA_BACKGROUND_UTAMA);

JButton editButton = new JButton("Edit Komentar");
editButton.setBackground(TOMBOL);
editButton.setForeground(buttonTextColor);
editButton.setFont(customFont);

JButton hapusButton = new JButton("Hapus Komentar");
hapusButton.setBackground(new Color(255, 105, 97));
hapusButton.setForeground(buttonTextColor);
hapusButton.setFont(customFont);


buttonPanel.add(editButton);
buttonPanel.add(hapusButton);
panel.add(buttonPanel, BorderLayout.SOUTH);

editButton.addActionListener(e -> {
    int row = table.getSelectedRow();
    if (row < 0) {
        JOptionPane.showMessageDialog(this, "Pilih komentar...", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int idKomen = (int) tableModel.getValueAt(row, 0);
    String isiLama = (String) tableModel.getValueAt(row, 1);

    JTextArea areaIsi = new JTextArea(isiLama, 5, 20);
    JPanel inputPanel = new JPanel(new GridLayout(0, 1, 5, 5));
    inputPanel.add(new JLabel("Edit Isi:"));
    inputPanel.add(new JScrollPane(areaIsi));

    int result = JOptionPane.showConfirmDialog(
        this, inputPanel, "Edit Komentar (ID: " + idKomen + ")",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
    );

    if (result == JOptionPane.OK_OPTION) {
        String isiBaru = areaIsi.getText();
        String sqlUpdate = "UPDATE komen SET isi = ? WHERE id_komen = ? AND id_user = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
            pstmt.setString(1, isiBaru);
            pstmt.setInt(2, idKomen);
            pstmt.setInt(3, this.userId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                tableModel.setValueAt(isiBaru, row, 1);
                JOptionPane.showMessageDialog(this, "Update berhasil.");
            } else {
                JOptionPane.showMessageDialog(this, "Update gagal.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});

// Hapus komentar
hapusButton.addActionListener(e -> {
    int row = table.getSelectedRow();
    if (row < 0) {
        JOptionPane.showMessageDialog(this, "Pilih komentar...", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int idKomen = (int) tableModel.getValueAt(row, 0);
    int result = JOptionPane.showConfirmDialog(
        this, "Yakin hapus ID: " + idKomen + "?",
        "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE
    );

    if (result == JOptionPane.YES_OPTION) {
        String sqlDelete = "DELETE FROM komen WHERE id_komen = ? AND id_user = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
            pstmt.setInt(1, idKomen);
            pstmt.setInt(2, this.userId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                tableModel.removeRow(row);
                JOptionPane.showMessageDialog(this, "Hapus berhasil.");
            } else {
                JOptionPane.showMessageDialog(this, "Hapus gagal.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});

return panel;
}

    private void setupTableStyle(JTable table, Color contentBg) {
  
    table.setBackground(Color.WHITE);
    table.setForeground(new Color(0, 0, 0));
    table.setFont(new Font("OCR A Extended", Font.PLAIN, 12));
    table.setRowHeight(25);

    table.setRowMargin(5);
    table.setIntercellSpacing(new Dimension(5, 5));

    table.setGridColor(TOMBOL);
    table.setShowGrid(true);
    table.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    JTableHeader header = table.getTableHeader();
    header.setBackground(new Color(255, 185, 215));
    header.setForeground(Color.WHITE);
    header.setFont(new Font("OCR A Extended", Font.BOLD, 12));
    header.setOpaque(false);

    table.setOpaque(false);
    table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.dispose();
            super.paintComponent(g);
        }
    });
}
    
    private void setupScrollPaneStyle(JScrollPane scrollPane, JTable table) {
         scrollPane.getViewport().setBackground(table.getBackground());
         scrollPane.setBorder(BorderFactory.createLineBorder(TOMBOL));
    }

    @Override
    public void showInfo() {
        System.out.println("UserPanel aktif - User ID: " + this.userId);
    }
}