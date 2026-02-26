package View;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TelaPrincipal extends JFrame {
    public JPanel painelPrincipal;
    private JPanel jpTopo;
    private JMenuBar jmBar;
    public  JMenu jmCadastros;
    private JMenu jmRelatorios;
    private JMenu jmVendas;
    private JMenu jmSair;
    private JPanel jpNomeSist;
    private JLabel jlNomeSist;
    private JMenuItem jmiSair;
    private JMenuItem jmiClientes;
    private JMenuItem jmiVeiculos;
    private JMenuItem jmiPecas;
    private JMenuItem jmiClientesAtivos;
    private JMenuItem jmiRelatorioFinanceiro;
    private JMenuItem jmiServicosVendidos;
    private JMenuItem jmiPecasUtili;
    private JMenuItem jmiVendaService;
    private JLabel jlImagens;
    private JMenuItem jmiFuncionario;
    public JMenu jmFinanceiro;
    private JMenuItem jmiDespesas;
    private JMenuItem jmiUsuarios;
    private JMenuItem jmiVendaPecas;
    private JMenuItem jmiFornecedores;
    private JMenuBar jmBarSair;
    private JLabel jlLogoMarca;

    public TelaPrincipal() {

        setSize(1095,720);
        setUndecorated(true);
        setContentPane(painelPrincipal);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        jmiSair.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int resposta = JOptionPane.showConfirmDialog(null, "Voce Deseja Sair", "Sair do Sistema Meu Pet", JOptionPane.YES_NO_OPTION);

                if (resposta == JOptionPane.YES_NO_OPTION) {
                    System.exit(0);

                } else {

                    JOptionPane.showMessageDialog(null, "Continuar no Sistema");
                }
            }
        });


        jmiClientes.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Clientes();

            }
        });


        jmiVeiculos.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Veiculos();

            }
        });
        jmiPecas.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Pecas();

            }
        });


        jmiVendaService.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Vendas_Servicos();

            }
        });

        jmiVendaPecas.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Vendas_Pecas();

            }
        });



        jmiServicosVendidos.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Servicos_Realizados();

            }
        });

        jmiPecasUtili.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new RelaPecasUtili();

            }
        });

        jmiRelatorioFinanceiro.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Relat_Financeiro();

            }
        });

        jmiFuncionario.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Funcionarios();

            }
        });

        jmiDespesas.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Despesas();

            }
        });

        jmiFornecedores.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Fornecedores();

            }
        });
    }
}


