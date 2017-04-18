/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.RecursoDao;
import br.edu.ifsul.modelo.Recurso;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Huelison
 */
@ManagedBean(name="controleRecurso")
@SessionScoped
public class ControleRecurso implements Serializable{
    private RecursoDao dao;
    private Recurso objeto;

    public ControleRecurso() {
        dao = new RecursoDao();
    }

    public String listar(){
        return "/privado/recurso/listar?faces-redirect=true";
    }
    
    public String novo(){
        objeto = new Recurso();
        
        return "formulario";
    }
    
    public String salvar(){
        if(dao.salvar(objeto)){
            Util.mensagemInformacao(dao.getMensagem());
            return "listar";
        }else{
            Util.mensagemErro(dao.getMensagem());
            return "formulario";
        }
    }
    
    public String cancelar(){
        return "listar";
    }
    
    public String editar(Integer id){
        try{
            objeto = dao.localizar(id);
            return "formulario";
        }catch(Exception e){
            Util.mensagemErro("Erro ao recuperar objeto"+Util.getMensagemErro(e));
            return "listar";
        }
    }
    
    public void remover(Integer id){
        objeto = dao.localizar(id);
        if(dao.remover(objeto)){
            Util.mensagemInformacao(dao.getMensagem());
        }else{
            Util.mensagemErro(dao.getMensagem());
        }
            
    }
    
    public RecursoDao getDao() {
        return dao;
    }

    public void setDao(RecursoDao dao) {
        this.dao = dao;
    }

    public Recurso getObjeto() {
        return objeto;
    }

    public void setObjeto(Recurso objeto) {
        this.objeto = objeto;
    }
    
    
}
