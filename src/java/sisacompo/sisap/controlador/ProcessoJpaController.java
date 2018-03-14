/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sisacompo.sisap.controlador;

import aut.com.mb.LoginBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.el.ELContext;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.*;
import org.primefaces.event.SelectEvent;
import sisacompo.sisap.modelo.Memorando;
import sisacompo.sisap.modelo.Processo;
import sisacompo.sisap.modelo.UsuarioProcesso;

/**
 *
 * @author eneas
 */
@ManagedBean(name = "ProcessoControlador")
@SessionScoped
public class ProcessoJpaController {

    private Processo processo;
    private Processo processo_inserir;
    private List<Processo> processos;
    private long codMemorando = 0;
    private String numMemorando = "";
    private String tipoOutroProcesso = "";
    private String outroTipoProcessoEdit = "";
    //private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Sisacompo1PU");
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Sisacompo1PU");
    private EntityManager em = emf.createEntityManager();
    private String utGeral;
    private String ucGeral;
    private Long qtdAtivoGeral;
    private Long qtdArquivadoGeral;
    private Long totalProcessosGeral;
    private String utServico;
    private String ucServico;
    private Long qtdAtivoServico;
    private Long qtdArquivadoServico;
    private Long totalProcessosServico;
    private String utCompras;
    private String ucCompras;
    private Long qtdAtivoCompras;
    private Long qtdArquivadoCompras;
    private Long totalProcessosCompras;
    private String utDiarias;
    private String ucDiarias;
    private Long qtdAtivoDiarias;
    private Long qtdArquivadoDiarias;
    private Long totalProcessosDiarias;
    private String utOutros;
    private String ucOutros;
    private Long qtdAtivoOutros;
    private Long qtdArquivadoOutros;
    private Long totalProcessosOutros;
    private boolean outro;
    private boolean cadOutro;
    private String assuntoMemorando = "";
    private String tipoMemorando = "";
    private String categoria = "";
    private String categoriaOutros = "";
    private boolean exibeSigiloso = false;
    LoginBean lb = (LoginBean) getManagedBean("loginBean");
    
    TramitacaoProcessoJpaController tp = (TramitacaoProcessoJpaController) getManagedBean("TramiteProcessoControlador");

    public ProcessoJpaController() {
    }

    public Processo getProcesso() {
        if (this.processo == null) {
            this.processo = new Processo();
        }
        return processo;
    }

    public Processo getProcesso_inserir() {
        if (this.processo_inserir == null) {
            this.processo_inserir = new Processo();
        }
        return processo_inserir;
    }

