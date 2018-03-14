package aut.com.mb;

import aut.com.dao.UsuarioDao;
import aut.com.login.Usuario;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import sisacompo.sisap.modelo.Superusuario;
import sisacompo.sisap.modelo.UsuarioJPA;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    Usuario usuario = new Usuario();
    Superusuario superusuario = new Superusuario();
    UsuarioJPA usuario_jpa = new UsuarioJPA();
    FacesContext fc;
    private boolean acesso = false;
    HttpSession session;
    private String exibe = "";
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Sisacompo1PU");
    private EntityManager em = emf.createEntityManager();

    public UsuarioJPA getUsuario_jpa() {
        return usuario_jpa;
    }

    public void setUsuario_jpa(UsuarioJPA usuario_jpa) {
        this.usuario_jpa = usuario_jpa;
    }

    public EntityManager getEm() {
        return em;
    }

    public HttpSession getSession() {
        return session;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getExibe() {
        return exibe;
    }

    public void setExibe(String exibe) {
        this.exibe = exibe;
    }

    public Superusuario getSuperusuario() {
        return superusuario;
    }

    public void setSuperusuario(Superusuario superusuario) {
        this.superusuario = superusuario;
    }

    public HttpSession criaSession() {
        fc = FacesContext.getCurrentInstance();
        session = (HttpSession) fc.getExternalContext().getSession(false);
        return session;
    }

    public static String md5(String texto) {
        String sen = "";
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger hash = new BigInteger(1, md.digest(texto.getBytes()));
        sen = hash.toString(16);

        return sen;
    }

    public boolean isAcesso() {
        return acesso;
    }

    public void setAcesso(boolean acesso) {
        this.acesso = acesso;
    }

    public String logon() {

        criaSession();
        UsuarioDao dao = new UsuarioDao();
        Usuario usu = new Usuario();
        System.err.println("SENHA DO USUÁRIO: "+usuario.getSenha());
        usu = dao.autenticarUsuario(usuario.getLogin(), usuario.getSenha());

        if (usu != null) {

            if (usu.getAcessos().contains("25")) {

                Query query = em.createQuery("select u from UsuarioJPA u where u.nomeCompleto = '" + usu.getNome() + "'");
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                this.usuario_jpa = (UsuarioJPA) query.getSingleResult();

                query = em.createQuery("select su.privilegio from Superusuario su where su.matricula = " + usu.getMatricula() + " and su.loginSu = "
                        + "'" + usu.getLogin() + "' and su.nomeCompletoSu = '" + usu.getNome() + "'");
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");

                if (query.getResultList().isEmpty()) {
                    session.setAttribute("privilegio", "COMUM");
                } else {
                    session.setAttribute("privilegio", (String) query.getResultList().get(0));
                }

                session.setAttribute("autenticado", true);
                session.setAttribute("usuarioLogado", usu);
                session.setAttribute("login", usu.getLogin());
                session.setAttribute("nome", usu.getNome());
                session.setAttribute("setor", usuario_jpa.getSetorId().getSetorNome());
                session.setAttribute("setor_id", usuario_jpa.getSetorId().getSetorId());

                return "index.xhtml?faces-redirect=true";

            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuário não tem permissão para acessar o Sistema!", ""));
                
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Entre em contato com a GTI."));
                
                return null;
            }

        } else {

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário e/ou Senha [intranet] Inválidos!!!", ""));

            return null;
        }
    }

    public String logout() {

        FacesContext fcon = FacesContext.getCurrentInstance();
        HttpSession httpsession = (HttpSession) fcon.getExternalContext().getSession(false);
        session.removeAttribute("autenticado");
        session.removeAttribute("login");
        session.removeAttribute("nome");
        session.removeAttribute("setor");
        session.removeAttribute("setor_id");
        httpsession.invalidate();

        return "login.xhtml?faces-redirect=true";
    }

    public String principal() {
        return "index.xhtml?faces-redirect=true";
    }

    public String inicial() {
        return "login.xhtml?faces-redirect=true";
    }
}
