/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sisacompo.sisap.controlador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.*;
import org.primefaces.event.SelectEvent;
import sisacompo.sisap.modelo.Contrato;
import sisacompo.sisap.modelo.Empresa;
import sisacompo.sisap.modelo.Processo;

/**
 *
 * @author eneas
 */
@ManagedBean(name = "ContratoControlador")
@SessionScoped
public class ContratoJpaController {

    private Contrato contrato;
    private List<Contrato> contratos;
    private String cnpjEmpresa = "";
    private String fantasiaEmpresa = "";
    private String numProcesso = "";
    private long codProcesso = 0;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Sisacompo1PU");
    private EntityManager em = emf.createEntityManager();
    private int houveAditamento = 0;
    private int qtdvzsAditamento = 0;
    private String categoriaProcesso = "";
    private String assuntoProcesso = "";

    public ContratoJpaController() {
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public List<Contrato> getContratos() {

        Collection<Contrato> c = new ArrayList();
        Query query = em.createNamedQuery("Contrato.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        c = query.getResultList();
        this.contratos = new ArrayList(c);
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public long getCodProcesso() {
        return codProcesso;
    }

    public void setCodProcesso(long codProcesso) {
        this.codProcesso = codProcesso;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public void setCnpjEmpresa(String cnpjEmpresa) {
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public String getFantasiaEmpresa() {
        return fantasiaEmpresa;
    }

    public void setFantasiaEmpresa(String fantasiaEmpresa) {
        this.fantasiaEmpresa = fantasiaEmpresa;
    }

    public String getNumProcesso() {
        return numProcesso;
    }

    public String getAssuntoProcesso() {
        return assuntoProcesso;
    }

    public void setAssuntoProcesso(String assuntoProcesso) {
        this.assuntoProcesso = assuntoProcesso;
    }

    public String getCategoriaProcesso() {
        return categoriaProcesso;
    }

    public void setCategoriaProcesso(String categoriaProcesso) {
        this.categoriaProcesso = categoriaProcesso;
    }

    public void setNumProcesso(String numProcesso) {
        this.numProcesso = numProcesso;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public int getHouveAditamento() {
        return houveAditamento;
    }

    public void setHouveAditamento(int houveAditamento) {
        this.houveAditamento = houveAditamento;
    }

    public int getQtdvzsAditamento() {
        return qtdvzsAditamento;
    }

    public void setQtdvzsAditamento(int qtdvzsAditamento) {
        this.qtdvzsAditamento = qtdvzsAditamento;
    }

    public void inserir(ActionEvent e) {
        try {
            em.getTransaction().begin();
            this.contrato.setAssuntoContrato(this.contrato.getAssuntoContrato().toUpperCase());
            this.contrato.setObservacaoContrato(this.contrato.getObservacaoContrato().toUpperCase());
            this.contrato.setDescricaoContrato(this.contrato.getDescricaoContrato().toUpperCase());
            this.contrato.setDataCadContrato(new Date());
            this.contrato.setCodProcesso(em.find(Processo.class, codProcesso));
            this.contrato.setNumVzsAditamento(0);
            this.contrato.setAditamento("NÃO");
            em.persist(contrato);
            em.getTransaction().commit();

            getContratos();
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Sucesso", "Inserido o Contrato n°: " + contrato.getNumContrato()));
            this.contrato = new Contrato();
            this.codProcesso = 0;


        } catch (Throwable t) {

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O Contrato já existe."));

            if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O Contrato já existe."));
            }
        }
    }

    public void alterar(ActionEvent e) {

        if (houveAditamento == 1) {

            qtdvzsAditamento = this.contrato.getNumVzsAditamento();

            Contrato temp = this.contrato;
            em.getTransaction().begin();
            temp.setAssuntoContrato(this.contrato.getAssuntoContrato().toUpperCase());
            temp.setObservacaoContrato(this.contrato.getObservacaoContrato().toUpperCase());
            temp.setDescricaoContrato(this.contrato.getDescricaoContrato().toUpperCase());
            //temp.setCnpjEmpresa(em.find(Empresa.class, cnpjEmpresa));
            temp.setCodProcesso(em.find(Processo.class, codProcesso));
            temp.setAditamento("SIM");
            temp.setNumVzsAditamento(qtdvzsAditamento+1);
            em.merge(temp);
            em.getTransaction().commit();
            getContratos();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Sucesso", "Contrato n°: " + contrato.getNumContrato() + " Atualizado!"));

            this.contrato = new Contrato();
            this.qtdvzsAditamento = 0;
            this.houveAditamento = 0;

        } else {

            Contrato temp = this.contrato;
            em.getTransaction().begin();
            temp.setAssuntoContrato(this.contrato.getAssuntoContrato().toUpperCase());
            temp.setObservacaoContrato(this.contrato.getObservacaoContrato().toUpperCase());
            temp.setDescricaoContrato(this.contrato.getDescricaoContrato().toUpperCase());
            //temp.setCnpjEmpresa(em.find(Empresa.class, cnpjEmpresa));
            temp.setCodProcesso(em.find(Processo.class, codProcesso));
            em.merge(temp);
            em.getTransaction().commit();
            getContratos();

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Sucesso", "Contrato n°: " + contrato.getNumContrato() + " Atualizado!"));

            this.contrato = new Contrato();
        }
    }

    public void excluir(ActionEvent e) {
        
        Contrato temp = this.contrato;
        Contrato temp2 = em.merge(temp);
        em.remove(temp2);
        em.getTransaction().begin();
        em.getTransaction().commit();
        getContratos();
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso", "Excluído o Contrato: "+ this.contrato.getNumContrato()));
        
        this.contrato = new Contrato();
    }

    public List<String> completaCnpj(String q) {
        Collection<String> c = em.createQuery("SELECT DISTINCT e.cnpj FROM Empresa e WHERE e.cnpj like '%" + q + "%'").getResultList();
        return new ArrayList<String>(c);
    }

    public List<String> completaNumProcesso(String q) {
        Collection<String> c = em.createQuery("SELECT DISTINCT p.numProcesso FROM Processo p WHERE p.numProcesso like '%" + q + "%' and p.tipoProcesso = 'COMPRA' or p.tipoProcesso = 'SERVIÇO'").getResultList();
        return new ArrayList<String>(c);
    }

    public void retornaFantasiaEmpresa(AjaxBehaviorEvent actionEvent) {
        Collection<Empresa> c = em.createQuery("SELECT e FROM Empresa e WHERE e.cnpj like '%" + cnpjEmpresa + "%'").getResultList();
        if (c.size() > 0) {
            this.fantasiaEmpresa = new ArrayList<Empresa>(c).get(0).getFantasia();
        } else {
            this.fantasiaEmpresa = "";
        }
    }

    public void preparaInsercao(ActionEvent e) {
        this.contrato = new Contrato();
        this.contratos = new ArrayList<Contrato>();
        this.cnpjEmpresa = "";
        this.fantasiaEmpresa = "";
        this.numProcesso = "";
        this.assuntoProcesso = "";
        this.categoriaProcesso = "";
        this.codProcesso = 0;
    }

    public void exibeAssuntoCategoriaProcesso(SelectEvent event) {
        if (!this.numProcesso.equals("")) {
            try {
                Processo p_temp = (Processo) em.createQuery("select p from Processo p where p.numProcesso = '" + this.numProcesso + "'").getSingleResult();
                this.assuntoProcesso = p_temp.getAssuntoProcesso();
                this.categoriaProcesso = p_temp.getTipoProcesso();
                this.codProcesso = p_temp.getCodProcesso();
            } catch (NoResultException e) {
                e.printStackTrace();
            }
        }
    }
}
