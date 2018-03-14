/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sisacompo.sisap.controlador;

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
import sisacompo.sisap.modelo.Processo;
import sisacompo.sisap.modelo.UsuarioJPA;
import sisacompo.sisap.modelo.UsuarioProcesso;

/**
 *
 * @author eneas
 */
@ManagedBean(name = "UsuProControlador")
@SessionScoped
public class UsuProJpaController {

    private List<UsuarioProcesso> usuarios_processos;
    private UsuarioProcesso usuario_processo;
    private List<String> usuarioNome;
    private List<String> usuariosSetor;
    private UsuarioJPA usuario;
    private List<UsuarioJPA> usuarios;
    private String numProcesso = "";
    private long codProcesso = 0;
    //private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Sisacompo1PU");
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Sisacompo1PU");
    private EntityManager em = emf.createEntityManager();
    private int indice = 0;
    
    private String nome_setor = "";
    private int setor_id = 0;
    private String user_name = "";
    private String nome_completo = "";

    public UsuProJpaController() {
    }

    public UsuarioProcesso getUsuario_processo() {
        if (this.usuario_processo == null) {
            this.usuario_processo = new UsuarioProcesso();
        }
        return usuario_processo;
    }

    public void setUsuario_processo(UsuarioProcesso usuario_processo) {
        this.usuario_processo = usuario_processo;
    }

