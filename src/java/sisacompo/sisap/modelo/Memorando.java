/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sisacompo.sisap.modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author eneas
 */
@Entity
@Table(name = "memorando", catalog = "empresa", schema = "sisap")
@NamedQueries({
    @NamedQuery(name = "Memorando.findAll", query = "SELECT m FROM Memorando m"),
    @NamedQuery(name = "Memorando.findByNumMemorando", query = "SELECT m FROM Memorando m WHERE m.numMemorando = :numMemorando"),
    @NamedQuery(name = "Memorando.findByAssuntoMemorando", query = "SELECT m FROM Memorando m WHERE m.assuntoMemorando = :assuntoMemorando"),
    @NamedQuery(name = "Memorando.findByDataMemorando", query = "SELECT m FROM Memorando m WHERE m.dataMemorando = :dataMemorando"),
    @NamedQuery(name = "Memorando.findByDescricaoMemorando", query = "SELECT m FROM Memorando m WHERE m.descricaoMemorando = :descricaoMemorando"),
    @NamedQuery(name = "Memorando.findByCategoriaMemorando", query = "SELECT m FROM Memorando m WHERE m.categoriaMemorando = :categoriaMemorando"),
    @NamedQuery(name = "Memorando.findByObservacaoMemorando", query = "SELECT m FROM Memorando m WHERE m.observacaoMemorando = :observacaoMemorando"),
    @NamedQuery(name = "Memorando.findBySetorCadastrante", query = "SELECT m FROM Memorando m WHERE m.setorCadastrante = :setorCadastrante"),
    @NamedQuery(name = "Memorando.findByCodMemorando", query = "SELECT m FROM Memorando m WHERE m.codMemorando = :codMemorando")})
public class Memorando implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "num_memorando")
    private String numMemorando;
    @Basic(optional = false)
    @Column(name = "assunto_memorando")
    private String assuntoMemorando;
    @Column(name = "data_memorando")
    @Temporal(TemporalType.DATE)
    private Date dataMemorando;
    @Basic(optional = false)
    @Column(name = "descricao_memorando")
    private String descricaoMemorando;
    @Column(name = "categoria_memorando")
    private String categoriaMemorando;
    @Column(name = "observacao_memorando")
    private String observacaoMemorando;
    @Basic(optional = false)
    @Column(name = "setor_cadastrante")
    private String setorCadastrante;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_memorando")
    private Long codMemorando;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codMemorando")
    private Collection<AnexoMemorando> anexoMemorandoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codMemorando")
    private Collection<Processo> processoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codMemorando")
    private Collection<TramitacaoMemorando> tramitacaoMemorandoCollection;

    public Memorando() {
    }

    public Memorando(Long codMemorando) {
        this.codMemorando = codMemorando;
    }

    public Memorando(Long codMemorando, String numMemorando, String assuntoMemorando, String descricaoMemorando, String setorCadastrante) {
        this.codMemorando = codMemorando;
        this.numMemorando = numMemorando;
        this.assuntoMemorando = assuntoMemorando;
        this.descricaoMemorando = descricaoMemorando;
        this.setorCadastrante = setorCadastrante;
    }

    public String getNumMemorando() {
        return numMemorando;
    }

    public void setNumMemorando(String numMemorando) {
        this.numMemorando = numMemorando;
    }

    public String getAssuntoMemorando() {
        return assuntoMemorando;
    }

    public void setAssuntoMemorando(String assuntoMemorando) {
        this.assuntoMemorando = assuntoMemorando;
    }

    public Date getDataMemorando() {
        return dataMemorando;
    }

    public void setDataMemorando(Date dataMemorando) {
        this.dataMemorando = dataMemorando;
    }

    public String getDescricaoMemorando() {
        return descricaoMemorando;
    }

    public void setDescricaoMemorando(String descricaoMemorando) {
        this.descricaoMemorando = descricaoMemorando;
    }

    public String getCategoriaMemorando() {
        return categoriaMemorando;
    }

    public void setCategoriaMemorando(String categoriaMemorando) {
        this.categoriaMemorando = categoriaMemorando;
    }

    public String getObservacaoMemorando() {
        return observacaoMemorando;
    }

    public void setObservacaoMemorando(String observacaoMemorando) {
        this.observacaoMemorando = observacaoMemorando;
    }

    public String getSetorCadastrante() {
        return setorCadastrante;
    }

    public void setSetorCadastrante(String setorCadastrante) {
        this.setorCadastrante = setorCadastrante;
    }

    public Long getCodMemorando() {
        return codMemorando;
    }

    public void setCodMemorando(Long codMemorando) {
        this.codMemorando = codMemorando;
    }

    public Collection<AnexoMemorando> getAnexoMemorandoCollection() {
        return anexoMemorandoCollection;
    }

    public void setAnexoMemorandoCollection(Collection<AnexoMemorando> anexoMemorandoCollection) {
        this.anexoMemorandoCollection = anexoMemorandoCollection;
    }

    public Collection<Processo> getProcessoCollection() {
        return processoCollection;
    }

    public void setProcessoCollection(Collection<Processo> processoCollection) {
        this.processoCollection = processoCollection;
    }

    public Collection<TramitacaoMemorando> getTramitacaoMemorandoCollection() {
        return tramitacaoMemorandoCollection;
    }

    public void setTramitacaoMemorandoCollection(Collection<TramitacaoMemorando> tramitacaoMemorandoCollection) {
        this.tramitacaoMemorandoCollection = tramitacaoMemorandoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codMemorando != null ? codMemorando.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Memorando)) {
            return false;
        }
        Memorando other = (Memorando) object;
        if ((this.codMemorando == null && other.codMemorando != null) || (this.codMemorando != null && !this.codMemorando.equals(other.codMemorando))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sisacompo.sisap.modelo.Memorando[ codMemorando=" + codMemorando + " ]";
    }
    
}
