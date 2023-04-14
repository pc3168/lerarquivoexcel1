package br.com.pc.lerarquivo.excel;

import br.com.pc.lerarquivo.excel.exception.ArquivoInvalidoException;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;
import java.util.List;

public abstract class PadraoExcel<T> {

    private File file;

    private Workbook workbook;

    private Sheet aba;

    private String nomeAba = "";

    private List<?> dados;

    private String[] cabecalhos;

    public PadraoExcel(File file, TipoExtensao tipoExtensao) {
        this.file = file;
        carregarPlanilha(tipoExtensao);
    }

    public PadraoExcel(String file, TipoExtensao tipoExtensao) {
        this(new File(file), tipoExtensao);
    }

    public PadraoExcel(ExtensaoArquivo file) {
        this(file.getFile(), file.getTipoExtensao());
    }

    private void carregarPlanilha(TipoExtensao tipoExtensao){
        if (tipoExtensao.equals(TipoExtensao.XLS)){
            workbook = new HSSFWorkbook();
        }else if (tipoExtensao.equals(TipoExtensao.XLSX)){
            workbook = new XSSFWorkbook();
        }else{
            throw new ArquivoInvalidoException(file.getName());
        }
    }

    private void imprimir(String str){
        System.out.println(str);
    }

    public List<?> toList(Iterator<?> iterator){
        return IteratorUtils.toList(iterator);
    }

    private void criarAba() {
        if (nomeAba.isEmpty()){
            aba = workbook.createSheet();
        }else{
            aba = workbook.createSheet(nomeAba);
        }
    }

    public void adicionarCabecalho(int numeroLinha, String[] cabecalhos){
        Row linha = aba.createRow(numeroLinha);
        for (int i = 0; i < cabecalhos.length; i++){
            adicionarCelula(linha, i,cabecalhos[i]);
        }
    }

    public abstract void adicionarDados(int numeroLinha);

    private void adicionarCelula(Row linha, int coluna, String valor) {
        Cell cell = linha.createCell(coluna);
        cell.setCellValue(valor);
    }

    private void adicionarCelula(Row linha, int coluna, int valor) {
        Cell cell = linha.createCell(coluna);
        cell.setCellValue(valor);
    }

    public String getNomeAba() {
        return nomeAba;
    }

    public void setNomeAba(String nomeAba) {
        this.nomeAba = nomeAba;
    }

    public String[] getCabecalhos() {
        return cabecalhos;
    }

    public void setCabecalhos(String[] cabecalhos) {
        this.cabecalhos = cabecalhos;
    }

    public List<?> getDados() {
        return dados;
    }

    public void setDados(List<?> dados) {
        this.dados = dados;
    }

    public void processar() throws FileNotFoundException {
        criarAba();
        int totalLinhas = dados.size();
        if (cabecalhos.length != 0){
            adicionarCabecalho(totalLinhas,cabecalhos);
            totalLinhas++;
        }
        adicionarDados(totalLinhas);
        try(FileOutputStream fss = new FileOutputStream(file)){
            workbook.write(fss);
        }catch (IOException e){
            throw new ArquivoInvalidoException(file.getName(), e);
        }
    }
}
