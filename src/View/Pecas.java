package View;


import Controller.PecasDAO;
import DAO.FabricaConexao;
import Model.MPecas;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class Pecas extends JFrame{
    public JPanel jpTopo;
    private JPanel painelPecas;
    private JLabel jlCadaPecas;
    private JTextField jtxtNomePeca;
    private JTextField jtxtCodigo;
    private JTextField jtxtDescricao;
    private JTextField jtxtQuantidade;
    private JTextField jtxtPrecoCusto;
    private JTextField jtxtPrecoVenda;
    private JTextField jtxtIdFornecedor;
    private JButton jbutSair;
    private JLabel jlNomePeca;
    private JLabel jlCodigo;
    private JLabel jlDescricao;
    private JLabel jlQuantidade;
    private JLabel jlPrecoCusto;
    private JLabel jlPrecoVenda;
    private JLabel jlIdFornecedor;
    private JButton jbutNovo;
    private JButton jbutEditar;
    private JButton jbutDeletar;
    private JButton jbutSalvar;
    private JPanel jpBotoes;
    private JPanel jpCP;
    private JTable jtabPecas;
    private JPanel jpTabela;
    private JScrollPane jScrollPane;
    private JTextField jtxtId;
    private JLabel jlId;

    public Pecas() {

        setSize(530,400);
        setUndecorated(true);
        setContentPane(painelPecas);
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
                String nomePeca = jtxtNomePeca.getText().toString();
                String codigo = jtxtCodigo.getText().toString();
                String descricao = jtxtDescricao.getText().toString();
                int quantidade = Integer.parseInt(jtxtQuantidade.getText().toString());
                double precoCusto = Double.parseDouble(jtxtPrecoCusto.getText().toString());
                double precoVenda = Double.parseDouble(jtxtPrecoVenda.getText().toString());
                int idFornecedor = Integer.parseInt(jtxtIdFornecedor.getText().toString());


                MPecas peca = new MPecas();
                peca.setId(id);
                peca.setNomePeca(nomePeca);
                peca.setCodigo(codigo);
                peca.setDescricao(descricao);
                peca.setQuantidade(quantidade);
                peca.setPrecoCusto(precoCusto);
                peca.setPrecoVenda(precoVenda);
                peca.setIdFornecedor(idFornecedor);

                PecasDAO pecasDAO = new PecasDAO();
                pecasDAO.editarPeca(peca);
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

                PecasDAO pecasDAO = new PecasDAO();
                MPecas peca = new MPecas();

                peca.setId(id);

                pecasDAO.deletarPeca(peca);

                carregarDados();
                limparCampos();

            }
        });
        jbutSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PecasDAO pecasDAO = new PecasDAO();
                MPecas peca = new MPecas();

                peca.setNomePeca(jtxtNomePeca.getText());
                peca.setCodigo(jtxtCodigo.getText());
                peca.setDescricao(jtxtDescricao.getText());
                peca.setQuantidade(Integer.parseInt(jtxtQuantidade.getText()));
                peca.setPrecoCusto(Double.parseDouble(jtxtPrecoCusto.getText()));
                peca.setPrecoVenda(Double.parseDouble(jtxtPrecoVenda.getText()));
                peca.setIdFornecedor(Integer.parseInt(jtxtIdFornecedor.getText()));

                int idFornecedor = Integer.parseInt(jtxtIdFornecedor.getText());
                if (!pecasDAO.verificaFornecedorExiste(idFornecedor)) {
                    JOptionPane.showMessageDialog(null, "O fornecedor informado não existe no sistema.\nPor favor, insira um ID válido.");
                    return;
                }
                peca.setIdFornecedor(idFornecedor);

                if (pecasDAO.inserirPeca(peca)) {

                    JOptionPane.showMessageDialog(null, "Peca cadastrada com sucesso!");

                    limparCampos();
                    desabilita();
                    jbutNovo.setEnabled(true);
                    carregarDados();

                }else {

                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar peca!");

                }
            }
        });
        jtabPecas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()) {
                    int linhaCelecionada = jtabPecas.getSelectedRow();

                    if (linhaCelecionada != -1) {

                        //preencher os campos de texto com os dados da linha selecionada
                        jtxtId.setText(jtabPecas.getValueAt(linhaCelecionada, 0).toString());
                        jtxtNomePeca.setText(jtabPecas.getValueAt(linhaCelecionada, 1).toString());
                        jtxtCodigo.setText(jtabPecas.getValueAt(linhaCelecionada, 2).toString());
                        jtxtDescricao.setText(jtabPecas.getValueAt(linhaCelecionada, 3).toString());
                        jtxtQuantidade.setText(jtabPecas.getValueAt(linhaCelecionada, 4).toString());
                        jtxtPrecoCusto.setText(jtabPecas.getValueAt(linhaCelecionada, 5).toString());
                        jtxtPrecoVenda.setText(jtabPecas.getValueAt(linhaCelecionada, 6).toString());
                        jtxtIdFornecedor.setText(jtabPecas.getValueAt(linhaCelecionada, 7).toString());

                    }
                }
            }
        });
    }

    public void carregarDados() {

        try ( Connection conn = FabricaConexao.conectar()){

            String query = "SELECT id_Peca, nome, codigo, descricao, qtd_Estoque, preco_Custo, preco_Venda, fk_id_Fornecedor FROM Pecas ";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            //Obter as colunas das tabelas
            Vector<String> colunas = new Vector<>();
            colunas.add("id_Peca");
            colunas.add("nome");
            colunas.add("codigo");
            colunas.add("descricao");
            colunas.add("qtd_Estoque");
            colunas.add("preco_Custo");
            colunas.add("preco_Venda");
            colunas.add("fk_id_Fornecedor");

            //Obter os dados da tabela

            Vector<Vector<Object>> dados = new Vector<>();
            while (rs.next()) {

                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString("id_Peca"));
                linha.add(rs.getString("nome"));
                linha.add(rs.getString("codigo"));
                linha.add(rs.getString("descricao"));
                linha.add(rs.getString("qtd_Estoque"));
                linha.add(rs.getString("preco_Custo"));
                linha.add(rs.getString("preco_Venda"));
                linha.add(rs.getString("fk_id_Fornecedor"));
                dados.add(linha);
            }
            //Cliar a talela com os dados das colunas

            jtabPecas.setModel(new DefaultTableModel(dados, colunas));
            jtabPecas.setRowHeight(30);

        }catch (SQLException e){

            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"Erro ao carregar dados", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limparCampos(){
        jtxtNomePeca.setText("");
        jtxtCodigo.setText("");
        jtxtDescricao.setText("");
        jtxtQuantidade.setText("");
        jtxtPrecoCusto.setText("");
        jtxtPrecoVenda.setText("");
        jtxtIdFornecedor.setText("");
    }
    public void desabilita(){
        jtxtNomePeca.setEnabled(false);
        jtxtCodigo.setEnabled(false);
        jtxtDescricao.setEnabled(false);
        jtxtQuantidade.setEnabled(false);
        jtxtPrecoCusto.setEnabled(false);
        jtxtPrecoVenda.setEnabled(false);
        jtxtIdFornecedor.setEnabled(false);
        jbutDeletar.setEnabled(false);
        jbutEditar.setEnabled(false);
        jbutSalvar.setEnabled(false);
    }
    public void habilitar(){
        jtxtNomePeca.setEnabled(true);
        jtxtCodigo.setEnabled(true);
        jtxtDescricao.setEnabled(true);
        jtxtQuantidade.setEnabled(true);
        jtxtPrecoCusto.setEnabled(true);
        jtxtPrecoVenda.setEnabled(true);
        jtxtIdFornecedor.setEnabled(true);
        jbutDeletar.setEnabled(true);
        jbutEditar.setEnabled(true);
        jbutSalvar.setEnabled(true);
        jbutNovo.setEnabled(false);
    }
}
