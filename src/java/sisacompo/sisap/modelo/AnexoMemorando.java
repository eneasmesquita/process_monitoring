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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author eneas
 */
@Entity
@Table(name = "anexo_memorando", catalog = "empresa", schema = "sisap")
@NamedQueries({
    @NamedQuery(name = "AnexoMemorando.findAll", query = "SELECT a FROM AnexoMemorando a"),
    @NamedQuery(name = "AnexoMemorando.findByCodAnexoMemo", query = "SELECT a FROM AnexoMemorando a WHERE a.codAnexoMemo = :codAnexoMemo"),
    @NamedQuery(name = "AnexoMemorando.findByNomeArquivoMemo", query = "SELECT a FROM AnexoMemorando a WHERE a.nomeArquivoMemo = :nomeArquivoMemo"),
    @NamedQuery(name = "AnexoMemorando.findByTamanho", query = "SELECT a FROM AnexoMemorando a WHERE a.tamanho = :tamanho"),
    @NamedQuery(name = "AnexoMemorando.findBySetorCadastrante", query = "SELECT a FROM AnexoMemorando a WHERE a.setorCadastrante = :setorCadastrante")})
public class AnexoMemorando implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_anexo_memo")
    private Integer codAnexoMemo;
    @Basic(optional = false)
    @Column(name = "nome_arquivo_memo")
    private String nomeArquivoMemo;
    @Basic(optional = false)
    @Lob
    @Column(name = "arquivo_anexo_memo")
    private byte[] arquivoAnexoMemo;
    @Basic(optional = false)
    @Column(name = "tamanho")
    private long tamanho;
    @Basic(optional = false)
    @Column(name = "setor_cadastrante")
    private String setorCadastrante;
    @JoinColumn(name = "cod_memorando", referencedColumnName = "cod_memorando")
    @ManyToOne(optional = false)
    private Memorando codMemorando;

    public AnexoMemorando() {
    }

    public AnexoMemorando(Integer codAnexoMemo) {
        this.codAnexoMemo = codAnexoMemo;
    }

    public AnexoMemorando(Integer codAnexoMemo, String nomeArquivoMemo, byte[] arquivoAnexoMemo, long tamanho, String setorCadastrante) {
        this.codAnexoMemo = codAnexoMemo;
        this.nomeArquivoMemo = nomeArquivoMemo;
        this.arquivoAnexoMemo = arquivoAnexoMemo;
        this.tamanho = tamanho;
        this.setorCadastrante = setorCadastrante;
    }

    public Integer getCodAnexoMemo() {
        return codAnexoMemo;
    }

    public void setCodAnexoMemo(Integer codAnexoMemo) {
        this.codAnexoMemo = codAnexoMemo;
    }

    public String getNomeArquivoMemo() {
        return nomeArquivoMemo;
    }

    public void setNomeArquivoMemo(String nomeArquivoMemo) {
        this.nomeArquivoMemo = nomeArquivoMemo;
    }

    public byte[] getArquivoAnexoMemo() {
        return arquivoAnexoMemo;
    }

    public void setArquivoAnexoMemo(byte[] arquivoAnexoMemo) {
        this.arquivoAnexoMemo = arquivoAnexoMemo;
    }

    public long getTamanho() {
        return tamanho;
    }

    public void setTamanho(long tamanho) {
        this.tamanho = tamanho;
    }

    public String getSetorCadastrante() {
        return setorCadastrante;
    }

    public void setSetorCadastrante(String setorCadastrante) {
        this.setorCadastrante = setorCadastrante;
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
        hash += (codAnexoMemo != null ? codAnexoMemo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnexoMemorando)) {
            return false;
        }
        AnexoMemorando other = (AnexoMemorando) object;
        if ((this.codAnexoMemo == null && other.codAnexoMemo != null) || (this.codAnexoMemo != null && !this.codAnexoMemo.equals(other.codAnexoMemo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sisacompo.sisap.modelo.AnexoMemorando[ codAnexoMemo=" + codAnexoMemo + " ]";
    }
    
}
