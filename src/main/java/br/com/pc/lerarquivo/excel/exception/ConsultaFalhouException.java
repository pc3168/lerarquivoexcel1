package br.com.pc.lerarquivo.excel.exception;

public class ConsultaFalhouException extends Exception{

    private static final long serialVersionUID = 1L;
    public ConsultaFalhouException(Throwable causa){
        super(causa);
    }
    public ConsultaFalhouException(String mensagem, Throwable causa){
        super(mensagem, causa);
    }
}
