/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sisacompo.sisap.controlador;

import aut.com.mb.LoginBean;
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
import sisacompo.sisap.modelo.Memorando;
import sisacompo.sisap.modelo.Setor;
import sisacompo.sisap.modelo.TramitacaoMemorando;

/**
 *
 * @author eneas
 */
@ManagedBean(name = "TramiteMemorandoControlador")
@SessionScoped
public class TramitacaoMemorandoJpaController {

    private TramitacaoMemorando tramite_memorando;
    private TramitacaoMemorando tramite_memorando_inserir;
    private List<TramitacaoMemorando> tramitacoes_memorandos;
    private List<TramitacaoMemorando> memorandos_pendentes;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Sisacompo1PU");
    private EntityManager em = emf.createEntityManager();
    private String numMemorando = "";
    private long codMemorando = 0;
    public String exibeBotao = "";
    public String exibeBotao2 = "";
    private long ocorrencia = 0;
    private String css = "";
    private String setorDestino = "";
    private int setorDestinoId = 0;
    private int setorOrigemId = 0;
    public String destinatario = "";
    private Setor setor;
    private String obsDespacho = "";
    private boolean exibeBtn_tramite_memo = false;
    private boolean tramiteLegado = false;
    private boolean exibeTramiteLegado = false;
    private boolean exibeTramiteLegadoRecebe = false;
    private boolean exibeCheckBox = false;
    
    private String setorCadastrante = "";
    
    LoginBean lb = (LoginBean) getManagedBean("loginBean");

    public TramitacaoMemorandoJpaController() {
    }

    public String getNumMemorando() {
        return numMemorando;
    }

    public String getExibeBotao() {
        return exibeBotao;
    }

    public long getCodMemorando() {
        return codMemorando;
    }

    public void setCodMemorando(long codMemorando) {
        this.codMemorando = codMemorando;
    }

    public void setExibeBotao(String exibeBotao) {
        this.exibeBotao = exibeBotao;
    }

    public boolean isExibeBtn_tramite_memo() {
        return exibeBtn_tramite_memo;
    }

    public void setExibeBtn_tramite_memo(boolean exibeBtn_tramite_memo) {
        this.exibeBtn_tramite_memo = exibeBtn_tramite_memo;
    }

    public void setNumMemorando(String numMemorando) {
        this.numMemorando = numMemorando;
    }

    public void setMemorandos_pendentes(List<TramitacaoMemorando> memorandos_pendentes) {
        this.memorandos_pendentes = memorandos_pendentes;
    }

    public int getSetorDestinoId() {
        return setorDestinoId;
    }

    public void setSetorDestinoId(int setorDestinoId) {
        this.setorDestinoId = setorDestinoId;
    }

    public String getObsDespacho() {
        return obsDespacho;
    }

    public String getSetorCadastrante() {
        return setorCadastrante;
    }

    public void setSetorCadastrante(String setorCadastrante) {
        this.setorCadastrante = setorCadastrante;
    }

    public TramitacaoMemorando getTramite_memorando_inserir() {
        if (this.tramite_memorando_inserir == null) {
            this.tramite_memorando_inserir = new TramitacaoMemorando();
        }
        return tramite_memorando_inserir;
    }

    public void setTramite_memorando_inserir(TramitacaoMemorando tramite_memorando_inserir) {
        this.tramite_memorando_inserir = tramite_memorando_inserir;
    }

    public boolean isTramiteLegado() {
        return tramiteLegado;
    }

    public boolean isExibeTramiteLegado() {
        return exibeTramiteLegado;
    }

    public void setExibeTramiteLegado(boolean exibeTramiteLegado) {
        this.exibeTramiteLegado = exibeTramiteLegado;
    }

    public int getSetorOrigemId() {
        return setorOrigemId;
    }

    public void setSetorOrigemId(int setorOrigemId) {
        this.setorOrigemId = setorOrigemId;
    }

    public boolean isExibeTramiteLegadoRecebe() {
        return exibeTramiteLegadoRecebe;
    }

    public void setExibeTramiteLegadoRecebe(boolean exibeTramiteLegadoRecebe) {
        this.exibeTramiteLegadoRecebe = exibeTramiteLegadoRecebe;
    }

    public void setTramiteLegado(boolean tramiteLegado) {
        this.tramiteLegado = tramiteLegado;
    }

