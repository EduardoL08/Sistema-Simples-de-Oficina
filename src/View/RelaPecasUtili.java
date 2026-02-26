package View;

import DAO.FabricaConexao;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class RelaPecasUtili extends JFrame {
    public JPanel painelRelaPecasUtili;
    private JTextField txtNome1;
    private JTextField txtQtdUsa1;
    private JTextField txtOsUtili1;
    private JTextField txtData1;
    private JTextField txtPrecaUni1;
    private JTextField txtNome2;
    private JTextField txtQtdUsa2;
    private JTextField txtOsUtili2;
    private JTextField txtData2;
    private JTextField txtPrecaUni2;
    private JTextField txtTotalGas2;
    private JTextField txtNome3;
    private JTextField txtQtdUsa3;
    private JTextField txtOsUtili3;
    private JTextField txtData3;
    private JTextField txtPrecaUni3;
    private JTextField txtTotalGas3;
    private JButton jbutSair;
    private JButton jbutPDF;
    private JButton jbutEditar;
    private JLabel jlRelaPecasPend;
    private JTextField txtTotalGas1;
    private JPanel jpRPV;
    private JPanel jpTabela;
    private JTable jtabRPV;
    private JScrollPane jScrollPane;
    private JPanel jpBotoes;

    public RelaPecasUtili() {

        setSize(1095, 300);
        setUndecorated(true);
        setContentPane(painelRelaPecasUtili);
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

            String query = "SELECT Codigo_Venda, Nome_Cliente ,Nome_Vendedor ,Qtd_Pecas ,Pecas_Vendidas ," +
                    "Data_Venda ,valor_Total, forma_Pagamento, desconto from Relatorio_Pecas_Vendidas  ";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            Vector<String> colunas = new Vector<>();
            colunas.add("Codigo_Venda");
            colunas.add("Nome_Cliente");
            colunas.add("Nome_Vendedor");
            colunas.add("Qtd_Pecas");
            colunas.add("Pecas_Vendidas");
            colunas.add("Data_Venda");
            colunas.add("valor_Total");
            colunas.add("forma_Pagamento");
            colunas.add("desconto");


            Vector<Vector<Object>> dados = new Vector<>();
            while (rs.next()) {

                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString("Codigo_Venda"));
                linha.add(rs.getString("Nome_Cliente"));
                linha.add(rs.getString("Nome_Vendedor"));
                linha.add(rs.getString("Qtd_Pecas"));
                linha.add(rs.getString("Pecas_Vendidas"));
                linha.add(rs.getString("Data_Venda"));
                linha.add(rs.getString("valor_Total"));
                linha.add(rs.getString("forma_Pagamento"));
                linha.add(rs.getString("desconto"));
                dados.add(linha);

            }

            jtabRPV.setModel(new DefaultTableModel(dados, colunas));
            jtabRPV.setRowHeight(30);

        }catch (SQLException e){

            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"Erro ao carregar dados", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
