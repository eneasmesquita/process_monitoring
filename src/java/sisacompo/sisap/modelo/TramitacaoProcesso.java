/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sisacompo.sisap.modelo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author eneas
 */
@Entity
@Table(name = "tramitacao_processo", catalog = "empresa", schema = "sisap")
@NamedQueries({
    @NamedQuery(name = "TramitacaoProcesso.findAll", query = "SELECT t FROM TramitacaoProcesso t"),
    @NamedQuery(name = "TramitacaoProcesso.findByCodTramitacaoProcesso", query = "SELECT t FROM TramitacaoProcesso t WHERE t.codTramitacaoProcesso = :codTramitacaoProcesso"),
    @NamedQuery(name = "TramitacaoProcesso.findByRemetenteTp", query = "SELECT t FROM TramitacaoProcesso t WHERE t.remetenteTp = :remetenteTp"),
    @NamedQuery(name = "TramitacaoProcesso.findBySetorOrigemTp", query = "SELECT t FROM TramitacaoProcesso t WHERE t.setorOrigemTp = :setorOrigemTp"),
    @NamedQuery(name = "TramitacaoProcesso.findBySetorDestinoTp", query = "SELECT t FROM TramitacaoProcesso t WHERE t.setorDestinoTp = :setorDestinoTp"),
    @NamedQuery(name = "TramitacaoProcesso.findByDataEnvioTp", query = "SELECT t FROM TramitacaoProcesso t WHERE t.dataEnvioTp = :dataEnvioTp"),
    @NamedQuery(name = "TramitacaoProcesso.findByDataRecebimentoTp", query = "SELECT t FROM TramitacaoProcesso t WHERE t.dataRecebimentoTp = :dataRecebimentoTp"),
    @NamedQuery(name = "TramitacaoProcesso.findByObservacoesTp", query = "SELECT t FROM TramitacaoProcesso t WHERE t.observacoesTp = :observacoesTp"),
    @NamedQuery(name = "TramitacaoProcesso.findByFaseTp", query = "SELECT t FROM TramitacaoProcesso t WHERE t.faseTp = :faseTp"),
    @NamedQuery(name = "TramitacaoProcesso.findByColaboradorRecebeuTp", query = "SELECT t FROM TramitacaoProcesso t WHERE t.colaboradorRecebeuTp = :colaboradorRecebeuTp"),
    @NamedQuery(name = "TramitacaoProcesso.findBySituacaoTp", query = "SELECT t FROM TramitacaoProcesso t WHERE t.situacaoTp = :situacaoTp"),
    @NamedQuery(name = "TramitacaoProcesso.findBySetorOrigemTpId", query = "SELECT t FROM TramitacaoProcesso t WHERE t.setorOrigemTpId = :setorOrigemTpId"),
    @NamedQuery(name = "TramitacaoProcesso.findBySetorDestinoTpId", query = "SELECT t FROM TramitacaoProcesso t WHERE t.setorDestinoTpId = :setorDestinoTpId")})