    public void setObsDespacho(String obsDespacho) {
        this.obsDespacho = obsDespacho;
    }

    public String getExibeBotao2() {
        return exibeBotao2;
    }

    public boolean isExibeCheckBox() {
        return exibeCheckBox;
    }

    public void setExibeCheckBox(boolean exibeCheckBox) {
        this.exibeCheckBox = exibeCheckBox;
    }

    public void setExibeBotao2(String exibeBotao2) {
        this.exibeBotao2 = exibeBotao2;
    }

    public List<TramitacaoMemorando> getMemorandos_pendentes() {

        Collection<TramitacaoMemorando> c = new ArrayList();
        Collection<TramitacaoMemorando> c2 = new ArrayList();
        Collection<TramitacaoMemorando> aux = new ArrayList();
        Query query = em.createQuery("select tm from TramitacaoMemorando tm where tm.setorDestinoTmId = '"
                + lb.getSession().getAttribute("setor_id") + "'");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        c = query.getResultList();

        for (TramitacaoMemorando tramitacaoMemorando : c) {
            if (tramitacaoMemorando.getSituacaoTm().equals("DESPACHADO")) {
                c2.add(tramitacaoMemorando);
            }
        }

        for (TramitacaoMemorando tramitacaoMemorando : c) {
            for (TramitacaoMemorando tramitacaoMemorando1 : c2) {
                if (tramitacaoMemorando.getSituacaoTm().equals("RECEBIDO")
                    && tramitacaoMemorando.getCodMemorando().getNumMemorando().equals(tramitacaoMemorando1.getCodMemorando().getNumMemorando())) {
                    aux.add(tramitacaoMemorando1);
                }
            }
        }

        c2.removeAll(aux);
        this.memorandos_pendentes = new ArrayList(c2);
        return memorandos_pendentes;
    }

    public void exibirBotoesTramiteMemo() {
        this.exibeBtn_tramite_memo = true;
    }

    public List<TramitacaoMemorando> getTramitacoes_memorandos() {

        Collection<TramitacaoMemorando> c = new ArrayList();
        Collection<TramitacaoMemorando> c2 = new ArrayList();
        c = em.createQuery("SELECT tm FROM TramitacaoMemorando tm where tm.codMemorando.numMemorando = '" + numMemorando + "' and tm.codMemorando.setorCadastrante = '"+setorCadastrante+"'").getResultList();

        for (TramitacaoMemorando tramitacaoMemorando : c) {
            if (tramitacaoMemorando.getSetorDestinoTmId().equals(lb.getSession().getAttribute("setor_id"))
                    && tramitacaoMemorando.getSituacaoTm().equals("RECEBIDO")
                    || tramitacaoMemorando.getSetorOrigemTmId().equals(lb.getSession().getAttribute("setor_id"))
                    || lb.getSession().getAttribute("privilegio").equals("ADMINISTRADOR")) {
                this.exibeBotao = "sim";
            } else {
                this.exibeBotao = "não";
            }
        }

        for (TramitacaoMemorando tramitacaoMemorando : c) {
            if (tramitacaoMemorando.getSituacaoTm().equals("DESPACHADO")) {
                c2.add(tramitacaoMemorando);
            }
        }

        for (TramitacaoMemorando tramitacaoMemorando : c) {
            for (TramitacaoMemorando tramitacaoMemorando1 : c2) {
                if (tramitacaoMemorando.getCodMemorando().getNumMemorando().equals(tramitacaoMemorando1.getCodMemorando().getNumMemorando()) && !tramitacaoMemorando.getSituacaoTm().equals("RECEBIDO")) {
                    this.exibeBotao2 = "não";
                } else {
                    this.exibeBotao2 = "sim";
                }
            }
        }

        this.tramitacoes_memorandos = new ArrayList(c);
        return tramitacoes_memorandos;
    }

