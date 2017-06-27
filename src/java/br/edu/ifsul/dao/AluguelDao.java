/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Aluguel;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Huelison
 */
public class AluguelDao<T> extends DAOGenerico<Aluguel> implements Serializable {

    private Boolean filtroInicioContrato = false;
    private Boolean filtroFimContrato = false;
    private Calendar dataInicial;
    private Calendar dataFinal;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public AluguelDao() {
        super();
        super.setClassePersistente(Aluguel.class);
    }

    @Override
    public List<Aluguel> getListaObjetos() {
        String jpql = "from " + classePersistente.getSimpleName();
        String where = "";
        filtro = filtro.replaceAll("[';-]", "");

        if (filtro.length() > 0) {
            if (ordem.equals("id")) {
                try {
                    Integer.parseInt(filtro);
                    where += " where " + ordem + " = '" + filtro + "' ";
                } catch (Exception e) {
                }
            } else {
                where += " where upper(" + ordem + ") like '" + filtro.toUpperCase() + "%' ";
            }
        }

        if (filtroInicioContrato) {
            if (!(where.length() > 0)) {
                where = " where ";
            } else {
                where += " and ";
            }
            where += " inicioContrato >= '" + sdf.format(dataInicial.getTime()) + "' "
                    + " and inicioContrato <= '" + sdf.format(dataFinal.getTime()) + "' ";
            
        }

        if (filtroFimContrato) {
            if (!(where.length() > 0)) {
                where = " where ";
            } else {
                where += " and ";
            }
            where += " fimContrato >= '" + sdf.format(dataInicial.getTime()) + "' "
                    + " and fimContrato <= '" + sdf.format(dataFinal.getTime()) + "' ";
        }

        jpql += where;
        jpql += " order by " + ordem;
        totalObjetos = em.createQuery("select id from " + classePersistente.getSimpleName()
                + where + " order by " + ordem).getResultList().size();

        System.out.println("JPQL: " + jpql);
        return em.createQuery(jpql).
                setFirstResult(posicaoAtual).
                setMaxResults(maximoObjetos).getResultList();
    }

    public Boolean getFiltroInicioContrato() {
        return filtroInicioContrato;
    }

    public void setFiltroInicioContrato(Boolean filtroInicioContrato) {
        this.filtroInicioContrato = filtroInicioContrato;
        if (this.filtroInicioContrato) {
            this.filtroFimContrato = false;
        }
    }

    public Calendar getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Calendar dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Calendar getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Calendar dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Boolean getFiltroFimContrato() {
        return filtroFimContrato;
    }
  
    public void setFiltroFimContrato(Boolean filtroFimContrato) {
        this.filtroFimContrato = filtroFimContrato;
        if (this.filtroFimContrato) {
            this.filtroInicioContrato = false;
        }
    }
}
