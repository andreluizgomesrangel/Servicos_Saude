package br.com.mobilesaude.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.FormParam;
import javax.xml.bind.JAXBException;

import br.com.mobilesaude.connection.DBConnection;
import br.com.mobilesaude.dao.RequisicaoDao;
import br.com.mobilesaude.dao.ServiceDao;
import br.com.mobilesaude.resources.EstatisticasServicoDia;
import br.com.mobilesaude.resources.Requisicao;
import br.com.mobilesaude.resources.Service;
import br.com.mobilesaude.resources.Status_History;
import br.com.mobilesaude.resources.TipoDeResposta;

@ManagedBean
@ViewScoped
public class ServiceDayResponseBean extends RequisicaoJSFBean {

	private long id;
	private String date = new String();

	private String Estatistica = new String();

	List<Status_History> status = new ArrayList<Status_History>();
	List<Requisicao> allHistorics = new ArrayList<Requisicao>();
	List<Service> services = new ArrayList<Service>();
	EstatisticasServicoDia estatisticas = new EstatisticasServicoDia();

	
	public ServiceDayResponseBean() {
		System.out.println(">>>>>>>>>>>>>>>>>>> ServiceDayResponseBean");
		
		//DB = (DBConnection) lookup(null);
		
		setLists();
		setUrlParameter();
		setServiceHistoric(id, date);
	}

	public Object lookup(Class<?> clazz) {
		InitialContext context;
		try {
			context = new InitialContext();
			return context.lookup("java:global/Servicos_Saude/DBConnection");
		} catch (NamingException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro na busca do EJB");
		}
	}
	
	public void setLists() {
		//DB.setLists();
		allHistorics = DB.getAllHistorics();
		services = DB.getServices();

		for (int i = 0; i < services.size(); i++) {
			lastHistorics.add(allHistorics.get(i));
		}
	}

	public EstatisticasServicoDia getDayResponse(String day, long id, int response) {
		List<Requisicao> list = DB.getListDayRequest(response, id, day);
		EstatisticasServicoDia EstatisticaDoDia = DB.getEstatisticaDoDia(list.size(), id, day);
		EstatisticaDoDia.setRequisicoes(list);
		return EstatisticaDoDia;
	}

	
	public void setUrlParameter() {

		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		String projectId = paramMap.get("id");
		String projectResponse = paramMap.get("response");
		id = Integer.parseInt(projectId);
		date = paramMap.get("date");
		estatisticas = getDayResponse(date, id, Integer.parseInt(projectResponse));
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

}
