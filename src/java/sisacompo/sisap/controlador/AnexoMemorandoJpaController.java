/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sisacompo.sisap.controlador;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import sisacompo.sisap.modelo.AnexoMemorando;
import sisacompo.sisap.modelo.Memorando;

/**
 *
 * @author eneas
 */
@ManagedBean(name = "AnexoMemorandoControlador")
@SessionScoped
public class AnexoMemorandoJpaController {

    private AnexoMemorando anexoMemorando;
    private List<AnexoMemorando> anexosMemorando = new ArrayList<AnexoMemorando>();
    private long codMemorando = 0;
    private String numMemorando = "";
    private String dataMemorando = "";
    private Date dataMemorandoTeste = new Date();
    private String assuntoMemorando = "";
    private int codAnexoMemo = 0;
    private ServletOutputStream outStream;
    private byte[] arquivoPDF = null;
    private UploadedFile uf;
    
    private String setorCadastrante = "";
    
    //private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Sisacompo1PU");
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Sisacompo1PU");
    private EntityManager em = emf.createEntityManager();
    

    public AnexoMemorandoJpaController() {
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public AnexoMemorando getAnexoMemorando() {
        if(this.anexoMemorando == null){
            this.anexoMemorando = new AnexoMemorando();
        }
        return anexoMemorando;
    }

    public void setAnexoMemorando(AnexoMemorando anexoMemorando) {
        this.anexoMemorando = anexoMemorando;
    }

    public String getSetorCadastrante() {
        return setorCadastrante;
    }

    public long getCodMemorando() {
        return codMemorando;
    }

    public void setCodMemorando(long codMemorando) {
        this.codMemorando = codMemorando;
    }

    public void setSetorCadastrante(String setorCadastrante) {
        this.setorCadastrante = setorCadastrante;
    }

    public ServletOutputStream getOutStream() {
        return outStream;
    }

    public UploadedFile getUf() {
        return uf;
    }

    public void setUf(UploadedFile uf) {
        this.uf = uf;
    }

    public void setOutStream(ServletOutputStream outStream) {
        this.outStream = outStream;
    }

    public Date getDataMemorandoTeste() {
        return dataMemorandoTeste;
    }

    public void setDataMemorandoTeste(Date dataMemorandoTeste) {
        this.dataMemorandoTeste = dataMemorandoTeste;
    }

    public List<AnexoMemorando> getAnexosMemorando() {
        Collection<AnexoMemorando> c = new ArrayList();
        c = em.createNamedQuery("AnexoMemorando.findAll").getResultList();
        this.anexosMemorando = new ArrayList(c);
        return anexosMemorando;
    }

    public List<AnexoMemorando> getDocumentosAnexosMemorando() {
        Collection<AnexoMemorando> c = new ArrayList();
        
        Query query = em.createQuery("SELECT am FROM AnexoMemorando am WHERE am.codMemorando.codMemorando = " + codMemorando + "");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        c = query.getResultList();
        this.anexosMemorando = new ArrayList(c);
        return anexosMemorando;
    }

    public void setAnexosMemorando(List<AnexoMemorando> anexosMemorando) {
        this.anexosMemorando = anexosMemorando;
    }

    public String getAssuntoMemorando() {
        return assuntoMemorando;
    }

    public void setAssuntoMemorando(String assuntoMemorando) {
        this.assuntoMemorando = assuntoMemorando;
    }

    public String getDataMemorando() {
        return dataMemorando;
    }

    public void setDataMemorando(String dataMemorando) {
        this.dataMemorando = dataMemorando;
    }

    public String getNumMemorando() {
        return numMemorando;
    }

    public void setNumMemorando(String numMemorando) {
        this.numMemorando = numMemorando;
    }

    public byte[] getArquivoPDF() {
        return arquivoPDF;
    }

    public void setArquivoPDF(byte[] arquivoPDF) {
        this.arquivoPDF = arquivoPDF;
    }

    public int getCodAnexoMemo() {
        return codAnexoMemo;
    }

    public void setCodAnexoMemo(int codAnexoMemo) {
        this.codAnexoMemo = codAnexoMemo;
    }

    public void handleFileUpload(FileUploadEvent event) {

        this.arquivoPDF = event.getFile().getContents();

        
        anexoMemorando.setCodMemorando(em.find(Memorando.class, this.getCodMemorando()));
        anexoMemorando.setArquivoAnexoMemo(arquivoPDF);
        anexoMemorando.setNomeArquivoMemo(event.getFile().getFileName());
        anexoMemorando.setTamanho(event.getFile().getSize());

        try {

            em.getTransaction().begin();
            em.persist(anexoMemorando);
            em.getTransaction().commit();
//            getAnexosMemorando();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Sucesso", "Inserido o Anexo " + event.getFile().getFileName() + " ao Memorando nº " + this.anexoMemorando.getCodMemorando().getNumMemorando()));

            this.anexoMemorando = new AnexoMemorando();
            this.arquivoPDF = null;
            this.numMemorando = "";
            this.codMemorando = 0;
            //this.codAnexoMemo = "";

        } catch (Throwable t) {

            //t.printStackTrace();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe o arquivo "
                    + event.getFile().getFileName().toUpperCase() + " anexado ao memorando n° " + this.anexoMemorando.getCodMemorando().getNumMemorando()));

            if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O Anexo já existe."));
            }
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://000.00.00.000:0000/banco", "eneas", "eneas");
        } catch (Throwable t) {
            System.out.println("A descrição da conexão está incorreta.");
            System.exit(0);
            return null;
        }
    }

    public void downloadAnexoMemorando(ActionEvent e) {
        
        try {

            Connection conn = getConnection();
            PreparedStatement ps = null;
            
            ps = conn.prepareStatement("select arquivo_anexo_memo, nome_arquivo_memo, tamanho from sisap.anexo_memorando where cod_memorando = "
                    + codMemorando + " and cod_anexo_memo = "+codAnexoMemo+"");
            
            ResultSet rs = ps.executeQuery();
            rs.next();

            String nome = rs.getString("nome_arquivo_memo");
            byte[] arquivoAnexo = new byte[(int) rs.getLong("tamanho")];
            rs.getBinaryStream("arquivo_anexo_memo").read(arquivoAnexo);

            rs.close();
            ps.close();
            conn.close();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

            response.setCharacterEncoding("ISO-8859-1");
            response.setContentLength(arquivoAnexo.length);
            response.addHeader("Content-Disposition", "inline; filename=\"" + nome + "\"");
            response.setContentType("application/pdf");
            response.addHeader("Cache-Control", "no-cache");

            InputStream stream = new ByteArrayInputStream(arquivoAnexo);
            try {
                outStream = response.getOutputStream();

                byte[] buffer = new byte[0000];
                int n = 0;

                while ((n = stream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, n);
                }
                outStream.flush();
                outStream.close();
                FacesContext.getCurrentInstance().responseComplete();

            } catch (Throwable t) {
               // t.printStackTrace();
            }

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public List<String> completaNumMemorando(String q) {
        Collection<String> c = em.createQuery("SELECT DISTINCT m.numMemorando FROM Memorando m WHERE m.numMemorando like '%" + q + "%'").getResultList();
        return new ArrayList<String>(c);
    }

    public void retornaAtributosMemorando(AjaxBehaviorEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O processo já existe."));

        Collection<Memorando> c = em.createQuery("SELECT m FROM Memorando m WHERE m.numMemorando like '%" + numMemorando + "%'").getResultList();

        if (c.size() > 0) {
            Date d = new ArrayList<Memorando>(c).get(0).getDataMemorando();
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            this.dataMemorando = f.format(d);
            this.assuntoMemorando = new ArrayList<Memorando>(c).get(0).getAssuntoMemorando();
            //this.numMemorando = new ArrayList<Memorando>(c).get(0).getNumMemorando();
            this.numMemorando = new ArrayList<Memorando>(c).get(0).getNumMemorando();
        } else {
            this.dataMemorando = "";
            this.assuntoMemorando = "";
            this.numMemorando = "";
        }
    }

    public void preparaInsercao(ActionEvent e) {
        this.anexoMemorando = new AnexoMemorando();
        this.anexosMemorando = new ArrayList<AnexoMemorando>();
        this.numMemorando = "";
        this.dataMemorando = "";
        this.assuntoMemorando = "";
        this.arquivoPDF = null;
        this.codMemorando = 0;
        this.codAnexoMemo = 0;
    }
    
    public void excluir(ActionEvent e){
        AnexoMemorando anexo_memo_temp = this.anexoMemorando;
        em.remove(anexo_memo_temp);
        em.getTransaction().begin();
        em.getTransaction().commit();
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso", "Excluído o anexo: " + anexoMemorando.getNomeArquivoMemo()));
        
        getAnexosMemorando();
        this.anexoMemorando = new AnexoMemorando();
    }
}
