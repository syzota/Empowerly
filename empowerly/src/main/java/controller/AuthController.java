package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.DatabaseConnection;

public class AuthController extends AbstractController {

    @Override
    public void showInfo() {
        System.out.println("AuthController aktif — koneksi: " + (conn != null));
    }

    public Object[] validateLogin(String username, String password) {
        String sqlUser = "SELECT id_user FROM user WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUser)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int idUser = rs.getInt("id_user");

                String sqlAdminCheck = "SELECT status FROM admin WHERE id_user = ?";
                try (PreparedStatement pstmtAdmin = conn.prepareStatement(sqlAdminCheck)) {
                    pstmtAdmin.setInt(1, idUser);
                    ResultSet rsAdmin = pstmtAdmin.executeQuery();

                    if (rsAdmin.next()) {

                        String status = rsAdmin.getString("status");
                        System.out.println("[DEBUG AuthController] Login berhasil sebagai ADMIN (" + status + ")");
                        return new Object[]{idUser, "admin", status};
                    }
                }

                System.out.println("[DEBUG AuthController] Login berhasil sebagai USER");
                return new Object[]{idUser, "user", null};
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("[DEBUG AuthController] Login gagal – akun tidak ditemukan.");
        return null;
    }

    public boolean registerUser(String username, String password, int umur) {
        String sqlCheck = "SELECT id_user FROM user WHERE username = ?";
        try (PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck)) {
            pstmtCheck.setString(1, username);
            ResultSet rsCheck = pstmtCheck.executeQuery();
            if (rsCheck.next()) {
                return false; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        try {

            String sqlUser = "INSERT INTO user (username, password) VALUES (?, ?)";
            PreparedStatement pstmtUser = conn.prepareStatement(sqlUser, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmtUser.setString(1, username);
            pstmtUser.setString(2, password);
            pstmtUser.executeUpdate();

            ResultSet generatedKeys = pstmtUser.getGeneratedKeys();
            int idUser = 0;
            if (generatedKeys.next()) {
                idUser = generatedKeys.getInt(1);
            }

            String sqlMember = "INSERT INTO member (id_user, umur) VALUES (?, ?)";
            PreparedStatement pstmtMember = conn.prepareStatement(sqlMember);
            pstmtMember.setInt(1, idUser);
            pstmtMember.setInt(2, umur);
            pstmtMember.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
