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
@Table(name = "anexo", catalog = "empresa", schema = "sisap")
@NamedQueries({
    @NamedQuery(name = "Anexo.findAll", query = "SELECT a FROM Anexo a"),
    @NamedQuery(name = "Anexo.findByCodAnexo", query = "SELECT a FROM Anexo a WHERE a.codAnexo = :codAnexo"),
    @NamedQuery(name = "Anexo.findByNomeAnexo", query = "SELECT a FROM Anexo a WHERE a.nomeAnexo = :nomeAnexo"),
    @NamedQuery(name = "Anexo.findByTamanho", query = "SELECT a FROM Anexo a WHERE a.tamanho = :tamanho")})
public class Anexo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cod_anexo")
    private Integer codAnexo;
    @Basic(optional = false)
    @Column(name = "nome_anexo")
    private String nomeAnexo;
    @Basic(optional = false)
    @Lob
    @Column(name = "arquivo_anexo")
    private byte[] arquivoAnexo;
    @Basic(optional = false)
    @Column(name = "tamanho")
    private long tamanho;
    @JoinColumn(name = "cod_processo", referencedColumnName = "cod_processo")
    @ManyToOne(optional = false)
    private Processo codProcesso;

    public Anexo() {
    }

    public Anexo(Integer codAnexo) {
        this.codAnexo = codAnexo;
    }

    public Anexo(Integer codAnexo, String nomeAnexo, byte[] arquivoAnexo, long tamanho) {
        this.codAnexo = codAnexo;
        this.nomeAnexo = nomeAnexo;
        this.arquivoAnexo = arquivoAnexo;
        this.tamanho = tamanho;
    }

    public Integer getCodAnexo() {
        return codAnexo;
    }

    public void setCodAnexo(Integer codAnexo) {
        this.codAnexo = codAnexo;
    }

    public String getNomeAnexo() {
        return nomeAnexo;
    }

    public void setNomeAnexo(String nomeAnexo) {
        this.nomeAnexo = nomeAnexo;
    }

    public byte[] getArquivoAnexo() {
        return arquivoAnexo;
    }

    public void setArquivoAnexo(byte[] arquivoAnexo) {
        this.arquivoAnexo = arquivoAnexo;
    }

    public long getTamanho() {
        return tamanho;
    }

    public void setTamanho(long tamanho) {
        this.tamanho = tamanho;
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
        hash += (codAnexo != null ? codAnexo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Anexo)) {
            return false;
        }
        Anexo other = (Anexo) object;
        if ((this.codAnexo == null && other.codAnexo != null) || (this.codAnexo != null && !this.codAnexo.equals(other.codAnexo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sisacompo.sisap.modelo.Anexo[ codAnexo=" + codAnexo + " ]";
    }
    
}
