package view;

import controller.IWelcomeAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.CardLayout;
import java.awt.Dimension;

public class MainFrame extends JFrame implements IWelcomeAction {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public static final String WELCOME_PANEL = "WELCOME";
    public static final String LOGIN_PANEL = "LOGIN";
    public static final String REGISTER_PANEL = "REGISTER";
    public static final String ADMIN_PANEL = "ADMIN";
    public static final String USER_PANEL = "USER";
    public static final String ABOUT_PANEL = "ABOUT"; 

    private AdminPanel adminPanel;
    private UserPanel userPanel;
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;
    private AboutPanel aboutPanel; // 

    private int currentUserId;
    private String currentUserRole;
    private String currentAdminStatus;

    public MainFrame() {
        setTitle("Empowerly!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 720));
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Inisialisasi semua panel
        WelcomePanel welcomePanel = new WelcomePanel(this);
        loginPanel = new LoginPanel(this);
        registerPanel = new RegisterPanel(this);
        adminPanel = new AdminPanel(this) {};
        userPanel = new UserPanel(this);
        aboutPanel = new AboutPanel(this); // 🆕 Tambah panel about

        // Daftarin ke card layout
        mainPanel.add(welcomePanel, WELCOME_PANEL);
        mainPanel.add(loginPanel, LOGIN_PANEL);
        mainPanel.add(registerPanel, REGISTER_PANEL);
        mainPanel.add(adminPanel, ADMIN_PANEL);
        mainPanel.add(userPanel, USER_PANEL);
        mainPanel.add(aboutPanel, ABOUT_PANEL); // 🆕

        add(mainPanel);
        showPanel(ABOUT_PANEL);
    }

    public void showPanel(String panelName) {
        System.out.println("[DEBUG MainFrame] showPanel dipanggil untuk: " + panelName);

        if (LOGIN_PANEL.equals(panelName) && loginPanel != null) {
            loginPanel.clearFields();
        } else if (REGISTER_PANEL.equals(panelName) && registerPanel != null) {
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
        this.currentUserId = id_user;
        this.currentUserRole = (role != null) ? role.trim().toLowerCase() : "";
        this.currentAdminStatus = (adminStatus != null) ? adminStatus : "";

        switch (currentUserRole) {
            case "admin":
                if (adminPanel == null) {
                    adminPanel = new AdminPanel(this);
                    mainPanel.add(adminPanel, ADMIN_PANEL);
                }
                adminPanel.loadData(currentUserId, currentAdminStatus);
                showPanel(ADMIN_PANEL);
                break;

            case "user":
                if (userPanel == null) {
                    userPanel = new UserPanel(this);
                    mainPanel.add(userPanel, USER_PANEL);
                }
                userPanel.loadData(currentUserId);
                showPanel(USER_PANEL);
                break;

            default:
                JOptionPane.showMessageDialog(this,
                        "Role pengguna '" + currentUserRole + "' tidak valid.",
                        "Login Error", JOptionPane.ERROR_MESSAGE);
                showPanel(LOGIN_PANEL);
                break;
        }
    }

    public void handleLogout() {
        this.currentUserId = 0;
        this.currentUserRole = null;
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
