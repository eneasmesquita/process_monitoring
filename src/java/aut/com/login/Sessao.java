/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aut.com.login;

/**
 *
 * @author eneas
 */
public class Sessao {

    private static Sessao instance = null;
    private Usuario usuario;
    private String databaseName;

    private Sessao() {
        this.databaseName = "CorporeRM_Desenv";
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public static Sessao getInstance() {
        if (instance == null) {
            instance = new Sessao();
        }
        return instance;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}