    public void setProcesso_inserir(Processo processo_inserir) {
        this.processo_inserir = processo_inserir;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public boolean isExibeSigiloso() {
        return exibeSigiloso;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNumMemorando() {
        return numMemorando;
    }

    public void setNumMemorando(String numMemorando) {
        this.numMemorando = numMemorando;
    }

    public void setExibeSigiloso(boolean exibeSigiloso) {
        this.exibeSigiloso = exibeSigiloso;
    }

    public List<Processo> getProcessos() {
        Collection<Processo> c = new ArrayList();
        c = em.createNamedQuery("Processo.findAll").getResultList();
        this.processos = new ArrayList(c);
        return processos;
    }

    public String getCategoriaOutros() {
        return categoriaOutros;
    }

    public void setCategoriaOutros(String categoriaOutros) {
        this.categoriaOutros = categoriaOutros;
    }

    public String getAssuntoMemorando() {
        return assuntoMemorando;
    }

    public void setAssuntoMemorando(String assuntoMemorando) {
        this.assuntoMemorando = assuntoMemorando;
    }

    public String getTipoMemorando() {
        return tipoMemorando;
    }

    public void setTipoMemorando(String tipoMemorando) {
        this.tipoMemorando = tipoMemorando;
    }

    public List<Processo> getProcessosServico() {

        Collection<Processo> c = new ArrayList();
        Query query = em.createQuery("SELECT p FROM Processo p WHERE p.tipoProcesso = 'SERVIÇO'");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        c = query.getResultList();

        this.processos = new ArrayList(c);
        return processos;
    }

    public boolean verificaExibicaoCompras(String num_processo, String setor_id, String login) {

        Processo pro_temp = new Processo();
        UsuarioProcesso up_temp = new UsuarioProcesso();
        boolean exibe = false;
        boolean verificaNulo = true;

        try {
            up_temp = (UsuarioProcesso) em.createQuery("select u from UsuarioProcesso u where u.codProcesso.numProcesso = '" + num_processo + "' and "
                    + "u.setorId = '"+setor_id+"' and u.username = '"+login+"'").getSingleResult();
        } catch (NoResultException e) {
            verificaNulo = false;
        }

        try {
            pro_temp = (Processo) em.createQuery("SELECT p FROM Processo p WHERE p.tipoProcesso = 'COMPRA' and p.numProcesso = '" + num_processo + "'").getSingleResult();
        } catch (NoResultException e) {
            verificaNulo = false;
        }

        if (verificaNulo == false) {
            exibe = false;
        } else {
            if (pro_temp.getSigiloso() == true) {
                if (up_temp.getCodProcesso().getCodProcesso() == pro_temp.getCodProcesso()
                        && up_temp.getUsername().equals(lb.getSession().getAttribute("login"))) {
                    exibe = true;
                }
            } else {
                exibe = false;
            }
        }
        return exibe;
    }

    public boolean verificaExibicaoServico(String num_processo, String setor_id, String login) {

        Processo pro_temp = new Processo();
        UsuarioProcesso up_temp = new UsuarioProcesso();
        boolean exibe = false;
        boolean verificaNulo = true;

        try {
            up_temp = (UsuarioProcesso) em.createQuery("select u from UsuarioProcesso u where u.codProcesso.numProcesso = '" + num_processo + "' and "
                    + "u.setorId = '"+setor_id+"' and u.username = '"+login+"'").getSingleResult();
        } catch (NoResultException e) {
            verificaNulo = false;
        }

        try {
            pro_temp = (Processo) em.createQuery("SELECT p FROM Processo p WHERE p.tipoProcesso = 'SERVIÇO' and p.numProcesso = '" + num_processo + "'").getSingleResult();
        } catch (NoResultException e) {
            verificaNulo = false;
        }

        if (verificaNulo == false) {
            exibe = false;
        } else {
            if (pro_temp.getSigiloso() == true) {
                if (up_temp.getCodProcesso().getCodProcesso() == pro_temp.getCodProcesso()
                        && up_temp.getUsername().equals(lb.getSession().getAttribute("login"))) {
                    exibe = true;
                }
            } else {
                exibe = false;
            }
        }
        return exibe;
    }

    public boolean verificaExibicaoDiarias(String num_processo, String setor_id, String login) {

        Processo pro_temp = new Processo();
        UsuarioProcesso up_temp = new UsuarioProcesso();
        boolean exibe = false;
        boolean verificaNulo = true;

        try {
            up_temp = (UsuarioProcesso) em.createQuery("select u from UsuarioProcesso u where u.codProcesso.numProcesso = '" + num_processo + "' and "
                    + "u.setorId = '"+setor_id+"' and u.username = '"+login+"'").getSingleResult();
        } catch (NoResultException e) {
            verificaNulo = false;
        }

        try {
            pro_temp = (Processo) em.createQuery("SELECT p FROM Processo p WHERE p.tipoProcesso = 'DIÁRIA' and p.numProcesso = '" + num_processo + "'").getSingleResult();
        } catch (NoResultException e) {
            verificaNulo = false;
        }

        if (verificaNulo == false) {
            exibe = false;
        } else {
            if (pro_temp.getSigiloso() == true) {
                if (up_temp.getCodProcesso().getCodProcesso() == pro_temp.getCodProcesso()
                        && up_temp.getUsername().equals(lb.getSession().getAttribute("login"))) {
                    exibe = true;
                }
            } else {
                exibe = false;
            }
        }
        return exibe;
    }

    public boolean verificaExibicaoOutros(String num_processo, String setor_id, String login) {

        Processo pro_temp = new Processo();
        UsuarioProcesso up_temp = new UsuarioProcesso();
        boolean exibe = false;
        boolean verificaNulo = true;

        try {
            up_temp = (UsuarioProcesso) em.createQuery("select u from UsuarioProcesso u where u.codProcesso.numProcesso = '" + num_processo + "' and "
                    + "u.setorId = '"+setor_id+"' and u.username = '"+login+"'").getSingleResult();
        } catch (NoResultException e) {
            verificaNulo = false;
        }

        try {
            pro_temp = (Processo) em.createQuery("SELECT p FROM Processo p WHERE p.tipoProcesso = 'OUTROS' and p.numProcesso = '" + num_processo + "'").getSingleResult();
        } catch (NoResultException e) {
            verificaNulo = false;
        }

        if (verificaNulo == false) {
            exibe = false;
        } else {
            if (pro_temp.getSigiloso() == true) {
                if (up_temp.getCodProcesso().getCodProcesso() == pro_temp.getCodProcesso()
                        && up_temp.getUsername().equals(lb.getSession().getAttribute("login"))) {
                    exibe = true;
                }
            } else {
                exibe = false;
            }
        }
        return exibe;
    }

    public List<Processo> getProcessosDiaria() {

        Collection<Processo> c = new ArrayList();
        c = em.createQuery("SELECT p FROM Processo p WHERE p.tipoProcesso = 'DIÁRIA'").getResultList();
        this.processos = new ArrayList(c);
        return processos;
    }

    public List<Processo> getProcessosCompra() {

        Collection<Processo> c = new ArrayList();
        Query query = em.createQuery("SELECT p FROM Processo p WHERE p.tipoProcesso = 'COMPRA'");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        c = query.getResultList();
        this.processos = new ArrayList(c);
        return processos;
    }

    public List<Processo> getProcessosOutros() {

        Collection<Processo> c = new ArrayList();
        c = em.createQuery("SELECT p FROM Processo p WHERE p.tipoProcesso <> 'COMPRA' AND "
                + "p.tipoProcesso <> 'DIÁRIA' AND p.tipoProcesso <> 'SERVIÇO'").getResultList();
        this.processos = new ArrayList(c);
        return processos;
    }

    public List<Processo> getProcessosSigilosos() {
        Collection<Processo> c = new ArrayList();
        Query query = em.createQuery("SELECT p FROM Processo p WHERE p.sigiloso = true order by p.numProcesso asc");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        c = query.getResultList();
        this.processos = new ArrayList(c);
        return processos;
    }

    public void insereCategoria() {
        this.categoria = this.processo_inserir.getTipoProcesso();
    }

    public void insereOutraCategoria() {
        this.categoriaOutros = this.tipoOutroProcesso.toUpperCase();
    }

    public void setProcessos(List<Processo> processos) {
        this.processos = processos;
    }

    public long getCodMemorando() {
        return codMemorando;
    }

    public void setCodMemorando(long codMemorando) {
        this.codMemorando = codMemorando;
    }

    public Long getQtdArquivadoCompras() {
        try {
            qtdArquivadoCompras = (Long) em.createQuery("SELECT COUNT(p.ativoProcesso) FROM Processo p WHERE p.tipoProcesso = 'COMPRA' AND p.ativoProcesso = false").getSingleResult();
        } catch (NoResultException e) {
            return Long.valueOf(0);
        }
        return qtdArquivadoCompras;
    }

    public Long getQtdArquivadoDiarias() {
        try {
            qtdArquivadoDiarias = (Long) em.createQuery("SELECT COUNT(p.ativoProcesso) FROM Processo p WHERE p.tipoProcesso = 'DIÁRIA' AND p.ativoProcesso = false").getSingleResult();
        } catch (NoResultException e) {
            return Long.valueOf(0);
        }
        return qtdArquivadoDiarias;
    }

    public Long getQtdArquivadoOutros() {
        try {
            qtdArquivadoOutros = (Long) em.createQuery("SELECT COUNT(p.ativoProcesso) FROM Processo p "
                    + "WHERE p.tipoProcesso <> 'COMPRA' AND p.tipoProcesso <> 'SERVIÇO' AND p.tipoProcesso <> 'DIÁRIA' AND p.ativoProcesso = false").getSingleResult();
        } catch (NoResultException e) {
            return Long.valueOf(0);
        }
        return qtdArquivadoOutros;
    }

    public Long getQtdArquivadoServico() {
        try {
            qtdArquivadoServico = (Long) em.createQuery("SELECT COUNT(p.ativoProcesso) FROM Processo p WHERE p.tipoProcesso = 'SERVIÇO' AND p.ativoProcesso = false").getSingleResult();
        } catch (NoResultException e) {
            return Long.valueOf(0);
        }
        return qtdArquivadoServico;
    }

    public Long getQtdAtivoCompras() {
        try {
            qtdAtivoCompras = (Long) em.createQuery("SELECT COUNT(p.ativoProcesso) FROM Processo p WHERE p.tipoProcesso = 'COMPRA' AND p.ativoProcesso = true").getSingleResult();
        } catch (NoResultException e) {
            return Long.valueOf(0);
        }
        return qtdAtivoCompras;
    }

    public Long getQtdAtivoDiarias() {
        try {
            qtdAtivoDiarias = (Long) em.createQuery("SELECT COUNT(p.ativoProcesso) FROM Processo p WHERE p.tipoProcesso = 'DIÁRIA' AND p.ativoProcesso = true").getSingleResult();
        } catch (NoResultException e) {
            return Long.valueOf(0);
        }
        return qtdAtivoDiarias;
    }

    public Long getQtdAtivoOutros() {
        try {
            qtdAtivoOutros = (Long) em.createQuery("SELECT COUNT(p.ativoProcesso) FROM Processo p WHERE "
                    + "p.tipoProcesso <> 'COMPRA' AND p.tipoProcesso <> 'SERVIÇO' AND p.tipoProcesso <> 'DIÁRIA' AND p.ativoProcesso = true").getSingleResult();
        } catch (NoResultException e) {
            return Long.valueOf(0);
        }
        return qtdAtivoOutros;
    }

    public Long getQtdAtivoServico() {
        try {
            qtdAtivoServico = (Long) em.createQuery("SELECT COUNT(p.ativoProcesso) FROM Processo p WHERE p.tipoProcesso = 'SERVIÇO' AND p.ativoProcesso = true").getSingleResult();
            return qtdAtivoServico;
        } catch (NoResultException e) {
            return Long.valueOf(0);
        }
    }

    public String getUcCompras() {
        try {
            ucCompras = (String) em.createQuery("SELECT MAX(p.numProcesso) FROM Processo p WHERE p.tipoProcesso = 'COMPRA'").getSingleResult();
        } catch (NoResultException e) {
            return "";
        }
        return ucCompras;
    }

    public String getUcDiarias() {
        try {
            ucDiarias = (String) em.createQuery("SELECT MAX(p.numProcesso) FROM Processo p WHERE p.tipoProcesso = 'DIÁRIA'").getSingleResult();
        } catch (NoResultException e) {
            return "";
        }
        return ucDiarias;
    }

    public String getUcOutros() {
        try {
            ucOutros = (String) em.createQuery("SELECT MAX(p.numProcesso) FROM Processo p WHERE"
                    + " p.tipoProcesso <> 'COMPRA' AND p.tipoProcesso <> 'SERVIÇO' AND p.tipoProcesso <> 'DIÁRIA'").getSingleResult();
        } catch (NoResultException e) {
            return "";
        }
        return ucOutros;
    }

    public String getUcServico() {
        try {
            ucServico = (String) em.createQuery("SELECT MAX(p.numProcesso) FROM Processo p WHERE p.tipoProcesso = 'SERVIÇO'").getSingleResult();
        } catch (NoResultException e) {
            return "";
        }
        return ucServico;
    }

    public String getUtCompras() {

        try {
            utCompras = (String) em.createNativeQuery("select p.num_processo from sisap.tramitacao_processo tp, sisap.processo p"
                    + " where tp.cod_processo = p.cod_processo and p.tipo_processo = 'COMPRA' order by tp.cod_tramitacao_processo desc limit 1").getSingleResult();
        } catch (NoResultException e) {
            return "";
        }
        return utCompras;
    }

    public String getUtDiarias() {
        try {
            utDiarias = (String) em.createNativeQuery("select p.num_processo from sisap.tramitacao_processo tp, sisap.processo p"
                    + " where tp.cod_processo = p.cod_processo and p.tipo_processo = 'DIÁRIA' order by tp.cod_tramitacao_processo desc limit 1").getSingleResult();
        } catch (NoResultException e) {
            return "";
        }
        return utDiarias;

    }

    public String getUtOutros() {
        try {
            utOutros = (String) em.createNativeQuery("SELECT p.num_processo FROM sisap.tramitacao_processo as tp, sisap.processo as p WHERE tp.cod_processo = p.cod_processo "
                    + "and p.tipo_processo <> 'SERVIÇO' and p.tipo_processo <> 'COMPRA' and p.tipo_processo <> 'DIÁRIA' "
                    + "order by tp.cod_tramitacao_processo desc limit 1").getSingleResult();
        } catch (NoResultException e) {
            return "";
        }
        return utOutros;
    }

    public String getUtServico() {
        try {
            utServico = (String) em.createNativeQuery("select p.num_processo from sisap.tramitacao_processo tp, sisap.processo p"
                    + " where tp.cod_processo = p.cod_processo and p.tipo_processo = 'SERVIÇO' order by tp.cod_tramitacao_processo desc limit 1").getSingleResult();
        } catch (NoResultException e) {
            return "";
        }
        return utServico;
    }

    public Long getQtdArquivadoGeral() {
        try {
            qtdArquivadoGeral =
                    (Long) em.createQuery("SELECT COUNT(p.ativoProcesso) FROM Processo p WHERE p.ativoProcesso = false").getSingleResult();
        } catch (NoResultException e) {
            return Long.valueOf(0);
        }
        return qtdArquivadoGeral;
    }

    public Long getQtdAtivoGeral() {
        try {
            qtdAtivoGeral =
                    (Long) em.createQuery("SELECT COUNT(p.ativoProcesso) FROM Processo p WHERE p.ativoProcesso = true").getSingleResult();
        } catch (NoResultException e) {
            return Long.valueOf(0);
        }
        return qtdAtivoGeral;
    }

    public Long getTotalProcessosGeral() {
        try {
            totalProcessosGeral = (Long) em.createQuery("SELECT COUNT(p) FROM Processo p").getSingleResult();
        } catch (NoResultException e) {
            return Long.valueOf(0);
        }
        return totalProcessosGeral;
    }

    public String getUcGeral() {
        try {
            ucGeral = (String) em.createQuery("SELECT MAX(p.numProcesso) FROM Processo p").getSingleResult();
        } catch (NoResultException e) {
            return "";
        }
        return ucGeral;
    }

    public String getUtGeral() {
        try {
            utGeral = (String) em.createNativeQuery("select p.num_processo from sisap.tramitacao_processo tp, sisap.processo "
                    + "p where p.cod_processo = tp.cod_processo order by cod_tramitacao_processo desc limit 1").getSingleResult();
        } catch (NoResultException e) {
            return "";
        }
        return utGeral;
    }

    public Long getTotalProcessosCompras() {
        try {
            totalProcessosCompras = (Long) em.createQuery("SELECT COUNT(p) FROM Processo p where p.tipoProcesso = 'COMPRA'").getSingleResult();
        } catch (NoResultException e) {
            return Long.valueOf(0);
        }
        return totalProcessosCompras;
    }

    public Long getTotalProcessosDiarias() {
        try {
            totalProcessosDiarias = (Long) em.createQuery("SELECT COUNT(p) FROM Processo p where p.tipoProcesso = 'DIÁRIA'").getSingleResult();
        } catch (NoResultException e) {
            return Long.valueOf(0);
        }
        return totalProcessosDiarias;
    }

    public Long getTotalProcessosOutros() {
        try {
            totalProcessosOutros = (Long) em.createQuery("SELECT COUNT(p) FROM Processo p where"
                    + " p.tipoProcesso <> 'COMPRA' AND p.tipoProcesso <> 'SERVIÇO' AND p.tipoProcesso <> 'DIÁRIA'").getSingleResult();
        } catch (NoResultException e) {
            return Long.valueOf(0);
        }
        return totalProcessosOutros;
    }

    public Long getTotalProcessosServico() {
        try {
            totalProcessosServico = (Long) em.createQuery("SELECT COUNT(p) FROM Processo p where p.tipoProcesso = 'SERVIÇO'").getSingleResult();
        } catch (NoResultException e) {
            return Long.valueOf(0);
        }
        return totalProcessosServico;
    }

    public String getTipoOutroProcesso() {
        return tipoOutroProcesso;
    }

    public void setTipoOutroProcesso(String tipoOutroProcesso) {
        this.tipoOutroProcesso = tipoOutroProcesso;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public boolean isOutro() {
        if (this.processo.getTipoProcesso() != null) {
            if (!this.processo.getTipoProcesso().equals("SERVIÇO") && !this.processo.getTipoProcesso().equals("DIÁRIA") && !this.processo.getTipoProcesso().equals("COMPRA")) {
                this.outro = true;
            } else {
                this.outro = false;
                this.outroTipoProcessoEdit = "";
            }
        }
        return outro;
    }

    public boolean isCadOutro() {
        if (this.processo_inserir.getTipoProcesso() != null) {
            if (!this.processo_inserir.getTipoProcesso().equals("SERVIÇO") && !this.processo_inserir.getTipoProcesso().equals("DIÁRIA") && !this.processo_inserir.getTipoProcesso().equals("COMPRA")) {
                this.cadOutro = true;
            } else {
                this.cadOutro = true;
                this.tipoOutroProcesso = "";
            }
        }
        return cadOutro;
    }

    public void cadZeraCategoria() {
        if (this.cadOutro == true) {
            this.processo.setTipoProcesso("");
        } else {
            this.tipoOutroProcesso = "";
        }
    }

    public void setCadOutro(boolean cadOutro) {
        this.cadOutro = cadOutro;
    }

    public void setOutro(boolean outro) {
        this.outro = outro;
    }

    public String getOutroTipoProcessoEdit() {
        if (this.processo.getTipoProcesso() != null) {
            if (!this.processo.getTipoProcesso().equals("SERVIÇO") && !this.processo.getTipoProcesso().equals("DIÁRIA") && !this.processo.getTipoProcesso().equals("COMPRA")) {
                this.outroTipoProcessoEdit = this.processo.getTipoProcesso();
            }
        }
        return outroTipoProcessoEdit;
    }

    public void zeraCategoria() {
        if (this.outro == true) {
            this.processo.setTipoProcesso("");
        } else {
            this.outroTipoProcessoEdit = "";
        }
    }

    public void setOutroTipoProcessoEdit(String outroTipoProcessoEdit) {
        this.outroTipoProcessoEdit = outroTipoProcessoEdit;
    }

    public void inserir(ActionEvent e) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date data = new Date();
            String intermediario = "";

            em.getTransaction().begin();
            
            this.processo_inserir.setCodMemorando(em.find(Memorando.class, codMemorando));
            this.processo_inserir.setAssuntoProcesso(this.processo_inserir.getAssuntoProcesso().toUpperCase());
            this.processo_inserir.setDescricaoProcesso(this.processo_inserir.getDescricaoProcesso().toUpperCase());
            this.processo_inserir.setObservacaoProcesso(this.processo_inserir.getObservacaoProcesso().toUpperCase());

            if (this.tipoOutroProcesso.isEmpty()) {
                this.processo_inserir.setTipoProcesso(this.processo_inserir.getTipoProcesso().toUpperCase());
            } else {
                this.processo_inserir.setTipoProcesso(this.tipoOutroProcesso.toUpperCase());
            }
            
            //this.processo_inserir.setNumMemorando(em.find(Memorando.class, numMemorando));
            this.processo_inserir.setAtivoProcesso(true);
            intermediario = sdf.format(data);
            data = sdf.parse(intermediario);
            this.processo_inserir.setDataAberturaProcesso(data);

//            this.processo.setAnexoCollection(anexos);

            em.persist(processo_inserir);
            em.getTransaction().commit();

            getProcessos();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Sucesso", "Inserido o Processo n°: " + processo_inserir.getNumProcesso()));

        } catch (Throwable t) {
            //t.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Memorando não anexado ao Processo."));

            if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O processo já existe."));
            }
        }
    }