    public List<UsuarioProcesso> getUsuarios_processos() {
        Collection<UsuarioProcesso> c = new ArrayList();
        c = em.createNamedQuery("UsuarioProcesso.findAll").getResultList();
        this.usuarios_processos = new ArrayList(c);
        return usuarios_processos;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public long getCodProcesso() {
        return codProcesso;
    }

    public void setCodProcesso(long codProcesso) {
        this.codProcesso = codProcesso;
    }

    public void setUsuarios_processos(List<UsuarioProcesso> usuarios_processos) {
        this.usuarios_processos = usuarios_processos;
    }

    public UsuarioJPA getUsuario() {
        if (this.usuario == null) {
            this.usuario = new UsuarioJPA();
        }
        return usuario;
    }

    public void setUsuario(UsuarioJPA usuario) {
        this.usuario = usuario;
    }

    public String getNumProcesso() {
        return numProcesso;
    }

    public String getNome_completo() {
        return nome_completo;
    }

    public void setNome_completo(String nome_completo) {
        this.nome_completo = nome_completo;
    }

    public void setNumProcesso(String numProcesso) {
        this.numProcesso = numProcesso;
    }

    public int getIndice() {
        return indice;
    }

    public String getNome_setor() {
        return nome_setor;
    }

    public int getSetor_id() {
        return setor_id;
    }

    public void setSetor_id(int setor_id) {
        this.setor_id = setor_id;
    }

    public void setNome_setor(String nome_setor) {
        this.nome_setor = nome_setor;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public void setUsuarios(List<UsuarioJPA> usuarios) {
        this.usuarios = usuarios;
    }

    public List<UsuarioJPA> getUsuarios() {
        Collection<UsuarioJPA> c = new ArrayList();
        c = em.createNamedQuery("Usuario.findAll").getResultList();
        this.usuarios = new ArrayList(c);
        return usuarios;
    }

    public List<String> getUsuariosSetor() {
        Collection<String> c = new ArrayList();
        c = em.createQuery("select distinct s.setorNome from Setor s where s.setorNome <> '' and s.setorInativo = false order by s.setorNome asc").getResultList();
        this.usuariosSetor = new ArrayList(c);
        return usuariosSetor;
    }

    public List<String> getUsuarioNome() {
        Collection<String> c = new ArrayList();
        c = em.createQuery("select u.nomeCompleto from UsuarioJPA u where u.nomeCompleto <> '' and u.existe = true "
                + "and u.matricula <> -1 and u.setorId is not null order by u.nomeCompleto asc").getResultList();
        this.usuarioNome = new ArrayList(c);
        return usuarioNome;
    }

    public void retornaUsuarioLogin(SelectEvent event) {
        Collection<UsuarioJPA> c = em.createQuery("select u from UsuarioJPA u where u.nomeCompleto like '%"+this.nome_completo+"%'").getResultList();
        if (c.size() > 0) {
            this.user_name = new ArrayList<UsuarioJPA>(c).get(0).getUsername();
        } else {
            this.user_name = "";
        }
    }

    public void insereUsuarioLogin() {
        Query query = em.createQuery("select u from UsuarioJPA u where u.nomeCompleto = '"
                + this.usuario_processo.getNomeCompleto() + "'");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        this.usuario = (UsuarioJPA) query.getSingleResult();
        this.user_name = usuario.getUsername();
       // System.out.println("username: "+this.user_name);
        this.nome_setor = usuario.getSetorId().getSetorNome();
        //System.out.println("nome_setor: "+this.nome_setor);
        this.setor_id = usuario.getSetorId().getSetorId();
       // System.out.println("setor_id: "+this.setor_id);
    }
    
    public void insereSetorId() {
        Query query = em.createQuery("select s.setorId from Setor s where= s.setorNome like %'"+this.nome_setor+"'%");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        this.setor_id = (Integer) query.getSingleResult();
    }
    
    public List<String> completaNomeUsuario(String s){
        Collection<String> c = em.createQuery("SELECT DISTINCT u.nomeCompleto FROM UsuarioJPA u WHERE u.nomeCompleto like '%" + s.toUpperCase() + "%' order by u.nomeCompleto asc").getResultList();
        return new ArrayList<String>(c);
    }
    
    public List<String> completaNomeSetor(String s){
        Collection<String> c = em.createQuery("SELECT DISTINCT s.setorNome FROM Setor s WHERE s.setorNome like '%" + s.toUpperCase() + "%' order by s.setorNome asc").getResultList();
        return new ArrayList<String>(c);
    }
    
    public EntityManager getEm() {
        return em;
    }

    public void setUsuarios_sisap(List<UsuarioJPA> usuarios) {
        this.usuarios = usuarios;
    }

    public void setUsuarioNome(List<String> usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public List<UsuarioJPA> getUsuarioJpa() {
        Collection<String> c = new ArrayList();
        c = em.createQuery("select u from UsuarioJPA u where u.username <> '' order by u.username asc").getResultList();
        this.usuarios = new ArrayList(c);
        return usuarios;
    }

    public List<String> retornaUsuariosLogin(String s) {
        Collection<String> c = em.createQuery("select u.username from UsuarioJPA u where u.username like '%" + s + "%'").getResultList();
        return new ArrayList<String>(c);
    }

    public void inserir(ActionEvent e) {

        try {
            usuario_processo.setCodProcesso(em.find(Processo.class, this.codProcesso));
            //usuario_processo.setNomeCompleto(this.nome_completo);
            usuario_processo.setSetorNome(this.nome_setor);
            usuario_processo.setSetorId(this.setor_id);
            usuario_processo.setUsername(this.user_name);
            em.persist(usuario_processo);
            em.getTransaction().begin();
            em.getTransaction().commit();
            getUsuarios_processos();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Sucesso", "Permissão de visualização do processo: " + this.numProcesso + " concedida ao usuário: " + this.usuario_processo.getUsername()));

            this.usuario_processo = new UsuarioProcesso();
            /*
            this.numProcesso = "";
            * 
            */

        } catch (Throwable t) {
            //t.printStackTrace();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível conceder permissão de visualização do processo: " + this.numProcesso + " para o usuário: " + usuario_processo.getUsername()));

            if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O Usuário já existe."));
            }
        }
    }

    public void alterar(ActionEvent e) {

        UsuarioProcesso usuario_temp = this.usuario_processo;

        em.merge(usuario_temp);
        em.getTransaction().begin();
        em.getTransaction().commit();
        getUsuarios_processos();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso", "Alterado o Usuario: " + usuario_processo.getUsername()));

        this.usuario_processo = new UsuarioProcesso();
        this.numProcesso = "";
    }

    public void excluir(ActionEvent e) {

        UsuarioProcesso usuario_temp = this.usuario_processo;

        em.getTransaction().begin();
        //usuario_temp.setNumProcesso(em.find(Processo.class, this.numProcesso));
        UsuarioProcesso up2 = em.merge(usuario_temp);
        em.remove(up2);
        em.getTransaction().commit();
        getUsuarios_processos();

        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso", "Excluída a permissão de visualização do processo: " + this.numProcesso + " para o usuário: " + usuario_processo.getUsername()));

        this.usuario_processo = new UsuarioProcesso();
        this.numProcesso = "";
    }

    public void preparaInsercao(ActionEvent e) {

        this.usuario_processo = new UsuarioProcesso();
        this.usuarios_processos = new ArrayList<UsuarioProcesso>();
        this.usuarioNome = new ArrayList<String>();
        this.usuariosSetor = new ArrayList<String>();
        this.usuario = new UsuarioJPA();
        this.usuarios = new ArrayList<UsuarioJPA>();
        this.numProcesso = "";
        this.codProcesso = 0;

    }
}
