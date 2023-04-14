package br.com.pc.lerarquivo.excel;

import br.com.pc.lerarquivo.excel.layout.LayoutBB;

import java.io.File;

public class ExcelLayoutBB extends PadraoExcel<LayoutBB>{
    public ExcelLayoutBB(File file, TipoExtensao tipoExtensao) {
        super(file, tipoExtensao);
    }

    public ExcelLayoutBB(String file, TipoExtensao tipoExtensao) {
        super(file, tipoExtensao);
    }

    public ExcelLayoutBB(ExtensaoArquivo file) {
        super(file);
    }

    @Override
    public void adicionarDados(int numeroLinha) {

    }

}
