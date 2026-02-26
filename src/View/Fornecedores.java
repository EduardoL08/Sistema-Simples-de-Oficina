package View;

import Controller.ClientesDAO;
import Controller.FornecedoresDAO;
import DAO.FabricaConexao;
import Model.MClientes;
import Model.MFornecedores;

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

public class Fornecedores extends JFrame{
    private JPanel painelFornecedores;
    private JPanel jpCadasForne;
    private JLabel jlCadasForne;
    private JTextField jtxtId;
    private JTextField jtxtNome;
    private JTextField jtxtCnpj;
    private JTextField jtxtEmail;
    private JTextField jtxtPecas;
    private JTextField jtxtQtd;
    private JTextField jtxtTelefone;
    private JLabel jlId;
    private JLabel jlNome;
    private JLabel jlCnpj;
    private JLabel jlEmail;
    private JLabel jlTelefone;
    private JLabel jlPecas;
    private JLabel jlQtd;
    private JPanel jpTopo;
    private JButton jbutNovo;
    private JButton jbutEditar;
    private JButton jbutDeletar;
    private JButton jbutSalvar;
    private JButton jbutSair;
    private JScrollPane jScrollPane;
    private JTable jTabFornecedor;
    private JPanel jpBotoes;
    private JPanel jpTabela;


    public Fornecedores() {

        setSize(560, 380);
        setUndecorated(true);
        setContentPane(painelFornecedores);
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
                String nome = jtxtNome.getText().toString();
                String cnpj = jtxtCnpj.getText().toString();
                String telefone = jtxtTelefone.getText().toString();
                String email = jtxtEmail.getText().toString();
                String pecaForne = jtxtPecas.getText().toString();
                int quantidade = Integer.parseInt( jtxtQtd.getText().toString());

                MFornecedores forne = new MFornecedores();
                forne.setId(id);
                forne.setNome(nome);
                forne.setCnpj(cnpj);
                forne.setTelefone(telefone);
                forne.setEmail(email);
                forne.setPecaForne(pecaForne);
                forne.setQtd(quantidade);

                FornecedoresDAO forneDAO = new FornecedoresDAO();
                forneDAO.editarFornecedor(forne);
                carregarDados();

                limparCampos();
                desabilita();
                jbutNovo.setEnabled(true);
                carregarDados();
            }
        });
        jbutDeletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int id = Integer.parseInt(jtxtId.getText().toString());

                MFornecedores forne = new MFornecedores();
                FornecedoresDAO forneDAO = new FornecedoresDAO();

                forne.setId(id);

                forneDAO.deletarFornecedor(forne);

                carregarDados();
                limparCampos();

            }
        });
        jbutSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                MFornecedores forne = new MFornecedores();
                FornecedoresDAO forneDAO = new FornecedoresDAO();

                forne.setNome(jtxtNome.getText().trim());
                forne.setCnpj(jtxtCnpj.getText().trim());
                forne.setTelefone(jtxtTelefone.getText().trim());
                forne.setEmail(jtxtEmail.getText().trim());
                forne.setPecaForne(jtxtPecas.getText().trim());
                forne.setQtd(Integer.parseInt(jtxtQtd.getText().trim()));

                if (forneDAO.inserirFornecedor(forne)) {

                    JOptionPane.showMessageDialog(null, "Fornecedor cadastrado com sucesso!");

                    limparCampos();
                    desabilita();
                    jbutNovo.setEnabled(true);
                    carregarDados();

                } else {

                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar Fornecedor!");
                }
            }
        });
        jTabFornecedor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()){
                    int linhaCelecionada = jTabFornecedor.getSelectedRow();

                    if (linhaCelecionada != -1){

                        //preencher os campos de texto com os dados da linha selecionada
                        jtxtId.setText(jTabFornecedor.getValueAt(linhaCelecionada, 0).toString());
                        jtxtNome.setText(jTabFornecedor.getValueAt(linhaCelecionada,1).toString());
                        jtxtCnpj.setText(jTabFornecedor.getValueAt(linhaCelecionada,2).toString());
                        jtxtTelefone.setText(jTabFornecedor.getValueAt(linhaCelecionada,3).toString());
                        jtxtEmail.setText(jTabFornecedor.getValueAt(linhaCelecionada,4).toString());
                        jtxtPecas.setText(jTabFornecedor.getValueAt(linhaCelecionada,5).toString());
                        jtxtQtd.setText(jTabFornecedor.getValueAt(linhaCelecionada,6).toString());
                    }
                }
            }
        });
    }

    public void carregarDados() {

        try ( Connection conn = FabricaConexao.conectar()) {

            String query = "SELECT id_Fornecedor, nome, cnpj, telefone, email, pecas_Fornecidas, quantidade FROM Fornecedores ";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            //Obter as colunas das tabelas
            Vector<String> colunas = new Vector<>();
            colunas.add("Id_Fornecedor");
            colunas.add("Nome");
            colunas.add("Cnpj");
            colunas.add("Telefone");
            colunas.add("Email");
            colunas.add("Pecas_Fornecidas");
            colunas.add("Quantidade");

            //Obter os dados da tabela

            Vector<Vector<Object>> dados = new Vector<>();
            while (rs.next()) {

                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString("id_Fornecedor"));
                linha.add(rs.getString("nome"));
                linha.add(rs.getString("cnpj"));
                linha.add(rs.getString("telefone"));
                linha.add(rs.getString("email"));
                linha.add(rs.getString("pecas_Fornecidas"));
                linha.add(rs.getString("quantidade"));
                dados.add(linha);

            }
            //Cliar a talela com os dados das colunas

            jTabFornecedor.setModel(new DefaultTableModel(dados, colunas));
            jTabFornecedor.setRowHeight(30);
        }catch (SQLException e){

            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"Erro ao carregar dados", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void limparCampos(){

        jtxtNome.setText("");
        jtxtCnpj.setText("");
        jtxtEmail.setText("");
        jtxtTelefone.setText("");
        jtxtPecas.setText("");
        jtxtQtd.setText("");
    }
    public void desabilita(){
        jtxtNome.setEnabled(false);
        jtxtCnpj.setEnabled(false);
        jtxtEmail.setEnabled(false);
        jtxtTelefone.setEnabled(false);
        jtxtPecas.setEnabled(false);
        jtxtQtd.setEnabled(false);
        jbutDeletar.setEnabled(false);
        jbutEditar.setEnabled(false);
        jbutSalvar.setEnabled(false);
    }
    public void habilitar(){
        jtxtNome.setEnabled(true);
        jtxtCnpj.setEnabled(true);
        jtxtEmail.setEnabled(true);
        jtxtTelefone.setEnabled(true);
        jtxtPecas.setEnabled(true);
        jtxtQtd.setEnabled(true);
        jbutDeletar.setEnabled(true);
        jbutEditar.setEnabled(true);
        jbutSalvar.setEnabled(true);
        jbutNovo.setEnabled(false);
    }
}
