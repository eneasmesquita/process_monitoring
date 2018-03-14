package sisacompo.sisap.controlador;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
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
import sisacompo.sisap.modelo.Anexo;
import sisacompo.sisap.modelo.Processo;

/**
 *
 * @author eneas
 */
@ManagedBean(name = "AnexoControlador")
@SessionScoped
public class AnexoJpaController {

    private Anexo anexo;
    private List<Anexo> anexos = new ArrayList<Anexo>();
    private long codProcesso = 0;
    private String numProcesso = "";
    private String tipoProcesso = "";
    private String assuntoProcesso = "";
    private String nomeArquivo;
    private int codAnexo = 0;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Sisacompo1PU");
    private EntityManager em = emf.createEntityManager();
    private ServletOutputStream outStream;
    private byte[] arquivoPDF;

    public AnexoJpaController() {
    }

    public Anexo getAnexo() {
        return anexo;
    }

    public void setAnexo(Anexo anexo) {
        this.anexo = anexo;
    }

    public List<Anexo> getAnexos() {
        Collection<Anexo> c = new ArrayList();
        c = em.createNamedQuery("Anexo.findAll").getResultList();
        this.anexos = new ArrayList(c);
        return anexos;
    }

    public List<Anexo> getAnexosProcesso() {
        Collection<Anexo> c = new ArrayList();
        Query query = em.createQuery("SELECT a FROM Anexo a WHERE a.codProcesso.codProcesso = " + codProcesso + "");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        c = query.getResultList();
        this.anexos = new ArrayList(c);
        return anexos;
    }

    public void setAnexos(List<Anexo> anexos) {
        this.anexos = anexos;
    }

    public long getCodProcesso() {
        return codProcesso;
    }

    public void setCodProcesso(long codProcesso) {
        this.codProcesso = codProcesso;
    }

    public String getAssuntoProcesso() {
        return assuntoProcesso;
    }

    public String getNumProcesso() {
        return numProcesso;
    }

    public void setNumProcesso(String numProcesso) {
        this.numProcesso = numProcesso;
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

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public ServletOutputStream getOutStream() {
        return outStream;
    }

    public void setOutStream(ServletOutputStream outStream) {
        this.outStream = outStream;
    }

    public byte[] getArquivoPDF() {
        return arquivoPDF;
    }

    public void setArquivoPDF(byte[] arquivoPDF) {
        this.arquivoPDF = arquivoPDF;
    }

    public int getCodAnexo() {
        return codAnexo;
    }

    public void setCodAnexo(int codAnexo) {
        this.codAnexo = codAnexo;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void handleFileUpload(FileUploadEvent event) {

        this.arquivoPDF = event.getFile().getContents();

        anexo.setCodProcesso(em.find(Processo.class, this.getCodProcesso()));
        anexo.setArquivoAnexo(arquivoPDF);
        anexo.setNomeAnexo(event.getFile().getFileName());
        anexo.setTamanho(event.getFile().getSize());

        try {

            em.getTransaction().begin();
            em.persist(anexo);
            em.getTransaction().commit();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Sucesso", "Inserido o Anexo " + event.getFile().getFileName() + " ao Processo nº " + anexo.getCodProcesso().getNumProcesso()));

            this.anexo = new Anexo();
            this.arquivoPDF = null;
            this.codProcesso = 0;

        } catch (Throwable t) {

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe o arquivo "
                    + event.getFile().getFileName().toUpperCase() + " anexado ao processo n° " + anexo.getCodProcesso().getNumProcesso()));

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

    public void downloadAnexoProcesso(ActionEvent e) {

        try {

            Connection conn = getConnection();
            PreparedStatement ps = null;

            ps = conn.prepareStatement("select arquivo_anexo, nome_anexo, tamanho from sisap.anexo where cod_processo = '"
                    + codProcesso + "'and cod_anexo = " + codAnexo + "");

            ResultSet rs = ps.executeQuery();
            rs.next();

            String nome = rs.getString("nome_anexo");
            byte[] arquivoAnexo = new byte[(int) rs.getLong("tamanho")];
            rs.getBinaryStream("arquivo_anexo").read(arquivoAnexo);

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

                byte[] buffer = new byte[1024];
                int n = 0;

                while ((n = stream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, n);
                }
                outStream.flush();
                outStream.close();
                FacesContext.getCurrentInstance().responseComplete();

            } catch (Throwable t) {
            }

        } catch (Throwable ex) {
        }
    }

    public void preparaInsercao(ActionEvent e) {
        this.anexo = new Anexo();
        this.anexos = new ArrayList<Anexo>();
        this.codProcesso = 0;
        this.tipoProcesso = "";
        this.assuntoProcesso = "";
        this.codAnexo = 0;
    }

    public List<String> completaNumProcesso(String q) {
        Collection<String> c = em.createQuery("SELECT DISTINCT p.numProcesso FROM Processo p WHERE p.numProcesso like '%" + q + "%'").getResultList();
        return new ArrayList<String>(c);
    }

    public void retornaAtributosProcesso(AjaxBehaviorEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O processo já existe."));

        Collection<Processo> c = em.createQuery("SELECT p FROM Processo p WHERE p.numProcesso like '%" + numProcesso + "%'").getResultList();

        if (c.size() > 0) {
            this.tipoProcesso = new ArrayList<Processo>(c).get(0).getTipoProcesso();
            this.assuntoProcesso = new ArrayList<Processo>(c).get(0).getAssuntoProcesso();
            this.numProcesso = new ArrayList<Processo>(c).get(0).getNumProcesso();
        } else {
            this.tipoProcesso = "";
            this.assuntoProcesso = "";
            this.numProcesso = "";
        }
    }

    public void excluir(ActionEvent e) {
        Anexo anexo_tempo = this.anexo;
        em.remove(anexo_tempo);
        em.getTransaction().begin();
        em.getTransaction().commit();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso", "Excluído o anexo: " + anexo.getNomeAnexo()));

        getAnexos();
        this.anexo = new Anexo();
    }
}
