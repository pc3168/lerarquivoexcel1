package br.com.pc.lerarquivo.excel.banco;

import br.com.pc.lerarquivo.excel.banco.model.Parametros;
import br.com.pc.lerarquivo.excel.exception.ConexaoFalhouException;
import br.com.pc.lerarquivo.excel.exception.ConsultaFalhouException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Consultas {

    private BDConexao bdConexao;
    private Parametros parametros;

    public Consultas() throws ConexaoFalhouException, IOException {
        bdConexao = new BDConexao();
        parametros = new Parametros();
    }

    public String codigoContaBancaria(String numeroConta) throws SQLException, ConsultaFalhouException {
        String query = parametros.queryContasBancarias(numeroConta);
        ResultSet rs = bdConexao.prepararConsultas(query);
        String valor = "0";
        if (rs.next()) {
            valor = rs.getString(1);
        }
        return valor;
    }

    public List<String> conciliacao(String codigo, LocalDate dataInicial, LocalDate dataFinal) throws ConsultaFalhouException, SQLException {
        String query = parametros.queryReconciliacaoBancaria(codigo, dataInicial, dataFinal);
        ResultSet rs = bdConexao.prepararConsultas(query);
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<String> list = new ArrayList<>();
        while (rs.next()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                Object value = rs.getObject(i);
                if (i != 1) {
                    sb.append(";");
                }
                sb.append(value == null ? "" : value.toString());

            }
            list.add(sb.toString());
        }
        return list;
    }

}
