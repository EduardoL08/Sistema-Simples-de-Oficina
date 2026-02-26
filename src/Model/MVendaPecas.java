package Model;

import java.util.ArrayList;
import java.util.List;

public class MVendaPecas {

    private String codigoVenda;
    private int idVendedor;
    private int idCliente;
    private String formaPagamento;
    private String dataVenda;
    private double valorTotal;
    private double desconto;
    private List<PecasVendidas> PV = new ArrayList<>();

    public List<PecasVendidas> getPV() {
        return PV;
    }
    public void setPV(List<PecasVendidas> PV) {
        this.PV = PV;
    }
    public String getCodigoVenda() {
        return codigoVenda;
    }
    public void setCodigoVenda(String codigoVenda) {
        this.codigoVenda = codigoVenda;
    }
    public int getIdVendedor() {
        return idVendedor;
    }
    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }
    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    public String getFormaPagamento() {
        return formaPagamento;
    }
    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    public String getDataVenda() {
        return dataVenda;
    }
    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }
    public double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    public double getDesconto() {
        return desconto;
    }
    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public static class PecasVendidas {

        private int id;
        private String codigoVenda;
        private String codigoPeca;
        private int quantidade;
        private double valorUnitario;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCodigoVenda() {
            return codigoVenda;
        }

        public void setCodigoVenda(String codigoVenda) {
            this.codigoVenda = codigoVenda;
        }

        public String getCodigoPeca() {
            return codigoPeca;
        }

        public void setCodigoPeca(String codigoPeca) {
            this.codigoPeca = codigoPeca;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }

        public double getValorUnitario() {
            return valorUnitario;
        }

        public void setValorUnitario(double valorUnitario) {
            this.valorUnitario = valorUnitario;
        }
    }

}
