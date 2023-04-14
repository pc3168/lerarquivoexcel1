package br.com.pc.lerarquivo.excel.layout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class LayoutSicoob extends Layout{

    @Override
    public List<LayoutSicoob> armazenarDados(List<String> dados) {
        List<LayoutSicoob> dadosTratados = new ArrayList<>();
        List<String> novoDados = linhasTratadas(dados);
        for (int i = 0; i < novoDados.size(); i++){
            String valor = novoDados.get(i);
            String[] linha = valor.split(";");
            if (linha.length < 2) continue;
//            System.out.println(linha.length + " >>>> "+valor);
            LayoutSicoob lyoutSicoob = preparaValores(linha);
            dadosTratados.add(lyoutSicoob);
        }
        return dadosTratados;
    }


    private List<String> linhasTratadas(List<String> dados){
        List<String> listaLinhas = new ArrayList<>();
        StringBuilder sb = null;
        //ignora as 3 primeiras linhas do arquivo.
        for (int i = 3; i < dados.size()-1; i++){
            String valor = dados.get(i);
            String[] linha = valor.split(";");
            if (isData(linha)){
                listaLinhas.add(sb == null ? "" : sb.toString());
                //System.out.println(sb == null ? "" : i + " > "+sb.toString());
                sb = new StringBuilder();
                sb.append(verificaSeNumero(valor, linha));
                sb.append(";");
            }else{
                //linha somente é utilizando quando é iniciando o for em zero.
                if (!valor.contains(";;")){
                    sb = new StringBuilder();
                }
                if (valor.contains(";;")){
                    sb.append(valor.replace(";",""));
                    sb.append(";");
                }else{
                    sb.append(valor);
                    sb.append(";");
                }
            }
        }
        listaLinhas.add(sb == null ? "" : sb.toString());
        return listaLinhas;
    }

    //Retira a segunda coluna da planilha.
    private String verificaSeNumero(String valor, String[] linha) {
        if (isColunaNumero(linha) || isColunaPix(linha)){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < linha.length; i++){
                if (i == 1) continue;
                if (i != 0) sb.append(";");
                sb.append(linha[i]);
            }
            valor = sb.toString();
        }
        return valor;
    }

    private boolean isData(String[] linha) {
        try{
            LocalDate.parse(linha[0], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return true;
        }catch (DateTimeParseException e){

        }
        return false;
    }

    private boolean isCpf(String valor){
        return valor.contains("CPF: ");
    }

    private boolean isFav(String valor){
        return valor.contains("FAV.: ");
    }

    private boolean isEnvelope(String valor){
        return valor.contains("ENVELOPE: ");
    }

    private boolean isColunaNumero(String[] linha){
        return linha[1].trim().matches("[0-9]*");
    }

    private boolean isColunaPix(String[] linha){
        return linha[1].contains("Pix");
    }

    @Override
    public String[] getCabecalho() {
        String[] str = {"DATA","PROTOCOLO","VALOR","TIPO","IDENTIFICAÇÃO","DADOS_NAO_UTILIZADOS"};
        return str;
    }

    @Override
    public String getNumeroConta() {
        return "20471-4";
    }

    private LayoutSicoob preparaValores(String[] linha) {
        LayoutSicoob layoutSicoob = new LayoutSicoob();
        layoutSicoob.data = LocalDate.parse(linha[0], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        layoutSicoob.identificacao = prepararIdentificacao(linha);
        layoutSicoob.protocolo = prepararProtocolo(linha);
        layoutSicoob.dadosNaoUtilizados = concatenacao(linha);
        layoutSicoob.valor = valorTratado(linha[2]);
        layoutSicoob.tipo = prepararTipo(linha[2]);

        return layoutSicoob;
    }

    private String prepararIdentificacao(String[] linha){
        String str = "";
        for(int i = 3; i < linha.length; i++){
            str = linha[i];
            if (isCpf(str) || isFav(str)){
                return str;
            }
        }
        return str;
    }

    private String prepararTipo(String linha){
        String str = linha.replaceAll("[0-9.,]+", "");
        return str;
    }

    private Double valorTratado(String linha){
        String str = linha
                .replaceAll("[^0-9,]+", "")
                .replace(',', '.');
        return Double.parseDouble(str);
    }


    private String concatenacao(String[] linha) {
        StringBuilder sb = new StringBuilder();
        sb.append(linha[1]);
        sb.append(";");
        sb.append(linha[2]);
        return sb.toString();
    }


    private String prepararProtocolo(String[] linha){
        String str = "0";
        for(int i = 3; i < linha.length; i++){
            str = linha[i];
            if (isEnvelope(str)){
                return trataProtocolo(str);
            }
        }
        return "0";
    }

    private String trataProtocolo(String str){
        String tipo = "ENVELOPE: ";
        int index = str.indexOf(tipo);
        if (index != -1){
            String num = str.substring(index+tipo.length());
            Long numero = Long.parseLong(num);
            return String.valueOf(numero);
        }
        return str;
    }


}
