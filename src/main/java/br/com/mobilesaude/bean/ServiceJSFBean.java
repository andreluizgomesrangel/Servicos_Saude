package br.com.mobilesaude.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.FormParam;
import javax.xml.bind.JAXBException;

import br.com.mobilesaude.dao.RequisicaoDao;
import br.com.mobilesaude.dao.ServiceDao;
import br.com.mobilesaude.resources.EstatisticasServicoDia;
import br.com.mobilesaude.resources.Requisicao;
import br.com.mobilesaude.resources.Service;
import br.com.mobilesaude.resources.Status_History;
import br.com.mobilesaude.resources.TipoDeResposta;

@ManagedBean
@ViewScoped
public class ServiceJSFBean extends RequisicaoJSFBean {

	private long id;
	private String date = new String();

	private String Estatistica = new String();

	List<Status_History> status = new ArrayList<Status_History>();
	List<Requisicao> allHistorics = new ArrayList<Requisicao>();
	List<Service> services = new ArrayList<Service>();
	EstatisticasServicoDia estatisticas = new EstatisticasServicoDia();

	@EJB
	RequisicaoDao rdao;
	@EJB
	ServiceDao sdao;

	public ServiceJSFBean() {
		
		System.out.println(">>>>>>>>>>>>>>>>>>> ServiceJSFBean");
		
		setLists();
		setUrlParameter();
		setServiceHistoric(id, date);
	}

	public void setLists() {

		// CRequisicao ch = new CRequisicao();
		// CService cs = new CService();

		allHistorics = rdao.getLista();
		services = sdao.getLista();

		for (int i = 0; i < services.size(); i++) {
			lastHistorics.add(allHistorics.get(i));
		}
	}

	public EstatisticasServicoDia getDay(String day, long id) {
		List<Requisicao> list = rdao.getDay(day, id);
		EstatisticasServicoDia EstatisticaDoDia = rdao.getEstatisticas(day, id, list.size());
		EstatisticaDoDia.setRequisicoes(list);

		return EstatisticaDoDia;

	}

	public void setUrlParameter() {

		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		String projectId = paramMap.get("id");
		id = Integer.parseInt(projectId);
		date = paramMap.get("date");
		estatisticas = getDay(date, id);
		setarEstatisticas(estatisticas);

	}

	private void setarEstatisticas(EstatisticasServicoDia estatistica) {
		StringBuilder string = new StringBuilder();
		List<TipoDeResposta> respostas = estatistica.getRespostas();
		string.append("| quantidade de consultas - " + estatisticas.getRequisicoes().size() + " | ");
		for (int i = 0; i < respostas.size(); i++) {
			string.append(
					" Codigo " + respostas.get(i).getResponse() + " - " + respostas.get(i).getPorcentagem() + " |");
		}
		Estatistica = string.toString();
	}

	public List<Requisicao> getAllHistorics() {
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEstatistica() {
		return Estatistica;
	}

	public void setEstatistica(String estatistica) {
		Estatistica = estatistica;
	}

	public List<Status_History> getStatus() {
		return status;
	}

	public void setStatus(List<Status_History> status) {
		this.status = status;
	}

	public EstatisticasServicoDia getEstatisticas() {
		return estatisticas;
	}

	public void setEstatisticas(EstatisticasServicoDia estatisticas) {
		this.estatisticas = estatisticas;
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

}
