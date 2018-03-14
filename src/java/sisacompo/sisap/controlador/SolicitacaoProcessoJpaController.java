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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import sisacompo.sisap.modelo.Processo;
import sisacompo.sisap.modelo.SolicitacaoProcesso;

/**
 *
 * @author eneas
 */
@ManagedBean(name = "SolicitacaoProcessoControlador")
@SessionScoped
public class SolicitacaoProcessoJpaController {

    private int setor_id;
    private String setorAlvoSolicitacao;
    private SolicitacaoProcesso solicitacao_processo;
    private List<SolicitacaoProcesso> solicitacao_processos;
    private List<SolicitacaoProcesso> solicitacoes_pendentes;
    private String numProcesso;
    private long codProcesso = 0;
    //private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Sisacompo1PU");
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Sisacompo1PU");
    private EntityManager em = emf.createEntityManager();
    private long ocorrencia = 0;
    private String css = "";
    LoginBean lb = (LoginBean) getManagedBean("loginBean");
    private String sp;

    public SolicitacaoProcessoJpaController() {
    }

    public String getSetorAlvoSolicitacao() {
        return setorAlvoSolicitacao;
    }

    public void setSetorAlvoSolicitacao(String setorAlvoSolicitacao) {
        this.setorAlvoSolicitacao = setorAlvoSolicitacao;
    }

    public int getSetor_id() {
        return setor_id;
    }

    public void setSetor_id(int setor_id) {
        this.setor_id = setor_id;
    }

    public long getCodProcesso() {
        return codProcesso;
    }

    public void setCodProcesso(long codProcesso) {
        this.codProcesso = codProcesso;
    }

    public String getSp() {

        Query query = em.createQuery("select count(sp.codProcesso.numProcesso) from SolicitacaoProcesso sp where sp.setorAlvoSolicitacaoId = '"
                + lb.getSession().getAttribute("setor_id") + "' and sp.status = 'PENDENTE'");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        this.ocorrencia = (Long) query.getSingleResult();

        Query query2 = em.createQuery("select count(sp.codProcesso.numProcesso) from SolicitacaoProcesso sp where sp.setorAlvoSolicitacaoId = '"
                + lb.getSession().getAttribute("setor_id") + "' and sp.status <> 'PENDENTE'");
        query2.setHint("javax.persistence.cache.storeMode", "REFRESH");
        long ocorrencia1 = (Long) query2.getSingleResult();

        if (this.ocorrencia > ocorrencia1) {
            this.ocorrencia -= ocorrencia1;
            this.css = "quant-rec";
        } else {
            this.ocorrencia = 0;
            this.css = "quant-rec2";
        }
        return sp;
    }

    public String getCss() {
        return css;
    }

    public void insereSetorId() {
        Query query = em.createQuery("select s.setorId from Setor s where s.setorNome like '%" + this.setorAlvoSolicitacao + "%'");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        this.setor_id = (Integer) query.getSingleResult();
    }

    public SolicitacaoProcesso getSolicitacao_processo() {
        if (this.solicitacao_processo == null) {
            this.solicitacao_processo = new SolicitacaoProcesso();
        }
        return solicitacao_processo;
    }

    public void setSolicitacao_processo(SolicitacaoProcesso solicitacao_processo) {
        this.solicitacao_processo = solicitacao_processo;
    }

    public List<SolicitacaoProcesso> getSolicitacao_processos() {
        Collection<SolicitacaoProcesso> c = new ArrayList();
        c = em.createNamedQuery("SolicitacaoProcesso.findAll").getResultList();
        this.solicitacao_processos = new ArrayList(c);
        return solicitacao_processos;
    }

    public void setSolicitacao_processos(List<SolicitacaoProcesso> solicitacao_processos) {
        this.solicitacao_processos = solicitacao_processos;
    }

    public String getNumProcesso() {
        return numProcesso;
    }

    public void setNumProcesso(String numProcesso) {
        this.numProcesso = numProcesso;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public long getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(long ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public void inserir(ActionEvent e) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date data = new Date();
            String intermediario = "";

            em.getTransaction().begin();
            this.solicitacao_processo.setSolicitante(String.valueOf(lb.getSession().getAttribute("nome")));
            this.solicitacao_processo.setSetorSolicitanteId(Integer.valueOf(String.valueOf(lb.getSession().getAttribute("setor_id"))));
            this.solicitacao_processo.setSetorSolicitante(String.valueOf(lb.getSession().getAttribute("setor")));
            this.solicitacao_processo.setSetorAlvoSolicitacao(this.setorAlvoSolicitacao);
            this.solicitacao_processo.setSetorAlvoSolicitacaoId(this.setor_id);
            this.solicitacao_processo.setMotivoSolicitacao(this.solicitacao_processo.getMotivoSolicitacao().toUpperCase());
            this.solicitacao_processo.setCodProcesso(em.find(Processo.class, codProcesso));
            this.solicitacao_processo.setStatus("PENDENTE");
            intermediario = sdf.format(data);
            data = sdf.parse(intermediario);
            this.solicitacao_processo.setDataSolicitacao(data);
            em.persist(solicitacao_processo);
            em.getTransaction().commit();
            getSolicitacao_processos();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Sucesso", "Solicitação do Processo nº" + this.solicitacao_processo.getCodProcesso().getNumProcesso() + ", cadastrada!"));

            context.addMessage(null, new FacesMessage("Aviso", "Para saber se sua solicitação foi deferida, acompanhe a listagem de Solicitações de Processos!"));

            this.solicitacao_processo = new SolicitacaoProcesso();
            this.setorAlvoSolicitacao = "";
            this.setor_id = 0;
            this.numProcesso = "";
            this.codProcesso = 0;

        } catch (Throwable t) {

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Já existe o solicitante: "
                    + solicitacao_processo.getSolicitante() + " associado ao processo " + this.solicitacao_processo.getCodProcesso().getNumProcesso()));

