/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sisacompo.sisap.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author eneas
 */
@Entity
@Table(name = "superusuario", catalog = "empresa", schema = "sisap")
@NamedQueries({
    @NamedQuery(name = "Superusuario.findAll", query = "SELECT s FROM Superusuario s"),
    @NamedQuery(name = "Superusuario.findByCodSuperusuario", query = "SELECT s FROM Superusuario s WHERE s.codSuperusuario = :codSuperusuario"),
    @NamedQuery(name = "Superusuario.findByLoginSu", query = "SELECT s FROM Superusuario s WHERE s.loginSu = :loginSu"),
    @NamedQuery(name = "Superusuario.findByNomeCompletoSu", query = "SELECT s FROM Superusuario s WHERE s.nomeCompletoSu = :nomeCompletoSu"),
    @NamedQuery(name = "Superusuario.findByMatricula", query = "SELECT s FROM Superusuario s WHERE s.matricula = :matricula"),
    @NamedQuery(name = "Superusuario.findByPrivilegio", query = "SELECT s FROM Superusuario s WHERE s.privilegio = :privilegio")})
public class Superusuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_superusuario")
    private Integer codSuperusuario;
    @Basic(optional = false)
    @Column(name = "login_su")
    private String loginSu;
    @Basic(optional = false)
    @Column(name = "nome_completo_su")
    private String nomeCompletoSu;
    @Basic(optional = false)
    @Column(name = "matricula")
    private int matricula;
    @Basic(optional = false)
    @Column(name = "privilegio")
    private String privilegio;

    public Superusuario() {
    }

    public Superusuario(Integer codSuperusuario) {
        this.codSuperusuario = codSuperusuario;
    }

    public Superusuario(Integer codSuperusuario, String loginSu, String nomeCompletoSu, int matricula, String privilegio) {
        this.codSuperusuario = codSuperusuario;
        this.loginSu = loginSu;
        this.nomeCompletoSu = nomeCompletoSu;
        this.matricula = matricula;
        this.privilegio = privilegio;
    }

    public Integer getCodSuperusuario() {
        return codSuperusuario;
    }

    public void setCodSuperusuario(Integer codSuperusuario) {
        this.codSuperusuario = codSuperusuario;
    }

    public String getLoginSu() {
        return loginSu;
    }

    public void setLoginSu(String loginSu) {
        this.loginSu = loginSu;
    }

    public String getNomeCompletoSu() {
        return nomeCompletoSu;
    }

    public void setNomeCompletoSu(String nomeCompletoSu) {
        this.nomeCompletoSu = nomeCompletoSu;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(String privilegio) {
        this.privilegio = privilegio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codSuperusuario != null ? codSuperusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Superusuario)) {
            return false;
        }
        Superusuario other = (Superusuario) object;
        if ((this.codSuperusuario == null && other.codSuperusuario != null) || (this.codSuperusuario != null && !this.codSuperusuario.equals(other.codSuperusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sisacompo.sisap.modelo.Superusuario[ codSuperusuario=" + codSuperusuario + " ]";
    }
    
}
