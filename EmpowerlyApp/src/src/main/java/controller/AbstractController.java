package controller;

import java.sql.Connection;
import main.DatabaseConnection;

import java.sql.Connection;
import main.DatabaseConnection;

public abstract class AbstractController {
    protected Connection conn;

    public AbstractController() {
        
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    public abstract void showInfo();
}
