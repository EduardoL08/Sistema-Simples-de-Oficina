package View;

import Controller.ClientesDAO;
import Controller.PecasDAO;
import Controller.VeiculosDAO;
import DAO.FabricaConexao;
import Model.MClientes;
import Model.MPecas;
import Model.MVeiculos;

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

public class Veiculos extends JFrame {
    public JPanel painelVeiculos;
    private JPanel jpTopo;
    private JLabel jlVeiculos;
    private JTextField jtxtPlacas;
    private JTextField jtxtModelos;
    private JTextField jtxtMarcas;
    private JTextField jtxtAnos;
    private JTextField jtxtCores;
    private JButton jbutSair;
    private JLabel jlPlaca;
    private JLabel jlModelos;
    private JLabel jlMarcas;
    private JLabel jlAnos;
    private JLabel jlCores;
    private JTextField jtxtTipos;
    private JTextField jtxtKms;
    private JTextField jtxtObser;
    private JLabel jlTpos;
    private JLabel jlKms;
    private JLabel jlObser;
    private JPanel pjCadastroVeiculo;
    private JButton jbutNovo;
    private JButton jbutEditar;
    private JButton jbutDeletar;
    private JButton jbutSalvar;
    private JPanel jpBotoes;
    private JTextField jtxtIdCliente;
    private JLabel jlIdCliente;
    private JTable jtabVeiculos;
    private JPanel jpTabela;
    private JScrollPane jScrollPane;
    private JTextField jtxtId;
    private JLabel jlId;


