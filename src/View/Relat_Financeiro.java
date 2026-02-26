package View;

import DAO.FabricaConexao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Relat_Financeiro extends JFrame{
    private JPanel painelRelaFinan;
    private JPanel jpRelaFinan;
    private JLabel jlRelaFinan;
    private JPanel jpBotoes;
    private JButton jbutSair;
    private JPanel jpTabela;
    private JScrollPane jScrollPane;
    private JTable jtabRF;


    public Relat_Financeiro() {

        setSize(700, 500);
        setUndecorated(true);
        setContentPane(painelRelaFinan);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        carregarDados();

        jbutSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();
            }
        });

    }

    public void carregarDados() {

        try ( Connection conn = FabricaConexao.conectar()) {

            String query = "SELECT tipo_receita, data_Venda ,forma_Pagamento ,total_receita from Relatorio_Financeiro  ";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            Vector<String> colunas = new Vector<>();
            colunas.add("tipo_receita");
            colunas.add("data_Venda");
            colunas.add("forma_Pagamento");
            colunas.add("total_receita");


            Vector<Vector<Object>> dados = new Vector<>();
            while (rs.next()) {

                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString("tipo_receita"));
                linha.add(rs.getString("data_Venda"));
                linha.add(rs.getString("forma_Pagamento"));
                linha.add(rs.getString("total_receita"));
                dados.add(linha);

            }

            jtabRF.setModel(new DefaultTableModel(dados, colunas));
            jtabRF.setRowHeight(30);

        }catch (SQLException e){

            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"Erro ao carregar dados", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
