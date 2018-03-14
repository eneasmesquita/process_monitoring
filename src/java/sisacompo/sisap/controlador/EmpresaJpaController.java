/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sisacompo.sisap.controlador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import sisacompo.sisap.modelo.Contrato;
import sisacompo.sisap.modelo.Empresa;

/**
 *
 * @author eneas
 */
@ManagedBean(name = "EmpresaControlador")
@SessionScoped
public class EmpresaJpaController {

    private Empresa empresa;
    private List<Empresa> empresas;
    private String numContrato = "";
    private long codContrato = 0;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Sisacompo1PU");
    private EntityManager em = emf.createEntityManager();
    private String assuntoContrato = "";
    private String data = "";
    private String vigencia_data_inicial = "";
    private String vigencia_data_final = "";
    private String artigo = "";

    public EmpresaJpaController() {

    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Empresa> getEmpresas() {
        Collection<Empresa> c = new ArrayList();
        c = em.createNamedQuery("Empresa.findAll").getResultList();
        this.empresas = new ArrayList(c);
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public String getNumContrato() {
        return numContrato;
    }

    public String getAssuntoContrato() {
        return assuntoContrato;
    }

    public String getArtigo() {
        return artigo;
    }

    public long getCodContrato() {
        return codContrato;
    }

    public void setCodContrato(long codContrato) {
        this.codContrato = codContrato;
    }

    public void setArtigo(String artigo) {
        this.artigo = artigo;
    }

    public void setAssuntoContrato(String assuntoContrato) {
        this.assuntoContrato = assuntoContrato;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getVigencia_data_final() {
        return vigencia_data_final;
    }

    public void setVigencia_data_final(String vigencia_data_final) {
        this.vigencia_data_final = vigencia_data_final;
    }

    public String getVigencia_data_inicial() {
        return vigencia_data_inicial;
    }

    public void setVigencia_data_inicial(String vigencia_data_inicial) {
        this.vigencia_data_inicial = vigencia_data_inicial;
    }

    public void setNumContrato(String numContrato) {
        this.numContrato = numContrato;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void inserir(ActionEvent e) {
        try {
            em.getTransaction().begin();
            this.empresa.setEndereco(this.empresa.getEndereco().toUpperCase());
            this.empresa.setFantasia(this.empresa.getFantasia().toUpperCase());
            this.empresa.setRazao(this.empresa.getRazao().toUpperCase());
            this.empresa.setCodContrato(em.find(Contrato.class, codContrato));
            this.empresa.setResponsavel(this.empresa.getResponsavel().toUpperCase());

            em.persist(empresa);
            em.getTransaction().commit();

            getEmpresas();
            
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Sucesso", "Inserida a Empresa : " + empresa.getFantasia()));
            
            this.empresa = new Empresa();

        } catch (Throwable t) {
            
            FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "A Empresa já existe."));
            
            if (t.getLocalizedMessage().indexOf("duplicate key") > -1) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "A empresa já existe."));
            }
        }
    }

    public List<String> completaNumContrato(String q) {
        Collection<String> c = em.createQuery("SELECT DISTINCT c.numContrato FROM Contrato c WHERE c.numContrato like '%"+ q +"%'").getResultList();
        return new ArrayList<String>(c);
    }

    public void retornaDadosContrato(AjaxBehaviorEvent actionEvent) {
        Collection<Contrato> c = em.createQuery("SELECT DISTINCT c FROM Contrato c WHERE c.numContrato like '%" + numContrato + "%'").getResultList();
        if (c.size() > 0) {
            this.assuntoContrato = new ArrayList<Contrato>(c).get(0).getAssuntoContrato();
            this.codContrato = new ArrayList<Contrato>(c).get(0).getCodContrato();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            this.data = sdf.format(new ArrayList<Contrato>(c).get(0).getDataContrato());
            this.vigencia_data_inicial = sdf.format(new ArrayList<Contrato>(c).get(0).getVigenciaDataInicial());
            this.vigencia_data_final = sdf.format(new ArrayList<Contrato>(c).get(0).getVigenciaDataFinal());
            this.artigo = "à";
        } else {
            this.assuntoContrato = "";
            this.data = "";
            this.vigencia_data_inicial = "";
            this.vigencia_data_final = "";
            this.artigo = "";
        }
    }
    
    public void atualizar(ActionEvent e) {
        Empresa temp = this.empresa;
        em.getTransaction().begin();
        temp.setEndereco(this.empresa.getEndereco().toUpperCase());
        temp.setFantasia(this.empresa.getFantasia().toUpperCase());
        temp.setRazao(this.empresa.getRazao().toUpperCase());
        temp.setResponsavel(this.empresa.getResponsavel().toUpperCase());
        temp.setCodContrato(em.find(Contrato.class, codContrato));
        em.merge(temp);
        em.getTransaction().commit();
        getEmpresas();
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso", "Empresa: " + empresa.getFantasia() + " Atualizada!"));
        
        this.empresa = new Empresa();
    }

    public void excluir(ActionEvent e) {
        Empresa temp = this.empresa;
        Empresa temp2 = em.merge(temp);
        em.remove(temp2);
        em.getTransaction().begin();
        em.getTransaction().commit();
        getEmpresas();
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Sucesso", "Excluída a empresa: "+ this.empresa.getFantasia()));
        
        this.empresa = new Empresa();
        this.numContrato = "";
    }

    public void preparaInsercao(ActionEvent e) {
        empresa = new Empresa();
        empresas = new ArrayList();
        this.assuntoContrato = "";
        this.data = "";
        this.vigencia_data_inicial = "";
            this.vigencia_data_final = "";
        this.numContrato = "";
        this.artigo = "";
        this.codContrato = 0;
    }
    
    public void limparExibicao(ActionEvent e){
       this.data = "";
       this.vigencia_data_inicial = "";
       this.vigencia_data_final = "";
       this.assuntoContrato = "";
       this.artigo = "";
    }
}
