package view;

import main.DatabaseConnection;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class AdminPanel extends BasePanel {

    private MainFrame mainFrame;
    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private CardLayout contentCardLayout;
    private List<JButton> sidebarButtons = new ArrayList<>();
    private int adminId;
    private String adminStatus;
  
    private final Color WARNA_BACKGROUND_UTAMA = new Color(255, 255, 255, 0);
    private final Color WARNA_PANEL_CONTENT = new Color(255, 255, 255, 100);
    private final Color WARNA_TOMBOL_PINK = Color.decode("#DF1C89");
    private final Color WARNA_TOMBOL_BIRU = new Color(86, 193, 229);
    
    private final Color buttonTextColor = Color.WHITE;
    private final Color buttonTxtColor = Color.BLACK;
    
    private static final String MATERI = "MATERI";
    private static final String FORUM = "FORUM";
    private static final String KOMENTAR = "KOMENTAR";
    
    private DefaultTableModel materiTableModel;
    private JTable materiTable;
    private JTextField txtMateriJudul, txtMateriTipe, txtMateriKonten, txtMateriIdKategori;
    private JButton btnMateriSimpan, btnMateriEdit, btnMateriHapus;
    private JButton btnMateriTambah;
    
    private int selectedMateriId = -1;
    private Image sidebarBg;
    private Image logoImage;
    final Color baseColor = new Color(241, 184, 63);

    public AdminPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
    }

    public void loadData(int adminId, String adminStatus) {
        this.adminId = adminId;
        this.adminStatus = (adminStatus != null) ? adminStatus : "";
        removeAll(); sidebarButtons.clear();
        materiTableModel = null;
        materiTable = null;
        txtMateriJudul = null;
        createSidebar(); createContentArea();
        revalidate(); repaint();
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
        ImageIcon originalIcon = new ImageIcon("logo.png"); // ganti sesuai nama file logomu
        Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel logoLabel = new JLabel(scaledIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebarPanel.add(logoLabel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20))); // jarak bawah logo
    } catch (Exception e) {
        System.out.println("[DEBUG] Logo gagal dimuat: " + e.getMessage());
    }

    sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20))); 

    // Tombol menu (Materi / Forum tergantung status)
    if ("Komunitas".equalsIgnoreCase(this.adminStatus)) {
        addSidebarButton("Forum", FORUM);
        addSidebarButton("Komentar", KOMENTAR);
    } else {
        addSidebarButton("Materi", MATERI);
    }

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

            Color topColor = new Color(255, 230, 90);
            Color bottomColor = new Color(241, 184, 63);
            GradientPaint gp = new GradientPaint(0, 0, topColor, 0, getHeight(), bottomColor);
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

            g2.setColor(new Color(255, 255, 255, 90));
            g2.fillRoundRect(0, 0, getWidth(), getHeight() / 2, 20, 20);

            g2.setColor(new Color(0, 0, 0, 25));
            g2.fillRoundRect(3, 3, getWidth() - 6, getHeight() - 6, 20, 20);

            g2.setColor(new Color(255, 255, 255, 200));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 20, 20);

            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = (getHeight() + fm.getAscent()) / 2 - 4;
            g2.setColor(Color.BLACK);
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
            button.setBackground(new Color(255, 240, 110));
            button.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setBackground(baseColor);
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
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10))); // jarak antar tombol
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
    
    // PANELLL KONTENN

    private void createContentArea() {
    contentCardLayout = new CardLayout();

    contentPanel = new JPanel(contentCardLayout);
    contentPanel.setOpaque(false); // biar transparan, biar background keliatan

    if (materiTable != null) materiTable = null;
    if (materiTableModel != null) materiTableModel = null;
    txtMateriJudul = null; 
    txtMateriTipe = null; 
    txtMateriKonten = null; 
    txtMateriIdKategori = null;
    btnMateriSimpan = null; 
    btnMateriEdit = null; 
    btnMateriHapus = null; 
    btnMateriTambah = null;

    if ("Komunitas".equalsIgnoreCase(this.adminStatus)) {
        JPanel forumPanel = createForumCrudPanel();
        JPanel komentarPanel = createKomentarCrudPanel();
        if (forumPanel != null) contentPanel.add(forumPanel, FORUM);
        if (komentarPanel != null) contentPanel.add(komentarPanel, KOMENTAR);
    } else {
        JPanel materiPanel = createMateriCrudPanel();
        if (materiPanel != null) contentPanel.add(materiPanel, MATERI);
    }

    // === PANEL TRANSPARAN OVERLAY (80% opacity) ===
    JPanel overlayPanel = new JPanel(new BorderLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            g2.setColor(new Color(255, 255, 255)); // warna putih agak transparan
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

    if (!sidebarButtons.isEmpty()) {
        String defaultPanel = "Komunitas".equalsIgnoreCase(this.adminStatus) ? FORUM : MATERI;
        if (contentPanel.getComponentCount() > 0) {
            contentCardLayout.show(contentPanel, defaultPanel);
            setActiveButton(sidebarButtons.get(0));
        }
    }
}


    private JPanel createMateriCrudPanel() {
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
            }
        };
        titleLabel.setOpaque(false);
        titleLabel.setPreferredSize(new Dimension(220, 60));
        
        topPanel.add(titleLabel, BorderLayout.WEST);
        btnMateriTambah = new JButton("Tambah");
        btnMateriTambah.setBackground(WARNA_TOMBOL_PINK); btnMateriTambah.setForeground(buttonTextColor);
        btnMateriTambah.setFont(new Font("OCR A Extended", Font.BOLD, 12)); btnMateriTambah.setFocusPainted(false);
        btnMateriTambah.addActionListener(e -> clearMateriForm());
        
        JPanel tambahButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        tambahButtonPanel.setOpaque(false);
        tambahButtonPanel.add(btnMateriTambah);
        topPanel.add(tambahButtonPanel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        JPanel contentSplitPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        contentSplitPanel.setOpaque(false);
        JPanel tableComponent = createMateriTablePanel();
        JPanel formComponent = createMateriFormPanel();
        if (formComponent != null) contentSplitPanel.add(formComponent);
        if (tableComponent != null) contentSplitPanel.add(tableComponent);
        mainPanel.add(contentSplitPanel, BorderLayout.CENTER);
         if (materiTable != null) {
            materiTable.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting() && materiTable.getSelectedRow() != -1) {
                    handleTableSelectionChange();
                }
            });
         } else {
             System.err.println("[DEBUG AdminPanel] materiTable null saat menambahkan listener di createMateriCrudPanel.");
         }
        loadMateriTableData();
        return mainPanel;
    }

    private JPanel createMateriFormPanel() {
        JPanel formPanel = new JPanel();
        GroupLayout layout = new GroupLayout(formPanel);
        formPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        formPanel.setBackground(WARNA_PANEL_CONTENT);
        
        JLabel lblJudul = new JLabel("Judul Materi:");
        txtMateriJudul = new JTextField(20);
        JLabel lblTipe = new JLabel("Tipe:");
        txtMateriTipe = new JTextField();
        JLabel lblKonten = new JLabel("Konten:");
        txtMateriKonten = new JTextField();
        txtMateriIdKategori = new JTextField();
        btnMateriSimpan = new JButton("Simpan");
        
        btnMateriSimpan.setBackground(WARNA_TOMBOL_PINK); btnMateriSimpan.setForeground(buttonTextColor);
        btnMateriSimpan.setFont(new Font("OCR A Extended", Font.BOLD, 12)); btnMateriSimpan.setFocusPainted(false);
        btnMateriSimpan.addActionListener(this::handleMateriSimpan);
        
        btnMateriEdit = new JButton("Edit");
        btnMateriEdit.setBackground(WARNA_TOMBOL_PINK); btnMateriEdit.setForeground(buttonTextColor);
        btnMateriEdit.setFont(new Font("OCR A Extended", Font.BOLD, 12)); btnMateriEdit.setFocusPainted(false);
        btnMateriEdit.addActionListener(this::handleMateriEdit);
        btnMateriEdit.setEnabled(false);
        
        btnMateriHapus = new JButton("Hapus");
        btnMateriHapus.setBackground(WARNA_TOMBOL_PINK); btnMateriHapus.setForeground(buttonTextColor);
        btnMateriHapus.setFont(new Font("OCR A Extended", Font.BOLD, 12)); btnMateriHapus.setFocusPainted(false);
        btnMateriHapus.addActionListener(this::handleMateriHapus);
        btnMateriHapus.setEnabled(false);
        
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblJudul).addComponent(txtMateriJudul).addComponent(lblTipe).addComponent(txtMateriTipe)
                .addComponent(lblKonten).addComponent(txtMateriKonten)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(btnMateriSimpan, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMateriEdit, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMateriHapus, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(lblJudul).addComponent(txtMateriJudul, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(10)
                .addComponent(lblTipe).addComponent(txtMateriTipe, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(10)
                .addComponent(lblKonten).addComponent(txtMateriKonten, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addGap(20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btnMateriSimpan, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMateriEdit, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMateriHapus, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        return formPanel;
     }

    private JPanel createMateriTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout(10, 10));
        tablePanel.setBackground(WARNA_PANEL_CONTENT);
        
        String[] columnNames = {"ID", "Judul", "Tipe", "Kategori", "Konten", "ID_Kategori_Hidden"};
        materiTableModel = new DefaultTableModel(columnNames, 0) {
             @Override public boolean isCellEditable(int r, int c){ return false; }
        };
        materiTable = new JTable(materiTableModel);
        setupTableStyle(materiTable, WARNA_BACKGROUND_UTAMA);
        materiTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        materiTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        materiTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        materiTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        materiTable.getColumnModel().getColumn(4).setPreferredWidth(250);
        hideTableColumn(materiTable, 5);
        JScrollPane scrollPane = new JScrollPane(materiTable);
        setupScrollPaneStyle(scrollPane, materiTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        return tablePanel;
    }

    private void hideTableColumn(JTable table, int columnIndex) {
        if (table != null && table.getColumnModel().getColumnCount() > columnIndex) {
            table.getColumnModel().getColumn(columnIndex).setMinWidth(0);
            table.getColumnModel().getColumn(columnIndex).setMaxWidth(0);
            table.getColumnModel().getColumn(columnIndex).setWidth(0);
            table.getColumnModel().getColumn(columnIndex).setPreferredWidth(0);
        }
    }

    private void loadMateriTableData() {
    if (materiTableModel == null) {
        System.err.println("materiTableModel null di loadData");
        return;
    }

    materiTableModel.setRowCount(0);
    Connection conn = DatabaseConnection.getInstance().getConnection();
    String sql = "SELECT m.id_materi, m.judul, m.tipe, k.nama_kategori, m.konten, m.id_kategori " +
                 "FROM materi m JOIN kategori k ON m.id_kategori = k.id_kategori " +
                 "WHERE k.id_user = ?";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, this.adminId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            materiTableModel.addRow(new Object[]{
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getInt(6)
            });
        }

        System.out.println("[DEBUG AdminPanel] Data materi dimuat. Baris: " + materiTableModel.getRowCount());
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal memuat data.", "DB Error", JOptionPane.ERROR_MESSAGE);
    } catch (NullPointerException npe) {
        System.err.println("[DEBUG AdminPanel] NPE loadMateriTableData.");
        npe.printStackTrace();
    }
}


 private void handleMateriSimpan(ActionEvent e) {
    try {
        String judul = txtMateriJudul.getText().trim();
        String tipe = txtMateriTipe.getText().trim();
        String konten = txtMateriKonten.getText().trim();

        int idKategori;

        if (selectedMateriId > 0 && materiTable.getSelectedRow() >= 0) {
            idKategori = Integer.parseInt(txtMateriIdKategori.getText().trim());
        } else {
            String idKategoriStr = JOptionPane.showInputDialog(this, "Masukkan ID Kategori (milik Anda):");
            if (idKategoriStr == null || idKategoriStr.trim().isEmpty())
                throw new Exception("ID Kategori harus diisi.");
            try {
                idKategori = Integer.parseInt(idKategoriStr.trim());
            } catch (NumberFormatException nfe) {
                throw new Exception("ID Kategori harus angka.");
            }
        }

        if (judul.isEmpty() || tipe.isEmpty() || konten.isEmpty())
            throw new Exception("Judul, Tipe, Konten harus diisi");

        if (!cekKepemilikanKategori(idKategori))
            throw new Exception("ID Kategori " + idKategori + " bukan milik Anda.");

        Connection conn = DatabaseConnection.getInstance().getConnection();

        if (selectedMateriId == -1) {
            String sqlInsert = "INSERT INTO materi (judul, konten, tipe, id_kategori) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, judul);
                pstmt.setString(2, konten);
                pstmt.setString(3, tipe);
                pstmt.setInt(4, idKategori);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    loadMateriTableData();
                    JOptionPane.showMessageDialog(this, "Materi baru ditambahkan!");
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal insert.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        } else {
            String sqlUpdate = "UPDATE materi SET judul = ?, tipe = ?, id_kategori = ?, konten = ? WHERE id_materi = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
                pstmt.setString(1, judul);
                pstmt.setString(2, tipe);
                pstmt.setInt(3, idKategori);
                pstmt.setString(4, konten);
                pstmt.setInt(5, selectedMateriId);
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    loadMateriTableData();
                    JOptionPane.showMessageDialog(this, "Materi diperbarui!");
                } else {
                    JOptionPane.showMessageDialog(this, "Update gagal.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        }

        clearMateriForm();

    } catch (NumberFormatException nfe) {
        JOptionPane.showMessageDialog(this, "Error: ID Kategori harus angka.", "Input Error", JOptionPane.ERROR_MESSAGE);
        nfe.printStackTrace();
    } catch (SQLException sqlEx) {
        JOptionPane.showMessageDialog(this, "Error DB: " + sqlEx.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        sqlEx.printStackTrace();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
}

    private void handleMateriEdit(ActionEvent e) {
        loadSelectedMateriToForm();
    }

    private void handleTableSelectionChange() {
        if (materiTable == null || materiTable.getSelectedRow() < 0) {
             if (btnMateriEdit != null) btnMateriEdit.setEnabled(false);
             if (btnMateriHapus != null) btnMateriHapus.setEnabled(false);
            return;
        }
         if (btnMateriEdit != null) btnMateriEdit.setEnabled(true);
         if (btnMateriHapus != null) btnMateriHapus.setEnabled(true);
    }

    private void loadSelectedMateriToForm() {
        if (materiTable == null || materiTableModel == null) { System.err.println("Tabel null di loadSelectedMateriToForm"); return; }
        int row = materiTable.getSelectedRow();
        if (row < 0) { return; }
        if (txtMateriJudul == null || txtMateriTipe == null || txtMateriKonten == null || txtMateriIdKategori == null) { System.err.println("Field form null di loadSelectedMateriToForm"); return; }
        try {
            int idMateri = (int) materiTableModel.getValueAt(row, 0);
            String judul = (String) materiTableModel.getValueAt(row, 1);
            String tipe = (String) materiTableModel.getValueAt(row, 2);
            String konten = (String) materiTableModel.getValueAt(row, 4);
            int idKategori = (int) materiTableModel.getValueAt(row, 5);
            txtMateriJudul.setText(judul);
            txtMateriTipe.setText(tipe);
            txtMateriKonten.setText(konten);
            txtMateriIdKategori.setText(String.valueOf(idKategori));
            this.selectedMateriId = idMateri;
            System.out.println("[DEBUG AdminPanel] Data dimuat ke form untuk ID: " + idMateri);
            if (btnMateriEdit != null) btnMateriEdit.setEnabled(true);
            if (btnMateriHapus != null) btnMateriHapus.setEnabled(true);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
             System.err.println("[DEBUG AdminPanel] Error indeks tabel saat loadSelectedMateriToForm. Row: " + row);
             aioobe.printStackTrace();
        } catch (NullPointerException npe) {
             System.err.println("[DEBUG AdminPanel] NPE saat loadSelectedMateriToForm. Mungkin komponen belum siap?");
             npe.printStackTrace();
        } catch (Exception ex) {
            System.err.println("[DEBUG AdminPanel] Error lain saat loadSelectedMateriToForm.");
            ex.printStackTrace();
        }
    }

    private void handleMateriHapus(ActionEvent e) {
    if (materiTable == null || materiTableModel == null) return;

    int row = materiTable.getSelectedRow();
    if (row < 0) {
        JOptionPane.showMessageDialog(this, "Pilih materi...", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int idMateri = (int) materiTableModel.getValueAt(row, 0);
    int result = JOptionPane.showConfirmDialog(this, "Yakin hapus ID: " + idMateri + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

    if (result == JOptionPane.YES_OPTION) {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String sqlDelete = "DELETE FROM materi WHERE id_materi = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
            pstmt.setInt(1, idMateri);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                if (row < materiTableModel.getRowCount())
                    materiTableModel.removeRow(row);
                else
                    loadMateriTableData();

                JOptionPane.showMessageDialog(this, "Materi dihapus.");
                clearMateriForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal hapus.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal hapus: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private void clearMateriForm() {
    if (txtMateriJudul == null || txtMateriTipe == null || txtMateriKonten == null ||
        txtMateriIdKategori == null || materiTable == null ||
        btnMateriEdit == null || btnMateriHapus == null) return;

    txtMateriJudul.setText("");
    txtMateriTipe.setText("tautan");
    txtMateriKonten.setText("https://");
    txtMateriIdKategori.setText("");

    materiTable.clearSelection();
    selectedMateriId = -1;

    btnMateriEdit.setEnabled(false);
    btnMateriHapus.setEnabled(false);

    System.out.println("[DEBUG AdminPanel] Form dibersihkan, Mode Tambah.");
}

private boolean cekKepemilikanKategori(int idKategori) {
    Connection conn = DatabaseConnection.getInstance().getConnection();
    String sql = "SELECT COUNT(*) FROM kategori WHERE id_kategori = ? AND id_user = ?";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, idKategori);
        pstmt.setInt(2, this.adminId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) return rs.getInt(1) > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}

    private JPanel createForumCrudPanel() {
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

        String[] columnNames = {"ID", "Judul", "Deskripsi"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT id_forum, judul, deskripsi FROM forum WHERE id_user = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.adminId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3)
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

        JButton tambahButton = new JButton("Tambah Forum");
        tambahButton.setBackground(WARNA_TOMBOL_BIRU);
        tambahButton.setForeground(Color.WHITE);

        JButton editButton = new JButton("Edit Forum");
        editButton.setBackground(WARNA_TOMBOL_PINK);
        editButton.setForeground(buttonTextColor);

        JButton hapusButton = new JButton("Hapus Forum");
        hapusButton.setBackground(WARNA_TOMBOL_PINK);
        hapusButton.setForeground(buttonTextColor);

        buttonPanel.add(tambahButton);
        buttonPanel.add(editButton);
        buttonPanel.add(hapusButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(30);   
        table.getColumnModel().getColumn(1).setPreferredWidth(400);  
        table.getColumnModel().getColumn(2).setPreferredWidth(400);  

        tambahButton.addActionListener(e -> {
            JTextField fieldJudul = new JTextField();
            JTextArea areaDeskripsi = new JTextArea(5, 20);

            JPanel finalInputPanel = new JPanel(new GridLayout(0, 1, 5, 5));
            finalInputPanel.add(new JLabel("Judul:"));
            finalInputPanel.add(fieldJudul);
            finalInputPanel.add(new JLabel("Deskripsi:"));
            finalInputPanel.add(new JScrollPane(areaDeskripsi));

            int result = JOptionPane.showConfirmDialog(
                this,
                finalInputPanel,
                "Tambah Forum",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                String judul = fieldJudul.getText();
                String deskripsi = areaDeskripsi.getText();

                if (judul.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Judul kosong.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String sqlInsert = "INSERT INTO forum (judul, deskripsi, id_user) VALUES (?, ?, ?)";

                try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setString(1, judul);
                    pstmt.setString(2, deskripsi);
                    pstmt.setInt(3, this.adminId);
                    pstmt.executeUpdate();

                    ResultSet rsKey = pstmt.getGeneratedKeys();
                    if (rsKey.next()) {
                        tableModel.addRow(new Object[]{
                            rsKey.getInt(1),
                            judul,
                            deskripsi
                        });
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });


        editButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Pilih forum...", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int idForum = (int) tableModel.getValueAt(row, 0);
            String judulLama = (String) tableModel.getValueAt(row, 1);
            String deskripsiLama = (String) tableModel.getValueAt(row, 2);

            JTextField fieldJudul = new JTextField(judulLama);
            JTextArea areaDeskripsi = new JTextArea(deskripsiLama, 5, 20);

            JPanel finalInputPanel = new JPanel(new GridLayout(0, 1, 5, 5));
            finalInputPanel.add(new JLabel("Judul:"));
            finalInputPanel.add(fieldJudul);
            finalInputPanel.add(new JLabel("Deskripsi:"));
            finalInputPanel.add(new JScrollPane(areaDeskripsi));

            int result = JOptionPane.showConfirmDialog(
                this,
                finalInputPanel,
                "Edit Forum (ID: " + idForum + ")",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                String judulBaru = fieldJudul.getText();
                String deskripsiBaru = areaDeskripsi.getText();

                String sqlUpdate = "UPDATE forum SET judul = ?, deskripsi = ? WHERE id_forum = ?";

                try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
                    pstmt.setString(1, judulBaru);
                    pstmt.setString(2, deskripsiBaru);
                    pstmt.setInt(3, idForum);
                    pstmt.executeUpdate();

                    tableModel.setValueAt(judulBaru, row, 1);
                    tableModel.setValueAt(deskripsiBaru, row, 2);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

 
        hapusButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Pilih forum...", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int idForum = (int) tableModel.getValueAt(row, 0);

            int result = JOptionPane.showConfirmDialog(
                this,
                "Yakin hapus ID: " + idForum + "?\n(KOMENTAR ikut terhapus!)",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                String sqlDeleteKomen = "DELETE FROM komen WHERE id_forum = ?";
                String sqlDeleteForum = "DELETE FROM forum WHERE id_forum = ?";

                try (Connection txConn = DatabaseConnection.getInstance().getConnection()) {
                    txConn.setAutoCommit(false);

                    try (PreparedStatement pstmtKomen = txConn.prepareStatement(sqlDeleteKomen)) {
                        pstmtKomen.setInt(1, idForum);
                        pstmtKomen.executeUpdate();
                    }

                    try (PreparedStatement pstmtForum = txConn.prepareStatement(sqlDeleteForum)) {
                        pstmtForum.setInt(1, idForum);
                        pstmtForum.executeUpdate();
                    }

                    txConn.commit();
                    tableModel.removeRow(row);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        return panel;

        }

        // ============================================================

        private JPanel createKomentarCrudPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(WARNA_BACKGROUND_UTAMA);

        // --- Title ---
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

        String[] columnNames = {"ID", "Isi", "ID Forum", "User"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT k.id_komen, k.isi, k.id_forum, k.id_user " +
                     "FROM komen k JOIN forum f ON k.id_forum = f.id_forum " +
                     "WHERE f.id_user = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.adminId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getInt(4)
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setupTableStyle(table, WARNA_BACKGROUND_UTAMA);
        JScrollPane scrollPane = new JScrollPane(table);
        setupScrollPaneStyle(scrollPane, table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(60);   
        table.getColumnModel().getColumn(1).setPreferredWidth(1400);  
        table.getColumnModel().getColumn(2).setPreferredWidth(60);  
        table.getColumnModel().getColumn(3).setPreferredWidth(60);   
        
        table.getTableHeader().setResizingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(WARNA_BACKGROUND_UTAMA);

        JButton hapusButton = new JButton("Hapus Komentar");
        hapusButton.setBackground(WARNA_TOMBOL_PINK);
        hapusButton.setForeground(buttonTextColor);

        buttonPanel.add(hapusButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) { // double-click
                    int row = table.getSelectedRow();

                    int idKomen = (int) tableModel.getValueAt(row, 0);
                    String isi = String.valueOf(tableModel.getValueAt(row, 1));
                    int idForum = (int) tableModel.getValueAt(row, 2);
                    int user = (int) tableModel.getValueAt(row, 3);

                    JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(panel), 
                                                 "Detail Komentar " + idKomen, true);
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

                    JLabel idLabel = new JLabel("ID Komentar: " + idKomen);
                    idLabel.setFont(new Font("OCR A Extended", Font.BOLD, 16));
                    idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    contentPanel.add(idLabel);
                    contentPanel.add(Box.createRigidArea(new Dimension(0, 8)));

                    JLabel isiLabel = new JLabel("<html><b>Isi:</b><br>" + isi + "</html>");
                    isiLabel.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
                    isiLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    contentPanel.add(isiLabel);
                    contentPanel.add(Box.createRigidArea(new Dimension(0, 8)));

                    JLabel forumLabel = new JLabel("ID Forum: " + idForum);
                    forumLabel.setFont(new Font("OCR A Extended", Font.PLAIN, 14));
                    forumLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    contentPanel.add(forumLabel);
                    contentPanel.add(Box.createRigidArea(new Dimension(0, 8)));

                    JLabel userLabel = new JLabel("User ID: " + user);
                    userLabel.setFont(new Font("OCR A Extended", Font.BOLD, 14));
                    userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    contentPanel.add(userLabel);
                    contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                    dialog.add(contentPanel, BorderLayout.CENTER);

                    // --- Tombol Tutup ---
                    JButton closeButton = new JButton("Tutup");
                    closeButton.setBackground(WARNA_TOMBOL_PINK);
                    closeButton.setForeground(Color.WHITE);
                    closeButton.setFont(new Font("OCR A Extended", Font.BOLD, 14));
                    closeButton.addActionListener(ev -> dialog.dispose());

                    JPanel bottomPanel = new JPanel();
                    bottomPanel.setOpaque(false);
                    bottomPanel.add(closeButton);
                    dialog.add(bottomPanel, BorderLayout.SOUTH);

                    dialog.pack();
                    dialog.setSize(400, 550);
                    dialog.setLocationRelativeTo(panel);
                    dialog.setVisible(true);
                }
            }
        });


        hapusButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Pilih komentar...", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int idKomen = (int) tableModel.getValueAt(row, 0);

            int result = JOptionPane.showConfirmDialog(
                this,
                "Yakin hapus ID: " + idKomen + "?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                String sqlDelete = "DELETE FROM komen WHERE id_komen = ?";

                try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
                    pstmt.setInt(1, idKomen);
                    int affectedRows = pstmt.executeUpdate();

                    if (affectedRows > 0) {
                        if (row < tableModel.getRowCount()) {
                            tableModel.removeRow(row);
                        }
                        JOptionPane.showMessageDialog(this, "Hapus berhasil.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Hapus gagal.", "Warning", JOptionPane.WARNING_MESSAGE);
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

    table.setGridColor(WARNA_TOMBOL_PINK);
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
         scrollPane.setBorder(BorderFactory.createLineBorder(WARNA_TOMBOL_PINK));
    }

    @Override
    public void showInfo() {
        System.out.println("AdminPanel aktif - Admin ID: " + this.adminId + ", Status: " + this.adminStatus);
    }
}