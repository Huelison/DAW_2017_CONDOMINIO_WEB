/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.AluguelDao;
import br.edu.ifsul.dao.CondominioDao;
import br.edu.ifsul.dao.LocatarioDao;
import br.edu.ifsul.modelo.Aluguel;
import br.edu.ifsul.modelo.Condominio;
import br.edu.ifsul.modelo.Locatario;
import br.edu.ifsul.modelo.Mensalidades;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Huelison
 */
@ManagedBean(name = "controleAluguel")
@SessionScoped
public class ControleAluguel implements Serializable {

    private AluguelDao<Aluguel> dao;
    private Aluguel objeto;
    private LocatarioDao<Locatario> daoLocatario;
    private CondominioDao<Condominio> daoCondominio;
    private Mensalidades mensalidade;
    private Boolean novaMensalidade;

    public ControleAluguel() {
        dao = new AluguelDao<>();
        daoLocatario = new LocatarioDao<>();
        daoCondominio = new CondominioDao<>();
    }

    public String listar() {
        return "/privado/aluguel/listar?faces-redirect=true";
    }

    public void novo() {
        objeto = new Aluguel();
    }

    public void salvar() {
        boolean persistiu;
        if (objeto.getId() == null) {
            persistiu = dao.persist(objeto);
        } else {
            persistiu = dao.merge(objeto);
        }
        if (persistiu) {
            Util.mensagemInformacao(dao.getMensagem());
        } else {
            Util.mensagemErro(dao.getMensagem());
        }
    }

    public void editar(Integer id) {
        try {
            objeto = dao.localizar(id);
        } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar objeto" + Util.getMensagemErro(e));
        }
    }

    public void remover(Integer id) {
        objeto = dao.localizar(id);
        if (dao.remover(objeto)) {
            Util.mensagemInformacao(dao.getMensagem());
        } else {
            Util.mensagemErro(dao.getMensagem());
        }
    }

    public void alterarMensalidade(int index) {
        System.out.println("IDX alterar: " + index);
        mensalidade = objeto.getMensalidade().get(index);
        novaMensalidade = false;
    }

    public void salvarMensalidade() {
        if (novaMensalidade) {
            objeto.adicionarMensalidade(mensalidade);
        } else {
            
        }
        Util.mensagemInformacao("Operação realizada com sucesso");
    }

    public void removerMensalidade(int index) {
        System.out.println("IDX remover: " + index);
        objeto.removermensalidade(index);
        Util.mensagemInformacao("Mensalidade removida com sucesso");
    }

    public void gerarMensalidades() {
        Boolean temPagamento = false;
        for (Mensalidades p : objeto.getMensalidade()) {
            if (p.getDataPagamento() != null
                    || p.getValorPagamento() != null) {
                temPagamento = true;
                break;
            }
        }
        if (temPagamento) {
            Util.mensagemErro("Mensalidades não podem ser geradas novamente "
                    + "por já existirem mensalidades pagas!");
        } else {

            if (objeto.getInicioContrato().after(objeto.getFimContrato())) {

                Util.mensagemErro("Mensalidades não podem ser geradas "
                        + "pois a data de inicio do contrato é posterior a data de fim!");
            } else {
                try {
                    objeto.getMensalidade().clear();
                    objeto.gerarMensalidades();
                    Util.mensagemInformacao("Mensalidades geradas com sucesso!");
                } catch (Exception e) {
                    Util.mensagemErro("Erro ao gerar mensalidades " + Util.getMensagemErro(e));
                }
            }
        }
    }

    public AluguelDao getDao() {
        return dao;
    }

    public void setDao(AluguelDao dao) {
        this.dao = dao;
    }

    public Aluguel getObjeto() {
        return objeto;
    }

    public void setObjeto(Aluguel objeto) {
        this.objeto = objeto;
    }

    public LocatarioDao<Locatario> getDaoLocatario() {
        return daoLocatario;
    }

    public void setDaoLocatario(LocatarioDao<Locatario> daoLocatario) {
        this.daoLocatario = daoLocatario;
    }

    public CondominioDao<Condominio> getDaoCondominio() {
        return daoCondominio;
    }

    public void setDaoCondominio(CondominioDao<Condominio> daoCondominio) {
        this.daoCondominio = daoCondominio;
    }

    public Mensalidades getMensalidade() {
        return mensalidade;
    }

    public void setMensalidade(Mensalidades mensalidade) {
        this.mensalidade = mensalidade;
    }

    public Boolean getNovaMensalidade() {
        return novaMensalidade;
    }

    public void setNovaMensalidade(Boolean novaMensalidade) {
        this.novaMensalidade = novaMensalidade;
    }

}