    public void setOcorrencia(long ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public long getOcorrencia() {

        Query query = em.createQuery("select count(tm.codMemorando.numMemorando) from TramitacaoMemorando tm where tm.setorDestinoTmId = '"
                + lb.getSession().getAttribute("setor_id") + "' and tm.situacaoTm = 'DESPACHADO'");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        this.ocorrencia = (Long) query.getSingleResult();

        Query query2 = em.createQuery("select count(tm.codMemorando.numMemorando) from TramitacaoMemorando tm where tm.setorDestinoTmId = '"
                + lb.getSession().getAttribute("setor_id") + "' and tm.situacaoTm = 'RECEBIDO'");
        query2.setHint("javax.persistence.cache.storeMode", "REFRESH");
        long ocorrencia1 = (Long) query2.getSingleResult();

        if (this.ocorrencia > ocorrencia1) {
            this.ocorrencia -= ocorrencia1;
            this.css = "quant-rec";
        } else {
            this.ocorrencia = 0;
            this.css = "quant-rec2";
        }
        return ocorrencia;
    }

    public String getCss() {
        return css;
    }

    public void setTramitacoes_memorandos(List<TramitacaoMemorando> tramitacoes_memorandos) {
        this.tramitacoes_memorandos = tramitacoes_memorandos;
    }

    public TramitacaoMemorando getTramite_memorando() {
        if (this.tramite_memorando == null) {
            this.tramite_memorando = new TramitacaoMemorando();
        }
        return tramite_memorando;
    }

    public void setTramite_memorando(TramitacaoMemorando tramite_memorando) {
        this.tramite_memorando = tramite_memorando;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void insereSetorDestinoId() {
        Query query = em.createQuery("select s from Setor s where s.setorNome = '"
                + this.tramite_memorando_inserir.getSetorDestinoTm() + "'");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        this.setor = (Setor) query.getSingleResult();
        this.setorDestinoId = setor.getSetorId();
    }

    public void insereSetorOrigemId() {
        Query query = em.createQuery("select s from Setor s where s.setorNome = '"
                + this.tramite_memorando_inserir.getSetorOrigemTm() + "'");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        this.setor = (Setor) query.getSingleResult();
        this.setorOrigemId = setor.getSetorId();
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public void setSetorDestino(String setorDestino) {
        this.setorDestino = setorDestino;
    }

    public String getSetorDestino() {
        return setorDestino;
    }

    public void tramitarMemorando(ActionEvent e) {

        if (exibeTramiteLegado == true) {

            try {

                em.getTransaction().begin();

                this.tramite_memorando_inserir.setCodMemorando(em.find(Memorando.class, codMemorando));
                this.tramite_memorando_inserir.setObservacoesTm(this.tramite_memorando_inserir.getObservacoesTm().toUpperCase());
                this.tramite_memorando_inserir.setSituacaoTm("DESPACHADO");
                this.tramite_memorando_inserir.setSetorOrigemTmId(setorOrigemId);
                this.tramite_memorando_inserir.setSetorDestinoTmId(setorDestinoId);
                

                em.persist(tramite_memorando_inserir);
                em.getTransaction().commit();

                getTramitacoes_memorandos();

                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Sucesso", "Tramitação realizada!!"));


            } catch (Throwable t) {
                //t.printStackTrace();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível realizar a Tramitação."));

                if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Esse Memorando já foi despachado por: " + tramite_memorando.getRemetenteTm() + ", para o Setor: " + tramite_memorando.getSetorDestinoTm()));
                }
            }

        } else {

            try {

                em.getTransaction().begin();

                this.tramite_memorando_inserir.setCodMemorando(em.find(Memorando.class, codMemorando));
                this.tramite_memorando_inserir.setRemetenteTm(String.valueOf(lb.getSession().getAttribute("nome")));
                this.tramite_memorando_inserir.setSetorOrigemTmId(Integer.valueOf(String.valueOf(lb.getSession().getAttribute("setor_id"))));
                this.tramite_memorando_inserir.setSetorOrigemTm(String.valueOf(lb.getSession().getAttribute("setor")));
                this.tramite_memorando_inserir.setSetorDestinoTmId(this.setorDestinoId);

                this.tramite_memorando_inserir.setDataEnvioTm(new Date());
                this.tramite_memorando_inserir.setObservacoesTm(this.tramite_memorando_inserir.getObservacoesTm().toUpperCase());
                this.tramite_memorando_inserir.setSituacaoTm("DESPACHADO");
                em.persist(tramite_memorando_inserir);
                em.getTransaction().commit();

                getTramitacoes_memorandos();

                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Sucesso", "Tramitação realizada!!"));

                this.tramite_memorando_inserir = new TramitacaoMemorando();

            } catch (Throwable t) {
                //t.printStackTrace();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível realizar a Tramitação."));

                if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Esse Memorando já foi despachado por: " + tramite_memorando.getRemetenteTm() + ", para o Setor: " + tramite_memorando.getSetorDestinoTm()));
                }
            }
        }
    }

    public void receberMemorando(ActionEvent e) {

        if (exibeTramiteLegadoRecebe == true) {

            try {
                
              em.getTransaction().begin();
                
                this.tramite_memorando_inserir.setCodMemorando(em.find(Memorando.class, codMemorando));
                this.tramite_memorando_inserir.setDataEnvioTm(this.tramite_memorando.getDataEnvioTm());
                this.tramite_memorando_inserir.setRemetenteTm(tramite_memorando.getRemetenteTm());
                this.tramite_memorando_inserir.setSetorDestinoTm(this.tramite_memorando.getSetorDestinoTm());
                this.tramite_memorando_inserir.setSetorDestinoTmId(this.tramite_memorando.getSetorDestinoTmId());        
                this.tramite_memorando_inserir.setSetorOrigemTm(this.tramite_memorando.getSetorOrigemTm());
                this.tramite_memorando_inserir.setSetorOrigemTmId(this.tramite_memorando.getSetorOrigemTmId()); 
                
                this.tramite_memorando_inserir.setSituacaoTm("RECEBIDO");
                
                if (!obsDespacho.equals("")) {
                    this.tramite_memorando_inserir.setObservacoesTm(this.obsDespacho.toUpperCase());
                }
                
                em.persist(tramite_memorando_inserir);
                em.getTransaction().commit();
                
                this.tramite_memorando_inserir = new TramitacaoMemorando();
                
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Sucesso", "Tramitação realizada!!"));
                
                
            } catch (Throwable t) {
                //t.printStackTrace();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível realizar a Tramitação."));

                if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O Memorando já foi Recebido por seu respectivo Destinatário!."));
                }
            }

        } else {

            try {

                em.getTransaction().begin();
                if (!obsDespacho.equals("")) {
                    this.tramite_memorando.setObservacoesTm(this.obsDespacho.toUpperCase());
                }
                
                this.tramite_memorando.setCodMemorando(em.find(Memorando.class, codMemorando));
                this.tramite_memorando.setDataRecebimentoTm(new Date());
                this.tramite_memorando.setSituacaoTm("RECEBIDO");
                this.tramite_memorando.setColaboradorRecebeuTm(String.valueOf(lb.getSession().getAttribute("nome")));
                em.persist(tramite_memorando);

                em.getTransaction().commit();

                getTramitacoes_memorandos();

                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Sucesso", "Tramitação realizada!!"));

                this.tramite_memorando = new TramitacaoMemorando();

            } catch (Throwable t) {
                //t.printStackTrace();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível realizar a Tramitação."));

                if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O Memorando já foi Recebido por seu respectivo Destinatário!."));
                }
            }
        }
    }

    public void excluirTramiteMemo(ActionEvent e) {
        TramitacaoMemorando tm_temp_delete = this.tramite_memorando;
        em.getTransaction().begin();
        em.remove(tm_temp_delete);
        em.getTransaction().commit();
        getTramitacoes_memorandos();
        this.tramite_memorando = new TramitacaoMemorando();
    }

    public void preparaInsercao(ActionEvent e) {
        this.tramite_memorando_inserir = new TramitacaoMemorando();
        this.tramitacoes_memorandos = new ArrayList<TramitacaoMemorando>();
        this.setorDestino = "";
        this.setorDestinoId = 0;
        this.setorOrigemId = 0;
        this.setor = new Setor();
        this.exibeBtn_tramite_memo = false;
        this.exibeTramiteLegado = false;
        this.exibeTramiteLegadoRecebe = false;
        this.tramiteLegado = false;
        this.exibeBtn_tramite_memo = false;
        this.obsDespacho = "";
    }

    public void verificaTramiteLegado() {
        if (this.tramiteLegado == true) {
            this.exibeTramiteLegado = true;
        } else {
            this.exibeTramiteLegado = false;
        }
    }

    public void verificaTramiteLegadoRecebe() {
        if (this.tramiteLegado == true) {
            this.exibeTramiteLegadoRecebe = true;
        } else {
            this.exibeTramiteLegadoRecebe = false;
        }
    }

    public void limpaTabelaDtMp(ActionEvent e) {
        this.memorandos_pendentes = new ArrayList<TramitacaoMemorando>();
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
