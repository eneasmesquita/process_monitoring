/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sisacompo.sisap.utilBean;

import javax.el.ELContext;
import javax.faces.FacesException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import sisacompo.sisap.controlador.TramitacaoMemorandoJpaController;
import sisacompo.sisap.controlador.TramitacaoProcessoJpaController;

/**
 *
 * @author eneas
 */
@ManagedBean(name="controladorInterface")
@SessionScoped

public class ControladorInterfaceBean {

    /**
     * Creates a new instance of ControladorInterfaceBean
     */
    private boolean tipoProcesso = true;
    private boolean listTipoProcesso = false;
    private boolean listMemorando = false;
    private boolean listSolicitacaoProcesso = false;
    private boolean listContratoEmpresa = false;
    private boolean listAndamentoProcesso = false;
    private boolean listAndamentoMemorando = false;
    private boolean listRelacaoProSigilosos = false;
    private String classeProcesso = "";
    TramitacaoProcessoJpaController tp = (TramitacaoProcessoJpaController) getManagedBean("TramiteProcessoControlador");
    TramitacaoMemorandoJpaController tm = (TramitacaoMemorandoJpaController) getManagedBean("TramiteMemorandoControlador");
    
    public ControladorInterfaceBean() {
    }

    public boolean isListAndamentoProcesso() {
        return listAndamentoProcesso;
    }

    public void setListAndamentoProcesso(boolean listAndamentoProcesso) {
        this.listAndamentoProcesso = listAndamentoProcesso;
    }

    public boolean isListContratoEmpresa() {
        return listContratoEmpresa;
    }

    public void setListContratoEmpresa(boolean listContratoEmpresa) {
        this.listContratoEmpresa = listContratoEmpresa;
    }

    public boolean isListMemorando() {
        return listMemorando;
    }

    public void setListMemorando(boolean listMemorando) {
        this.listMemorando = listMemorando;
    }

    public boolean isListSolicitacaoProcesso() {
        return listSolicitacaoProcesso;
    }

    public void setListSolicitacaoProcesso(boolean listSolicitacaoProcesso) {
        this.listSolicitacaoProcesso = listSolicitacaoProcesso;
    }

    public boolean isListTipoProcesso() {
        return listTipoProcesso;
    }

    public void setListTipoProcesso(boolean listTipoProcesso) {
        this.listTipoProcesso = listTipoProcesso;
    }

    public boolean isTipoProcesso() {
        return tipoProcesso;
    }

    public void setTipoProcesso(boolean tipoProcesso) {
        this.tipoProcesso = tipoProcesso;
    }

    public boolean isListAndamentoMemorando() {
        return listAndamentoMemorando;
    }

    public void setListAndamentoMemorando(boolean listAndamentoMemorando) {
        this.listAndamentoMemorando = listAndamentoMemorando;
    }

    public String getClasseProcesso() {
        return classeProcesso;
    }

    public void setClasseProcesso(String classeProcesso) {
        this.classeProcesso = classeProcesso;
    }

    public boolean isListRelacaoProSigilosos() {
        return listRelacaoProSigilosos;
    }

    public void setListRelacaoProSigilosos(boolean listRelacaoProSigilosos) {
        this.listRelacaoProSigilosos = listRelacaoProSigilosos;
    }
    
     public void exibetipoProcesso(ActionEvent e) {
        this.tipoProcesso = true;
        this.listTipoProcesso = false;
        this.listMemorando = false;
        this.listSolicitacaoProcesso = false;
        this.listContratoEmpresa = false;
        this.listAndamentoProcesso = false;
        this.listAndamentoMemorando = false;
        this.listRelacaoProSigilosos = false;
    }

    public void exibelisProcessoCompra(ActionEvent e) {
        this.listTipoProcesso = true;
        this.tipoProcesso = false;
        this.listMemorando = false;
        this.listSolicitacaoProcesso = false;
        this.listContratoEmpresa = false;
        this.listAndamentoProcesso = false;
        this.listAndamentoMemorando = false;
        this.listRelacaoProSigilosos = false;
        this.tp.setContador(0);
        this.tp.setExibeBotao(false);
        this.classeProcesso = "compra";
    }
    
