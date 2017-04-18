/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.jpa.EntityManagerUtil;
import br.edu.ifsul.modelo.Recurso;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Huelison
 */
public class RecursoDao implements Serializable{
    private String mensagem = "";
    private EntityManager em;

    public RecursoDao() {
        this.em = EntityManagerUtil.getEntityManager();
    }
    
    public List<Recurso> getLista(){
        return em.createQuery("from Recurso order by descricao").getResultList();
    }
    
    public boolean salvar (Recurso obj){
        try{
            em.getTransaction().begin();
            if(obj.getId() == null){
                em.persist(obj);
            }else{
                //mensagem = "teste";
                em.merge(obj);
                //mensagem = "teste222";
            
            }
            em.getTransaction().commit();
            mensagem = "Objeto persistido com sucesso";
            return true;
        }catch (Exception e){
            if (em.getTransaction().isActive() == false) {
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            mensagem+= "Erro ao persistir objeto: "+Util.getMensagemErro(e);
            return false;
        }
    }
    public boolean remover(Recurso obj){
        try{
            em.getTransaction().begin();
            em.remove(obj);
            em.getTransaction().commit();
            mensagem = "Objeto persistido com sucesso";
            return true;
        } catch(Exception e){
            if(em.getTransaction().isActive()==false){
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            mensagem = "Erro ao remover objeto: "+Util.getMensagemErro(e);
            return false;
        }
    }
    
    public Recurso localizar(Integer id){
        return em.find(Recurso.class, id);
    }
    
    
    
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    
}
