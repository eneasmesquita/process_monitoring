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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "processo", catalog = "empresa", schema = "sisap")
@NamedQueries({
    @NamedQuery(name = "Processo.findAll", query = "SELECT p FROM Processo p"),
    @NamedQuery(name = "Processo.findByNumProcesso", query = "SELECT p FROM Processo p WHERE p.numProcesso = :numProcesso"),
    @NamedQuery(name = "Processo.findByAssuntoProcesso", query = "SELECT p FROM Processo p WHERE p.assuntoProcesso = :assuntoProcesso"),
    @NamedQuery(name = "Processo.findByTipoProcesso", query = "SELECT p FROM Processo p WHERE p.tipoProcesso = :tipoProcesso"),
    @NamedQuery(name = "Processo.findByDescricaoProcesso", query = "SELECT p FROM Processo p WHERE p.descricaoProcesso = :descricaoProcesso"),
    @NamedQuery(name = "Processo.findByObservacaoProcesso", query = "SELECT p FROM Processo p WHERE p.observacaoProcesso = :observacaoProcesso"),
    @NamedQuery(name = "Processo.findByDataAberturaProcesso", query = "SELECT p FROM Processo p WHERE p.dataAberturaProcesso = :dataAberturaProcesso"),
    @NamedQuery(name = "Processo.findByAtivoProcesso", query = "SELECT p FROM Processo p WHERE p.ativoProcesso = :ativoProcesso"),
    @NamedQuery(name = "Processo.findBySigiloso", query = "SELECT p FROM Processo p WHERE p.sigiloso = :sigiloso"),
    @NamedQuery(name = "Processo.findByCodProcesso", query = "SELECT p FROM Processo p WHERE p.codProcesso = :codProcesso")})
public class Processo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "num_processo")
    private String numProcesso;
    @Basic(optional = false)
    @Column(name = "assunto_processo")
    private String assuntoProcesso;
    @Column(name = "tipo_processo")
    private String tipoProcesso;
    @Column(name = "descricao_processo")
    private String descricaoProcesso;
    @Column(name = "observacao_processo")
    private String observacaoProcesso;
    @Basic(optional = false)
    @Column(name = "data_abertura_processo")
    @Temporal(TemporalType.DATE)
    private Date dataAberturaProcesso;
    @Column(name = "ativo_processo")
    private Boolean ativoProcesso;
    @Column(name = "sigiloso")
    private Boolean sigiloso;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_processo")
    private Long codProcesso;
    @JoinColumn(name = "cod_memorando", referencedColumnName = "cod_memorando")
    @ManyToOne(optional = false)
    private Memorando codMemorando;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codProcesso")
    private Collection<Anexo> anexoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codProcesso")
    private Collection<SolicitacaoProcesso> solicitacaoProcessoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codProcesso")
    private Collection<TramitacaoProcesso> tramitacaoProcessoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codProcesso")
    private Collection<UsuarioProcesso> usuarioProcessoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codProcesso")
    private Collection<Contrato> contratoCollection;

    public Processo() {
    }

    public Processo(Long codProcesso) {
        this.codProcesso = codProcesso;
    }

    public Processo(Long codProcesso, String numProcesso, String assuntoProcesso, Date dataAberturaProcesso) {
        this.codProcesso = codProcesso;
        this.numProcesso = numProcesso;
        this.assuntoProcesso = assuntoProcesso;
        this.dataAberturaProcesso = dataAberturaProcesso;
    }

    public String getNumProcesso() {
        return numProcesso;
    }

    public void setNumProcesso(String numProcesso) {
        this.numProcesso = numProcesso;
    }

    public String getAssuntoProcesso() {
        return assuntoProcesso;
    }

    public void setAssuntoProcesso(String assuntoProcesso) {
        this.assuntoProcesso = assuntoProcesso;
    }

    public String getTipoProcesso() {
        return tipoProcesso;
    }

    public void setTipoProcesso(String tipoProcesso) {
        this.tipoProcesso = tipoProcesso;
    }

    public String getDescricaoProcesso() {
        return descricaoProcesso;
    }

    public void setDescricaoProcesso(String descricaoProcesso) {
        this.descricaoProcesso = descricaoProcesso;
    }

    public String getObservacaoProcesso() {
        return observacaoProcesso;
    }

    public void setObservacaoProcesso(String observacaoProcesso) {
        this.observacaoProcesso = observacaoProcesso;
    }

    public Date getDataAberturaProcesso() {
        return dataAberturaProcesso;
    }

    public void setDataAberturaProcesso(Date dataAberturaProcesso) {
        this.dataAberturaProcesso = dataAberturaProcesso;
    }

    public Boolean getAtivoProcesso() {
        return ativoProcesso;
    }

    public void setAtivoProcesso(Boolean ativoProcesso) {
        this.ativoProcesso = ativoProcesso;
    }

    public Boolean getSigiloso() {
        return sigiloso;
    }

    public void setSigiloso(Boolean sigiloso) {
        this.sigiloso = sigiloso;
    }

    public Long getCodProcesso() {
        return codProcesso;
    }

    public void setCodProcesso(Long codProcesso) {
        this.codProcesso = codProcesso;
    }

    public Memorando getCodMemorando() {
        return codMemorando;
    }

    public void setCodMemorando(Memorando codMemorando) {
        this.codMemorando = codMemorando;
    }

    public Collection<Anexo> getAnexoCollection() {
        return anexoCollection;
    }

    public void setAnexoCollection(Collection<Anexo> anexoCollection) {
        this.anexoCollection = anexoCollection;
    }

    public Collection<SolicitacaoProcesso> getSolicitacaoProcessoCollection() {
        return solicitacaoProcessoCollection;
    }

    public void setSolicitacaoProcessoCollection(Collection<SolicitacaoProcesso> solicitacaoProcessoCollection) {
        this.solicitacaoProcessoCollection = solicitacaoProcessoCollection;
    }

    public Collection<TramitacaoProcesso> getTramitacaoProcessoCollection() {
        return tramitacaoProcessoCollection;
    }

    public void setTramitacaoProcessoCollection(Collection<TramitacaoProcesso> tramitacaoProcessoCollection) {
        this.tramitacaoProcessoCollection = tramitacaoProcessoCollection;
    }

    public Collection<UsuarioProcesso> getUsuarioProcessoCollection() {
        return usuarioProcessoCollection;
    }

    public void setUsuarioProcessoCollection(Collection<UsuarioProcesso> usuarioProcessoCollection) {
        this.usuarioProcessoCollection = usuarioProcessoCollection;
    }

    public Collection<Contrato> getContratoCollection() {
        return contratoCollection;
    }

    public void setContratoCollection(Collection<Contrato> contratoCollection) {
        this.contratoCollection = contratoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codProcesso != null ? codProcesso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Processo)) {
            return false;
        }
        Processo other = (Processo) object;
        if ((this.codProcesso == null && other.codProcesso != null) || (this.codProcesso != null && !this.codProcesso.equals(other.codProcesso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sisacompo.sisap.modelo.Processo[ codProcesso=" + codProcesso + " ]";
    }
    
}
