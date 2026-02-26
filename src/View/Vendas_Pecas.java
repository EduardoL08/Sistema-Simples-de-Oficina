package View;

import Controller.ClientesDAO;
import Controller.FuncionariosDAO;
import Controller.PecasDAO;
import Controller.VendaPecasDAO;
import DAO.FabricaConexao;
import Model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Vendas_Pecas extends JFrame {
    private JPanel painelVendaPecas;
    private JPanel jpVenPec;
    private JLabel jlVenPec;
    private JPanel jpBotoes;
    private JButton jbutNovaVenda;
    private JButton jbutDeletar;
    private JButton jbutSalvarVenda;
    private JButton jbutSair;
    private JPanel jpVP1;
    private JLabel jlCodigoVenda;
    private JComboBox jcbVendedor;
    private JTextField jtxtDataVenda;
    private JTextField jtxtCodigoVenda;
    private JLabel jlDataVenda;
    private JPanel jpVP2;
    private JLabel jlSeleCliente;
    private JComboBox jcbClientes;
    private JRadioButton rbBoleto;
    private JRadioButton rbCartaoDebito;
    private JRadioButton rbCartaoCredito;
    private JRadioButton rbPix;
    private JLabel jlFormaPagamento;
    private JLabel jlDesconto;
    private JLabel jlValorTotal;
    private JLabel jlValorPeca;
    private JTextField jtxtValorpeca;
    private JTextField jtxtDesconto;
    private JTextField jtxtValorTotal;
    private JLabel jlPecas;
    private JComboBox jcbPecaVendida;
    private JLabel jlQtd;
    private JTextField jtxtQtd;
    private JLabel jlCodigoPeca;
    private JTextField jtxtCodigoPeca;
    private JButton jbutAdicionarMais;
    private JPanel jpVP3;
    private JPanel jpVP4;
    private JLabel jlListagem;
    private JLabel jlTotal;
    private JLabel jlSeleVende;

    private MVendaPecas vendaAtual = new MVendaPecas();
    private StringBuilder itensSelecionados = new StringBuilder(" Listagem   de  Peças   Selecionadas: <br>");

    public Vendas_Pecas() {

        setSize(890, 310);
        setExtendedState(JFrame.MAXIMIZED_HORIZ);
        setResizable(true);
        setContentPane(painelVendaPecas);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setVisible(true);

        desabilita();

        preenchaComboClientes();
        preencherComboVendedor();
        preencherComboPecas();
        preencherDataAtual();
        NovoCodigoVenda();

        jbutNovaVenda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                habilitar();

            }
        });
        jbutSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

            }
        });
        jbutDeletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
                limparListagemETotal();
                desabilita();
                jbutNovaVenda.setEnabled(true);
                vendaAtual = new MVendaPecas();

            }
        });
        jbutSalvarVenda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                // Pegar os valores dos campos
                vendaAtual.setCodigoVenda(jtxtCodigoVenda.getText());
                vendaAtual.setIdVendedor(jcbVendedor.getSelectedIndex() + 1);
                vendaAtual.setIdCliente(jcbClientes.getSelectedIndex() + 1);

                try {
                    SimpleDateFormat br = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat mysqlFor = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = br.parse(jtxtDataVenda.getText());
                    String mysqlDate = mysqlFor.format(date);
                    vendaAtual.setDataVenda(mysqlDate);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Data inválida. Use o formato dd/MM/yyyy");
                    return;
                }

                // Determina forma de pagamento
                if (rbPix.isSelected()) {
                    vendaAtual.setFormaPagamento("PIX");
                } else if (rbCartaoCredito.isSelected()) {
                    vendaAtual.setFormaPagamento("Cartão de Crédito");
                } else if (rbCartaoDebito.isSelected()) {
                    vendaAtual.setFormaPagamento("Cartão de Débito");
                } else if (rbBoleto.isSelected()) {
                    vendaAtual.setFormaPagamento("Boleto");
                }

                // Pegar valor total e desconto

                String valorTxt = jlTotal.getText();
                if (valorTxt.startsWith("Total: R$ ")) {
                    valorTxt = valorTxt.substring("Total: R$ ".length());
                }
                valorTxt = valorTxt.replace(".", "").replace(",", ".");

                try {
                    double valorTotal = Double.parseDouble(valorTxt);
                    vendaAtual.setValorTotal(valorTotal);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erro: Valor total inválido!");
                    return;
                }

                String descontoStr = jtxtDesconto.getText().replace("%", "").trim();
                if (descontoStr.isEmpty()) {
                    vendaAtual.setDesconto(0.0);
                } else {
                    try {
                        vendaAtual.setDesconto(Double.parseDouble(descontoStr));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Valor de desconto inválido! Usando 0%");
                        vendaAtual.setDesconto(0.0);
                    }
                }

                // Validar campos obrigatórios
                if (vendaAtual.getIdVendedor() == 0
                        || vendaAtual.getIdCliente() == 0
                        || vendaAtual.getFormaPagamento() == null
                        || vendaAtual.getFormaPagamento().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
                    return;
                }

                if (vendaAtual.getPV().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Adicione pelo menos um item à venda!");
                    return;
                }
                // Salvar venda
                VendaPecasDAO vpDAO = new VendaPecasDAO();
                vpDAO.SalvarVendas(vendaAtual);

                // Zerar a listagem e o total
                limparListagemETotal();

                NovoCodigoVenda();

                // Limpar campos e desabilitar
                limparCampos();
                desabilita();
                jbutNovaVenda.setEnabled(true);

                vendaAtual = new MVendaPecas();

            }
        });

        jcbPecaVendida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nomeSelecionado = (String) jcbPecaVendida.getSelectedItem();

                if (nomeSelecionado != null) {

                    try {

                        String sql = "SELECT preco_Venda FROM Pecas WHERE nome = ?";

                        PreparedStatement ps = FabricaConexao.conectar().prepareStatement(sql);
                        ps.setString(1, nomeSelecionado);
                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {

                            double preco = rs.getDouble("preco_Venda");
                            jtxtValorpeca.setText(String.format("%.2f", preco));

                        } else {

                            jtxtValorpeca.setText("Não encontrado");

                            rs.close();
                            ps.close();

                        }

                    } catch (SQLException ex) {

                        JOptionPane.showMessageDialog(null, "Erro ao encontrar Peças");

                    }
                }
            }
        });
        jcbPecaVendida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String codigoSelecionado = (String) jcbPecaVendida.getSelectedItem();

                if (codigoSelecionado != null) {
                    try {
                        String sql = "SELECT codigo FROM Pecas WHERE nome = ?";

                        PreparedStatement ps = FabricaConexao.conectar().prepareStatement(sql);
                        ps.setString(1, codigoSelecionado);
                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {

                            String codigo = rs.getString("codigo");
                            jtxtCodigoPeca.setText(codigo);

                        } else {

                            jtxtCodigoPeca.setText("Não encontrado");

                            rs.close();
                            ps.close();
                        }

                    } catch (SQLException ex) {

                        JOptionPane.showMessageDialog(null, "Erro ao encontrar Peças");
                    }
                }
            }
        });


        rbPix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtxtDesconto.setText("10%");

            }
        });
        rbCartaoCredito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtxtDesconto.setText("5%");

            }
        });
        rbCartaoDebito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtxtDesconto.setText("5%");

            }
        });
        rbBoleto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtxtDesconto.setText("0");

            }
        });

        jbutAdicionarMais.addActionListener(new ActionListener() {
            private double valorTotal = 0.0;

            @Override
            public void actionPerformed(ActionEvent e) {

                String item1 = (String) jcbPecaVendida.getSelectedItem();
                String descontoStr = jtxtQtd.getText().trim();
                String quantidade = jtxtQtd.getText().trim();
                String valorUnitario = jtxtValorpeca.getText().trim();
                String valorTxt = jtxtValorTotal.getText().trim();

                if (!valorTxt.isEmpty() && !quantidade.isEmpty()) {
                    valorTxt = valorTxt.replace(".", "").replace(",", ".");

                    try {double valorUnitDouble = Double.parseDouble(valorUnitario.replace(".", "").replace(",", "."));

                        int qtd = Integer.parseInt(quantidade);

                        MVendaPecas.PecasVendidas pv = new MVendaPecas.PecasVendidas();
                        pv.setCodigoPeca(jtxtCodigoPeca.getText());
                        pv.setCodigoVenda(jtxtCodigoVenda.getText());
                        pv.setQuantidade(qtd);
                        pv.setValorUnitario(valorUnitDouble);

                        double valorAtual = Double.parseDouble(valorTxt);
                        valorTotal += valorAtual;

                        DecimalFormat df = new DecimalFormat("#,##0.00");
                        String valorFormatado = df.format(valorAtual);

                        vendaAtual.getPV().add(pv);

                        // Atualiza a listagem
                        itensSelecionados.append(item1 ).append(" | Qtd: ").append( descontoStr).append(" | R$ ").append(valorFormatado).append("<br>");
                        jlListagem.setText("<html>" + itensSelecionados.toString() + "</html>");

                        // Atualiza o total
                        jlTotal.setText("Total: R$ " + df.format(valorTotal));

                        // Limpa os campos após adicionar
                        limparCamposAdicao();


                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Valor inválido!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Preencha o campo Valor Total");
                    jtxtValorTotal.requestFocus();
                }

            }
        });

        jtxtQtd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    // Primeiro substituímos a vírgula por ponto no valor da peça
                    String valorPecaStr = jtxtValorpeca.getText().replace(".", "").replace(",", ".");
                    double valorUnitario = Double.parseDouble(valorPecaStr);

                    // Verifica se o campo quantidade não está vazio
                    String qtdStr = jtxtQtd.getText().trim();
                    int quantidade = qtdStr.isEmpty() ? 0 : Integer.parseInt(qtdStr);

                    // Trata o desconto, removendo o símbolo % e convertendo
                    String descontoStr = jtxtDesconto.getText().replace("%", "").trim();
                    double desconto = descontoStr.isEmpty() ? 0 : Double.parseDouble(descontoStr);

                    // Calcula o total
                    double subtotal = valorUnitario * quantidade;
                    double totalComDesconto = subtotal - (subtotal * (desconto / 100.0));

                    // Formata o resultado usando vírgula como separador decimal
                    DecimalFormat df = new DecimalFormat("#,##0.00");
                    jtxtValorTotal.setText(df.format(totalComDesconto));
                } catch (NumberFormatException e) {
                    jtxtValorTotal.setText("0,00");
                }
            }
        });
    }
    public void preenchaComboClientes() {

        try {

            MClientes cliente = new MClientes();

            ClientesDAO cliDAO = new ClientesDAO();

            ResultSet rs = cliDAO.ListaClientes();

            ArrayList<String> ListaClientes = new ArrayList<>();

            while (rs.next()) {

                cliente.setNome(rs.getNString("nome"));
                cliente.setCpf(rs.getNString("cpf"));
                cliente.setId(Integer.parseInt(rs.getString("id_cliente")));
                ListaClientes.add(cliente.getId() + " - " + cliente.getNome() + " - " + cliente.getCpf());
            }

            for (String percorrer : ListaClientes) {

                jcbClientes.addItem(percorrer);
            }
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }
    public void preencherComboVendedor() {

        try {

            MFuncionarios func = new MFuncionarios();

            FuncionariosDAO funcDAO = new FuncionariosDAO();

            ResultSet rs = funcDAO.ListaFuncionarios();

            ArrayList<String> ListaFuncionarios = new ArrayList<>();

            while (rs.next()) {

                func.setNome(rs.getNString("nome"));
                func.setCpf(rs.getNString("cpf"));
                func.setId(Integer.parseInt(rs.getString("id_funcionario")));
                ListaFuncionarios.add(func.getId() + " - " + func.getNome() + " - " + func.getCpf());
            }

            for (String percorrer : ListaFuncionarios) {

                jcbVendedor.addItem(percorrer);
            }
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }
    public void preencherComboPecas() {

        try {

            MPecas peca = new MPecas();

            PecasDAO pecaDAO = new PecasDAO();

            ResultSet rs = pecaDAO.ListaPecas();

            ArrayList<String> ListaPecas = new ArrayList<>();

            while (rs.next()) {

                peca.setNomePeca(rs.getNString("nome"));
                ListaPecas.add(peca.getNomePeca());
            }

            for (String percorrer : ListaPecas) {

                jcbPecaVendida.addItem(percorrer);
            }
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }
    private void preencherDataAtual() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataAtual = sdf.format(new Date());
        jtxtDataVenda.setText(dataAtual);
    }
    private void NovoCodigoVenda() {
        try {
            String sql = "SELECT codigo_Venda FROM VendasPecas ORDER BY codigo_Venda DESC LIMIT 1";

            PreparedStatement ps = FabricaConexao.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            String novoCodigo = "VP001"; // Código inicial

            if (rs.next()) {
                String ultimoCodigo = rs.getString("codigo_Venda");
                if (ultimoCodigo != null && ultimoCodigo.startsWith("VP")) {
                    // Extrai o número do código e incrementa
                    String numeroStr = ultimoCodigo.substring(3);
                    try {
                        int numero = Integer.parseInt(numeroStr);
                        novoCodigo = String.format("VP%03d", numero + 1);
                    } catch (NumberFormatException e) {
                        // Se não conseguir converter, usa o código padrão
                        novoCodigo = "VP001";
                    }
                }
            }

            jtxtCodigoVenda.setText(novoCodigo);

            rs.close();
            ps.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar código de venda: " + e.getMessage());
        }
    }

    public void limparCampos() {
        rbPix.setSelected(false);
        rbCartaoCredito.setSelected(false);
        rbCartaoDebito.setSelected(false);
        rbBoleto.setSelected(false);

        jtxtDesconto.setText("");
        jtxtValorpeca.setText("");
        jtxtValorTotal.setText("");
        jtxtQtd.setText("");
        jtxtCodigoPeca.setText("");

        // Limpa a listagem e o total
        limparListagemETotal();

        // Reseta os comboboxes
        if (jcbVendedor.getItemCount() > 0) jcbVendedor.setSelectedIndex(0);
        if (jcbClientes.getItemCount() > 0) jcbClientes.setSelectedIndex(0);
        if (jcbPecaVendida.getItemCount() > 0) jcbPecaVendida.setSelectedIndex(0);

    }
    public void desabilita() {
        jcbVendedor.setEnabled(false);
        jcbClientes.setEnabled(false);
        jcbPecaVendida.setEnabled(false);
        rbPix.setEnabled(false);
        rbCartaoCredito.setEnabled(false);
        rbCartaoDebito.setEnabled(false);
        rbBoleto.setEnabled(false);
        jtxtCodigoVenda.setEnabled(false);
        jtxtCodigoPeca.setEnabled(false);
        jtxtDataVenda.setEnabled(false);
        jtxtDesconto.setEnabled(false);
        jtxtValorpeca.setEnabled(false);
        jtxtValorTotal.setEnabled(false);
        jtxtQtd.setEnabled(false);
        jbutDeletar.setEnabled(false);
        jbutAdicionarMais.setEnabled(false);
        jbutSalvarVenda.setEnabled(false);
    }
    public void habilitar() {
        jcbVendedor.setEnabled(true);
        jcbClientes.setEnabled(true);
        jcbPecaVendida.setEnabled(true);
        rbPix.setEnabled(true);
        rbCartaoCredito.setEnabled(true);
        rbCartaoDebito.setEnabled(true);
        rbBoleto.setEnabled(true);
        jtxtCodigoVenda.setEnabled(true);
        jtxtCodigoPeca.setEnabled(true);
        jtxtDataVenda.setEnabled(true);
        jtxtQtd.setEnabled(true);
        jtxtDesconto.setEnabled(true);
        jtxtValorpeca.setEnabled(true);
        jtxtValorTotal.setEnabled(true);
        jbutDeletar.setEnabled(true);
        jbutAdicionarMais.setEnabled(true);
        jbutSalvarVenda.setEnabled(true);
        jbutNovaVenda.setEnabled(false);
    }
    private void limparCamposAdicao() {
        jtxtValorTotal.setText("");
        jtxtQtd.setText("");
        rbPix.setSelected(false);
        rbCartaoCredito.setSelected(false);
        rbCartaoDebito.setSelected(false);
        rbBoleto.setSelected(false);
    }
    private void limparListagemETotal() {
        itensSelecionados.setLength(0);
        itensSelecionados.append(" Listagem de Peças Selecionadas: <br>");

        jlListagem.setText("<html>" + itensSelecionados.toString() + "</html>");

        jlTotal.setText("Total: R$ 0,00");
    }

}
