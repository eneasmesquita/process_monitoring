package aut.com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eneas
 */
public class ConexaoDB {

    public ConexaoDB(String banco) {
        this.servidor = "000.00.00.000";
        //this.servidor = "localhost";
        this.porta = "0000";
        this.driverdb = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        this.banco = banco;
        this.urljdbc = "jdbc:sqlserver://" + this.servidor + ":" + this.porta + ";databaseName=" + this.banco;
        this.usuario = "rm";
        this.senha = "rm";
        this.descricao = this.servidor + "/" + this.banco;
    }

    public ConexaoDB() {
        this.driverdb = "org.postgresql.Driver";
        this.servidor = "172.16.95.153";
        this.porta = "5432";
        this.banco = "caer";
        this.urljdbc = "jdbc:postgresql://" + this.servidor + ":" + this.porta + "/" + this.banco;
        this.descricao = this.servidor + "/" + this.banco;
        this.usuario = "caer_admin";
        this.senha = "Caer.";
    }

    private String servidor;
    private String porta;
    private String driverdb;
    private String urljdbc;
    private String banco;
    private String usuario;
    private String senha;
    private String descricao;

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public String getUrljdbc() {
        return urljdbc;
    }

    public void setUrljdbc(String urljdbc) {
        this.urljdbc = urljdbc;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDriverdb() {
        return driverdb;
    }

    public void setDriverdb(String driverdb) {
        this.driverdb = driverdb;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Connection conectar() {
        Connection conn = null;
        try {
            Class.forName(driverdb);
            conn = DriverManager.getConnection(urljdbc, usuario, senha);
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            //Exceptions exp = new Exceptions(null, ex.toString());
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexaoDB.class.getName()).log(Level.SEVERE, null, ex);
            //Exceptions exp = new Exceptions(null, ex.toString());
            return null;
        }
        return conn;
    }
}
