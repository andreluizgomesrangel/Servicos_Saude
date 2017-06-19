package br.com.mobilesaude.bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import br.com.mobilesaude.dao.RequisicaoDao;
import br.com.mobilesaude.dao.Teste;
import br.com.mobilesaude.resources.LastRequest;

@ManagedBean
@RequestScoped
public class Bean {

	@EJB
	RequisicaoDao rdao;

	@Inject
	Teste teste;
	
	List<LastRequest> lastRequests = new ArrayList<LastRequest>();
	// CRequisicao cr = new CRequisicao();

	public Bean() {
		System.out.println(">>>>>>>>>>>>>>>>>>> Bean");
		
		// lastRequests = cr.getLastOnes();
//		lastRequests = rdao.getLastRequests();
		teste.teste();
	}

	public List<LastRequest> getLastRequests() {
		return lastRequests;
	}

	public void setLastRequests(List<LastRequest> lastRequests) {
		this.lastRequests = lastRequests;
	}

	public RequisicaoDao getRdao() {
		return rdao;
	}

	public void setRdao(RequisicaoDao rdao) {
		this.rdao = rdao;
	}

}
