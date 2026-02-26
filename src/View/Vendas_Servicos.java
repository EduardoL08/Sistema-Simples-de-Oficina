package View;

import Controller.*;
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

public class Vendas_Servicos extends JFrame{
    public JPanel painelVS;
    private JPanel jpVendasServicos;
    private JLabel jlVendasServ;

    private JPanel jpVS1;
    private JLabel jlDataVenda;
    private JLabel jlSeleVendedor;
    private JLabel jlCodigoVenda;
    private JLabel jlCodigoServico;
    private JTextField jtxtCodigoServico;
    private JComboBox jcbVendedor;
    private JTextField jtxtDataVenda;
    private JTextField jtxtCodigoVenda;

    private JPanel jpVS2;
    private JLabel jlSeleCliente;
    private JLabel jlFormaPagamento;
    private JComboBox jcbClientes;
    private JRadioButton rbPix;
    private JRadioButton rbCartaoCredito;
    private JRadioButton rbCartaoDebito;
    private JRadioButton rbBoleto;

    private JPanel jpVS3;
    private JLabel jlPlacaVeiculo;
    private JLabel jlServiExecut;
    private JLabel jlDiagTec;
    private JLabel jlValorServico;
    private JLabel jlDesconto;
    private JLabel jlValorTotal;
    private JLabel jlDataPrevEntre;
    private JTextField jtxtPlacaVeiculo;
    private JComboBox jcbServiExecu;
    private JTextField jtxtDescriProble;
    private JTextField jtxtDiagTecni;
    private JTextField jtxtValorServico;
    private JTextField jtxtDesconto;
    private JTextField jtxtValorTotal;
    private JTextField jtxtPreviEntrega;

    private JPanel jpVS4;
    private JLabel jlListagem;
    private JLabel jlTotal;

    private JPanel jpVSBotoes;
    private JButton jbutNovoServico;
    private JButton jbutAdicionarMais;
    private JButton jbutDeletar;
    private JButton jbutSalvarServico;
    private JButton jbutSair;
    private JLabel jlStatus;
    private JTextField jtxtStatus;

    private MVendaServicos vendaAtual = new MVendaServicos();
    private StringBuilder itensSelecionados = new StringBuilder(" Listagem de Serviços Selecionadas: <br>");
    private double valorTotal = 0.0;

    public Vendas_Servicos() {

        setSize(800,400);
        setUndecorated(true);
        setContentPane(painelVS);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        desabilita();
        preenchaComboClientes();
        preencherComboVendedor();
        ListaServicos();
        preencherDataAtual();
        NovoCodigoVenda();

        jbutNovoServico.addActionListener(new ActionListener() {
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
                valorTotal = 0.0;
                desabilita();
                jbutNovoServico.setEnabled(true);
                vendaAtual = new MVendaServicos();
            }
        });
        jbutSalvarServico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                vendaAtual.setCodigoVenda(jtxtCodigoVenda.getText());
                vendaAtual.setIdVendedor(jcbVendedor.getSelectedIndex() + 1);
                vendaAtual.setIdCliente(jcbClientes.getSelectedIndex() + 1);
                vendaAtual.setPlaca(jtxtPlacaVeiculo.getText());

                try {
                    SimpleDateFormat br = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat mysqlFor = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = br.parse(jtxtDataVenda.getText());
                    vendaAtual.setDataVenda(mysqlFor.format(date));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Data inválida. Use o formato dd/MM/yyyy");
                    return;
                }

                try {
                    SimpleDateFormat br = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat mysqlFor = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = br.parse(jtxtPreviEntrega.getText());
                    vendaAtual.setPreviEntrega(mysqlFor.format(date));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Data inválida. Use o formato dd/MM/yyyy");
                    return;
                }

                if (rbPix.isSelected()) vendaAtual.setFormaPagamento("PIX");
                else if (rbCartaoCredito.isSelected()) vendaAtual.setFormaPagamento("Cartão de Crédito");
                else if (rbCartaoDebito.isSelected()) vendaAtual.setFormaPagamento("Cartão de Débito");
                else if (rbBoleto.isSelected()) vendaAtual.setFormaPagamento("Boleto");

                String valorTxt = jlTotal.getText().replace("Total: R$ ", "").replace(".", "").replace(",", ".");
                try {
                    vendaAtual.setValorTotal(Double.parseDouble(valorTxt));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erro: Valor total inválido!");
                    return;
                }

                String descontoStr = jtxtDesconto.getText().trim();
                vendaAtual.setDesconto(descontoStr.isEmpty() ? "0%" : descontoStr);

                if (vendaAtual.getIdVendedor() == 0 || vendaAtual.getIdCliente() == 0 ||
                        vendaAtual.getFormaPagamento() == null || vendaAtual.getFormaPagamento().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
                    return;
                }

