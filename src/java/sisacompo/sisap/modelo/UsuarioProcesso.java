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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author eneas
 */
@Entity
@Table(name = "usuario_processo", catalog = "empresa", schema = "sisap")
@NamedQueries({
    @NamedQuery(name = "UsuarioProcesso.findAll", query = "SELECT u FROM UsuarioProcesso u"),
    @NamedQuery(name = "UsuarioProcesso.findByCodUsuarioProcesso", query = "SELECT u FROM UsuarioProcesso u WHERE u.codUsuarioProcesso = :codUsuarioProcesso"),
    @NamedQuery(name = "UsuarioProcesso.findByUsername", query = "SELECT u FROM UsuarioProcesso u WHERE u.username = :username"),
    @NamedQuery(name = "UsuarioProcesso.findByNomeCompleto", query = "SELECT u FROM UsuarioProcesso u WHERE u.nomeCompleto = :nomeCompleto"),
    @NamedQuery(name = "UsuarioProcesso.findBySetorNome", query = "SELECT u FROM UsuarioProcesso u WHERE u.setorNome = :setorNome"),
    @NamedQuery(name = "UsuarioProcesso.findBySetorId", query = "SELECT u FROM UsuarioProcesso u WHERE u.setorId = :setorId")})
public class UsuarioProcesso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_usuario_processo")
    private Long codUsuarioProcesso;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Column(name = "nome_completo")
    private String nomeCompleto;
    @Column(name = "setor_nome")
    private String setorNome;
    @Column(name = "setor_id")
    private Integer setorId;
    @JoinColumn(name = "cod_processo", referencedColumnName = "cod_processo")
    @ManyToOne(optional = false)
    private Processo codProcesso;

    public UsuarioProcesso() {
    }

    public UsuarioProcesso(Long codUsuarioProcesso) {
        this.codUsuarioProcesso = codUsuarioProcesso;
    }

    public UsuarioProcesso(Long codUsuarioProcesso, String username) {
        this.codUsuarioProcesso = codUsuarioProcesso;
        this.username = username;
    }

    public Long getCodUsuarioProcesso() {
        return codUsuarioProcesso;
    }

    public void setCodUsuarioProcesso(Long codUsuarioProcesso) {
        this.codUsuarioProcesso = codUsuarioProcesso;
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

    public String getSetorNome() {
        return setorNome;
    }

    public void setSetorNome(String setorNome) {
        this.setorNome = setorNome;
    }

    public Integer getSetorId() {
        return setorId;
    }

    public void setSetorId(Integer setorId) {
        this.setorId = setorId;
    }

    public Processo getCodProcesso() {
        return codProcesso;
    }

    public void setCodProcesso(Processo codProcesso) {
        this.codProcesso = codProcesso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codUsuarioProcesso != null ? codUsuarioProcesso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioProcesso)) {
            return false;
        }
        UsuarioProcesso other = (UsuarioProcesso) object;
        if ((this.codUsuarioProcesso == null && other.codUsuarioProcesso != null) || (this.codUsuarioProcesso != null && !this.codUsuarioProcesso.equals(other.codUsuarioProcesso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sisacompo.sisap.modelo.UsuarioProcesso[ codUsuarioProcesso=" + codUsuarioProcesso + " ]";
    }
    
}
