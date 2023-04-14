package br.com.pc.lerarquivo.excel;

import br.com.pc.lerarquivo.excel.layout.Layout;
import br.com.pc.lerarquivo.excel.layout.LayoutItec;
import br.com.pc.lerarquivo.excel.layout.LayoutSicoob;

import java.util.ArrayList;
import java.util.List;

public class CompararLayouts {

    private List<LayoutItec> listaItec;
    private List<Layout> listaBancos;
    private List<String> novaLista;
    private List<LayoutItec> listaNaoEncontrado;

    public CompararLayouts(List<LayoutItec> listaItec, List<?> listaBancos) {
        this.listaItec = listaItec;
        this.listaNaoEncontrado = listaItec;
        this.listaBancos = (List<Layout>) listaBancos;
        novaLista = new ArrayList<>();
    }

    public void compararValores(){
        //for (int i = 0; i < listaBancos.size(); i++){
        boolean encontrou = false;
        StringBuilder sb;
        for (Layout layout : listaBancos){
            encontrou = false;
            sb = new StringBuilder();
            for (LayoutItec layoutItec: listaItec){
                if (layout.getProtocolo().equals(layoutItec.getProtocolo()) &&
                        //layout.getData().isEqual(layoutItec.getData()) &&
                        layout.getValor() == layoutItec.getValor() ){

                    encontrou = true;
                    novaLista.add("BANCO;"+layout.toString()+";ITEC;"+layoutItec.toString());
                    listaNaoEncontrado.remove(layoutItec);
                    break;
                }

            }
            if (!encontrou){
                novaLista.add("BANCO;"+layout.toString()+";ITEC;");
                //System.out.println("BANCO;"+layout.toString()+";ITEC;");
            }else{
                //System.out.println(sb.toString());
            }
        }

        System.out.println("NAOENCONTRADOS "+listaNaoEncontrado.size());
//        System.out.println("LISTA FALTANTES");
//        System.out.println("---------------------------------------------------");
//        listaFaltantes.forEach(System.out::println);
    }

    public List<String> getNovaLista() {
        return novaLista;
    }

    public List<LayoutItec> getListaNaoEncontrado() {
        return listaNaoEncontrado;
    }

    public void getListaNaoEncontrado(Layout layout){
        for (LayoutItec naoEncontrado: listaNaoEncontrado){
            novaLista.add("BANCO;"+layout.toString()+";ITEC;"+naoEncontrado.toString());
        }
    }
}
