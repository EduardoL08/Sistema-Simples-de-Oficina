import View.Login;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {


        JFrame f = new JFrame("Sistema de Gestão - Oficina");

        f.setSize(220,200);
        f.setUndecorated(true);
        f.setContentPane(new Login().painelLogin);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);
/*
        LOGINS      SENHAS      FUNCÕES
        adm         adm         Administrador
        mec         mec         Mecanico


 */
    }
}