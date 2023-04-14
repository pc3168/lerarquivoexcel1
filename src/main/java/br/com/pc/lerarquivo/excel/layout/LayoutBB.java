package br.com.pc.lerarquivo.excel.layout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LayoutBB extends Layout{

    @Override
    public List<LayoutBB> armazenarDados(List<String> dados) {
        List<LayoutBB> dadosTratados = new ArrayList<>();
        for (int i = 0; i < dados.size(); i++){
            String[] linha = dados.get(i).split(";");
            LayoutBB layoutBB = preparaValores(linha);
            dadosTratados.add(layoutBB);
        }
        return dadosTratados;
    }

    @Override
    public String[] getCabecalho() {
        String[] str = {"DATA","PROTOCOLO","VALOR","TIPO","IDENTIFICAÇÃO","DADOS_NAO_UTILIZADOS"};
        return str;
    }

    @Override
    public String getNumeroConta() {
        return "519006";
    }

    private LayoutBB preparaValores(String[] linha) {
        LayoutBB layoutBB = new LayoutBB();
        layoutBB.data = LocalDate.parse(linha[0], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        layoutBB.protocolo = trataProtocolo(linha);
        layoutBB.valor = valorTratado(linha);
        layoutBB.tipo = linha[9];
        layoutBB.identificacao = linha[10];
        StringBuilder sb = concatenacao(linha);
        layoutBB.dadosNaoUtilizados = sb.toString();
        return layoutBB;
    }

    private String trataProtocolo(String[] linha){
        String prot = linha[5];
        try{
            Long l = Long.parseLong(prot);
            prot = String.valueOf(l);
        }catch (NumberFormatException e){
        }
        return prot;
    }

    private double valorTratado(String[] linha) {
        return Double.parseDouble(linha[8]
                .replace("*", "")
                .replace(".","")
                .replace(",",".")
        );
    }

    private StringBuilder concatenacao(String[] linha) {
        StringBuilder sb = new StringBuilder();
        sb.append(linha[1]);
        sb.append(";");
        sb.append(linha[2]);
        sb.append(";");
        sb.append(linha[3]);
        sb.append(";");
        sb.append(linha[4]);
        sb.append(";");
        sb.append(linha[6]);
        sb.append(";");
        sb.append(linha[7]);
        return sb;
    }

}
