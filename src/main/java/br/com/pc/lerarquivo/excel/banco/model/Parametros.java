package br.com.pc.lerarquivo.excel.banco.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Parametros {


    public String queryReconciliacaoBancaria(String codigo, LocalDate dataInicial, LocalDate dataFinal){
        String query = String.format("Select " +
                " CD_BC_LANC," +
                " DT_LANCTO," +
                " HIST_LANC," +
                " VLR_LANC," +
                " DT_COMPES," +
                " NR_CNT," +
                " DS_CNT," +
                " CD_CHQ_LANC," +
                " TP_LANC," +
                " ORIG_LANC, " +
                " TIPO_CONC " +
                " From " +
                " UFD_BC_LANC_CONCILIACAO_MANUAL(1,'%s','%s','%s',0,0,'0,1',0) " +
                " Where CD_EMP = 1 " +
                " Order By DT_LANCTO,CD_BC_LANC",
                codigo,
                dataInicial.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                dataFinal.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        return query;
    }


    public String queryContasBancarias(String numeroConta){
        String query = String.format(
                " Select " +
                " BC_CONTAS.CD_CNT, " +
                " BC_CONTAS.DS_CNT, " +
                " BC_CONTAS.TP_CNT, " +
                " BC_CONTAS.TITULAR, " +
                " BC_CONTAS.NR_CNT, " +
                " BC_CONTAS.AGC_CNT AS AGENCIA " +
                " From BC_BANCO " +
                " RIGHT JOIN BC_CONTAS ON BC_BANCO.CD_BC = BC_CONTAS.CD_BC " +
                " Where BC_CONTAS.CD_EMP=1 " +
                " AND BC_CONTAS.NR_CNT = '%s'",
                numeroConta);
        return query;
    }


    /*
    *
    * UFD_BC_LANC_CONCILIACAO_MANUAL(1,'40','03/27/2023','03/27/2023',0,0,'0,1',0)
    *   campo - 1 - CD_EMP
    *   campo - 2 - CODIGO BANCO
    *   campo - 3 - DATA INCIAL
    *   campo - 4 - DATA FINAL
    *   campo - 5 - 0 DESMARCADO , 1 MARCADO - VISUALIZAR CHEQUE EMPRESA
    *   campo - 6 - 0 NAO CONCILIADOS , 1 CONCILIADOS, 2 SEM CRITERIO
    *   campo - 7 - 0,1 AMBOS , 0 CREDITO , 1 DEBITO
    *   campo - 8 - 0 ?
    *
    * */
}
