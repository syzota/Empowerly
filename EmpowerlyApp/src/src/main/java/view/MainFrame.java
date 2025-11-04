package view;

import controller.IWelcomeAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.CardLayout;
import java.awt.Dimension;

public class MainFrame extends JFrame  implements IWelcomeAction{

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public static final String WELCOME_PANEL = "WELCOME";
    public static final String LOGIN_PANEL = "LOGIN";
    public static final String REGISTER_PANEL = "REGISTER";
    public static final String ADMIN_PANEL = "ADMIN";
    public static final String USER_PANEL = "USER";

    private AdminPanel adminPanel;
    private UserPanel userPanel;
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;

    private int currentUserId;
    private String currentUserRole;
    private String currentAdminStatus;

    public MainFrame() {
        setTitle("EMPOWERLY");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 720));
        setLocationRelativeTo(null);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        WelcomePanel welcomePanel = new WelcomePanel(this);
        loginPanel = new LoginPanel(this);
        registerPanel = new RegisterPanel(this);
        adminPanel = new AdminPanel(this) {};
        userPanel = new UserPanel(this);

        mainPanel.add(welcomePanel, WELCOME_PANEL);
        mainPanel.add(loginPanel, LOGIN_PANEL);
        mainPanel.add(registerPanel, REGISTER_PANEL);
        mainPanel.add(adminPanel, ADMIN_PANEL);
        mainPanel.add(userPanel, USER_PANEL);

        add(mainPanel);
        showPanel(WELCOME_PANEL);
    }

    public void showPanel(String panelName) {
        System.out.println("[DEBUG MainFrame] showPanel dipanggil untuk: " + panelName);

        if (LOGIN_PANEL.equals(panelName) && loginPanel != null) {
             System.out.println("[DEBUG MainFrame] Membersihkan field LoginPanel...");
            loginPanel.clearFields();
        } else if (REGISTER_PANEL.equals(panelName) && registerPanel != null) {
             System.out.println("[DEBUG MainFrame] Membersihkan field RegisterPanel...");
            registerPanel.clearFields();
        }

        if (cardLayout != null && mainPanel != null) {
            cardLayout.show(mainPanel, panelName);
            System.out.println("[DEBUG MainFrame] Panel '" + panelName + "' ditampilkan.");
        } else {
             System.err.println("[DEBUG MainFrame] Error: cardLayout atau mainPanel null di showPanel.");
        }
    }

    public void handleLoginSuccess(int id_user, String role, String adminStatus) {
    System.out.println("=============================================");
    System.out.println("[DEBUG MainFrame] handleLoginSuccess dipanggil!");

    String safeRole = (role != null) ? role.trim().toLowerCase() : "";
    String safeAdminStatus = (adminStatus != null) ? adminStatus : "";

    System.out.println("[DEBUG MainFrame] Diterima -> ID: " + id_user + ", Role: '" + safeRole + "', Status: '" + safeAdminStatus + "'");

    this.currentUserId = id_user;
    this.currentUserRole = safeRole;
    this.currentAdminStatus = safeAdminStatus;

    if (safeRole.isEmpty()) {
        System.out.println("[DEBUG MainFrame] ERROR: Role kosong atau null!");
        JOptionPane.showMessageDialog(this, "Role pengguna tidak terdefinisi.", "Login Error", JOptionPane.ERROR_MESSAGE);
        showPanel(LOGIN_PANEL);
        return;
    }

    switch (safeRole) {
        case "admin":
            System.out.println("[DEBUG MainFrame] Routing ke AdminPanel...");
            try {
                if (adminPanel == null) {
                    adminPanel = new AdminPanel(this);
                    mainPanel.add(adminPanel, ADMIN_PANEL);
                }
                adminPanel.loadData(currentUserId, currentAdminStatus);
                showPanel(ADMIN_PANEL);
                System.out.println("[DEBUG MainFrame] showPanel(ADMIN_PANEL) dipanggil.");
            } catch (Exception e) {
                System.err.println("[DEBUG MainFrame] ERROR saat load/show AdminPanel:");
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Gagal memuat panel admin: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                showPanel(LOGIN_PANEL);
            }
            break;

        case "user":
            System.out.println("[DEBUG MainFrame] Routing ke UserPanel...");
            try {
                if (userPanel == null) {
                    userPanel = new UserPanel(this);
                    mainPanel.add(userPanel, USER_PANEL);
                }
                userPanel.loadData(currentUserId);
                showPanel(USER_PANEL);
                System.out.println("[DEBUG MainFrame] showPanel(USER_PANEL) dipanggil.");
            } catch (Exception e) {
                System.err.println("[DEBUG MainFrame] ERROR saat load/show UserPanel:");
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Gagal memuat panel pengguna: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                showPanel(LOGIN_PANEL);
            }
            break;

        default:
            System.out.println("[DEBUG MainFrame] Role tidak dikenal: '" + safeRole + "'");
            JOptionPane.showMessageDialog(this,
                    "Role pengguna '" + safeRole + "' tidak valid.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
            showPanel(LOGIN_PANEL);
            break;
    }

    System.out.println("=============================================");
}

    public void handleLogout() {
        this.currentUserId = 0; this.currentUserRole = null; 
        this.currentAdminStatus = null;
        showPanel(WELCOME_PANEL);
     }
    
    @Override
public void onLoginClicked() {
    showPanel(LOGIN_PANEL);
}

@Override
public void onRegisterClicked() {
    showPanel(REGISTER_PANEL);
}

@Override
public void onExitClicked() {
    System.exit(0);
}

}