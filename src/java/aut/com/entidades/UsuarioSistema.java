/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aut.com.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
//Iimport javax.validation.constraints.Size;

/**
 *
 * @author eneas
 */
@Entity
@Table(name = "usuario_sistema")
@NamedQueries({
    @NamedQuery(name = "UsuarioSistema.findAll", query = "SELECT u FROM UsuarioSistema u"),
    @NamedQuery(name = "UsuarioSistema.findByUsername", query = "SELECT u FROM UsuarioSistema u WHERE u.usuarioSistemaPK.username = :username"),
    @NamedQuery(name = "UsuarioSistema.findByIdSistema", query = "SELECT u FROM UsuarioSistema u WHERE u.usuarioSistemaPK.idSistema = :idSistema"),
    @NamedQuery(name = "UsuarioSistema.findByAcesso", query = "SELECT u FROM UsuarioSistema u WHERE u.acesso = :acesso"),
    @NamedQuery(name = "UsuarioSistema.findByUsuarioSistema", query = "SELECT u FROM UsuarioSistema u WHERE u.usuarioSistema = :usuarioSistema"),
    @NamedQuery(name = "UsuarioSistema.findByUltimoAcesso", query = "SELECT u FROM UsuarioSistema u WHERE u.ultimoAcesso = :ultimoAcesso")})
public class UsuarioSistema implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsuarioSistemaPK usuarioSistemaPK;
    @Column(name = "acesso")
    private Boolean acesso;
    //@Size(max = 100)
    @Column(name = "usuario_sistema")
    private String usuarioSistema;
    @Column(name = "ultimo_acesso")
    @Temporal(TemporalType.DATE)
    private Date ultimoAcesso;

    public UsuarioSistema() {
    }

    public UsuarioSistema(UsuarioSistemaPK usuarioSistemaPK) {
        this.usuarioSistemaPK = usuarioSistemaPK;
    }

    public UsuarioSistema(String username, int idSistema) {
        this.usuarioSistemaPK = new UsuarioSistemaPK(username, idSistema);
    }

    public UsuarioSistemaPK getUsuarioSistemaPK() {
        return usuarioSistemaPK;
    }

    public void setUsuarioSistemaPK(UsuarioSistemaPK usuarioSistemaPK) {
        this.usuarioSistemaPK = usuarioSistemaPK;
    }

    public Boolean getAcesso() {
        return acesso;
    }

    public void setAcesso(Boolean acesso) {
        this.acesso = acesso;
    }

    public String getUsuarioSistema() {
        return usuarioSistema;
    }

    public void setUsuarioSistema(String usuarioSistema) {
        this.usuarioSistema = usuarioSistema;
    }

    public Date getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(Date ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioSistemaPK != null ? usuarioSistemaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioSistema)) {
            return false;
        }
        UsuarioSistema other = (UsuarioSistema) object;
        if ((this.usuarioSistemaPK == null && other.usuarioSistemaPK != null) || (this.usuarioSistemaPK != null && !this.usuarioSistemaPK.equals(other.usuarioSistemaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "aut.com.entidades.UsuarioSistema[ usuarioSistemaPK=" + usuarioSistemaPK + " ]";
    }
    
}