    public void atualizar(ActionEvent e) {

        em.getTransaction().begin();
        Processo temp = this.processo;

        temp.setAssuntoProcesso(this.processo.getAssuntoProcesso().toUpperCase());
        temp.setDescricaoProcesso(this.processo.getDescricaoProcesso().toUpperCase());
        temp.setObservacaoProcesso(this.processo.getObservacaoProcesso().toUpperCase());


        if (this.outroTipoProcessoEdit.isEmpty()) {
            temp.setTipoProcesso(this.processo.getTipoProcesso().toUpperCase());
        } else {
            temp.setTipoProcesso(this.outroTipoProcessoEdit.toUpperCase());
        }

        //temp.setNumMemorando(em.find(Memorando.class, numMemorando));
        em.merge(temp);
        em.getTransaction().commit();
        getProcessos();
        this.tp.getTramite_processo().setCodProcesso(em.find(Processo.class, temp.getCodProcesso()));

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso", "Processo n°: " + processo.getNumProcesso() + " Atualizado!"));

        this.processo = new Processo();
    }

    public void excluir(ActionEvent e) {
        Processo temp = this.processo;
        em.getTransaction().begin();
        em.remove(temp);
        em.getTransaction().commit();
        getProcessos();
        this.processo = new Processo();
    }

