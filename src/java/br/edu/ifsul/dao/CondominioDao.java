/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Condominio;
import br.edu.ifsul.modelo.UnidadeCondominal;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Huelison
 */
public class CondominioDao<T> extends DAOGenerico<Condominio> implements Serializable {

    private List<UnidadeCondominal> listaUnCondominal;

    public CondominioDao() {
        super();
        super.setClassePersistente(Condominio.class);
    }

    public List<UnidadeCondominal> getListaUnCondominal() {
        String jpql = "from " + UnidadeCondominal.class.getSimpleName() + " order by numero";
        return em.createQuery(jpql).getResultList();
    }

    public void setListaUnCondominal(List<UnidadeCondominal> listaUnCondominal) {
        this.listaUnCondominal = listaUnCondominal;
    }
}
