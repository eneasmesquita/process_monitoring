/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sisacompo.sisap.controlador;

import aut.com.mb.LoginBean;
import java.util.*;
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
import sisacompo.sisap.modelo.Setor;
import sisacompo.sisap.modelo.TramitacaoProcesso;

/**
 *
 * @author eneas
 */
@ManagedBean(name = "TramiteProcessoControlador")
@SessionScoped
public class TramitacaoProcessoJpaController {

    private TramitacaoProcesso tramite_processo;
    private TramitacaoProcesso tramite_processo_inserir;
    private List<TramitacaoProcesso> tramitacoes_processos;
    private List<TramitacaoProcesso> processos_pendentes;
    private int setorDestinoId = 0;
    private Setor setor;
    private long codProcesso = 0;
    //private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Sisacompo1PU");
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Sisacompo1PU");
    private EntityManager em = emf.createEntityManager();
    private String numProcesso = "";
    private boolean ativoProcesso = false;
    private String faseCPL = "-----";
    private boolean exibeBotao = false;
    private long ocorrencia = 0;
    private String css = "";
    private String setorDestino = "-----";
    private int contador = 0;
    private boolean exibeSolPro = false;
    private boolean exibeBotao2 = false;
    private boolean exibePL = false;
    private boolean processoLicitatorio = false;
    private boolean naoRealizarTramitacao = false;
    private boolean exibeBotaoPL = false;
    private boolean exibeCheckBox = false;
    LoginBean lb = (LoginBean) getManagedBean("loginBean");
    private boolean exibeBtn_tramite_pro = false;
    private boolean exibeTramiteLegado = false;
    private boolean exibeTramiteLegadoRecebe = false;
    private boolean tramiteLegado = false;
    private int setorOrigemId = 0;
    private String exibeCertame = "";

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public TramitacaoProcessoJpaController() {
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public long getCodProcesso() {
        return codProcesso;
    }

    public void setCodProcesso(long codProcesso) {
        this.codProcesso = codProcesso;
    }

    public boolean isExibeTramiteLegado() {
        return exibeTramiteLegado;
    }

    public boolean isTramiteLegado() {
        return tramiteLegado;
    }

    public String getExibeCertame() {
        return exibeCertame;
    }

    public void setExibeCertame(String exibeCertame) {
        this.exibeCertame = exibeCertame;
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

    public void setExibeTramiteLegado(boolean exibeTramiteLegado) {
        this.exibeTramiteLegado = exibeTramiteLegado;
    }

    public int getSetorOrigemId() {
        return setorOrigemId;
    }

    public void setSetorOrigemId(int setorOrigemId) {
        this.setorOrigemId = setorOrigemId;
    }

    public boolean isExibeCheckBox() {
        return exibeCheckBox;
    }

    public void setExibeCheckBox(boolean exibeCheckBox) {
        this.exibeCheckBox = exibeCheckBox;
    }

    public boolean isExibeBtn_tramite_pro() {
        return exibeBtn_tramite_pro;
    }

    public void setExibeBtn_tramite_pro(boolean exibeBtn_tramite_pro) {
        this.exibeBtn_tramite_pro = exibeBtn_tramite_pro;
    }

    public int getSetorDestinoId() {
        return setorDestinoId;
    }

    public void setSetorDestinoId(int setorDestinoId) {
        this.setorDestinoId = setorDestinoId;
    }

    public boolean isExibeBotaoPL() {
        return exibeBotaoPL;
    }

    public boolean isExibeSolPro() {
        return exibeSolPro;
    }

    public void setExibeSolPro(boolean exibeSolPro) {
        this.exibeSolPro = exibeSolPro;
    }

    public void setExibeBotaoPL(boolean exibeBotaoPL) {
        this.exibeBotaoPL = exibeBotaoPL;
    }

    public boolean isExibeBotao2() {
        return exibeBotao2;
    }

    public TramitacaoProcesso getTramite_processo_inserir() {
        return tramite_processo_inserir;
    }

    public void setTramite_processo_inserir(TramitacaoProcesso tramite_processo_inserir) {
        this.tramite_processo_inserir = tramite_processo_inserir;
    }

    public boolean isProcessoLicitatorio() {
        return processoLicitatorio;
    }

    public void setProcessoLicitatorio(boolean processoLicitatorio) {
        this.processoLicitatorio = processoLicitatorio;
    }

    public void setExibeBotao2(boolean exibeBotao2) {
        this.exibeBotao2 = exibeBotao2;
    }

    public boolean isNaoRealizarTramitacao() {
        return naoRealizarTramitacao;
    }

    public void setNaoRealizarTramitacao(boolean naoRealizarTramitacao) {
        this.naoRealizarTramitacao = naoRealizarTramitacao;
    }

    public List<TramitacaoProcesso> getTramitacoes_processos() {
        try {
            Collection<TramitacaoProcesso> c = new ArrayList();
            Collection<TramitacaoProcesso> c2 = new ArrayList();

            c = em.createQuery("SELECT tp FROM TramitacaoProcesso tp where tp.codProcesso.numProcesso = '"
                    + numProcesso + "'").getResultList();

            for (TramitacaoProcesso tramitacaoProcesso : c) {

                if (tramitacaoProcesso.getSetorDestinoTpId().equals(lb.getSession().getAttribute("setor_id"))
                        && tramitacaoProcesso.getSituacaoTp().equals("RECEBIDO")
                        || !tramitacaoProcesso.getFaseTp().equals("-----") && tramitacaoProcesso.getSetorDestinoTpId() == 0
                        && tramitacaoProcesso.getSetorOrigemTpId() == 0 && lb.getSession().getAttribute("setor_id").equals(Integer.valueOf("13"))
                        || tramitacaoProcesso.getSetorOrigemTpId().equals(lb.getSession().getAttribute("setor_id"))
                        || lb.getSession().getAttribute("privilegio").equals("ADMINISTRADOR")) {
                    this.exibeBotao = true;
                } else {
                    this.exibeBotao = false;
                }

                if (lb.getSession().getAttribute("setor_id").equals(Integer.valueOf(tramitacaoProcesso.getSetorDestinoTpId()))) {
                    this.exibeSolPro = false;
                } else if (!lb.getSession().getAttribute("setor_id").equals(Integer.valueOf(tramitacaoProcesso.getSetorDestinoTpId()))
                        && tramitacaoProcesso.getSituacaoTp().equals("DESPACHADO")) {
                    this.exibeSolPro = false;
                } else if (lb.getSession().getAttribute("setor_id").equals(Integer.valueOf("13")) && tramitacaoProcesso.getSetorDestinoTpId() == 0
                        && tramitacaoProcesso.getSetorOrigemTpId() == 0) {
                    this.exibeSolPro = false;
                } else {
                    this.exibeSolPro = true;
                }

                if (!tramitacaoProcesso.getFaseTp().equals("-----")) {
                    this.exibeBotaoPL = true;
                    this.exibeCheckBox = true;
                } else {
                    this.exibeCheckBox = false;
                }

            }

            for (TramitacaoProcesso tramitacaoProcesso : c) {
                if (!tramitacaoProcesso.getSituacaoTp().equals("RECEBIDO")) {
                    c2.add(tramitacaoProcesso);
                }
            }

            for (TramitacaoProcesso tramitacaoProcesso : c) {
                for (TramitacaoProcesso tramitacaoProcesso1 : c2) {
                    if (tramitacaoProcesso.getCodProcesso() == tramitacaoProcesso1.getCodProcesso() && !tramitacaoProcesso.getSituacaoTp().equals("RECEBIDO")) {
                        this.exibeBotao2 = false;
                        if (!tramitacaoProcesso.getFaseTp().equals("-----")) {
                            this.exibeBotao2 = true;
                            this.exibePL = true;
                            this.processoLicitatorio = true;
                            
                        }
                    } else {
                        this.exibeBotao2 = true;
                        this.processoLicitatorio = false;
                        this.exibePL = false;
                        
                    }
                }
            }

            this.tramitacoes_processos = new ArrayList(c);

        } catch (Throwable t) {
            //t.printStackTrace();
        }
        return tramitacoes_processos;
    }

    public void setOcorrencia(long ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public boolean isExibePL() {
        return exibePL;
    }

    public void setExibePL(boolean exibePL) {
        this.exibePL = exibePL;
    }

    public void setProcessos_pendentes(List<TramitacaoProcesso> processos_pendentes) {
        this.processos_pendentes = processos_pendentes;
    }

    public void insereSetorDestinoId() {

        Query query = em.createQuery("select s from Setor s where s.setorNome = '"
                + this.tramite_processo_inserir.getSetorDestinoTp() + "'");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        this.setor = (Setor) query.getSingleResult();
        this.setorDestinoId = setor.getSetorId();
    }

    public void insereSetorOrigemId() {

        Query query = em.createQuery("select s from Setor s where s.setorNome = '"
                + this.tramite_processo_inserir.getSetorOrigemTp() + "'");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        this.setor = (Setor) query.getSingleResult();
        this.setorOrigemId = setor.getSetorId();
    }

    public void exibirBotoesTramitePro() {
        this.exibeBtn_tramite_pro = true;
    }

    public List<TramitacaoProcesso> getProcessos_pendentes() {

        Collection<TramitacaoProcesso> c = new ArrayList();
        Collection<TramitacaoProcesso> c2 = new ArrayList();
        Collection<TramitacaoProcesso> aux = new ArrayList();

        Query query = em.createQuery("select tp from TramitacaoProcesso tp where tp.setorDestinoTpId = '"
                + lb.getSession().getAttribute("setor_id") + "'");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        c = query.getResultList();

        for (TramitacaoProcesso tramitacaoProcesso : c) {
            if (tramitacaoProcesso.getSituacaoTp().equals("DESPACHADO")) {
                c2.add(tramitacaoProcesso);
            }
        }

        for (TramitacaoProcesso tramitacaoProcesso : c) {
            for (TramitacaoProcesso tramitacaoProcesso1 : c2) {
                if (tramitacaoProcesso.getSituacaoTp().equals("RECEBIDO")
                        && tramitacaoProcesso.getCodProcesso() == tramitacaoProcesso1.getCodProcesso()) {
                    aux.add(tramitacaoProcesso1);
                }
            }
        }

        c2.removeAll(aux);
        this.processos_pendentes = new ArrayList(c2);
        return processos_pendentes;
    }

    public long getOcorrencia() {
        Query query = em.createQuery("select count(tp.codProcesso) from TramitacaoProcesso tp where tp.setorDestinoTpId = '"
                + lb.getSession().getAttribute("setor_id") + "' and tp.situacaoTp = 'DESPACHADO'");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        this.ocorrencia = (Long) query.getSingleResult();

        Query query2 = em.createQuery("select count(tp.codProcesso) from TramitacaoProcesso tp where tp.setorDestinoTpId = '"
                + lb.getSession().getAttribute("setor_id") + "' and tp.situacaoTp = 'RECEBIDO'");
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

    public void setTramitacoes_processos(List<TramitacaoProcesso> tramitacoes_processos) {
        this.tramitacoes_processos = tramitacoes_processos;
    }

    public TramitacaoProcesso getTramite_processo() {
        if (this.tramite_processo == null) {
            this.tramite_processo = new TramitacaoProcesso();
        }
        return tramite_processo;
    }

    public void setTramite_processo(TramitacaoProcesso tramite_processo) {
        this.tramite_processo = tramite_processo;
    }

    public boolean getExibeBotao() {
        return exibeBotao;
    }

    public void setExibeBotao(boolean exibeBotao) {
        this.exibeBotao = exibeBotao;
    }

    public String getNumProcesso() {
        return numProcesso;
    }

    public void setNumProcesso(String numProcesso) {
        this.numProcesso = numProcesso;
    }

    public boolean isAtivoProcesso() {
        return ativoProcesso;
    }

    public void setAtivoProcesso(boolean ativoProcesso) {
        this.ativoProcesso = ativoProcesso;
    }

    public String getFaseCPL() {
        return faseCPL;
    }

    public void setFaseCPL(String faseCPL) {
        this.faseCPL = faseCPL;
    }

    public String getSetorDestino() {
        return setorDestino;
    }

    public void setSetorDestino(String setorDestino) {
        this.setorDestino = setorDestino;
    }

    public void verificaFaseAtual() {

        TramitacaoProcesso tp = new TramitacaoProcesso();

        Query query = em.createQuery("select tp from TramitacaoProcesso tp where tp.codProcesso.codProcesso = " + this.codProcesso + " order by tp.codTramitacaoProcesso desc");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        query.setMaxResults(1);
        tp = (TramitacaoProcesso) query.getSingleResult();

        if (tp.getFaseTp().equals(this.faseCPL)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O Trâmite Licitatório já está nessa fase ou já passou pela mesma!", ""));
            this.naoRealizarTramitacao = true;
        } else if (this.faseCPL.equals("-----")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Selecione uma Fase do Processo Licitatório!", ""));
            this.naoRealizarTramitacao = true;
        } else if (tp.getFaseTp().equals("ELABORAÇÃO DE EDITAL") && this.faseCPL.equals("CONTRATAÇÃO") || tp.getFaseTp().equals("ELABORAÇÃO DE EDITAL") && this.faseCPL.equals("CONCLUÍDO")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Não é permitido pular Fases do Trâmite Licitatório!", ""));
            this.naoRealizarTramitacao = true;
        } else if (tp.getFaseTp().equals("ABERTURA DE ENVELOPES") && this.faseCPL.equals("ELABORAÇÃO DE EDITAL") || tp.getFaseTp().equals("ABERTURA DE ENVELOPES") && this.faseCPL.equals("CONCLUÍDO")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Não é permitido pular Fases do Trâmite Licitatório ou retroceder!", ""));
            this.naoRealizarTramitacao = true;
        } else if (tp.getFaseTp().equals("CONTRATAÇÃO") && this.faseCPL.equals("ELABORAÇÃO DE EDITAL") || tp.getFaseTp().equals("CONTRATAÇÃO") && this.faseCPL.equals("ABERTURA DE ENVELOPES")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Não é permitido pular Fases do Trâmite Licitatório ou retroceder!", ""));
            this.naoRealizarTramitacao = true;
        } else if (tp.getFaseTp().equals("CONCLUÍDO") && this.faseCPL.equals("CONTRATAÇÃO") || tp.getFaseTp().equals("CONCLUÍDO") && this.faseCPL.equals("ABERTURA DE ENVELOPES")
                || tp.getFaseTp().equals("CONCLUÍDO") && this.faseCPL.equals("ELABORAÇÃO DE EDITAL") || tp.getFaseTp().equals("CONCLUÍDO") && this.faseCPL.equals("CANCELADO")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O Trâmite Licitatório já foi concluído!", ""));
            this.naoRealizarTramitacao = true;
        } else if (tp.getFaseTp().equals("CANCELADO") && this.faseCPL.equals("CONCLUÍDO") || tp.getFaseTp().equals("CANCELADO") && this.faseCPL.equals("CONTRATAÇÃO")
                || tp.getFaseTp().equals("CANCELADO") && this.faseCPL.equals("ABERTURA DE ENVELOPES") || tp.getFaseTp().equals("CANCELADO") && this.faseCPL.equals("ELABORAÇÃO DE EDITAL")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "O Trâmite Licitatório já foi cancelado!", ""));
            this.naoRealizarTramitacao = true;
        } else {
            this.naoRealizarTramitacao = false;
        }
    }

    public void tramitarProcesso(ActionEvent e) {

        if (this.naoRealizarTramitacao == true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi Possível Concluir o trâmite Licitatório, Fase nula!", ""));
        } else if (!faseCPL.equals("-----")) {
            try {

                int valor = new Random().nextInt(1000000);
                em.getTransaction().begin();
                this.tramite_processo_inserir.setDataEnvioTp(new Date());
                this.tramite_processo_inserir.setRemetenteTp("-----");
                this.tramite_processo_inserir.setSetorOrigemTp("-----");
                this.tramite_processo_inserir.setSetorOrigemTpId(0);
                if (this.getSetorDestino().equals("")) {
                    this.tramite_processo_inserir.setSetorDestinoTp("-----");
                    this.tramite_processo_inserir.setSetorDestinoTpId(0);
                } else {
                    this.tramite_processo_inserir.setSetorDestinoTpId(setorDestinoId);
                }
                this.tramite_processo_inserir.setObservacoesTp(this.tramite_processo_inserir.getObservacoesTp().toUpperCase());
                this.tramite_processo_inserir.setFaseTp(faseCPL);
                this.tramite_processo_inserir.setSituacaoTp(String.valueOf(valor));
                System.out.println("SAÍDA DO PARAMETRO codProcesso: "+codProcesso);
                this.tramite_processo_inserir.setCodProcesso(em.find(Processo.class, codProcesso));
                em.persist(tramite_processo_inserir);
                em.getTransaction().commit();

                getTramitacoes_processos();

                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Sucesso", "Tramitação realizada!!"));

                this.setor = new Setor();


            } catch (Throwable t) {
                //t.printStackTrace();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível realizar a Tramitação."));

                if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Esse Processo já foi despachado por: " + tramite_processo.getRemetenteTp() + ", para o Setor: " + tramite_processo.getSetorDestinoTp()));
                }
            }

        } else if (this.exibeTramiteLegado == true) {

            try {

                em.getTransaction().begin();
                this.tramite_processo_inserir.setObservacoesTp(this.tramite_processo_inserir.getObservacoesTp().toUpperCase());
                this.tramite_processo_inserir.setSituacaoTp("DESPACHADO");
                this.tramite_processo_inserir.setCodProcesso(em.find(Processo.class, codProcesso));
                this.tramite_processo_inserir.setSetorOrigemTpId(setorOrigemId);
                this.tramite_processo_inserir.setSetorDestinoTpId(setorDestinoId);
                this.tramite_processo_inserir.setFaseTp(this.getFaseCPL());
                
                em.persist(tramite_processo_inserir);
                em.getTransaction().commit();

                getTramitacoes_processos();

                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Sucesso", "Tramitação n°: " + tramite_processo_inserir.getCodTramitacaoProcesso() + " realizada!!"));

                this.setor = new Setor();
              

            } catch (Throwable t) {
                //t.printStackTrace();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível realizar a Tramitação."));

                if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Esse Processo já foi despachado por: " + tramite_processo.getRemetenteTp() + ", para o Setor: " + tramite_processo.getSetorDestinoTp()));
                }
            }

        } else {

            try {

                em.getTransaction().begin();
                this.tramite_processo_inserir.setDataEnvioTp(new Date());
                this.tramite_processo_inserir.setRemetenteTp(String.valueOf(lb.getSession().getAttribute("nome")));
                this.tramite_processo_inserir.setSetorOrigemTp(String.valueOf(lb.getSession().getAttribute("setor")));
                this.tramite_processo_inserir.setSetorOrigemTpId(Integer.valueOf(String.valueOf(lb.getSession().getAttribute("setor_id"))));
                this.tramite_processo_inserir.setSetorDestinoTpId(setorDestinoId);
                this.tramite_processo_inserir.setObservacoesTp(this.tramite_processo_inserir.getObservacoesTp().toUpperCase());
                this.tramite_processo_inserir.setSituacaoTp("DESPACHADO");
                this.tramite_processo_inserir.setFaseTp(this.getFaseCPL());
                this.tramite_processo_inserir.setCodProcesso(em.find(Processo.class, codProcesso));
                em.persist(tramite_processo_inserir);
                em.getTransaction().commit();

                getTramitacoes_processos();

                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Sucesso", "Tramitação n°: " + tramite_processo_inserir.getCodTramitacaoProcesso() + " realizada!!"));

                this.setor = new Setor();
 

            } catch (Throwable t) {
                //t.printStackTrace();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível realizar a Tramitação."));

                if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Esse Processo já foi despachado por: " + tramite_processo.getRemetenteTp() + ", para o Setor: " + tramite_processo.getSetorDestinoTp()));
                }
            }
        }
    }

    public void receberProcesso(ActionEvent e) {
        String aux_setor_id = "13";
        if (!faseCPL.equals("") && faseCPL.equals("CONCLUÍDO")) {

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível realizar a Tramitação."));

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O Processo já foi Concluído pela CPL."));

        } else if(exibeTramiteLegadoRecebe == true){
            
            try{
                
                em.getTransaction().begin();
                
                this.tramite_processo_inserir.setDataEnvioTp(this.tramite_processo.getDataEnvioTp());
                this.tramite_processo_inserir.setFaseTp(this.tramite_processo.getFaseTp());
                this.tramite_processo_inserir.setRemetenteTp(tramite_processo.getRemetenteTp());
                this.tramite_processo_inserir.setSetorDestinoTp(this.tramite_processo.getSetorDestinoTp());
                this.tramite_processo_inserir.setSetorDestinoTpId(this.tramite_processo.getSetorDestinoTpId());        
                this.tramite_processo_inserir.setSetorOrigemTp(this.tramite_processo.getSetorOrigemTp());
                this.tramite_processo_inserir.setSetorOrigemTpId(this.tramite_processo.getSetorOrigemTpId());
                
                        
                this.tramite_processo_inserir.setSituacaoTp("RECEBIDO");
                this.tramite_processo_inserir.setCodProcesso(em.find(Processo.class, codProcesso));
                
                em.persist(tramite_processo_inserir);
                em.getTransaction().commit();
                
                this.tramite_processo_inserir = new TramitacaoProcesso();
                
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Sucesso", "Tramitação realizada!!"));
                
            } catch (Throwable t) {
                //t.printStackTrace();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível realizar a Tramitação."));

                if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O Processo já foi Recebido por seu respectivo Destinatário!."));
                }
            }
        }
        
        else {

            try {

                em.getTransaction().begin();

                this.tramite_processo.setDataRecebimentoTp(new Date());
                this.tramite_processo.setSituacaoTp("RECEBIDO");
                this.tramite_processo.setColaboradorRecebeuTp(String.valueOf(lb.getSession().getAttribute("nome")));
                this.tramite_processo.setCodProcesso(em.find(Processo.class, codProcesso));
                /*
                System.out.println("1 - ATÉ AQUI INDEPENDENTE DE ERRO OU NAO, SEMPRE SEREI EXECUTADO!!");
                System.out.println("SETOR_ID: " + lb.getSession().getAttribute("setor_id"));
                System.out.println("SAÍDA DA CONDIÇÃO DE VERIFICAÇÃO DE ID DE SETOR IGUAL A 13: " + lb.getSession().getAttribute("setor_id").equals(Integer.valueOf(aux_setor_id)));
                System.out.println("SAÍDA DA CONDIÇÃO DE VERIFICAÇÃO DE ID DE SETOR IGUAL A 13, nº2: " + Integer.valueOf(aux_setor_id).equals(lb.getSession().getAttribute("setor_id")));
                */
                em.persist(tramite_processo);
                em.getTransaction().commit();

                //System.out.println("2 - A PARTIR DAQUI JÁ NÃO GARANTO TANTO!!");
                getTramitacoes_processos();

                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Sucesso", "Tramitação realizada!!"));

               // System.out.println("3 - SERÁ QUE AINDA SEREI EXECUTADO ESTANDO AQUI!?");
                //this.tramite_processo = new TramitacaoProcesso();

                //System.out.println("4 - PAÁÁÁRA QUE FUI EXECUTADO!! o_O");

            } catch (Throwable t) {
                //t.printStackTrace();
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível realizar a Tramitação."));

                if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O Processo já foi Recebido por seu respectivo Destinatário!."));
                }
            }
        }
    }

    public void excluirTramitePro(ActionEvent e) {
        TramitacaoProcesso tp_temp_delete = this.tramite_processo;
        em.getTransaction().begin();
        em.remove(tp_temp_delete);
        em.getTransaction().commit();
        getTramitacoes_processos();
        this.tramite_processo = new TramitacaoProcesso();
    }

    public void preparaInsercao(ActionEvent e) {
        this.exibeBtn_tramite_pro = false;
        this.tramite_processo_inserir = new TramitacaoProcesso();
        this.tramitacoes_processos = new ArrayList<TramitacaoProcesso>();
        this.setorDestino = "";
        this.setorDestinoId = 0;
        this.faseCPL = "-----";
        this.processoLicitatorio = false;
        this.exibePL = false;
        this.naoRealizarTramitacao = false;
        this.exibeBotaoPL = false;
        this.exibeCheckBox = false;
        this.tramiteLegado = false;
        this.exibeTramiteLegado = false;
        this.exibeTramiteLegadoRecebe = false;
    }

    public void verificaProcessoLicitatorio() {
        if (this.processoLicitatorio == true) {
            this.exibePL = true;
        } else {
            this.exibePL = false;
        }
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