    public void preparaInsercao(ActionEvent e) {
        this.processo_inserir = new Processo();
        this.categoria = "";
        this.categoriaOutros = "";
        this.tipoOutroProcesso = "";
        this.processos = new ArrayList<Processo>();
        this.codMemorando = 0;
        this.assuntoMemorando = "";
        this.tipoMemorando = "";
        this.cadOutro = false;
        this.assuntoMemorando = "";
        this.tipoMemorando = "";
    }
    
    public List<String> completaNumMemorando(String q) {

        Collection<String> c = new ArrayList();
        Collection<Memorando> c2 = new ArrayList();
        if (!categoria.equals("")) {
            Query query = em.createQuery("SELECT m FROM Memorando m WHERE m.numMemorando like '%" + q + "%' "
                    + "and m.categoriaMemorando = '" + this.categoria + "'");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            c2 = query.getResultList();
            for (Memorando memorando : c2) {
                c.add(memorando.getNumMemorando()+", "+memorando.getSetorCadastrante());
            }
        }
        return new ArrayList<String>(c);
    }

    public void exibeAssuntoTipoMemo(SelectEvent event) {

        if (!this.numMemorando.equals("")) {
            try {
                String aux = this.numMemorando.substring(0, 10);
                String aux2 = this.numMemorando.substring(12, numMemorando.length());
                System.out.println("SAÍDA DA SUBSTRING NO INTERVALO DE 0 A 10: "+aux);
                System.out.println("SAÍDA DA SUBSTRING NO INTERVALO DE 13 ATÉ O FIM DA STRING: "+aux2);
                Memorando m_temp = (Memorando) em.createQuery("select m from Memorando m where m.numMemorando = '" + aux + "' and m.setorCadastrante = '"+aux2+"'").getSingleResult();
                this.assuntoMemorando = m_temp.getAssuntoMemorando();
                this.tipoMemorando = m_temp.getCategoriaMemorando();
                this.codMemorando = m_temp.getCodMemorando();
            } catch (NoResultException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object getManagedBean(final String beanName) {
        FacesContext fc = FacesContext.getCurrentInstance();

        Object bean;
        try {
            ELContext elContext = fc.getELContext();
            bean = elContext.getELResolver().getValue(elContext, null, beanName);
        } catch (RuntimeException e) {
            throw new FacesException(e.getMessage(), e);
        }

        if (bean == null) {
            throw new FacesException("Managed bean with name '" + beanName + "' was not found. Check your faces-config.xml or @ManagedBean annotation.");
        }

        return bean;
    }
}