            if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {
//                FacesContext context = FacesContext.getCurrentInstance();
//                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O processo já existe."));
//                FacesContext.getCurrentInstance().addMessage(null,
//                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O processo já existe."));
            }
        }
    }

    public void deferirSolicitacaoProcesso(ActionEvent e) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();

        em.getTransaction().begin();
        SolicitacaoProcesso temp = new SolicitacaoProcesso();
        temp.setCodSolicitacao(this.solicitacao_processo.getCodSolicitacao());  //NECESSÁRIO PARA DIZER AO MÉTODO MERGE() QUAL REGISTRO ATUALIZAR
        temp.setAprovacao(true);
        temp.setDataAtSolicitacao(sdf.format(data));
        temp.setStatus("DEFERIDO");
        temp.setDataSolicitacao(this.solicitacao_processo.getDataSolicitacao());
        temp.setMotivoSolicitacao(this.solicitacao_processo.getMotivoSolicitacao().toUpperCase());
        temp.setSolicitante(this.solicitacao_processo.getSolicitante().toUpperCase());
        temp.setSetorSolicitante(this.solicitacao_processo.getSetorSolicitante().toUpperCase());
        temp.setSetorSolicitanteId(this.solicitacao_processo.getSetorSolicitanteId());
        temp.setSetorAlvoSolicitacao(this.solicitacao_processo.getSetorAlvoSolicitacao());
        temp.setSetorAlvoSolicitacaoId(this.solicitacao_processo.getSetorAlvoSolicitacaoId());
        temp.setCodProcesso(em.find(Processo.class, codProcesso));
        em.merge(temp);
        em.getTransaction().commit();
        getSolicitacao_processos();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Nota", "Solicitação do Processo nº" + this.solicitacao_processo.getCodProcesso().getNumProcesso() + ", deferida!"));

        this.solicitacao_processo = new SolicitacaoProcesso();
        this.setorAlvoSolicitacao = "";
        this.setor_id = 0;
        this.numProcesso = "";
        this.codProcesso = 0;

    }

    public void indeferirSolicitacaoProcesso(ActionEvent e) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();

        em.getTransaction().begin();
        SolicitacaoProcesso temp = new SolicitacaoProcesso();
        temp.setCodSolicitacao(this.solicitacao_processo.getCodSolicitacao()); //NECESSÁRIO PARA DIZER AO MÉTODO MERGE() QUAL REGISTRO ATUALIZAR
        temp.setAprovacao(false);
        temp.setDataAtSolicitacao(sdf.format(data));
        temp.setStatus("INDEFERIDO");
        temp.setDataSolicitacao(this.solicitacao_processo.getDataSolicitacao());
        temp.setMotivoSolicitacao(this.solicitacao_processo.getMotivoSolicitacao().toUpperCase());
        temp.setSolicitante(this.solicitacao_processo.getSolicitante().toUpperCase());
        temp.setSetorSolicitante(this.solicitacao_processo.getSetorSolicitante().toUpperCase());
        temp.setSetorSolicitanteId(this.solicitacao_processo.getSetorSolicitanteId());
        temp.setSetorAlvoSolicitacao(this.solicitacao_processo.getSetorAlvoSolicitacao());
        temp.setSetorAlvoSolicitacaoId(this.solicitacao_processo.getSetorAlvoSolicitacaoId());
        temp.setMotivoIndeferimento(this.solicitacao_processo.getMotivoIndeferimento().toUpperCase());
        temp.setCodProcesso(em.find(Processo.class, codProcesso));
        em.merge(temp);
        em.getTransaction().commit();
        getSolicitacao_processos();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Nota", "Solicitação do Processo nº" + this.solicitacao_processo.getCodProcesso().getNumProcesso() + ", indeferida!"));

        this.solicitacao_processo = new SolicitacaoProcesso();
        this.setorAlvoSolicitacao = "";
        this.setor_id = 0;
        this.numProcesso = "";
        this.codProcesso = 0;
    }

    public void preparaInsercao(ActionEvent e) {
        
        this.solicitacao_processo = new SolicitacaoProcesso();
        this.solicitacao_processos = new ArrayList<SolicitacaoProcesso>();
        //this.usuarioNome = new ArrayList<String>();
        this.numProcesso = "";
        this.setorAlvoSolicitacao = "";
        this.setor_id = 0;
        this.codProcesso = 0;
    }

    public void pegaSetorAlvoSolicitacao() {

        Query query = em.createQuery("select tp.setorDestinoTp from TramitacaoProcesso tp where tp.codProcesso.numProcesso = '" + this.numProcesso + "' order by tp.codTramitacaoProcesso desc");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        query.setMaxResults(1);
        this.setorAlvoSolicitacao = (String) query.getSingleResult();
        insereSetorId();
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

    public List<SolicitacaoProcesso> getSolicitacoes_pendentes() {
        Collection<SolicitacaoProcesso> c = new ArrayList();
        Query query = em.createQuery("select sp from SolicitacaoProcesso sp where sp.setorAlvoSolicitacaoId = " + lb.getSession().getAttribute("setor_id") + ""
                + " and sp.status = 'PENDENTE'");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        c = query.getResultList();
        this.solicitacoes_pendentes = new ArrayList(c);
        return solicitacoes_pendentes;
    }
}
