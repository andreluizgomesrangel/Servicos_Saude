package br.com.mobilesaude.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.mobilesaude.dao.RequisicaoDao;
import br.com.mobilesaude.dao.ServiceDao;
import br.com.mobilesaude.resources.LastRequest;
import br.com.mobilesaude.resources.Service;
import br.com.mobilesaude.temporizador.Pipeline;

@Stateless
public class VerificaStatusService {

	@EJB
	ServiceDao serviceDao;

	@EJB
	RequisicaoDao requisicaoDao;

	List<LastRequest> lastRequests = new ArrayList<LastRequest>();

	public VerificaStatusService() {

	}

	public void verificarStatus() {
		List<Service> list = serviceDao.getLista();

		for (Service x : list) {

			Thread t = new Thread(new Pipeline(x, requisicaoDao));
			t.start();

		}

	}

	public List<LastRequest> getLastRequests() {
		lastRequests = requisicaoDao.getLastRequests();
		return lastRequests;
	}

	public void setLastRequests(List<LastRequest> lastRequests) {
		this.lastRequests = lastRequests;
	}

}