                if (vendaAtual.getSV().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Adicione pelo menos um item à lista de venda!");
                    return;
                }

                VendaServicosDAO vsDAO = new VendaServicosDAO();
                vsDAO.SalvarVendas(vendaAtual);

                limparListagemETotal();
                valorTotal = 0.0;
                NovoCodigoVenda();
                limparCampos();
                desabilita();
                jbutNovoServico.setEnabled(true);
                vendaAtual = new MVendaServicos();
            }
        });

        jcbServiExecu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nomeSelecionado = (String) jcbServiExecu.getSelectedItem();

                if (nomeSelecionado != null){

                    try {

                        String sql = "SELECT preco FROM Servicos WHERE descricao = ?";

                        PreparedStatement ps = FabricaConexao.conectar().prepareStatement(sql);
                        ps.setString(1, nomeSelecionado);
                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {

                            double preco = rs.getDouble("preco");
                            jtxtValorServico.setText(String.format("%.2f",preco));

                            // Calculate total with discount
                            String descontoStr = jtxtDesconto.getText().replace("%", "").trim();
                            double desconto = descontoStr.isEmpty() ? 0 : Double.parseDouble(descontoStr);
                            double totalComDesconto = preco * (1 - (desconto / 100.0));

                            // Format and display total
                            DecimalFormat df = new DecimalFormat("#,##0.00");
                            jtxtValorTotal.setText(df.format(totalComDesconto));

                        } else {

                            jtxtValorServico.setText("Valor não encontrado");
                            jtxtValorTotal.setText("");

                            rs.close();
                            ps.close();
                        }

                    } catch (SQLException ex){

                        JOptionPane.showMessageDialog(null,"Erro ao encontrar Servicos");

                    }
                }
            }
        });
        jcbServiExecu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nomeSelecionado = (String) jcbServiExecu.getSelectedItem();

                if (nomeSelecionado != null){

                    try {

                        String sql = "SELECT codigo_Servico FROM Servicos WHERE descricao = ?";

                        PreparedStatement ps = FabricaConexao.conectar().prepareStatement(sql);
                        ps.setString(1, nomeSelecionado);
                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {

                            String codigo = rs.getString("codigo_Servico");
                            jtxtCodigoServico.setText(codigo);

                        }else{

                            jtxtCodigoServico.setText("Codigo não encontrado");

                            rs.close();
                            ps.close();

                        }

                    } catch (SQLException ex){

                        JOptionPane.showMessageDialog(null,"Erro ao encontrar Servicos");

                    }
                }
            }
        });
        jcbClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nomeSelecionado = (String) jcbClientes.getSelectedItem();

                if (nomeSelecionado != null){

                    try {

                        String sql = "SELECT placa FROM Veiculos WHERE fk_id_Cliente = ?";

                        PreparedStatement ps = FabricaConexao.conectar().prepareStatement(sql);
                        ps.setString(1, nomeSelecionado);
                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {

                            String placa = rs.getString("placa");
                            jtxtPlacaVeiculo.setText(placa);

                        }else{

                            jtxtPlacaVeiculo.setText("Placa não Encontrada");

                            rs.close();
                            ps.close();

                        }

                    } catch (SQLException ex){

                        JOptionPane.showMessageDialog(null,"Erro ao encontrar Veiculo");

                    }
                }
            }
        });



        rbPix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbPix.isSelected()) {
                    rbCartaoCredito.setSelected(false);
                    rbCartaoDebito.setSelected(false);
                    rbBoleto.setSelected(false);
                    rbPix.setSelected(true);
                    jtxtDesconto.setEnabled(true);
                    jtxtValorTotal.setEnabled(true);
                    jtxtDesconto.setText("10%");
                    JOptionPane.showMessageDialog(null, "Pagamento via PIX selecionado - Desconto de 10%!", "Desconto Aplicado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    jtxtDesconto.setEnabled(false);
                    jtxtValorTotal.setEnabled(false);
                }
            }
        });
        rbCartaoCredito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbCartaoCredito.isSelected()) {
                    rbPix.setSelected(false);
                    rbCartaoDebito.setSelected(false);
                    rbBoleto.setSelected(false);
                    rbCartaoCredito.setSelected(true);
                    jtxtDesconto.setEnabled(true);
                    jtxtValorTotal.setEnabled(true);
                    jtxtDesconto.setText("5%");
                    JOptionPane.showMessageDialog(null, "Pagamento com Cartão de Crédito selecionado - Desconto de 5%!", "Desconto Aplicado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    jtxtDesconto.setEnabled(false);
                    jtxtValorTotal.setEnabled(false);
                }
            }
        });
        rbCartaoDebito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbCartaoDebito.isSelected()) {
                    rbPix.setSelected(false);
                    rbCartaoCredito.setSelected(false);
                    rbBoleto.setSelected(false);
                    rbCartaoDebito.setSelected(true);
                    jtxtDesconto.setEnabled(true);
                    jtxtValorTotal.setEnabled(true);
                    jtxtDesconto.setText("5%");
                    JOptionPane.showMessageDialog(null, "Pagamento com Cartão de Débito selecionado - Desconto de 5%!", "Desconto Aplicado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    jtxtDesconto.setEnabled(false);
                    jtxtValorTotal.setEnabled(false);
                }
            }
        });
        rbBoleto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rbBoleto.isSelected()) {
                    rbPix.setSelected(false);
                    rbCartaoCredito.setSelected(false);
                    rbCartaoDebito.setSelected(false);
                    rbBoleto.setSelected(true);
                    jtxtDesconto.setEnabled(true);
                    jtxtValorTotal.setEnabled(true);
                    jtxtDesconto.setText("0");
                    JOptionPane.showMessageDialog(null, "Pagamento com Boleto selecionado - Sem desconto aplicado", "Sem Desconto", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    jtxtDesconto.setEnabled(false);
                    jtxtValorTotal.setEnabled(false);
                }
            }
        });

        jbutAdicionarMais.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String item1 = (String) jcbServiExecu.getSelectedItem();
                String valorTxt = jtxtValorTotal.getText().trim();

                if (!valorTxt.isEmpty()) {
                    try {
                        valorTxt = valorTxt.replace(".", "").replace(",", ".");
                        double valorAtual = Double.parseDouble(valorTxt);

                        MVendaServicos.ServicosVendidos sv = new MVendaServicos.ServicosVendidos();
                        sv.setCodigoServico(jtxtCodigoServico.getText());
                        sv.setCodigoVenda(jtxtCodigoVenda.getText());
                        sv.setValorUnitario(valorAtual);
                        sv.setDiagTecnico(jtxtDiagTecni.getText());

                        valorTotal += valorAtual;

                        DecimalFormat df = new DecimalFormat("#,##0.00");
                        String valorFormatado = df.format(valorAtual);

                        vendaAtual.getSV().add(sv);

                        // Atualiza a listagem
                        itensSelecionados.append(item1).append("<br>R$ ").append(valorFormatado).append("<br>");
                        jlListagem.setText("<html>" + itensSelecionados.toString() + "</html>");

                        // Atualiza o total
                        jlTotal.setText("Total: R$ " + df.format(valorTotal));

                        // Limpa os campos após adicionar
                        limparCamposAdicao();

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Valor inválido!");
                    }
                }

            }
        });
        jtxtDesconto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!rbPix.isSelected() && !rbCartaoCredito.isSelected() && !rbCartaoDebito.isSelected() && !rbBoleto.isSelected()) {
                        JOptionPane.showMessageDialog(null, "Selecione uma forma de pagamento primeiro", "Atenção", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    String valorServicoStr = jtxtValorServico.getText().replace(".", "").replace(",", ".");
                    double valorServico = Double.parseDouble(valorServicoStr);

                    // Trata o desconto, removendo o símbolo % e convertendo
                    String descontoStr = jtxtDesconto.getText().replace("%", "").trim();
                    double desconto = descontoStr.isEmpty() ? 0 : Double.parseDouble(descontoStr);

                    // Calcula o valor com desconto
                    double totalComDesconto = valorServico * (1 - (desconto / 100.0));

                    // Formata o resultado usando vírgula como separador decimal
                    DecimalFormat df = new DecimalFormat("#,##0.00");
                    jtxtValorTotal.setText(df.format(totalComDesconto));

                } catch (NumberFormatException ex) {
                    jtxtValorTotal.setText("0,00");

                    JOptionPane.showMessageDialog(null,"Verifique se vc marcou uma forma de pagamento","Erro",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    
    public  void preenchaComboClientes(){

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

            for (String percorrer : ListaClientes ) {

                jcbClientes.addItem(percorrer);
            }
        }catch (SQLException e){

            throw new RuntimeException(e);
        }
    }
    public void preencherComboVendedor(){

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

            for (String percorrer : ListaFuncionarios ) {

                jcbVendedor.addItem(percorrer);
            }
        }catch (SQLException e){

            throw new RuntimeException(e);
        }
    }
    public void ListaServicos(){

        try {

            String sql = " SELECT descricao FROM Servicos ";

            PreparedStatement ps = FabricaConexao.conectar().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                jcbServiExecu.addItem(rs.getNString("descricao"));

            }
            rs.close();
            ps.close();

            }catch (SQLException e){

            JOptionPane.showMessageDialog(this,"Erro ao carregar Serviços");
        }
    }
    private void preencherDataAtual() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataAtual = sdf.format(new Date());
        jtxtDataVenda.setText(dataAtual);
    }
    private void NovoCodigoVenda() {
        try {
            String sql = "SELECT codigo_Venda FROM VendaServicos ORDER BY codigo_Venda DESC LIMIT 1";

            PreparedStatement ps = FabricaConexao.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            String novoCodigo = "VS001"; // Código inicial

            if (rs.next()) {
                String ultimoCodigo = rs.getString("codigo_Venda");
                if (ultimoCodigo != null && ultimoCodigo.startsWith("VS")) {
                    // Extrai o número do código e incrementa
                    String numeroStr = ultimoCodigo.substring(3);
                    try {
                        int numero = Integer.parseInt(numeroStr);
                        novoCodigo = String.format("VS%03d", numero + 1);
                    } catch (NumberFormatException e) {
                        // Se não conseguir converter, usa o código padrão
                        novoCodigo = "VS001";
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


    public void limparCampos(){
        rbPix.setSelected(false);
        rbCartaoCredito.setSelected(false);
        rbCartaoDebito.setSelected(false);
        rbBoleto.setSelected(false);

        jtxtDesconto.setText("");
        jtxtValorServico.setText("");
        jtxtValorTotal.setText("");
        jtxtPlacaVeiculo.setText("");
        jtxtCodigoServico.setText("");
        jtxtDiagTecni.setText("");
        jtxtPreviEntrega.setText("");


        limparListagemETotal();
        
        // Reseta os comboboxes
        if (jcbVendedor.getItemCount() > 0) jcbVendedor.setSelectedIndex(0);
        if (jcbClientes.getItemCount() > 0) jcbClientes.setSelectedIndex(0);
        if (jcbServiExecu.getItemCount() > 0) jcbServiExecu.setSelectedIndex(0);
    }
    public void desabilita(){
        jcbVendedor.setEnabled(false);
        jcbClientes.setEnabled(false);
        jcbServiExecu.setEnabled(false);
        rbPix.setEnabled(false);
        rbCartaoCredito.setEnabled(false);
        rbCartaoDebito.setEnabled(false);
        rbBoleto.setEnabled(false);
        jtxtCodigoVenda.setEnabled(false);
        jtxtDataVenda.setEnabled(false);
        jtxtPreviEntrega.setEnabled(false);
        jtxtValorServico.setEnabled(false);
        jtxtPlacaVeiculo.setEnabled(false);
        jtxtCodigoServico.setEnabled(false);
        jtxtDesconto.setEnabled(false);
        jtxtDiagTecni.setEnabled(false);
        jtxtValorTotal.setEnabled(false);
        jbutDeletar.setEnabled(false);
        jbutAdicionarMais.setEnabled(false);
        jbutSalvarServico.setEnabled(false);
    }
    public void habilitar(){
        jcbVendedor.setEnabled(true);
        jcbClientes.setEnabled(true);
        jcbServiExecu.setEnabled(true);
        rbPix.setEnabled(true);
        rbCartaoCredito.setEnabled(true);
        rbCartaoDebito.setEnabled(true);
        rbBoleto.setEnabled(true);
        jtxtCodigoVenda.setEnabled(true);
        jtxtDataVenda.setEnabled(true);
        jtxtPreviEntrega.setEnabled(true);
        jtxtValorServico.setEnabled(true);
        jtxtDesconto.setEnabled(true);
        jtxtDiagTecni.setEnabled(true);
        jtxtValorTotal.setEnabled(true);
        jbutDeletar.setEnabled(true);
        jbutAdicionarMais.setEnabled(true);
        jbutSalvarServico.setEnabled(true);
        jbutNovoServico.setEnabled(false);
        jtxtPlacaVeiculo.setEnabled(false);
        jtxtCodigoServico.setEnabled(false);
    }
    private void limparCamposAdicao() {
        jtxtValorTotal.setText("");
        jtxtDiagTecni.setText("");
        jtxtValorServico.setText("");
        rbPix.setEnabled(false);
        rbCartaoCredito.setEnabled(false);
        rbCartaoDebito.setEnabled(false);
        rbBoleto.setEnabled(false);
    }
    private void limparListagemETotal() {
        // Reinicia a StringBuilder
        itensSelecionados.setLength(0);
        itensSelecionados.append(" Listagem   de   Serviços   Selecionadas: <br>");

        // Atualiza o label de listagem
        jlListagem.setText("<html>" + itensSelecionados.toString() + "</html>");

        // Reinicia o total
        jlTotal.setText("Total: R$ 0,00");
    }
}
