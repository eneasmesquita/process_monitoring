/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aut.com.login;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eneas
 */
public class Usuario {

    private String login;       //usuario do brma
    private String codusuario;  //usuario do rm
    private String nome;        //nome do usuario
    private String matricula;   //matricula caso seja funcionario
    private String chapa;       //chapa sistema rm caso funcionario
    private String centroCusto; //centro de custos do usuario
    private boolean brma;       //se o usu�rio possui brma
    private boolean flagSenha;  //indica for�ar troca de senha
    private String senha;
    private String setor_nome;
    private int setor_id;
    private List<String> acessos; //acessos do usuario

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario(String login, String nome, String matricula) {
        this.login = login;
        this.nome = nome;
        this.matricula = matricula;
    }

    public Usuario() {
        this.flagSenha = false;
        this.brma = false;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSetor_nome() {
        return setor_nome;
    }

    public void setSetor_nome(String setor_nome) {
        this.setor_nome = setor_nome;
    }

    public String getCodusuario() {
        return codusuario;
    }

    public void setCodusuario(String codusuario) {
        this.codusuario = codusuario;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(String centroCusto) {
        this.centroCusto = centroCusto;
    }

    public String getChapa() {
        return chapa;
    }

    public void setChapa(String chapa) {
        this.chapa = chapa;
    }

    public void setAcessos(ArrayList acessos) {
        this.acessos = acessos;
    }

    public boolean isFlagSenha() {
        return flagSenha;
    }

    public void setFlagSenha(boolean flagSenha) {
        this.flagSenha = flagSenha;
    }

    public boolean isBrma() {
        return brma;
    }

    public void setBrma(boolean brma) {
        this.brma = brma;
    }

    public int getSetor_id() {
        return setor_id;
    }

    public void setSetor_id(int setor_id) {
        this.setor_id = setor_id;
    }

    public List<String> getAcessos() {
        return acessos;
    }

    public void setAcessos(List<String> acessos) {
        this.acessos = acessos;
    }

    @Override
    public String toString() {
        return "Usuario{" + "login=" + login + "; codusuario=" + codusuario + "; nome=" + nome + "; matricula=" + matricula + "; chapa=" + chapa + "; brma=" + brma + "; flagSenha=" + flagSenha + '}';
    }
}
