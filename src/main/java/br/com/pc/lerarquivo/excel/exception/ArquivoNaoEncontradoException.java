package br.com.pc.lerarquivo.excel.exception;

public class ArquivoNaoEncontradoException extends RuntimeException{

    public ArquivoNaoEncontradoException(String arquivo) {
        super(String.format("o arquivo %s não foi encontrado.", arquivo));
    }

    public ArquivoNaoEncontradoException(String arquivo, Throwable cause) {
        super(String.format("o arquivo %s não foi encontrado.", arquivo), cause);
    }
}
