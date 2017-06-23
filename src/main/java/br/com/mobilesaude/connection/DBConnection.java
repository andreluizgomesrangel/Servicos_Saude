package br.com.mobilesaude.connection;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.mobilesaude.dao.RequisicaoDao;
import br.com.mobilesaude.dao.ServiceDao;
import br.com.mobilesaude.resources.EstatisticasServicoDia;
import br.com.mobilesaude.resources.LastRequest;
import br.com.mobilesaude.resources.Requisicao;
import br.com.mobilesaude.resources.Service;

@Stateless
public class DBConnection {

	@EJB
	RequisicaoDao rdao;
	@EJB
	ServiceDao sdao;

	List<Requisicao> allHistorics = new ArrayList<Requisicao>();
	List<Service> services = new ArrayList<Service>();
	
	List<LastRequest> lastRequests = new ArrayList<LastRequest>();

	List<Requisicao> listDay = new ArrayList<Requisicao>();
	
	List<Requisicao> listDayRequest = new ArrayList<Requisicao>();
	
	EstatisticasServicoDia EstatisticaDoDia;
	
	public void setLists() {
		
		this.services = sdao.getLista();
		lastRequests = rdao.getLastRequests();
		
	}
	
	
	
	public EstatisticasServicoDia getDay(String day, long id) {
		List<Requisicao> list = rdao.getDay(day, id);
		EstatisticasServicoDia EstatisticaDoDia = rdao.getEstatisticas(day, id, list.size());
		EstatisticaDoDia.setRequisicoes(list);
		return EstatisticaDoDia;
	}

	public RequisicaoDao getRdao() {
		return rdao;
	}

	public void setRdao(RequisicaoDao rdao) {
		this.rdao = rdao;
	}

	public ServiceDao getSdao() {
		return sdao;
	}

	public void setSdao(ServiceDao sdao) {
		this.sdao = sdao;
	}

	public List<Requisicao> getAllHistorics() {
		allHistorics = this.allHistorics = rdao.getLista();
		return allHistorics;
	}

	public void setAllHistorics(List<Requisicao> allHistorics) {
		this.allHistorics = allHistorics;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public List<LastRequest> getLastRequests() {
		return lastRequests;
	}

	public void setLastRequests(List<LastRequest> lastRequests) {
		this.lastRequests = lastRequests;
	}



	public List<Requisicao> getListDay(String day, long id) {
		listDay = rdao.getDay(day, id);
		return listDay;
	}



	public void setListDay(List<Requisicao> listDay) {
		this.listDay = listDay;
	}



	public EstatisticasServicoDia getEstatisticaDoDia(int size, long id, String day ) {
		EstatisticaDoDia = rdao.getEstatisticas(day, id, size);
		return EstatisticaDoDia;
	}



	public void setEstatisticaDoDia(EstatisticasServicoDia estatisticaDoDia) {
		EstatisticaDoDia = estatisticaDoDia;
	}



	public List<Requisicao> getListDay() {
		return listDay;
	}



	public List<Requisicao> getListDayRequest(int response, long id, String day) {
		listDayRequest = rdao.getDayResponse(day, id, response);
		return listDayRequest;
	}



	public void setListDayRequest(List<Requisicao> listDayRequest) {
		this.listDayRequest = listDayRequest;
	}



	public EstatisticasServicoDia getEstatisticaDoDia() {
		return EstatisticaDoDia;
	}

}