    public void exibelisProcessoServico(ActionEvent e) {
        this.listTipoProcesso = true;
        this.tipoProcesso = false;
        this.listMemorando = false;
        this.listSolicitacaoProcesso = false;
        this.listContratoEmpresa = false;
        this.listAndamentoProcesso = false;
        this.listAndamentoMemorando = false;
        this.listRelacaoProSigilosos = false;
        this.tp.setContador(0);
        this.tp.setExibeBotao(false);
        this.classeProcesso = "servico";
    }
    
    public void exibelisProcessoDiaria(ActionEvent e) {
        this.listTipoProcesso = true;
        this.tipoProcesso = false;
        this.listMemorando = false;
        this.listSolicitacaoProcesso = false;
        this.listContratoEmpresa = false;
        this.listAndamentoProcesso = false;
        this.listAndamentoMemorando = false;
        this.listRelacaoProSigilosos = false;
        this.tp.setContador(0);
        this.tp.setExibeBotao(false);
        this.classeProcesso = "diaria";
    }

    public void exibelisProcessoOutro(ActionEvent e) {
        this.listTipoProcesso = true;
        this.tipoProcesso = false;
        this.listMemorando = false;
        this.listSolicitacaoProcesso = false;
        this.listContratoEmpresa = false;
        this.listAndamentoProcesso = false;
        this.listAndamentoMemorando = false;
        this.listRelacaoProSigilosos = false;
        this.tp.setContador(0);
        this.tp.setExibeBotao(false);
        this.classeProcesso = "outro";
    }
    
    public void exibelistMemorando(ActionEvent e) {
        this.listMemorando = true;
        this.listTipoProcesso = false;
        this.tipoProcesso = false;
        this.listSolicitacaoProcesso = false;
        this.listContratoEmpresa = false;
        this.listAndamentoProcesso = false;
        this.listAndamentoMemorando = false;
        this.listRelacaoProSigilosos = false;
        this.tm.setExibeBotao("não");
    }

    public void exibelistSolicitacaoProcesso(ActionEvent e) {
        //nível de profundidade: 1
        this.listSolicitacaoProcesso = true;
        this.listMemorando = false;
        this.listTipoProcesso = false;
        this.tipoProcesso = false;
        this.listContratoEmpresa = false;
        this.listAndamentoProcesso = false;
        this.listAndamentoMemorando = false;
        this.listRelacaoProSigilosos = false;
    }

    public void exibelistContratoEmpresa(ActionEvent e) {
        //nível de profundidade: 1
        this.listContratoEmpresa = true;
        this.listSolicitacaoProcesso = false;
        this.listMemorando = false;
        this.listTipoProcesso = false;
        this.tipoProcesso = false;
        this.listAndamentoProcesso = false;
        this.listAndamentoMemorando = false;
        this.listRelacaoProSigilosos = false;
    }

    public void exibelistAndamentoProcesso(ActionEvent e) {
        this.listAndamentoProcesso = true;
        this.listTipoProcesso = false;
        this.listContratoEmpresa = false;
        this.listSolicitacaoProcesso = false;
        this.listMemorando = false;
        this.tipoProcesso = false;
        this.listAndamentoMemorando = false;
        this.listRelacaoProSigilosos = false;
    }
    
    public void exibelistAndamentoProcessoSP(ActionEvent e) {
        this.listAndamentoProcesso = true;
        this.listTipoProcesso = false;
        this.listContratoEmpresa = false;
        this.listSolicitacaoProcesso = false;
        this.listMemorando = false;
        this.tipoProcesso = false;
        this.listAndamentoMemorando = false;
        this.listRelacaoProSigilosos = false;
        this.classeProcesso = "SP";
    }
    
    public void exibelistAndamentoMemorando(ActionEvent e){
        this.listAndamentoMemorando = true;
        this.listAndamentoProcesso = false;
        this.listTipoProcesso = false;
        this.listContratoEmpresa = false;
        this.listSolicitacaoProcesso = false;
        this.listMemorando = false;
        this.tipoProcesso = false;
        this.listRelacaoProSigilosos = false;
    }
    
    public void exibeListRelacaoProSigilosos(ActionEvent e){
        this.listRelacaoProSigilosos = true;
        this.listAndamentoMemorando = false;
        this.listAndamentoProcesso = false;
        this.listTipoProcesso = false;
        this.listContratoEmpresa = false;
        this.listSolicitacaoProcesso = false;
        this.listMemorando = false;
        this.tipoProcesso = false;
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
