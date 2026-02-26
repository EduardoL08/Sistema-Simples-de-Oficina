package Model;

import java.util.ArrayList;
import java.util.List;

public class MVendaServicos {

    private String codigoVenda;
    private int idVendedor;
    private int idCliente;
    private String placa;
    private String formaPagamento;
    private String dataVenda;
    private double valorTotal;
    private String desconto;
    private String previEntrega;
    private List<ServicosVendidos> SV = new ArrayList<>();


    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getPreviEntrega() {
        return previEntrega;
    }

    public void setPreviEntrega(String previEntrega) {
        this.previEntrega = previEntrega;
    }

    public List<ServicosVendidos> getSV() {
        return SV;
    }

    public void setSV(List<ServicosVendidos> SV) {
        this.SV = SV;
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

    public String getDesconto() {
        return desconto;
    }

    public void setDesconto(String desconto) {
        this.desconto = desconto;
    }

    public static class ServicosVendidos {

        private int id;
        private String codigoVenda;
        private String codigoServico;
        private String diagTecnico;
        private double valorUnitario;
        private String status;

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

        public String getCodigoServico() {
            return codigoServico;
        }

        public void setCodigoServico(String codigoServico) {
            this.codigoServico = codigoServico;
        }

        public double getValorUnitario() {
            return valorUnitario;
        }

        public void setValorUnitario(double valorUnitario) {
            this.valorUnitario = valorUnitario;
        }

        public String getDiagTecnico() {
            return diagTecnico;
        }

        public void setDiagTecnico(String diagTecnico) {
            this.diagTecnico = diagTecnico;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
