/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sisacompo.sisap.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.primefaces.event.SelectEvent;
import sisacompo.sisap.modelo.Superusuario;
import sisacompo.sisap.modelo.UsuarioJPA;

/**
 *
 * @author eneas
 */
@ManagedBean(name = "SuControlador")
@SessionScoped
public class SuperusuarioJpaController implements Serializable {

    private Superusuario su;
    private List<Superusuario> su_listagem;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Sisacompo1PU");
    private EntityManager em = emf.createEntityManager();
    private String param_busca = "";
    private UsuarioJPA usuario;
    private List<UsuarioJPA> listagem_usuarios_param;

    public SuperusuarioJpaController() {
    }

    public Superusuario getSu() {
        if (this.su == null) {
            this.su = new Superusuario();
        }
        return su;
    }

    public void setSu(Superusuario su) {
        this.su = su;
    }

    public List<Superusuario> getSu_listagem() {
        Collection<Superusuario> c = new ArrayList();
        c = em.createNamedQuery("Superusuario.findAll").getResultList();
        this.su_listagem = new ArrayList(c);
        return su_listagem;
    }

    public void setSu_listagem(List<Superusuario> su_listagem) {
        this.su_listagem = su_listagem;
    }

    public String getParam_busca() {
        return param_busca;
    }

    public void setListagem_usuarios_param(List<UsuarioJPA> listagem_usuarios_param) {
        this.listagem_usuarios_param = listagem_usuarios_param;
    }

    public void setParam_busca(String param_busca) {
        this.param_busca = param_busca;
    }

    public UsuarioJPA getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioJPA usuario) {
        this.usuario = usuario;
    }

    public void exibeDadosUsuario(SelectEvent e) {
        if (!param_busca.equals("")) {
            Collection<UsuarioJPA> c = em.createQuery("select u from UsuarioJPA u where u.username like '" + this.param_busca + "%' and u.matricula <> -1 and u.existe <> false order by u.username asc").getResultList();
            if (c.size() > 0) {
                this.usuario = new ArrayList<UsuarioJPA>(c).get(0);
                this.su.setLoginSu(this.usuario.getUsername());
                this.su.setMatricula(this.usuario.getMatricula());
                this.su.setNomeCompletoSu(this.usuario.getNomeCompleto());
            }
        }
    }

    public void analisaParametro(SelectEvent event) {
        if (param_busca.equals("") || param_busca == null) {
            this.listagem_usuarios_param = new ArrayList();
        } else {
            Collection<UsuarioJPA> c = new ArrayList();
            Query query = em.createQuery("select u from UsuarioJPA u where u.username like '" + this.param_busca + "%' and u.matricula <> -1 and u.existe <> false order by u.username asc");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            c = query.getResultList();
            this.listagem_usuarios_param = new ArrayList(c);
        }
    }

    public List<UsuarioJPA> getListagem_usuarios_param() {

        return listagem_usuarios_param;
    }

    public List<String> completaLoginUsuario(String q) {
        Collection<String> c = new ArrayList();
        Query query = em.createQuery("select distinct u.username from UsuarioJPA u where u.username like '%" + q + "%' order by u.username");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        c = query.getResultList();
        return new ArrayList<String>(c);
    }

    public void preparaInsercao(ActionEvent e) {
        this.param_busca = "";
        this.usuario = new UsuarioJPA();
        this.su = new Superusuario();
        this.listagem_usuarios_param = new ArrayList();
    }

    public void limpaParamBusca(ActionEvent e) {
        this.param_busca = "";
        this.su = new Superusuario();
        this.usuario = new UsuarioJPA();
    }

    public void inserirUsuarios(ActionEvent e) {

        try {

            em.persist(su);
            em.getTransaction().begin();
            em.getTransaction().commit();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Sucesso", "Usuário " + this.su.getLoginSu() + " inserido."));

            this.param_busca = "";
            this.su = new Superusuario();
            this.usuario = new UsuarioJPA();

        } catch (Throwable t) {
            //t.printStackTrace();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível cadastrar o Usuário " + this.su.getLoginSu()));

            if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O Usuário já foi cadastrado."));
            }
        }
    }

    public void excluir(ActionEvent e) {
        Superusuario su_temp = this.su;
        em.remove(su_temp);
        em.getTransaction().begin();
        em.getTransaction().commit();
        
        FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Sucesso", "Usuário " + this.su.getLoginSu() + " excluído."));
            
            this.su = new Superusuario();
    }
}
