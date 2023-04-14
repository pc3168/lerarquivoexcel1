package br.com.pc.lerarquivo.excel.layout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LayoutItec extends Layout{
    @Override
    public List<LayoutItec> armazenarDados(List<String> dados) {
        List<LayoutItec> dadosTratados = new ArrayList<>();
        for (int i = 0; i < dados.size(); i++){
            String[] linha = dados.get(i).split(";");
            LayoutItec layout = preparaValores(linha);
            dadosTratados.add(layout);
        }
        return dadosTratados;
    }

    @Override
    public String[] getCabecalho() {
        String[] valores = {"LANÇAMENTO","DATA","HISTORICO","VALOR","DT_COMPES","NR_CNT","DS_CNT","CHEQUE","CRE/DEB","ORIGEM","TIPO_CONC","TIPO_CONC"};
        return valores;
    }

    @Override
    public String getNumeroConta() {
        return null;
    }

    private LayoutItec preparaValores(String[] linha) {
        LayoutItec layoutBB = new LayoutItec();
        layoutBB.data = extrairData(linha);
        layoutBB.protocolo = extrairProtocolo(linha);
        layoutBB.valor = valorTratado(linha);
        layoutBB.tipo = "1".equals(linha[8]) ? "DEBITO" : "CREDITO";
        layoutBB.identificacao = extrairFilial(linha);
        StringBuilder sb = concatenacao(linha);
        layoutBB.dadosNaoUtilizados = sb.toString();
        return layoutBB;
    }

    private double valorTratado(String[] linha) {
        return Double.parseDouble(linha[3]);
    }

    //TRABALHAR AQUI
    private String extrairProtocolo(String[] linha){
        String str = linha[2];
        String tipo = "PROT.: ";
        int index = str.indexOf(tipo);
        if (index != -1){
            return str.substring(index+tipo.length());
        }
        return "0";
    }

    private String extrairFilial(String[] linha){
        String str = linha[2];
        String tipo = "FIL: ";
        int index = str.indexOf(tipo);
        if (index != -1){
            str = str.substring(index,index+9);
        }
        return str;
    }

    private StringBuilder concatenacao(String[] linha) {
        StringBuilder sb = new StringBuilder();
        sb.append(linha[0]);
        sb.append(";");
        sb.append(linha[2]);
        sb.append(";");
        sb.append(linha[5]);
        sb.append(";");
        sb.append(linha[6]);
        return sb;
    }
    
    private LocalDate extrairData(String[] linha){
        String str = linha[2];
        String tipo = "DIA: ";
        LocalDate data = LocalDate.parse(linha[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        int index = str.indexOf(tipo);
        if (index != -1){
            data = LocalDate.parse(
                    str.substring(index+tipo.length(),index+13), 
                    DateTimeFormatter.ofPattern("dd/MM/yy"));
        }else{
            index = str.indexOf("/");
            if (index != -1){
                data = LocalDate.parse(
                                str.substring(index-2,index+8),
                                DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
        }
        return data;
    }






}
