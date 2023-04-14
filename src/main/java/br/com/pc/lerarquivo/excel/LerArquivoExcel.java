package br.com.pc.lerarquivo.excel;

import br.com.pc.lerarquivo.excel.exception.ArquivoInvalidoException;
import br.com.pc.lerarquivo.excel.exception.ArquivoNaoEncontradoException;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class LerArquivoExcel {

    private File file;
    private Workbook workbook;
    private int abaSelecionada = -1;
    private String abaSelecionadaNome = "";
    private Sheet sheet;

    public LerArquivoExcel(File file, TipoExtensao tipoExtensao) {
        this.file = file;
        carregarPlanilha(tipoExtensao);
    }

    public LerArquivoExcel(String file, TipoExtensao tipoExtensao) {
        this(new File(file), tipoExtensao);
    }

    public LerArquivoExcel(ExtensaoArquivo file) {
        this(file.getFile(), file.getTipoExtensao());
    }


    private void carregarPlanilha(TipoExtensao tipoExtensao){
        try {
            if (tipoExtensao.equals(TipoExtensao.XLS)){
                workbook = new HSSFWorkbook(new FileInputStream(file));
            }else if (tipoExtensao.equals(TipoExtensao.XLSX)){
                workbook = new XSSFWorkbook(file);
            }else{
                throw new ArquivoInvalidoException(file.getName());
            }
        } catch (IOException e) {
            throw new ArquivoNaoEncontradoException(file.getName(), e);
        } catch (InvalidFormatException e) {
            throw new ArquivoInvalidoException(file.getName(), e);
        }
    }

    public void lerArquivo(){

        try {

            Workbook workbook = new XSSFWorkbook(file);

            Sheet sheet = workbook.getSheetAt(0);

            List<Row> rows = (List<Row>) toList(sheet.iterator());

            rows.forEach(row -> {

                List<Cell> cells = (List<Cell>) toList(row.cellIterator());

                String str = cells.stream()
                        .map((a) -> {
                            String valor;
                            if (a.getCellType().name().equals("NUMERIC")) {
                                valor = String.valueOf((long) a.getNumericCellValue());
                            } else {
                                valor = a.getStringCellValue();
                            }
                            return valor;
                        })
                        .collect(Collectors.joining(";"));

                System.out.println(str);
            });

        } catch (Exception e) {
            throw new RuntimeException(e);

        }


    }

    public List<?> toList(Iterator<?> iterator){
        return IteratorUtils.toList(iterator);
    }

    public String[] listarNomesDeAba(){
        int totalDePlanilhas = workbook.getNumberOfSheets();
        String[] nomes = new String[totalDePlanilhas];
        for (int i = 0; i < nomes.length; i++){
            nomes[i] = workbook.getSheetName(i);
        }
        return nomes;
    }

    public Sheet carregarAba(String nome){
        return workbook.getSheet(nome);
    }

    public Sheet carregarAba(int index){
        return workbook.getSheetAt(index);
    }

    public List<String> carregarDados(Sheet sheet) {
        return carregarDados(sheet, ";");
    }

    public List<String> carregarDados(Sheet sheet, String delimitador) {
        List<String> dados = new ArrayList<>();

        List<Row> rows = (List<Row>) toList(sheet.iterator());

        rows.forEach(row -> {

            List<Cell> cells = (List<Cell>) toList(row.cellIterator());

            String str = cells.stream()
                    .map((a) -> {
                        String valor;
                        if (a.getCellType().name().equals("NUMERIC")) {
                            valor = String.valueOf((long) a.getNumericCellValue());
                        } else {
                            valor = a.getStringCellValue();
                        }
                        return valor;
                    })
                    .collect(Collectors.joining(delimitador));

            //System.out.println(str);
            dados.add(str);
        });
        return dados;
    }

    public List<String> processar(){
        if (abaSelecionada != -1){
            sheet = carregarAba(abaSelecionada);
        }else if(!abaSelecionadaNome.isEmpty()){
            sheet = carregarAba(abaSelecionadaNome);
        }
        return carregarDados(sheet);
    }

    public int getAbaSelecionada() {
        return abaSelecionada;
    }

    public void setAbaSelecionada(int abaSelecionada) {
        this.abaSelecionada = abaSelecionada;
    }

    public String getAbaSelecionadaNome() {
        return abaSelecionadaNome;
    }

    public void setAbaSelecionadaNome(String abaSelecionadaNome) {
        this.abaSelecionadaNome = abaSelecionadaNome;
    }
}
