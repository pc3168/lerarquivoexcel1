import br.com.pc.lerarquivo.excel.*;
import br.com.pc.lerarquivo.excel.banco.Consultas;
import br.com.pc.lerarquivo.excel.exception.ConexaoFalhouException;
import br.com.pc.lerarquivo.excel.layout.Layout;
import br.com.pc.lerarquivo.excel.layout.LayoutBB;
import br.com.pc.lerarquivo.excel.layout.LayoutItec;
import br.com.pc.lerarquivo.excel.layout.LayoutSicoob;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Principal {

    public static void main(String[] args) throws Exception{
        //File file = new File("C:\\Users\\paulo.silva\\Downloads\\51900.xlsx");
        //File file = new File("C:\\Users\\paulo.silva\\Downloads\\51900_03042023.xlsx");
        //File fileOut = new File("C:\\Unidade\\foto\\saida_dados.xlsx");
        File file = new File("C:\\Users\\paulo.silva\\Downloads\\20471-04-04.xlsx");
        ExtensaoArquivo entrada = new ExtensaoArquivo(file);
        LerArquivoExcel lerArquivoExcel = new LerArquivoExcel(entrada);
        String[] abas = lerArquivoExcel.listarNomesDeAba();
//        for (String aba :abas) {
//            System.out.println(">>>. "+aba);
//        }
        Sheet sheet = lerArquivoExcel.carregarAba(0);
        List<String> dados = lerArquivoExcel.carregarDados(sheet);
        dados.forEach(System.out::println);

//        Layout layoutBB = new LayoutBB();
//        List<LayoutBB> valores =(List<LayoutBB>) layoutBB.armazenarDados(dados);

//        valores.stream().forEach(System.out::println);

        Layout layoutSicoob = new LayoutSicoob();
        List<LayoutSicoob> valores =(List<LayoutSicoob>) layoutSicoob.armazenarDados(dados);
//
//        valores.stream().forEach(System.out::println);


//        Consultas consultas = new Consultas();
////        LocalDate dataInicial = LocalDate.of(2023,03,11);
////        LocalDate dataFinal = LocalDate.of(2023,03,13);
//        LocalDate dataInicial = LocalDate.of(2023,03,28);
//        LocalDate dataFinal = LocalDate.of(2023,04,04);
//        String numeroConta = consultas.codigoContaBancaria("20471-4");
//        System.out.println(numeroConta);
//        List<String> conciliacao = consultas.conciliacao(numeroConta, dataInicial, dataFinal);
//        //conciliacao.forEach(System.out::println);
//        //System.out.println("-----------------------------------------------");
//        Layout layoutItec = new LayoutItec();
//        List<LayoutItec> listaLayoutItec = (List<LayoutItec>) layoutItec.armazenarDados(conciliacao);
//        //listaLayoutItec.stream().forEach(System.out::println);
////
//        CompararLayouts compararLayouts = new CompararLayouts(listaLayoutItec,  valores);
//        compararLayouts.compararValores();
//        System.out.println("_------------------------------------------------------_");
//        compararLayouts.getListaNaoEncontrado(layoutItec);
//        compararLayouts.getNovaLista().stream().forEach(System.out::println);


//        ExtensaoArquivo saida = new ExtensaoArquivo(fileOut);
//        PadraoExcel<LayoutBB> criarArquivoExcel = new ExcelLayoutBB(saida);
//        criarArquivoExcel.setNomeAba("BB");
//        criarArquivoExcel.setCabecalhos(layout.getCabecalho());
//        criarArquivoExcel.setDados(valores);
//        criarArquivoExcel.processar();

        //dados.forEach(System.out::println);

    }
}
