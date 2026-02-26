package View;

import Controller.ClientesDAO;
import Controller.VendaServicosDAO;
import DAO.FabricaConexao;
import Model.MClientes;
import Model.MVendaServicos;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Servicos_Realizados extends JFrame {
    public JPanel jpServReal;
    private JButton jbutEditar;
    private JButton jbutSalvar;
    private JButton jbutSair;
    private JPanel painelServReal;
    private JLabel jlSR;
    private JPanel jpBotoes;
    private JTextField jtxtStatus;
    private JLabel jlStatus;
    private JTable jtabSR;
    private JPanel jpTabela;
    private JScrollPane jScrollpane;
    private JPanel jpDados;



    public Servicos_Realizados() {

        setSize(1095, 400);
        setUndecorated(true);
        setContentPane(painelServReal);
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
        jbutEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtxtStatus.setEnabled(true);
                jbutSalvar.setEnabled(true);

            }
        });
        jbutSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int linha = jtabSR.getSelectedRow();
                if (linha == -1) {
                    JOptionPane.showMessageDialog(null, "Selecione uma linha para salvar.");
                    return;
                }

                String codigoVenda = jtabSR.getValueAt(linha, 0).toString();
                String codigoServico = jtabSR.getValueAt(linha, 5).toString();
                String novoStatus = jtxtStatus.getText();

                VendaServicosDAO vsDAO = new VendaServicosDAO();
                boolean sucesso = vsDAO.atualizarStatus(codigoVenda, codigoServico, novoStatus);

                if (sucesso) {
                    JOptionPane.showMessageDialog(null, "Status atualizado com sucesso!");
                    carregarDados();
                    jtxtStatus.setEnabled(false);
                    jbutSalvar.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar o status.");
                }

            }
        });
        jtabSR.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()){
                    int linhaCelecionada = jtabSR.getSelectedRow();

                    if (linhaCelecionada != -1){

                        jtxtStatus.setText(jtabSR.getValueAt(linhaCelecionada, 12).toString());


                    }
                }
            }
        });
    }

    public void carregarDados() {

        try ( Connection conn = FabricaConexao.conectar()) {

            String query = "SELECT codigo_Venda, data_Venda ,Vendedor ,Cliente ,Praca ,codigo_Servico ,descricao_Servico, " +
                    "valor_Unitario ,forma_Pagamento, desconto,diagnostico_tecnico, previsao_entrega ,status  " +
                    "from Relatorio_Servicos_Vendidos  ";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            Vector<String> colunas = new Vector<>();
            colunas.add("codigo_Venda");
            colunas.add("data_Venda");
            colunas.add("Vendedor");
            colunas.add("Cliente");
            colunas.add("Praca");
            colunas.add("codigo_Servico");
            colunas.add("descricao_Servico");
            colunas.add("valor_Unitario");
            colunas.add("forma_Pagamento");
            colunas.add("desconto");
            colunas.add("diagnostico_tecnico");
            colunas.add("previsao_entrega");
            colunas.add("status");


            Vector<Vector<Object>> dados = new Vector<>();
            while (rs.next()) {

                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString("codigo_Venda"));
                linha.add(rs.getString("data_Venda"));
                linha.add(rs.getString("Vendedor"));
                linha.add(rs.getString("Cliente"));
                linha.add(rs.getString("Praca"));
                linha.add(rs.getString("codigo_Servico"));
                linha.add(rs.getString("descricao_Servico"));
                linha.add(rs.getString("valor_Unitario"));
                linha.add(rs.getString("forma_Pagamento"));
                linha.add(rs.getString("desconto"));
                linha.add(rs.getString("diagnostico_tecnico"));
                linha.add(rs.getString("previsao_entrega"));
                linha.add(rs.getString("status"));
                dados.add(linha);

            }

            jtabSR.setModel(new DefaultTableModel(dados, colunas));
            jtabSR.setRowHeight(30);
        }catch (SQLException e){

            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"Erro ao carregar dados", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
