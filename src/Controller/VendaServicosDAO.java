package Controller;

import DAO.FabricaConexao;
import Model.MVendaServicos;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VendaServicosDAO {

    public boolean SalvarVendas(MVendaServicos venda) {

        Connection conn = null ;
        PreparedStatement ps = null;
        PreparedStatement psPV = null;

      try {
        conn = FabricaConexao.conectar();
        conn.setAutoCommit(false);

        String sql = "INSERT INTO VendaServicos (codigo_Venda, fk_id_Vendedor, fk_id_Cliente,fk_placa, data_Venda,previsao_Entrega, forma_Pagamento, desconto , valor_Total) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ? , ?)";

        ps = conn.prepareStatement(sql);

        ps.setString(1, venda.getCodigoVenda());
        ps.setInt(2, venda.getIdVendedor());
        ps.setInt(3, venda.getIdCliente());
        ps.setString(4, venda.getPlaca());
        ps.setString(5, venda.getDataVenda());
        ps.setString(6,venda.getPreviEntrega());
        ps.setString(7, venda.getFormaPagamento());
        ps.setString(8, venda.getDesconto());
        ps.setDouble(9, venda.getValorTotal());

        ps.executeUpdate();

        String sqlPV = "INSERT INTO VendaServicos_Tem_Servicos (fk_codigo_Venda, fk_codigo_Servico,diagnostico_Tecnico, valor_Unitario, status) VALUES (?, ?, ?, ?, ?)";
        psPV = conn.prepareStatement(sqlPV);

        for (MVendaServicos.ServicosVendidos pv : venda.getSV()) {
            psPV.setString(1, venda.getCodigoVenda());
            psPV.setString(2, pv.getCodigoServico());
            psPV.setString(3, pv.getDiagTecnico());
            psPV.setDouble(4, pv.getValorUnitario());
            psPV.setString(5, pv.getStatus() != null ? pv.getStatus() : "Ativo");
            psPV.executeUpdate();
        }

        conn.commit();
        JOptionPane.showMessageDialog(null, "Venda realizada com sucesso!");

    } catch (
    SQLException e) {
        try {

            if (conn != null) {
                conn.rollback();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "Erro ao salvar venda: " + e.getMessage());
        e.printStackTrace();

    } finally {
        try {
            if (ps != null) ps.close();
            if (psPV != null) psPV.close();
            if (conn != null) conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        return false;
    }

    public boolean atualizarStatus(String codigoVenda, String codigoServico, String novoStatus) {
        String sql = "UPDATE VendaServicos_Tem_Servicos SET status = ? WHERE fk_codigo_Venda = ? AND fk_codigo_Servico = ?";
        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, novoStatus);
            ps.setString(2, codigoVenda);
            ps.setString(3, codigoServico);

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
