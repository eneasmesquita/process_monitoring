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
@Table(name = "contrato", catalog = "empresa", schema = "sisap")
@NamedQueries({
    @NamedQuery(name = "Contrato.findAll", query = "SELECT c FROM Contrato c"),
    @NamedQuery(name = "Contrato.findByNumContrato", query = "SELECT c FROM Contrato c WHERE c.numContrato = :numContrato"),
    @NamedQuery(name = "Contrato.findByAssuntoContrato", query = "SELECT c FROM Contrato c WHERE c.assuntoContrato = :assuntoContrato"),
    @NamedQuery(name = "Contrato.findByObservacaoContrato", query = "SELECT c FROM Contrato c WHERE c.observacaoContrato = :observacaoContrato"),
    @NamedQuery(name = "Contrato.findByDescricaoContrato", query = "SELECT c FROM Contrato c WHERE c.descricaoContrato = :descricaoContrato"),
    @NamedQuery(name = "Contrato.findByDataContrato", query = "SELECT c FROM Contrato c WHERE c.dataContrato = :dataContrato"),
    @NamedQuery(name = "Contrato.findByDataCadContrato", query = "SELECT c FROM Contrato c WHERE c.dataCadContrato = :dataCadContrato"),
    @NamedQuery(name = "Contrato.findByAditamento", query = "SELECT c FROM Contrato c WHERE c.aditamento = :aditamento"),
    @NamedQuery(name = "Contrato.findByNumVzsAditamento", query = "SELECT c FROM Contrato c WHERE c.numVzsAditamento = :numVzsAditamento"),
    @NamedQuery(name = "Contrato.findByVigenciaDataInicial", query = "SELECT c FROM Contrato c WHERE c.vigenciaDataInicial = :vigenciaDataInicial"),
    @NamedQuery(name = "Contrato.findByVigenciaDataFinal", query = "SELECT c FROM Contrato c WHERE c.vigenciaDataFinal = :vigenciaDataFinal"),
    @NamedQuery(name = "Contrato.findByCodContrato", query = "SELECT c FROM Contrato c WHERE c.codContrato = :codContrato")})
public class Contrato implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "num_contrato")
    private String numContrato;
    @Basic(optional = false)
    @Column(name = "assunto_contrato")
    private String assuntoContrato;
    @Column(name = "observacao_contrato")
    private String observacaoContrato;
    @Column(name = "descricao_contrato")
    private String descricaoContrato;
    @Column(name = "data_contrato")
    @Temporal(TemporalType.DATE)
    private Date dataContrato;
    @Column(name = "data_cad_contrato")
    @Temporal(TemporalType.DATE)
    private Date dataCadContrato;
    @Column(name = "aditamento")
    private String aditamento;
    @Column(name = "num_vzs_aditamento")
    private Integer numVzsAditamento;
    @Basic(optional = false)
    @Column(name = "vigencia_data_inicial")
    @Temporal(TemporalType.DATE)
    private Date vigenciaDataInicial;
    @Basic(optional = false)
    @Column(name = "vigencia_data_final")
    @Temporal(TemporalType.DATE)
    private Date vigenciaDataFinal;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_contrato")
    private Long codContrato;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codContrato")
    private Collection<Empresa> empresaCollection;
    @JoinColumn(name = "cod_processo", referencedColumnName = "cod_processo")
    @ManyToOne(optional = false)
    private Processo codProcesso;

    public Contrato() {
    }

    public Contrato(Long codContrato) {
        this.codContrato = codContrato;
    }

    public Contrato(Long codContrato, String numContrato, String assuntoContrato, Date vigenciaDataInicial, Date vigenciaDataFinal) {
        this.codContrato = codContrato;
        this.numContrato = numContrato;
        this.assuntoContrato = assuntoContrato;
        this.vigenciaDataInicial = vigenciaDataInicial;
        this.vigenciaDataFinal = vigenciaDataFinal;
    }

    public String getNumContrato() {
        return numContrato;
    }

    public void setNumContrato(String numContrato) {
        this.numContrato = numContrato;
    }

    public String getAssuntoContrato() {
        return assuntoContrato;
    }

    public void setAssuntoContrato(String assuntoContrato) {
        this.assuntoContrato = assuntoContrato;
    }

    public String getObservacaoContrato() {
        return observacaoContrato;
    }

    public void setObservacaoContrato(String observacaoContrato) {
        this.observacaoContrato = observacaoContrato;
    }

    public String getDescricaoContrato() {
        return descricaoContrato;
    }

    public void setDescricaoContrato(String descricaoContrato) {
        this.descricaoContrato = descricaoContrato;
    }

    public Date getDataContrato() {
        return dataContrato;
    }

    public void setDataContrato(Date dataContrato) {
        this.dataContrato = dataContrato;
    }

    public Date getDataCadContrato() {
        return dataCadContrato;
    }

    public void setDataCadContrato(Date dataCadContrato) {
        this.dataCadContrato = dataCadContrato;
    }

    public String getAditamento() {
        return aditamento;
    }

    public void setAditamento(String aditamento) {
        this.aditamento = aditamento;
    }

    public Integer getNumVzsAditamento() {
        return numVzsAditamento;
    }

    public void setNumVzsAditamento(Integer numVzsAditamento) {
        this.numVzsAditamento = numVzsAditamento;
    }

    public Date getVigenciaDataInicial() {
        return vigenciaDataInicial;
    }

    public void setVigenciaDataInicial(Date vigenciaDataInicial) {
        this.vigenciaDataInicial = vigenciaDataInicial;
    }

    public Date getVigenciaDataFinal() {
        return vigenciaDataFinal;
    }

    public void setVigenciaDataFinal(Date vigenciaDataFinal) {
        this.vigenciaDataFinal = vigenciaDataFinal;
    }

    public Long getCodContrato() {
        return codContrato;
    }

    public void setCodContrato(Long codContrato) {
        this.codContrato = codContrato;
    }

    public Collection<Empresa> getEmpresaCollection() {
        return empresaCollection;
    }

    public void setEmpresaCollection(Collection<Empresa> empresaCollection) {
        this.empresaCollection = empresaCollection;
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
        hash += (codContrato != null ? codContrato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contrato)) {
            return false;
        }
        Contrato other = (Contrato) object;
        if ((this.codContrato == null && other.codContrato != null) || (this.codContrato != null && !this.codContrato.equals(other.codContrato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sisacompo.sisap.modelo.Contrato[ codContrato=" + codContrato + " ]";
    }
    
}
