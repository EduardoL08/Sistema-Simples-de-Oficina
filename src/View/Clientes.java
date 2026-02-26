package View;

import Controller.ClientesDAO;
import DAO.FabricaConexao;
import Model.MClientes;

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

public class Clientes extends JFrame{
    public JPanel painelClientes;
    private JPanel jpTopo;
    private JLabel jlClientes;
    private JTextField jtxtNome;
    private JLabel jlNome;
    private JTextField jtxtCpf;
    private JLabel jlCpf;
    private JTextField jtxtTelefone;
    private JLabel jlTelefone;
    private JTextField jtxtEmail;
    private JLabel jlEmail;
    private JTextField jtxtEndereco;
    private JLabel jlEndereco;
    private JTextField jtxtDataCadastro;
    private JLabel jlDataCadast;
    private JButton jbutSair;
    private JPanel jpCadastroCliente;
    private JButton jbutNovo;
    private JButton jbutEditar;
    private JButton jbutDeletar;
    private JButton jbutSalvar;
    private JPanel jpBotoes;
    private JTable jtabClientes;
    private JPanel jpTabela;
    private JScrollPane jScrollPane;
    private JLabel jlId;
    private JTextField jtxtId;


    public Clientes() {

        setSize(525,370);
        setUndecorated(true);
        setContentPane(painelClientes);
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
                String cpf = jtxtCpf.getText().toString();
                String telefone = jtxtTelefone.getText().toString();
                String email = jtxtEmail.getText().toString();
                String endereco = jtxtEndereco.getText().toString();
                String dataCadastro = jtxtDataCadastro.getText().toString();

                MClientes cliente = new MClientes();
                cliente.setId(id);
                cliente.setNome(nome);
                cliente.setCpf(cpf);
                cliente.setTelefone(telefone);
                cliente.setEmail(email);
                cliente.setEndereco(endereco);
                cliente.setDataCadastro(dataCadastro);

                ClientesDAO cliDAO = new ClientesDAO();
                cliDAO.editarClientes(cliente);
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

                MClientes cli = new MClientes();
                ClientesDAO cliDAO = new ClientesDAO();

                cli.setId(id);

                cliDAO.deletarClientes(cli);

                carregarDados();
                limparCampos();

            }
        });
        jbutSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ClientesDAO clientesDAO = new ClientesDAO();
                MClientes cliente = new MClientes();

                cliente.setNome(jtxtNome.getText().trim());
                cliente.setCpf(jtxtCpf.getText().trim());
                cliente.setTelefone(jtxtTelefone.getText().trim());
                cliente.setEmail(jtxtEmail.getText().trim());
                cliente.setEndereco(jtxtEndereco.getText().trim());
                cliente.setDataCadastro(jtxtDataCadastro.getText().trim());

                if (clientesDAO.inserirClientes(cliente)) {

                    JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");

                    limparCampos();
                    desabilita();
                    jbutNovo.setEnabled(true);
                    carregarDados();

                } else {

                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar cliente!");
                }
            }
        });
        jtabClientes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()){
                    int linhaCelecionada = jtabClientes.getSelectedRow();

                    if (linhaCelecionada != -1){

                        //preencher os campos de texto com os dados da linha selecionada
                        jtxtId.setText(jtabClientes.getValueAt(linhaCelecionada, 0).toString());
                        jtxtNome.setText(jtabClientes.getValueAt(linhaCelecionada,1).toString());
                        jtxtCpf.setText(jtabClientes.getValueAt(linhaCelecionada,2).toString());
                        jtxtTelefone.setText(jtabClientes.getValueAt(linhaCelecionada,3).toString());
                        jtxtEmail.setText(jtabClientes.getValueAt(linhaCelecionada,4).toString());
                        jtxtEndereco.setText(jtabClientes.getValueAt(linhaCelecionada,5).toString());
                        jtxtDataCadastro.setText(jtabClientes.getValueAt(linhaCelecionada,6).toString());
                    }
                }
            }
        });
    }

    public void carregarDados() {

        try ( Connection conn = FabricaConexao.conectar()) {

            String query = "SELECT id_Cliente, nome,cpf, telefone, email, endereco, dataCadastro FROM Clientes ";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            //Obter as colunas das tabelas
            Vector<String> colunas = new Vector<>();
            colunas.add("id_Cliente");
            colunas.add("Nome");
            colunas.add("Cpf");
            colunas.add("Telefone");
            colunas.add("Email");
            colunas.add("Endereco");
            colunas.add("DataCadastro");

            //Obter os dados da tabela

            Vector<Vector<Object>> dados = new Vector<>();
            while (rs.next()) {

                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString("id_cliente"));
                linha.add(rs.getString("nome"));
                linha.add(rs.getString("cpf"));
                linha.add(rs.getString("telefone"));
                linha.add(rs.getString("email"));
                linha.add(rs.getString("endereco"));
                linha.add(rs.getString("datacadastro"));
                dados.add(linha);

            }
            //Cliar a talela com os dados das colunas

            jtabClientes.setModel(new DefaultTableModel(dados, colunas));
            jtabClientes.setRowHeight(30);
        }catch (SQLException e){

            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"Erro ao carregar dados", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void limparCampos(){

        jtxtNome.setText("");
        jtxtCpf.setText("");
        jtxtEmail.setText("");
        jtxtTelefone.setText("");
        jtxtEndereco.setText("");
        jtxtDataCadastro.setText("");
    }
    public void desabilita(){
        jtxtNome.setEnabled(false);
        jtxtCpf.setEnabled(false);
        jtxtEmail.setEnabled(false);
        jtxtTelefone.setEnabled(false);
        jtxtEndereco.setEnabled(false);
        jtxtDataCadastro.setEnabled(false);
        jbutDeletar.setEnabled(false);
        jbutEditar.setEnabled(false);
        jbutSalvar.setEnabled(false);
    }
    public void habilitar(){
        jtxtNome.setEnabled(true);
        jtxtCpf.setEnabled(true);
        jtxtEmail.setEnabled(true);
        jtxtTelefone.setEnabled(true);
        jtxtEndereco.setEnabled(true);
        jtxtDataCadastro.setEnabled(true);
        jbutDeletar.setEnabled(true);
        jbutEditar.setEnabled(true);
        jbutSalvar.setEnabled(true);
        jbutNovo.setEnabled(false);
    }


}
