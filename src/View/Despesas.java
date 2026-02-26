package View;

import Controller.ClientesDAO;
import Controller.DespesasDAO;
import DAO.FabricaConexao;
import Model.MClientes;
import Model.MDespesas;

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

public class Despesas extends JFrame{

    public JPanel painelDespesas;
    private JPanel jpCP;
    private JPanel jpTopo;
    private JPanel jpBotoes;
    private JLabel jlD;
    private JTextField jtxtDesp;
    private JTextField jtxtDescri;
    private JTextField jtxtValor;
    private JTextField jtxtDataVenci;
    private JButton jbutNovo;
    private JButton jbutEditar;
    private JButton jbutDeletar;
    private JButton jbutSalvar;
    private JButton jbutSair;
    private JLabel jlDesp;
    private JLabel jlDescri;
    private JLabel jlValor;
    private JLabel jlDataVenci;
    private JPanel jpTabela;
    private JScrollPane jScrollPane;
    private JTable jtabD;
    private JLabel jlStatus;
    private JTextField jtxtStatus;
    private JTextField jtxtId;
    private JLabel jlId;

    public Despesas() {

        setSize( 900, 400);
        setUndecorated(true);
        setContentPane(painelDespesas);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        desabilita();
        carregarDados();
        jtxtId.setEnabled(false);

        jbutSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose();

            }
        });
        jbutNovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                habilitar();

            }
        });
        jbutEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int id = Integer.parseInt( jtxtId.getText().toString());
                String despesa = jtxtDesp.getText().toString();
                String descricao = jtxtDescri.getText().toString();
                String dataV = jtxtDataVenci.getText().toString();
                String valor = jtxtValor.getText().toString();
                String status = jtxtStatus.getText().toString();

                MDespesas desp = new MDespesas();
                desp.setId(id);
                desp.setNomeDesp(despesa);
                desp.setDescricao(descricao);
                desp.setDataVenci(dataV);
                desp.setValor(valor);
                desp.setStatus(status);

                DespesasDAO despDAO = new DespesasDAO();
                despDAO.editarDespesa(desp);
                carregarDados();

                limparCampos();
                desabilita();
                jbutNovo.setEnabled(true);
            }
        });
        jbutDeletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int id = Integer.parseInt(jtxtId.getText().toString());

                MDespesas desp = new MDespesas();
                DespesasDAO despDAO = new DespesasDAO();

                desp.setId(id);

                despDAO.deletarDespesas(desp);

                carregarDados();
                limparCampos();
            }
        });
        jbutSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                MDespesas desp = new MDespesas();
                DespesasDAO despDAO = new DespesasDAO();

                desp.setNomeDesp(jtxtDesp.getText().trim());
                desp.setDescricao(jtxtDescri.getText().trim());
                desp.setDataVenci(jtxtDataVenci.getText().trim());
                desp.setValor(jtxtValor.getText().trim());
                desp.setStatus(jtxtStatus.getText().trim());

                if (despDAO.inserirDespesa(desp)) {

                    JOptionPane.showMessageDialog(null, "Despesa cadastrada com sucesso!");

                    limparCampos();
                    desabilita();
                    jbutNovo.setEnabled(true);
                    carregarDados();

                } else {

                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar Despesa!");
                }

            }
        });

        jtabD.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()){
                    int linhaCelecionada = jtabD.getSelectedRow();

                    if (linhaCelecionada != -1){

                        jtxtId.setText(jtabD.getValueAt(linhaCelecionada, 0).toString());
                        jtxtDesp.setText(jtabD.getValueAt(linhaCelecionada,1).toString());
                        jtxtDescri.setText(jtabD.getValueAt(linhaCelecionada,2).toString());
                        jtxtDataVenci.setText(jtabD.getValueAt(linhaCelecionada,3).toString());
                        jtxtValor.setText(jtabD.getValueAt(linhaCelecionada,4).toString());
                        jtxtStatus.setText(jtabD.getValueAt(linhaCelecionada,5).toString());
                    }
                }
            }
        });
    }
    public void carregarDados() {

        try ( Connection conn = FabricaConexao.conectar()) {

            String query = "SELECT id_Despesa, nome,descricao, dataValidade, valor, status FROM Despesas ";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            //Obter as colunas das tabelas
            Vector<String> colunas = new Vector<>();
            colunas.add("id_Despesa");
            colunas.add("nome");
            colunas.add("descricao");
            colunas.add("dataValidade");
            colunas.add("valor");
            colunas.add("status");

            //Obter os dados da tabela

            Vector<Vector<Object>> dados = new Vector<>();
            while (rs.next()) {

                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString("id_Despesa"));
                linha.add(rs.getString("nome"));
                linha.add(rs.getString("descricao"));
                linha.add(rs.getString("dataValidade"));
                linha.add(rs.getString("valor"));
                linha.add(rs.getString("status"));
                dados.add(linha);

            }
            //Cliar a talela com os dados das colunas

            jtabD.setModel(new DefaultTableModel(dados, colunas));
            jtabD.setRowHeight(30);
        }catch (SQLException e){

            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"Erro ao carregar dados", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void limparCampos () {

        jtxtDesp.setText("");
        jtxtDescri.setText("");
        jtxtValor.setText("");
        jtxtDataVenci.setText("");
        jtxtStatus.setText("");
    }
    public void desabilita () {
        jtxtDesp.setEnabled(false);
        jtxtDescri.setEnabled(false);
        jtxtValor.setEnabled(false);
        jtxtDataVenci.setEnabled(false);
        jtxtStatus.setEnabled(false);
        jbutDeletar.setEnabled(false);
        jbutEditar.setEnabled(false);
        jbutSalvar.setEnabled(false);

    }
    public void habilitar () {

        jtxtDesp.setEnabled(true);
        jtxtDescri.setEnabled(true);
        jtxtValor.setEnabled(true);
        jtxtDataVenci.setEnabled(true);
        jtxtStatus.setEnabled(true);
        jbutDeletar.setEnabled(true);
        jbutEditar.setEnabled(true);
        jbutSalvar.setEnabled(true);
        jbutNovo.setEnabled(false);

    }

}
