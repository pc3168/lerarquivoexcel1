package br.com.pc.lerarquivo.excel;

import java.io.File;

public class ExtensaoArquivo {

    private File file;
    private String extensao;

    public ExtensaoArquivo(File file) {
        this.file = file;
    }

    public String getExtensao() {
        String nome = file.getName();
        nome = nome.substring(nome.indexOf("."));
        return nome.replace(".","").toUpperCase();
    }

    public TipoExtensao getTipoExtensao(){
        return TipoExtensao.valueOf(getExtensao());
    }

    public File getFile() {
        return file;
    }
}
