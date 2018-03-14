/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sisacompo.sisap.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

/**
 *
 * @author eneas
 */
@Entity
@Table(name = "usuario", catalog="empresa", schema="intranet")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM UsuarioJPA u"),
    @NamedQuery(name = "Usuario.findByUsername", query = "SELECT u FROM UsuarioJPA u WHERE u.username = :username"),
    @NamedQuery(name = "Usuario.findByNomeCompleto", query = "SELECT u FROM UsuarioJPA u WHERE u.nomeCompleto = :nomeCompleto"),
    @NamedQuery(name = "Usuario.findByBrma", query = "SELECT u FROM UsuarioJPA u WHERE u.brma = :brma"),
    @NamedQuery(name = "Usuario.findByExiste", query = "SELECT u FROM UsuarioJPA u WHERE u.existe = :existe"),
    @NamedQuery(name = "Usuario.findByMatricula", query = "SELECT u FROM UsuarioJPA u WHERE u.matricula = :matricula"),
    @NamedQuery(name = "Usuario.findByEmailCorp", query = "SELECT u FROM UsuarioJPA u WHERE u.emailCorp = :emailCorp"),
    @NamedQuery(name = "Usuario.findByEmailPessoal", query = "SELECT u FROM UsuarioJPA u WHERE u.emailPessoal = :emailPessoal"),
    @NamedQuery(name = "Usuario.findBySetorNome", query = "SELECT u FROM UsuarioJPA u WHERE u.setorNome = :setorNome"),
    @NamedQuery(name = "Usuario.findBySenha", query = "SELECT u FROM UsuarioJPA u WHERE u.senha = :senha"),
    @NamedQuery(name = "Usuario.findBySenhaTemp", query = "SELECT u FROM UsuarioJPA u WHERE u.senhaTemp = :senhaTemp"),
    @NamedQuery(name = "Usuario.findByFlagSenha", query = "SELECT u FROM UsuarioJPA u WHERE u.flagSenha = :flagSenha"),
    @NamedQuery(name = "Usuario.findByDataTrocaSenha", query = "SELECT u FROM UsuarioJPA u WHERE u.dataTrocaSenha = :dataTrocaSenha"),
    @NamedQuery(name = "Usuario.findByMatriculaRm", query = "SELECT u FROM UsuarioJPA u WHERE u.matriculaRm = :matriculaRm"),
    @NamedQuery(name = "Usuario.findByRedefinidaPor", query = "SELECT u FROM UsuarioJPA u WHERE u.redefinidaPor = :redefinidaPor"),
    @NamedQuery(name = "Usuario.findByDataRedefinicao", query = "SELECT u FROM UsuarioJPA u WHERE u.dataRedefinicao = :dataRedefinicao"),
    @NamedQuery(name = "Usuario.findByInativo", query = "SELECT u FROM UsuarioJPA u WHERE u.inativo = :inativo"),
    @NamedQuery(name = "Usuario.findByDataInativo", query = "SELECT u FROM UsuarioJPA u WHERE u.dataInativo = :dataInativo")})
public class UsuarioJPA implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    //@NotNull
    //@Size(min = 1, max = 50)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    //@NotNull
    //@Size(min = 1, max = 100)
    @Column(name = "nome_completo")
    private String nomeCompleto;
    @Basic(optional = false)
    //@NotNull
    @Column(name = "brma")
    private boolean brma;
    @Basic(optional = false)
    //@NotNull
    @Column(name = "existe")
    private boolean existe;
    @Column(name = "matricula")
    private Integer matricula;
    //@Size(max = 100)
    @Column(name = "email_corp")
    private String emailCorp;
    //@Size(max = 100)
    @Column(name = "email_pessoal")
    private String emailPessoal;
    //@Size(max = 100)
    @Column(name = "setor_nome")
    private String setorNome;
    @Basic(optional = false)
    //@NotNull
    //@Size(min = 1, max = 64)
    @Column(name = "senha")
    private String senha;
    //@Size(max = 8)
    @Column(name = "senha_temp")
    private String senhaTemp;
    @Column(name = "flag_senha")
    private Boolean flagSenha;
    @Column(name = "data_troca_senha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataTrocaSenha;
    //@Size(max = 10)
    @Column(name = "matricula_rm")
    private String matriculaRm;
    //@Size(max = 50)
    @Column(name = "redefinida_por")
    private String redefinidaPor;
    @Column(name = "data_redefinicao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRedefinicao;
    @Basic(optional = false)
    //@NotNull
    @Column(name = "inativo")
    private boolean inativo;
    @Column(name = "data_inativo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInativo;
    @JoinColumn(name = "setor_id", referencedColumnName = "setor_id")
    @ManyToOne
    private Setor setorId;

    public UsuarioJPA() {
    }

    public UsuarioJPA(String username) {
        this.username = username;
    }

    public UsuarioJPA(String username, String nomeCompleto, boolean brma, boolean existe, String senha, boolean inativo) {
        this.username = username;
        this.nomeCompleto = nomeCompleto;
        this.brma = brma;
        this.existe = existe;
        this.senha = senha;
        this.inativo = inativo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public boolean getBrma() {
        return brma;
    }

    public void setBrma(boolean brma) {
        this.brma = brma;
    }

    public boolean getExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getEmailCorp() {
        return emailCorp;
    }

    public void setEmailCorp(String emailCorp) {
        this.emailCorp = emailCorp;
    }

    public String getEmailPessoal() {
        return emailPessoal;
    }

    public void setEmailPessoal(String emailPessoal) {
        this.emailPessoal = emailPessoal;
    }

    public String getSetorNome() {
        return setorNome;
    }

    public void setSetorNome(String setorNome) {
        this.setorNome = setorNome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenhaTemp() {
        return senhaTemp;
    }

    public void setSenhaTemp(String senhaTemp) {
        this.senhaTemp = senhaTemp;
    }

    public Boolean getFlagSenha() {
        return flagSenha;
    }

    public void setFlagSenha(Boolean flagSenha) {
        this.flagSenha = flagSenha;
    }

    public Date getDataTrocaSenha() {
        return dataTrocaSenha;
    }

    public void setDataTrocaSenha(Date dataTrocaSenha) {
        this.dataTrocaSenha = dataTrocaSenha;
    }

    public String getMatriculaRm() {
        return matriculaRm;
    }

    public void setMatriculaRm(String matriculaRm) {
        this.matriculaRm = matriculaRm;
    }

    public String getRedefinidaPor() {
        return redefinidaPor;
    }

    public void setRedefinidaPor(String redefinidaPor) {
        this.redefinidaPor = redefinidaPor;
    }

    public Date getDataRedefinicao() {
        return dataRedefinicao;
    }

    public void setDataRedefinicao(Date dataRedefinicao) {
        this.dataRedefinicao = dataRedefinicao;
    }

    public boolean getInativo() {
        return inativo;
    }

    public void setInativo(boolean inativo) {
        this.inativo = inativo;
    }

    public Date getDataInativo() {
        return dataInativo;
    }

    public void setDataInativo(Date dataInativo) {
        this.dataInativo = dataInativo;
    }

    public Setor getSetorId() {
        return setorId;
    }

    public void setSetorId(Setor setorId) {
        this.setorId = setorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioJPA)) {
            return false;
        }
        UsuarioJPA other = (UsuarioJPA) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sisacompo.sisap.modelo.Usuario[ username=" + username + " ]";
    }
    
}
