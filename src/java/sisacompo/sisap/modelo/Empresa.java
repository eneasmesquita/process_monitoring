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
@Table(name = "empresa", catalog = "empresa", schema = "sisap")
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e"),
    @NamedQuery(name = "Empresa.findByCnpj", query = "SELECT e FROM Empresa e WHERE e.cnpj = :cnpj"),
    @NamedQuery(name = "Empresa.findByRazao", query = "SELECT e FROM Empresa e WHERE e.razao = :razao"),
    @NamedQuery(name = "Empresa.findByFantasia", query = "SELECT e FROM Empresa e WHERE e.fantasia = :fantasia"),
    @NamedQuery(name = "Empresa.findByResponsavel", query = "SELECT e FROM Empresa e WHERE e.responsavel = :responsavel"),
    @NamedQuery(name = "Empresa.findByContato", query = "SELECT e FROM Empresa e WHERE e.contato = :contato"),
    @NamedQuery(name = "Empresa.findByEndereco", query = "SELECT e FROM Empresa e WHERE e.endereco = :endereco"),
    @NamedQuery(name = "Empresa.findByCodEmpresa", query = "SELECT e FROM Empresa e WHERE e.codEmpresa = :codEmpresa")})
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "cnpj")
    private String cnpj;
    @Basic(optional = false)
    @Column(name = "razao")
    private String razao;
    @Basic(optional = false)
    @Column(name = "fantasia")
    private String fantasia;
    @Basic(optional = false)
    @Column(name = "responsavel")
    private String responsavel;
    @Basic(optional = false)
    @Column(name = "contato")
    private String contato;
    @Basic(optional = false)
    @Column(name = "endereco")
    private String endereco;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_empresa")
    private Integer codEmpresa;
    @JoinColumn(name = "cod_contrato", referencedColumnName = "cod_contrato")
    @ManyToOne(optional = false)
    private Contrato codContrato;

    public Empresa() {
    }

    public Empresa(Integer codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public Empresa(Integer codEmpresa, String cnpj, String razao, String fantasia, String responsavel, String contato, String endereco) {
        this.codEmpresa = codEmpresa;
        this.cnpj = cnpj;
        this.razao = razao;
        this.fantasia = fantasia;
        this.responsavel = responsavel;
        this.contato = contato;
        this.endereco = endereco;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazao() {
        return razao;
    }

    public void setRazao(String razao) {
        this.razao = razao;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Integer getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(Integer codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public Contrato getCodContrato() {
        return codContrato;
    }

    public void setCodContrato(Contrato codContrato) {
        this.codContrato = codContrato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEmpresa != null ? codEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if ((this.codEmpresa == null && other.codEmpresa != null) || (this.codEmpresa != null && !this.codEmpresa.equals(other.codEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sisacompo.sisap.modelo.Empresa[ codEmpresa=" + codEmpresa + " ]";
    }
    
}
