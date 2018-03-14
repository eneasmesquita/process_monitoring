/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aut.com.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

/**
 *
 * @author eneas
 */
@Embeddable
public class UsuarioSistemaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    //@NotNull
    @Column(name = "id_sistema")
    private int idSistema;

    public UsuarioSistemaPK() {
    }

    public UsuarioSistemaPK(String username, int idSistema) {
        this.username = username;
        this.idSistema = idSistema;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdSistema() {
        return idSistema;
    }

    public void setIdSistema(int idSistema) {
        this.idSistema = idSistema;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        hash += (int) idSistema;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioSistemaPK)) {
            return false;
        }
        UsuarioSistemaPK other = (UsuarioSistemaPK) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        if (this.idSistema != other.idSistema) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "aut.com.entidades.UsuarioSistemaPK[ username=" + username + ", idSistema=" + idSistema + " ]";
    }
    
}
