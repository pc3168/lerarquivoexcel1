package br.com.pc.lerarquivo.excel.banco;


import br.com.pc.lerarquivo.excel.exception.ConexaoFalhouException;
import br.com.pc.lerarquivo.excel.exception.ConsultaFalhouException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class BDConexao {

    private String url;
    private String user;
    private String password;
    private Connection conn;


    public BDConexao() throws IOException, ConexaoFalhouException {
        inicializar();
    }

    private void inicializar() throws IOException, ConexaoFalhouException {
        Properties props = lerArquivoProperties();
        this.url = props.getProperty("connection.url");
        this.user = props.getProperty("connection.username");;
        this.password = props.getProperty("connection.password");
        conexao();
    }

    private void conexao() throws ConexaoFalhouException {
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e){
            throw new ConexaoFalhouException(e.getMessage(), e.getCause());
        }
    }

    public ResultSet prepararConsultas(String sql) throws ConsultaFalhouException {
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            return resultSet;
        }catch (SQLException e){
            throw new ConsultaFalhouException(e.getMessage(), e.getCause());
        }
    }


    public void fecharConexao(Connection conn){
        try {
            conn.close();
        }catch (SQLException e){

        }
    }

    private Properties lerArquivoProperties() throws IOException {
        Properties props = new Properties();
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("application.properties")) {
            props.load(inputStream);
        }
        return props;
    }


}
