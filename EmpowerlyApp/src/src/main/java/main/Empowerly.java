package main;

import view.MainFrame; 
import javax.swing.SwingUtilities;

public class Empowerly {

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
       
    }
}