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
@Table(name = "solicitacao_processo", catalog = "empresa", schema = "sisap")
@NamedQueries({
    @NamedQuery(name = "SolicitacaoProcesso.findAll", query = "SELECT s FROM SolicitacaoProcesso s"),
    @NamedQuery(name = "SolicitacaoProcesso.findByCodSolicitacao", query = "SELECT s FROM SolicitacaoProcesso s WHERE s.codSolicitacao = :codSolicitacao"),
    @NamedQuery(name = "SolicitacaoProcesso.findByDataSolicitacao", query = "SELECT s FROM SolicitacaoProcesso s WHERE s.dataSolicitacao = :dataSolicitacao"),
    @NamedQuery(name = "SolicitacaoProcesso.findBySolicitante", query = "SELECT s FROM SolicitacaoProcesso s WHERE s.solicitante = :solicitante"),
    @NamedQuery(name = "SolicitacaoProcesso.findBySetorSolicitante", query = "SELECT s FROM SolicitacaoProcesso s WHERE s.setorSolicitante = :setorSolicitante"),
    @NamedQuery(name = "SolicitacaoProcesso.findByAprovacao", query = "SELECT s FROM SolicitacaoProcesso s WHERE s.aprovacao = :aprovacao"),
    @NamedQuery(name = "SolicitacaoProcesso.findByStatus", query = "SELECT s FROM SolicitacaoProcesso s WHERE s.status = :status"),
    @NamedQuery(name = "SolicitacaoProcesso.findByMotivoSolicitacao", query = "SELECT s FROM SolicitacaoProcesso s WHERE s.motivoSolicitacao = :motivoSolicitacao"),
    @NamedQuery(name = "SolicitacaoProcesso.findByDataAtSolicitacao", query = "SELECT s FROM SolicitacaoProcesso s WHERE s.dataAtSolicitacao = :dataAtSolicitacao"),
    @NamedQuery(name = "SolicitacaoProcesso.findBySetorAlvoSolicitacao", query = "SELECT s FROM SolicitacaoProcesso s WHERE s.setorAlvoSolicitacao = :setorAlvoSolicitacao"),
    @NamedQuery(name = "SolicitacaoProcesso.findByMotivoIndeferimento", query = "SELECT s FROM SolicitacaoProcesso s WHERE s.motivoIndeferimento = :motivoIndeferimento"),
    @NamedQuery(name = "SolicitacaoProcesso.findBySetorSolicitanteId", query = "SELECT s FROM SolicitacaoProcesso s WHERE s.setorSolicitanteId = :setorSolicitanteId"),
    @NamedQuery(name = "SolicitacaoProcesso.findBySetorAlvoSolicitacaoId", query = "SELECT s FROM SolicitacaoProcesso s WHERE s.setorAlvoSolicitacaoId = :setorAlvoSolicitacaoId")})
public class SolicitacaoProcesso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_solicitacao")
    private Integer codSolicitacao;
    @Basic(optional = false)
    @Column(name = "data_solicitacao")
    @Temporal(TemporalType.DATE)
    private Date dataSolicitacao;
    @Basic(optional = false)
    @Column(name = "solicitante")
    private String solicitante;
    @Basic(optional = false)
    @Column(name = "setor_solicitante")
    private String setorSolicitante;
    @Column(name = "aprovacao")
    private Boolean aprovacao;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "motivo_solicitacao")
    private String motivoSolicitacao;
    @Column(name = "data_at_solicitacao")
    private String dataAtSolicitacao;
    @Column(name = "setor_alvo_solicitacao")
    private String setorAlvoSolicitacao;
    @Column(name = "motivo_indeferimento")
    private String motivoIndeferimento;
    @Basic(optional = false)
    @Column(name = "setor_solicitante_id")
    private int setorSolicitanteId;
    @Basic(optional = false)
    @Column(name = "setor_alvo_solicitacao_id")
    private int setorAlvoSolicitacaoId;
    @JoinColumn(name = "cod_processo", referencedColumnName = "cod_processo")
    @ManyToOne(optional = false)
    private Processo codProcesso;

    public SolicitacaoProcesso() {
    }

    public SolicitacaoProcesso(Integer codSolicitacao) {
        this.codSolicitacao = codSolicitacao;
    }

    public SolicitacaoProcesso(Integer codSolicitacao, Date dataSolicitacao, String solicitante, String setorSolicitante, String status, String motivoSolicitacao, int setorSolicitanteId, int setorAlvoSolicitacaoId) {
        this.codSolicitacao = codSolicitacao;
        this.dataSolicitacao = dataSolicitacao;
        this.solicitante = solicitante;
        this.setorSolicitante = setorSolicitante;
        this.status = status;
        this.motivoSolicitacao = motivoSolicitacao;
        this.setorSolicitanteId = setorSolicitanteId;
        this.setorAlvoSolicitacaoId = setorAlvoSolicitacaoId;
    }

    public Integer getCodSolicitacao() {
        return codSolicitacao;
    }

    public void setCodSolicitacao(Integer codSolicitacao) {
        this.codSolicitacao = codSolicitacao;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getSetorSolicitante() {
        return setorSolicitante;
    }

    public void setSetorSolicitante(String setorSolicitante) {
        this.setorSolicitante = setorSolicitante;
    }

    public Boolean getAprovacao() {
        return aprovacao;
    }

    public void setAprovacao(Boolean aprovacao) {
        this.aprovacao = aprovacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMotivoSolicitacao() {
        return motivoSolicitacao;
    }

    public void setMotivoSolicitacao(String motivoSolicitacao) {
        this.motivoSolicitacao = motivoSolicitacao;
    }

    public String getDataAtSolicitacao() {
        return dataAtSolicitacao;
    }

    public void setDataAtSolicitacao(String dataAtSolicitacao) {
        this.dataAtSolicitacao = dataAtSolicitacao;
    }

    public String getSetorAlvoSolicitacao() {
        return setorAlvoSolicitacao;
    }

    public void setSetorAlvoSolicitacao(String setorAlvoSolicitacao) {
        this.setorAlvoSolicitacao = setorAlvoSolicitacao;
    }

    public String getMotivoIndeferimento() {
        return motivoIndeferimento;
    }

    public void setMotivoIndeferimento(String motivoIndeferimento) {
        this.motivoIndeferimento = motivoIndeferimento;
    }

    public int getSetorSolicitanteId() {
        return setorSolicitanteId;
    }

    public void setSetorSolicitanteId(int setorSolicitanteId) {
        this.setorSolicitanteId = setorSolicitanteId;
    }

    public int getSetorAlvoSolicitacaoId() {
        return setorAlvoSolicitacaoId;
    }

    public void setSetorAlvoSolicitacaoId(int setorAlvoSolicitacaoId) {
        this.setorAlvoSolicitacaoId = setorAlvoSolicitacaoId;
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
        hash += (codSolicitacao != null ? codSolicitacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SolicitacaoProcesso)) {
            return false;
        }
        SolicitacaoProcesso other = (SolicitacaoProcesso) object;
        if ((this.codSolicitacao == null && other.codSolicitacao != null) || (this.codSolicitacao != null && !this.codSolicitacao.equals(other.codSolicitacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sisacompo.sisap.modelo.SolicitacaoProcesso[ codSolicitacao=" + codSolicitacao + " ]";
    }
    
}