public class TramitacaoProcesso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_tramitacao_processo")
    private Integer codTramitacaoProcesso;
    @Basic(optional = false)
    @Column(name = "remetente_tp")
    private String remetenteTp;
    @Basic(optional = false)
    @Column(name = "setor_origem_tp")
    private String setorOrigemTp;
    @Column(name = "setor_destino_tp")
    private String setorDestinoTp;
    @Column(name = "data_envio_tp")
    @Temporal(TemporalType.DATE)
    private Date dataEnvioTp;
    @Column(name = "data_recebimento_tp")
    @Temporal(TemporalType.DATE)
    private Date dataRecebimentoTp;
    @Column(name = "observacoes_tp")
    private String observacoesTp;
    @Column(name = "fase_tp")
    private String faseTp;
    @Column(name = "colaborador_recebeu_tp")
    private String colaboradorRecebeuTp;
    @Basic(optional = false)
    @Column(name = "situacao_tp")
    private String situacaoTp;
    @Column(name = "setor_origem_tp_id")
    private Integer setorOrigemTpId;
    @Column(name = "setor_destino_tp_id")
    private Integer setorDestinoTpId;
    @JoinColumn(name = "cod_processo", referencedColumnName = "cod_processo")
    @ManyToOne(optional = false)
    private Processo codProcesso;

    public TramitacaoProcesso() {
    }

    public TramitacaoProcesso(Integer codTramitacaoProcesso) {
        this.codTramitacaoProcesso = codTramitacaoProcesso;
    }

    public TramitacaoProcesso(Integer codTramitacaoProcesso, String remetenteTp, String setorOrigemTp, String situacaoTp) {
        this.codTramitacaoProcesso = codTramitacaoProcesso;
        this.remetenteTp = remetenteTp;
        this.setorOrigemTp = setorOrigemTp;
        this.situacaoTp = situacaoTp;
    }

    public Integer getCodTramitacaoProcesso() {
        return codTramitacaoProcesso;
    }

    public void setCodTramitacaoProcesso(Integer codTramitacaoProcesso) {
        this.codTramitacaoProcesso = codTramitacaoProcesso;
    }

    public String getRemetenteTp() {
        return remetenteTp;
    }

    public void setRemetenteTp(String remetenteTp) {
        this.remetenteTp = remetenteTp;
    }

    public String getSetorOrigemTp() {
        return setorOrigemTp;
    }

    public void setSetorOrigemTp(String setorOrigemTp) {
        this.setorOrigemTp = setorOrigemTp;
    }

    public String getSetorDestinoTp() {
        return setorDestinoTp;
    }

    public void setSetorDestinoTp(String setorDestinoTp) {
        this.setorDestinoTp = setorDestinoTp;
    }

    public Date getDataEnvioTp() {
        return dataEnvioTp;
    }

    public void setDataEnvioTp(Date dataEnvioTp) {
        this.dataEnvioTp = dataEnvioTp;
    }

    public Date getDataRecebimentoTp() {
        return dataRecebimentoTp;
    }

    public void setDataRecebimentoTp(Date dataRecebimentoTp) {
        this.dataRecebimentoTp = dataRecebimentoTp;
    }

    public String getObservacoesTp() {
        return observacoesTp;
    }

    public void setObservacoesTp(String observacoesTp) {
        this.observacoesTp = observacoesTp;
    }

    public String getFaseTp() {
        return faseTp;
    }

    public void setFaseTp(String faseTp) {
        this.faseTp = faseTp;
    }

    public String getColaboradorRecebeuTp() {
        return colaboradorRecebeuTp;
    }

    public void setColaboradorRecebeuTp(String colaboradorRecebeuTp) {
        this.colaboradorRecebeuTp = colaboradorRecebeuTp;
    }

    public String getSituacaoTp() {
        return situacaoTp;
    }

    public void setSituacaoTp(String situacaoTp) {
        this.situacaoTp = situacaoTp;
    }

    public Integer getSetorOrigemTpId() {
        return setorOrigemTpId;
    }

    public void setSetorOrigemTpId(Integer setorOrigemTpId) {
        this.setorOrigemTpId = setorOrigemTpId;
    }

    public Integer getSetorDestinoTpId() {
        return setorDestinoTpId;
    }

    public void setSetorDestinoTpId(Integer setorDestinoTpId) {
        this.setorDestinoTpId = setorDestinoTpId;
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
        hash += (codTramitacaoProcesso != null ? codTramitacaoProcesso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TramitacaoProcesso)) {
            return false;
        }
        TramitacaoProcesso other = (TramitacaoProcesso) object;
        if ((this.codTramitacaoProcesso == null && other.codTramitacaoProcesso != null) || (this.codTramitacaoProcesso != null && !this.codTramitacaoProcesso.equals(other.codTramitacaoProcesso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sisacompo.sisap.modelo.TramitacaoProcesso[ codTramitacaoProcesso=" + codTramitacaoProcesso + " ]";
    }
    
}
