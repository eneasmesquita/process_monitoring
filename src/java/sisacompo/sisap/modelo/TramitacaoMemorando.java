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
@Table(name = "tramitacao_memorando", catalog = "empresa", schema = "sisap")
@NamedQueries({
    @NamedQuery(name = "TramitacaoMemorando.findAll", query = "SELECT t FROM TramitacaoMemorando t"),
    @NamedQuery(name = "TramitacaoMemorando.findByCodTramitacaoMemorando", query = "SELECT t FROM TramitacaoMemorando t WHERE t.codTramitacaoMemorando = :codTramitacaoMemorando"),
    @NamedQuery(name = "TramitacaoMemorando.findByDataEnvioTm", query = "SELECT t FROM TramitacaoMemorando t WHERE t.dataEnvioTm = :dataEnvioTm"),
    @NamedQuery(name = "TramitacaoMemorando.findByRemetenteTm", query = "SELECT t FROM TramitacaoMemorando t WHERE t.remetenteTm = :remetenteTm"),
    @NamedQuery(name = "TramitacaoMemorando.findByDataRecebimentoTm", query = "SELECT t FROM TramitacaoMemorando t WHERE t.dataRecebimentoTm = :dataRecebimentoTm"),
    @NamedQuery(name = "TramitacaoMemorando.findByObservacoesTm", query = "SELECT t FROM TramitacaoMemorando t WHERE t.observacoesTm = :observacoesTm"),
    @NamedQuery(name = "TramitacaoMemorando.findByColaboradorRecebeuTm", query = "SELECT t FROM TramitacaoMemorando t WHERE t.colaboradorRecebeuTm = :colaboradorRecebeuTm"),
    @NamedQuery(name = "TramitacaoMemorando.findBySituacaoTm", query = "SELECT t FROM TramitacaoMemorando t WHERE t.situacaoTm = :situacaoTm"),
    @NamedQuery(name = "TramitacaoMemorando.findBySetorOrigemTmId", query = "SELECT t FROM TramitacaoMemorando t WHERE t.setorOrigemTmId = :setorOrigemTmId"),
    @NamedQuery(name = "TramitacaoMemorando.findBySetorDestinoTmId", query = "SELECT t FROM TramitacaoMemorando t WHERE t.setorDestinoTmId = :setorDestinoTmId"),
    @NamedQuery(name = "TramitacaoMemorando.findBySetorDestinoTm", query = "SELECT t FROM TramitacaoMemorando t WHERE t.setorDestinoTm = :setorDestinoTm"),
    @NamedQuery(name = "TramitacaoMemorando.findBySetorOrigemTm", query = "SELECT t FROM TramitacaoMemorando t WHERE t.setorOrigemTm = :setorOrigemTm")})
