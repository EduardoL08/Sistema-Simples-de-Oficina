package View;

import Controller.LoginDAO;
import Model.MUsuarios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame{
    public JPanel painelLogin;
    private JLabel jlLogin;
    private JTextField jtxtLogin;
    private JLabel jlSenha;
    private JPasswordField jtxtSenha;
    private JComboBox jcomboxPerfil;
    private JButton jbutLogar;
    private JButton jbutSair;
    private JPanel jpTopo;
    private JPanel jpBotoes;


    public Login() {

        jbutSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);
            }
        });
        jbutLogar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String login = jtxtLogin.getText();
                String senha = new String(jtxtSenha.getPassword());
                String perfil = String.valueOf(jcomboxPerfil.getSelectedItem());

                LoginDAO loginDAO = new LoginDAO();

                MUsuarios resultado = loginDAO.validarLogin(login,senha,perfil);

                if (resultado != null) {
                    System.out.println("Login bem-sucedido! ");

                    JFrame f = (JFrame) SwingUtilities.getWindowAncestor(painelLogin);

                    f.dispose();

                    SwingUtilities.invokeLater(() -> {

                        TelaPrincipal telaPrincipal = new TelaPrincipal();

                        if (resultado.getPerfil().equals("Mecanico")) {

                            telaPrincipal.jmFinanceiro.setEnabled(false);
                            telaPrincipal.setVisible(true);

                        }else {

                            telaPrincipal.setVisible(true);
                        }

                    });



                }else {

                    JOptionPane.showMessageDialog(null, "Login Invalido ");

                    jtxtLogin.requestFocus();
                    jtxtLogin.setText("");
                    jtxtSenha.requestFocus();
                    jtxtSenha.setText("");

                }
            }
        });
    }
}



