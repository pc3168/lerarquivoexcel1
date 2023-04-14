package br.com.pc.lerarquivo.excel.layout;

import java.time.LocalDate;
import java.util.List;

public abstract class Layout {

    protected LocalDate data;
    protected String protocolo;
    protected double valor;
    protected String tipo;
    protected String identificacao;
    protected String dadosNaoUtilizados;
    public abstract List<?> armazenarDados(List<String> dados);
    public abstract String[] getCabecalho();

    @Override
    public String toString() {
        return     "" + data
                + ";" + identificacao
                + ";" + protocolo
                + ";" + String.valueOf(valor).replace(".",",")
                + ";" + tipo
                + ";" + dadosNaoUtilizados;
    }

    public abstract String getNumeroConta();

    public LocalDate getData() {
        return data;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public double getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public String getDadosNaoUtilizados() {
        return dadosNaoUtilizados;
    }
}
