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
import sisacompo.sisap.modelo.Memorando;

/**
 *
 * @author eneas
 */
@ManagedBean(name = "MemorandoControlador")
@SessionScoped
public class MemorandoJpaController {

    private Memorando memorando;
    private Memorando memorando_inserir;
    private List<Memorando> memorandos;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Sisacompo1PU");
    private EntityManager em = emf.createEntityManager();
    private String outroTipoMemorando = "";
    private String outroTipoMemorandoEdit = "";
    LoginBean lb = (LoginBean) getManagedBean("loginBean");
    private boolean outro;
    private boolean cadOutro;
    private String numMemorando = "";
    private String setorCadastrante = "";

    public MemorandoJpaController() {
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public Memorando getMemorando() {
        if (this.memorando == null) {
            this.memorando = new Memorando();
        }
        return memorando;
    }

    public Memorando getMemorando_inserir() {
        if (this.memorando_inserir == null) {
            this.memorando_inserir = new Memorando();
        }
        return memorando_inserir;
    }

    public String getNumMemorando() {
        return numMemorando;
    }

    public void setNumMemorando(String numMemorando) {
        this.numMemorando = numMemorando;
    }

    public void setMemorando_inserir(Memorando memorando_inserir) {
        this.memorando_inserir = memorando_inserir;
    }

    public void setMemorando(Memorando memorando) {
        this.memorando = memorando;
    }

    public String getSetorCadastrante() {
        return setorCadastrante;
    }

    public void setSetorCadastrante(String setorCadastrante) {
        this.setorCadastrante = setorCadastrante;
    }

    public List<Memorando> getMemorandos() {
        Collection<Memorando> c = new ArrayList();
        c = em.createNamedQuery("Memorando.findAll").getResultList();
        this.memorandos = new ArrayList(c);
        return memorandos;
    }

    public String getOutroTipoMemorandoEdit() {

        if (this.memorando.getCategoriaMemorando() != null) {
            if (!this.memorando.getCategoriaMemorando().equals("SERVIÇO") && !this.memorando.getCategoriaMemorando().equals("DIÁRIA") && !this.memorando.getCategoriaMemorando().equals("COMPRA")) {
                this.outroTipoMemorandoEdit = this.memorando.getCategoriaMemorando();
            }
        }
        return outroTipoMemorandoEdit;
    }

    public void setOutroTipoMemorandoEdit(String outroTipoMemorandoEdit) {
        this.outroTipoMemorandoEdit = outroTipoMemorandoEdit;
    }

    public boolean isOutro() {
        if (this.memorando.getCategoriaMemorando() != null) {
            if (!this.memorando.getCategoriaMemorando().equals("SERVIÇO") && !this.memorando.getCategoriaMemorando().equals("DIÁRIA") && !this.memorando.getCategoriaMemorando().equals("COMPRA")) {
                this.outro = true;
            } else {
                this.outro = false;
                this.outroTipoMemorandoEdit = "";
            }
        }
        return outro;
    }

    public boolean isCadOutro() {
        if (this.memorando_inserir.getCategoriaMemorando() != null) {
            if (!this.memorando_inserir.getCategoriaMemorando().equals("SERVIÇO") && !this.memorando_inserir.getCategoriaMemorando().equals("DIÁRIA") && !this.memorando_inserir.getCategoriaMemorando().equals("COMPRA")) {
                this.cadOutro = true;
            } else{
                this.cadOutro = false;
                this.outroTipoMemorando = "";
            }
        }
        return cadOutro;
    }

    public void setCadOutro(boolean cadOutro) {
        this.cadOutro = cadOutro;
    }

    public void zeraCategoria() {
        if (this.outro == true) {
            this.memorando.setCategoriaMemorando("");
        } else {
            this.outroTipoMemorandoEdit = "";
        }
    }
    
    public void cadZeraCategoria() {
        if (this.cadOutro == true) {
            this.memorando.setCategoriaMemorando("");
        } else {
            this.outroTipoMemorando = "";
        }
    }
    
    public void setOutro(boolean outro) {
        this.outro = outro;
    }

    public void setMemorandos(List<Memorando> memorandos) {
        this.memorandos = memorandos;
    }

    public String getOutroTipoMemorando() {
        return outroTipoMemorando;
    }

    public void setOutroTipoMemorando(String outroTipoMemorando) {
        this.outroTipoMemorando = outroTipoMemorando;
    }

     public void inserir(ActionEvent e) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date data = new Date();
            String intermediario = "";

            em.getTransaction().begin();

            if (this.outroTipoMemorando.isEmpty()) {
                this.memorando_inserir.setCategoriaMemorando(this.memorando_inserir.getCategoriaMemorando());
            } else {
                this.memorando_inserir.setCategoriaMemorando(this.outroTipoMemorando.toUpperCase());
            }

            if(setorCadastrante.equals("")){
                this.memorando_inserir.setSetorCadastrante(String.valueOf(lb.getSession().getAttribute("setor")));
            } else {
               this.memorando_inserir.setSetorCadastrante(setorCadastrante);
            }
            
            this.memorando_inserir.setAssuntoMemorando(this.memorando_inserir.getAssuntoMemorando().toUpperCase());
            this.memorando_inserir.setDescricaoMemorando(this.memorando_inserir.getDescricaoMemorando().toUpperCase());
            this.memorando_inserir.setObservacaoMemorando(this.memorando_inserir.getObservacaoMemorando().toUpperCase());

            if (this.memorando_inserir.getDataMemorando() == null) {
                intermediario = sdf.format(data);
                data = sdf.parse(intermediario);
                this.memorando_inserir.setDataMemorando(data);
            }

            em.persist(memorando_inserir);
            em.getTransaction().commit();

            getMemorandos();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Sucesso", "Inserido o Memorando n°: " + memorando_inserir.getNumMemorando()));

        } catch (Throwable t) {

            //t.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O Memorando já existe."));

            if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O Memorando já existe."));
            }
        }
    }

    public void atualizar(ActionEvent e) {

        em.getTransaction().begin();
        Memorando temp = this.memorando;
        temp.setAssuntoMemorando(this.memorando.getAssuntoMemorando().toUpperCase());
        temp.setDescricaoMemorando(this.memorando.getDescricaoMemorando().toUpperCase());
        temp.setObservacaoMemorando(this.memorando.getObservacaoMemorando().toUpperCase());

        if (this.outroTipoMemorandoEdit.isEmpty()) {
            temp.setCategoriaMemorando(this.memorando.getCategoriaMemorando());
        } else {
            temp.setCategoriaMemorando(this.outroTipoMemorandoEdit.toUpperCase());
        }

        em.merge(temp);
        em.getTransaction().commit();
        getMemorandos();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso", "Memorando n°: " + memorando.getNumMemorando() + " Atualizado!"));

        this.memorando = new Memorando();
    }

    public void excluir(ActionEvent e) {
        Memorando temp = this.memorando;
        em.getTransaction().begin();
        em.remove(temp);
        em.getTransaction().commit();
        getMemorandos();
        this.memorando = new Memorando();
    }

    public void preparaInsercao(ActionEvent e) {
        this.memorando_inserir = new Memorando();
        this.memorandos = new ArrayList<Memorando>();
        this.numMemorando = "";
        this.setorCadastrante = "";
    }

    public void preparaAlteracao(ActionEvent e) {
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
