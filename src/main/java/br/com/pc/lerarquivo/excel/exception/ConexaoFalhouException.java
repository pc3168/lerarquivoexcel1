package br.com.pc.lerarquivo.excel.exception;

public class ConexaoFalhouException extends Exception{

    private static final long serialVersionUID = 1L;
    public ConexaoFalhouException(Throwable causa){
        super(causa);
    }
    public ConexaoFalhouException(String mensagem, Throwable causa){
        super(mensagem, causa);
    }
}