    public Veiculos() {

        setSize(530, 540);
        setUndecorated(true);
        setContentPane(painelVeiculos);
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
                int idCliente = Integer.parseInt( jtxtIdCliente.getText().toString());
                String placas = jtxtPlacas.getText().toString();
                String modelos = jtxtModelos.getText().toString();
                String marcas = jtxtMarcas.getText().toString();
                int anos = Integer.parseInt(jtxtAnos.getText().toString());
                String cores = jtxtCores.getText().toString();
                String tipos = jtxtTipos.getText().toString();
                double kms = Double.parseDouble(jtxtKms.getText().toString());
                String observacao = jtxtObser.getText().toString();

                MVeiculos veiculo = new MVeiculos();
                veiculo.setId(id);
                veiculo.setIdCliente(idCliente);
                veiculo.setPlacas(placas);
                veiculo.setModelos(modelos);
                veiculo.setMarcas(marcas);
                veiculo.setAnos(anos);
                veiculo.setCores(cores);
                veiculo.setTipos(tipos);
                veiculo.setKms(kms);
                veiculo.setObser(observacao);

                VeiculosDAO veiculoDAO = new VeiculosDAO();
                veiculoDAO.editarVeiculo(veiculo);
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

                VeiculosDAO veiculoDAO = new VeiculosDAO();
                MVeiculos veiculo = new MVeiculos();

                veiculo.setId(id);

                veiculoDAO.deletarVeiculo(veiculo);

                carregarDados();
                limparCampos();

            }
        });
        jbutSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                VeiculosDAO veiculoDAO = new VeiculosDAO();
                MVeiculos veiculo = new MVeiculos();

                veiculo.setIdCliente(Integer.parseInt(jtxtIdCliente.getText()));
                veiculo.setPlacas(jtxtPlacas.getText());
                veiculo.setModelos(jtxtModelos.getText());
                veiculo.setMarcas(jtxtMarcas.getText());
                veiculo.setAnos(Integer.parseInt(jtxtAnos.getText()));
                veiculo.setCores(jtxtCores.getText());
                veiculo.setTipos(jtxtTipos.getText());
                veiculo.setKms(Double.parseDouble(jtxtKms.getText()));
                veiculo.setObser(jtxtObser.getText());

                int idCliente = Integer.parseInt(jtxtIdCliente.getText());
                if (!VeiculosDAO.verificaClienteExiste(idCliente)) {
                    JOptionPane.showMessageDialog(null, "O fornecedor informado não existe no sistema.\nPor favor, insira um ID válido.");
                    return;
                }
                veiculo.setIdCliente(idCliente);

                if (veiculoDAO.inserirVeiculo(veiculo)) {

                    JOptionPane.showMessageDialog(null, "Veículo cadastrado com sucesso!");

                    limparCampos();
                    desabilita();
                    jbutNovo.setEnabled(true);
                    carregarDados();

                } else {

                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar veículo!");
                }
            }
        });
        jtabVeiculos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                if (!e.getValueIsAdjusting()){
                    int linhaCelecionada = jtabVeiculos.getSelectedRow();

                    if (linhaCelecionada != -1){

                        //preencher os campos de texto com os dados da linha selecionada
                        jtxtId.setText(jtabVeiculos.getValueAt(linhaCelecionada, 0).toString());
                        jtxtIdCliente.setText(jtabVeiculos.getValueAt(linhaCelecionada,1).toString());
                        jtxtPlacas.setText(jtabVeiculos.getValueAt(linhaCelecionada,2).toString());
                        jtxtMarcas.setText(jtabVeiculos.getValueAt(linhaCelecionada,3).toString());
                        jtxtModelos.setText(jtabVeiculos.getValueAt(linhaCelecionada,4).toString());
                        jtxtAnos.setText(jtabVeiculos.getValueAt(linhaCelecionada,5).toString());
                        jtxtCores.setText(jtabVeiculos.getValueAt(linhaCelecionada,6).toString());
                        jtxtTipos.setText(jtabVeiculos.getValueAt(linhaCelecionada,7).toString());
                        jtxtKms.setText(jtabVeiculos.getValueAt(linhaCelecionada,8).toString());
                        jtxtObser.setText(jtabVeiculos.getValueAt(linhaCelecionada,9).toString());
                    }
                }
            }
        });
    }

    public void carregarDados() {

        try ( Connection conn = FabricaConexao.conectar()) {

            String query = "SELECT id_Veiculo, fk_id_Cliente, placa, marca, modelo, ano, cor,  tipo, kms, observacao FROM Veiculos ";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            //Obter as colunas das tabelas
            Vector<String> colunas = new Vector<>();
            colunas.add("id_Veiculo");
            colunas.add("fk_id_Cliente");
            colunas.add("placa");
            colunas.add("marca");
            colunas.add("modelo");
            colunas.add("ano");
            colunas.add("cor");
            colunas.add("tipo");
            colunas.add("kms");
            colunas.add("observacao");

            //Obter os dados da tabela

            Vector<Vector<Object>> dados = new Vector<>();
            while (rs.next()) {

                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString("id_Veiculo"));
                linha.add(rs.getString("fk_id_Cliente"));
                linha.add(rs.getString("placa"));
                linha.add(rs.getString("marca"));
                linha.add(rs.getString("modelo"));
                linha.add(rs.getString("ano"));
                linha.add(rs.getString("cor"));
                linha.add(rs.getString("tipo"));
                linha.add(rs.getString("kms"));
                linha.add(rs.getString("observacao"));
                dados.add(linha);

            }
            //Cliar a talela com os dados das colunas

            jtabVeiculos.setModel(new DefaultTableModel(dados, colunas));
            jtabVeiculos.setRowHeight(30);

        }catch (SQLException e){

            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"Erro ao carregar dados", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void limparCampos() {
        jtxtIdCliente.setText("");
        jtxtPlacas.setText("");
        jtxtMarcas.setText("");
        jtxtModelos.setText("");
        jtxtAnos.setText("");
        jtxtCores.setText("");
        jtxtTipos.setText("");
        jtxtKms.setText("");
        jtxtObser.setText("");
    }
    public void desabilita() {
        jtxtIdCliente.setEnabled(false);
        jtxtPlacas.setEnabled(false);
        jtxtMarcas.setEnabled(false);
        jtxtModelos.setEnabled(false);
        jtxtAnos.setEnabled(false);
        jtxtCores.setEnabled(false);
        jtxtTipos.setEnabled(false);
        jtxtKms.setEnabled(false);
        jtxtObser.setEnabled(false);
        jbutDeletar.setEnabled(false);
        jbutEditar.setEnabled(false);
        jbutSalvar.setEnabled(false);
    }
    public void habilitar() {
        jtxtIdCliente.setEnabled(true);
        jtxtPlacas.setEnabled(true);
        jtxtMarcas.setEnabled(true);
        jtxtModelos.setEnabled(true);
        jtxtAnos.setEnabled(true);
        jtxtCores.setEnabled(true);
        jtxtTipos.setEnabled(true);
        jtxtKms.setEnabled(true);
        jtxtObser.setEnabled(true);
        jbutDeletar.setEnabled(true);
        jbutEditar.setEnabled(true);
        jbutSalvar.setEnabled(true);
        jbutNovo.setEnabled(false);
    }

}