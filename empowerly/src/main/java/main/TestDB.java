/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Asus GK
 */
package main;

import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        if (conn != null) {
            System.out.println("Koneksi ke database berhasil!");
            System.out.println(System.getProperty("user.dir"));
        } else {
            System.out.println("Gagal konek ke database.");
        }
    }
}
