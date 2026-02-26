package View;

import Controller.FuncionariosDAO;
import DAO.FabricaConexao;
import Model.MFuncionarios;

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


public class Funcionarios extends JFrame {
    public JPanel painelFuncionarios;
    private JPanel jpFunc;
    private JLabel jlFunc;
    private JPanel jpTopo;
    private JLabel jlNome;
    private JLabel jlCpfCnpj;
    private JLabel jlTelefone;
    private JLabel jlEmail;
    private JLabel jlEspecialidade;
    private JLabel jlDateCon;
    private JLabel jlStatus;
    private JLabel jlObser;
    private JTextField jtxtNome;
    private JTextField jtxtCpf;
    private JTextField jtxtTelefone;
    private JTextField jtxtEmail;
    private JTextField jtxtEspecialidade;
    private JTextField jtxtDataCon;
    private JTextField jtxtStatus;
    private JTextField jtxtObser;
    private JPanel jpBotoes;
    private JButton jbutNovo;
    private JButton jbutEditar;
    private JButton jbutDeletar;
    private JButton jbutSalvar;
    private JButton jbutSair;
    private JTable jtabFuncionarios;
    private JPanel jpTabela;
    private JScrollPane jScrollPane;
    private JTextField jtxtId;
    private JLabel jlId;


    public Funcionarios() {

        setSize(530,380);
        setUndecorated(true);
        setContentPane(painelFuncionarios);
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
                String especialidade = jtxtEspecialidade.getText().toString();
                String dataContratacao =jtxtDataCon.getText().toString();
                String status = jtxtStatus.getText().toString();
                String observacao = jtxtObser.getText().toString();

                MFuncionarios func = new MFuncionarios();
                func.setId(id);
                func.setNome(nome);
                func.setCpf(cpf);
                func.setTelefone(telefone);
                func.setEmail(email);
                func.setEspecialidade(especialidade);
                func.setDataCon(dataContratacao);
                func.setStatus(status);
                func.setObser(observacao);

                FuncionariosDAO funcDAO = new FuncionariosDAO();
                funcDAO.editarFuncionarios(func);
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

                FuncionariosDAO funcDAO = new FuncionariosDAO();
                MFuncionarios func = new MFuncionarios();

                func.setId(id);

                funcDAO.deletarFuncionarios(func);

                carregarDados();
                limparCampos();

            }
        });
        jbutSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                FuncionariosDAO funcDAO = new FuncionariosDAO();
                MFuncionarios func = new MFuncionarios();

                func.setNome(jtxtNome.getText());
                func.setCpf(jtxtCpf.getText());
                func.setEmail(jtxtEmail.getText());
                func.setTelefone(jtxtTelefone.getText());
                func.setEspecialidade(jtxtEspecialidade.getText());
                func.setDataCon(jtxtDataCon.getText());
                func.setStatus(jtxtStatus.getText());
                func.setObser(jtxtObser.getText());

                if (funcDAO.inserirFuncionarios(func)) {

                    JOptionPane.showMessageDialog(null, "Funcionario cadastrado com sucesso!");

                    limparCampos();
                    desabilita();
                    jbutNovo.setEnabled(true);
                    carregarDados();

                }else {

                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar Funcionario!");
                }
            }
        });

        jtabFuncionarios.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()) {
                    int linhaCelecionada = jtabFuncionarios.getSelectedRow();

                    if (linhaCelecionada != -1) {

                        //preencher os campos de texto com os dados da linha selecionada
                        jtxtId.setText(jtabFuncionarios.getValueAt(linhaCelecionada, 0).toString());
                        jtxtNome.setText(jtabFuncionarios.getValueAt(linhaCelecionada, 1).toString());
                        jtxtCpf.setText(jtabFuncionarios.getValueAt(linhaCelecionada, 2).toString());
                        jtxtEmail.setText(jtabFuncionarios.getValueAt(linhaCelecionada, 3).toString());
                        jtxtTelefone.setText(jtabFuncionarios.getValueAt(linhaCelecionada, 4).toString());
                        jtxtEspecialidade.setText(jtabFuncionarios.getValueAt(linhaCelecionada, 5).toString());
                        jtxtDataCon.setText(jtabFuncionarios.getValueAt(linhaCelecionada, 6).toString());
                        jtxtStatus.setText(jtabFuncionarios.getValueAt(linhaCelecionada, 7).toString());
                        jtxtObser.setText(jtabFuncionarios.getValueAt(linhaCelecionada, 8).toString());

                    }
                }
            }
        });
    }

    public void carregarDados() {

        try ( Connection conn = FabricaConexao.conectar()) {

            String query = "SELECT id_Funcionario, nome,cpf, telefone, email, especialidades, dataContratacao, statu, observacao FROM Funcionarios ";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            //Obter as colunas das tabelas
            Vector<String> colunas = new Vector<>();
            colunas.add("id_Funcionario");
            colunas.add("nome");
            colunas.add("cpf");
            colunas.add("telefone");
            colunas.add("email");
            colunas.add("especialidades");
            colunas.add("dataContratacao");
            colunas.add("statu");
            colunas.add("observacao");

            //Obter os dados da tabela

            Vector<Vector<Object>> dados = new Vector<>();
            while (rs.next()) {

                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString("id_Funcionario"));
                linha.add(rs.getString("nome"));
                linha.add(rs.getString("cpf"));
                linha.add(rs.getString("telefone"));
                linha.add(rs.getString("email"));
                linha.add(rs.getString("especialidades"));
                linha.add(rs.getString("dataContratacao"));
                linha.add(rs.getString("statu"));
                linha.add(rs.getString("observacao"));
                dados.add(linha);
            }
            //Cliar a talela com os dados das colunas

            jtabFuncionarios.setModel(new DefaultTableModel(dados, colunas));
            jtabFuncionarios.setRowHeight(30);


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
        jtxtEspecialidade.setText("");
        jtxtDataCon.setText("");
        jtxtStatus.setText("");
        jtxtObser.setText("");
    }
    public void desabilita(){
        jtxtNome.setEnabled(false);
        jtxtCpf.setEnabled(false);
        jtxtEmail.setEnabled(false);
        jtxtTelefone.setEnabled(false);
        jtxtEspecialidade.setEnabled(false);
        jtxtDataCon.setEnabled(false);
        jtxtStatus.setEnabled(false);
        jtxtObser.setEnabled(false);
        jbutDeletar.setEnabled(false);
        jbutEditar.setEnabled(false);
        jbutSalvar.setEnabled(false);
    }
    public void habilitar(){
        jtxtNome.setEnabled(true);
        jtxtCpf.setEnabled(true);
        jtxtEmail.setEnabled(true);
        jtxtTelefone.setEnabled(true);
        jtxtEspecialidade.setEnabled(true);
        jtxtDataCon.setEnabled(true);
        jtxtStatus.setEnabled(true);
        jtxtObser.setEnabled(true);
        jbutDeletar.setEnabled(true);
        jbutEditar.setEnabled(true);
        jbutSalvar.setEnabled(true);
        jbutNovo.setEnabled(false);


    }
}
