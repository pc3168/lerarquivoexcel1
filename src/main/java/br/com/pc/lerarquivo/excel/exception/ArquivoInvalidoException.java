package br.com.pc.lerarquivo.excel.exception;

public class ArquivoInvalidoException extends RuntimeException{

    public ArquivoInvalidoException(String arquivo) {
        super(String.format("o arquivo %s é inválido.", arquivo));
    }

    public ArquivoInvalidoException(String arquivo, Throwable cause) {
        super(String.format("o arquivo %s é inválido.", arquivo), cause);
    }
}