public class TramitacaoMemorando implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_tramitacao_memorando")
    private Integer codTramitacaoMemorando;
    @Basic(optional = false)
    @Column(name = "data_envio_tm")
    @Temporal(TemporalType.DATE)
    private Date dataEnvioTm;
    @Basic(optional = false)
    @Column(name = "remetente_tm")
    private String remetenteTm;
    @Column(name = "data_recebimento_tm")
    @Temporal(TemporalType.DATE)
    private Date dataRecebimentoTm;
    @Column(name = "observacoes_tm")
    private String observacoesTm;
    @Column(name = "colaborador_recebeu_tm")
    private String colaboradorRecebeuTm;
    @Basic(optional = false)
    @Column(name = "situacao_tm")
    private String situacaoTm;
    @Column(name = "setor_origem_tm_id")
    private Integer setorOrigemTmId;
    @Column(name = "setor_destino_tm_id")
    private Integer setorDestinoTmId;
    @Basic(optional = false)
    @Column(name = "setor_destino_tm")
    private String setorDestinoTm;
    @Basic(optional = false)
    @Column(name = "setor_origem_tm")
    private String setorOrigemTm;
    @JoinColumn(name = "cod_memorando", referencedColumnName = "cod_memorando")
    @ManyToOne(optional = false)
    private Memorando codMemorando;

    public TramitacaoMemorando() {
    }

    public TramitacaoMemorando(Integer codTramitacaoMemorando) {
        this.codTramitacaoMemorando = codTramitacaoMemorando;
    }

    public TramitacaoMemorando(Integer codTramitacaoMemorando, Date dataEnvioTm, String remetenteTm, String situacaoTm, String setorDestinoTm, String setorOrigemTm) {
        this.codTramitacaoMemorando = codTramitacaoMemorando;
        this.dataEnvioTm = dataEnvioTm;
        this.remetenteTm = remetenteTm;
        this.situacaoTm = situacaoTm;
        this.setorDestinoTm = setorDestinoTm;
        this.setorOrigemTm = setorOrigemTm;
    }

    public Integer getCodTramitacaoMemorando() {
        return codTramitacaoMemorando;
    }

    public void setCodTramitacaoMemorando(Integer codTramitacaoMemorando) {
        this.codTramitacaoMemorando = codTramitacaoMemorando;
    }

    public Date getDataEnvioTm() {
        return dataEnvioTm;
    }

    public void setDataEnvioTm(Date dataEnvioTm) {
        this.dataEnvioTm = dataEnvioTm;
    }

    public String getRemetenteTm() {
        return remetenteTm;
    }

    public void setRemetenteTm(String remetenteTm) {
        this.remetenteTm = remetenteTm;
    }

    public Date getDataRecebimentoTm() {
        return dataRecebimentoTm;
    }

    public void setDataRecebimentoTm(Date dataRecebimentoTm) {
        this.dataRecebimentoTm = dataRecebimentoTm;
    }

    public String getObservacoesTm() {
        return observacoesTm;
    }

    public void setObservacoesTm(String observacoesTm) {
        this.observacoesTm = observacoesTm;
    }

    public String getColaboradorRecebeuTm() {
        return colaboradorRecebeuTm;
    }

    public void setColaboradorRecebeuTm(String colaboradorRecebeuTm) {
        this.colaboradorRecebeuTm = colaboradorRecebeuTm;
    }

    public String getSituacaoTm() {
        return situacaoTm;
    }

    public void setSituacaoTm(String situacaoTm) {
        this.situacaoTm = situacaoTm;
    }

    public Integer getSetorOrigemTmId() {
        return setorOrigemTmId;
    }

    public void setSetorOrigemTmId(Integer setorOrigemTmId) {
        this.setorOrigemTmId = setorOrigemTmId;
    }

    public Integer getSetorDestinoTmId() {
        return setorDestinoTmId;
    }

    public void setSetorDestinoTmId(Integer setorDestinoTmId) {
        this.setorDestinoTmId = setorDestinoTmId;
    }

    public String getSetorDestinoTm() {
        return setorDestinoTm;
    }

    public void setSetorDestinoTm(String setorDestinoTm) {
        this.setorDestinoTm = setorDestinoTm;
    }

    public String getSetorOrigemTm() {
        return setorOrigemTm;
    }

    public void setSetorOrigemTm(String setorOrigemTm) {
        this.setorOrigemTm = setorOrigemTm;
    }

    public Memorando getCodMemorando() {
        return codMemorando;
    }

    public void setCodMemorando(Memorando codMemorando) {
        this.codMemorando = codMemorando;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codTramitacaoMemorando != null ? codTramitacaoMemorando.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TramitacaoMemorando)) {
            return false;
        }
        TramitacaoMemorando other = (TramitacaoMemorando) object;
        if ((this.codTramitacaoMemorando == null && other.codTramitacaoMemorando != null) || (this.codTramitacaoMemorando != null && !this.codTramitacaoMemorando.equals(other.codTramitacaoMemorando))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sisacompo.sisap.modelo.TramitacaoMemorando[ codTramitacaoMemorando=" + codTramitacaoMemorando + " ]";
    }
    
}
