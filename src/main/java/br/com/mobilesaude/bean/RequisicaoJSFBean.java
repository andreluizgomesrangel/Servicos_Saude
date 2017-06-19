package br.com.mobilesaude.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import br.com.mobilesaude.resources.EstatisticasServicoDia;
import br.com.mobilesaude.resources.Requisicao;
import br.com.mobilesaude.resources.Service;
import br.com.mobilesaude.resources.Status_History;

import br.com.mobilesaude.dao.RequisicaoDao;
import br.com.mobilesaude.dao.ServiceDao;

@ManagedBean
@ViewScoped
public class RequisicaoJSFBean {

	List<Requisicao> newHistorics = new ArrayList<Requisicao>();
	List<Requisicao> lastHistorics = new ArrayList<Requisicao>();
	List<Requisicao> allHistorics = new ArrayList<Requisicao>();
	List<Service> services = new ArrayList<Service>();
	List<Service> problems = new ArrayList<Service>();
	List<Requisicao> lastHour = new ArrayList<Requisicao>();
	List<Status_History> status = new ArrayList<Status_History>();
	List<Requisicao> serviceHistoric = new ArrayList<Requisicao>();
	long serviceId = 7;
	int qtdDias = 10;
	String[] dias = new String[qtdDias];
	String[] diasUS = new String[qtdDias];
	String[][] urlParam;
	int qtdServices;
	int parametrourl;

	long id;
	String inicio = new String();
	String proximo = new String();
	String anterior = new String();
	private int offset;
	private Calendar primeiroDia;
	private Calendar HOJE;
	private Calendar proximoDia;
	private Calendar antDia;

	private String hoje = new String();
	private String url = new String();
	private String urlProx = new String();
	private String urlAnt = new String();

	@EJB
	RequisicaoDao rdao;
	@EJB
	ServiceDao sdao;

	public RequisicaoJSFBean() {

		System.out.println(">>>>>>>>>>>>>>>>>>> RequisicaoJSFBean");
		
		offset = 0;
		primeiroDia = Calendar.getInstance();
		HOJE = primeiroDia;
		inicio = new String(dataToStringBR1(primeiroDia));

		setUrlParameter(); // obter offset a partir da url // vera o offset ou

		setDias(qtdDias); // ( v )
		setLists();
		qtdServices = services.size();
		getDays();
		setServicesInRequests();
		// setServicesInAlert();

		url = new String("primeiro dia: " + inicio + " causado pelo offset na url services.xhtml?data=" + offset);
		urlProx = new String("prox dia: " + proximo + " causado pelo offset na url services.xhtml?data=10");
		urlAnt = new String("anterior dia: " + anterior + " causado pelo offset na url services.xhtml?data=-10");
	}

	public void setdiainicial() {
		if (offset > 0) {
			for (int i = 0; i < offset; i++)
				primeiroDia.add(Calendar.DATE, -1);
		}
		if (offset < 0) {
			for (int i = offset; i > 0; i--)
				primeiroDia.add(Calendar.DATE, 1);
		}
		inicio = dataToStringBR1(primeiroDia);
	}

	public void setUrlParameter() {
		// System.out.println(">>>>> setando data inicial... ");
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		String projectId = paramMap.get("inicio");
		if (projectId == null) {
			offset = 0;
			setdiainicial();
		} else if (projectId.length() < 6) {
			offset = Integer.parseInt(projectId);
			setdiainicial();
		} else {
			inicio = paramMap.get("inicio");
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Calendar cal = Calendar.getInstance();
				cal.setTime(sdf.parse(inicio));

				primeiroDia = cal;

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		String diadehoje = dataToStringBR1(HOJE);
		proximoDia = setProxDia(inicio, 10, diadehoje);

		proximo = dataToStringBR1(proximoDia);
		antDia = setProxDia(inicio, -10, diadehoje);

		anterior = dataToStringBR1(antDia);

	}

	public Calendar setProxDia(String Inicial, int offset, String diadehoje) {

		Calendar hoje = Calendar.getInstance();
		Calendar inicial = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			inicial.setTime(sdf.parse(Inicial));
			hoje.setTime(sdf.parse(diadehoje));

			// System.out.println(">>>>>>>>>>>>>>>>>>>> hoje e inicial: " +
			// hoje.toString() + " " + inicial.toString());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println(">>>>> setando o proximo dia...");
		if (offset > 0) {
			for (int i = 0; i < offset; i++)
				inicial.add(Calendar.DATE, -1);
		}
		if (offset < 0) {
			if (inicial.compareTo(hoje) == 0)
				return inicial;
			for (int i = offset; i < 0; i++)
				if (inicial.compareTo(hoje) == 0)
					return inicial;
			inicial.add(Calendar.DATE, 1);
		}

		return inicial;

	}

	public void addOffset() {
		offset += 10;
	}

	public void asubOffset() {
		offset -= 10;
	}

	public String[] getDiasUS() {
		return diasUS;
	}

	public void setDiasUS(String[] diasUS) {
		this.diasUS = diasUS;
	}

	public String[][] getUrlParam() {
		return urlParam;
	}

	public void setUrlParam(String[][] urlParam) {
		this.urlParam = urlParam;
	}

	public void setLists() {

		// CRequisicao ch = new CRequisicao();
		// CService cs = new CService();

		// allHistorics = ch.getList();
		allHistorics = rdao.getLista();
		// services = cs.getlistSortById();
		services = sdao.getLista();

		if (services.size() != 0) {
			for (int i = 0; i < services.size(); i++) {
				lastHistorics.add(allHistorics.get(i));
			}

			urlParam = new String[qtdDias][services.size()];
			for (int i = 0; i < services.size(); i++) {
				for (int j = 0; j < qtdDias; j++) {
					urlParam[j][i] = dataToString(diasUS[j], services.get(i).getId());
					// System.out.print(" "+urlParam[j][i]);
				} // System.out.println();
			}
		}

	}

	/*
	 * public void setServicesInAlert() {
	 * 
	 * for (Service s : services) { if (s.isAlert() == true) { problems.add(s);
	 * }
	 * 
	 * }
	 * 
	 * }
	 */

	public void setServicesInRequests() {

		for (Requisicao h : allHistorics) {
			Service s = new Service();
			s = findService(h.getIdService(), services);
		}

	}

	public EstatisticasServicoDia getDay(String day, long id) {
		List<Requisicao> list = rdao.getDay(day, id);
		EstatisticasServicoDia EstatisticaDoDia = rdao.getEstatisticas(day, id, list.size());
		EstatisticaDoDia.setRequisicoes(list);
		return EstatisticaDoDia;
	}

	public void setServiceHistoric(long serviceId, String day) {
		serviceHistoric = getDay(day, serviceId).getRequisicoes();
	}

	// obter o status de cada service de no periodo de qtdDias
	public void getDays() {
		for (int i = 0; i < qtdServices; i++) {
			Status_History s = new Status_History(services.get(i).getId(), qtdDias, inicio);
			status.add(s);
		}
	}

	public void setDias(int qtdDias) {

		Calendar d = Calendar.getInstance();
		d = primeiroDia;
		for (int i = 0; i < qtdDias; i++) {
			dias[i] = dataToStringBR(d);
			diasUS[i] = dataToString(d);
			d.add(Calendar.DATE, -1);
		}

	}

	public String dataToString(String c, long id) {
		return "serviceday.xhtml?id=" + id + "&date=" + c;
	}

	public String dataToString(Calendar c, long id) {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String reportDate = df.format(c.getTime());
		return "serviceday.xhtml?id=" + id + "&date=" + reportDate;
	}

	public String dataToString2(Calendar c) {
		DateFormat df = new SimpleDateFormat("hh:mm:ss");
		String reportDate = df.format(c.getTime());
		return reportDate;
	}

	public String dataToString(Calendar c) {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String reportDate = df.format(c.getTime());
		return reportDate;
	}

	public String dataToStringBR1(Calendar c) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String reportDate = df.format(c.getTime());
		return reportDate;
	}

	public String dataToStringBR(Calendar c) {
		DateFormat df = new SimpleDateFormat("dd MMM");
		String reportDate = df.format(c.getTime());
		return reportDate;
	}

	public boolean validate(Date now, Date start, Date end) {
		if (now == null || start == null || end == null)
			return false;
		return now.after(start) && now.before(end);
	}

	public Service findService(long id, List<Service> list) {
		for (Service s : list) {
			if (id == s.getId())
				return s;
		}
		return null;
	}

	public void refresh() {
		FacesContext context = FacesContext.getCurrentInstance();

		Application application = context.getApplication();
		ViewHandler viewHandler = application.getViewHandler();
		UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
		context.setViewRoot(viewRoot);
		context.renderResponse();
	}

	public List<Requisicao> getNewHistorics() {
		return newHistorics;
	}

	public void setNewHistorics(List<Requisicao> newHistorics) {
		this.newHistorics = newHistorics;
	}

	public List<Requisicao> getLastHistorics() {
		return lastHistorics;
	}

	public void setLastHistorics(List<Requisicao> lastHistorics) {
		this.lastHistorics = lastHistorics;
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

	public List<Service> getProblems() {
		return problems;
	}

	public void setProblems(List<Service> problems) {
		this.problems = problems;
	}

	public List<Requisicao> getLastHour() {
		return lastHour;
	}

	public void setLastHour(List<Requisicao> lastHour) {
		this.lastHour = lastHour;
	}

	public List<Status_History> getStatus() {
		return status;
	}

	public void setStatus(List<Status_History> status) {
		this.status = status;
	}

	public int getQtdDias() {
		return qtdDias;
	}

	public void setQtdDias(int qtdDias) {
		this.qtdDias = qtdDias;
	}

	public String[] getDias() {
		return dias;
	}

	public void setDias(String[] dias) {
		this.dias = dias;
	}

	public int getQtdServices() {
		return qtdServices;
	}

	public void setQtdServices(int qtdServices) {
		this.qtdServices = qtdServices;
	}

	public List<Requisicao> getServiceHistoric() {
		return serviceHistoric;
	}

	public void setServiceHistoric(List<Requisicao> serviceHistoric) {
		this.serviceHistoric = serviceHistoric;
	}

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}

	public int getParametrourl() {
		return parametrourl;
	}

	public void setParametrourl(int parametrourl) {
		this.parametrourl = parametrourl;
	}

	public String getHoje() {
		return hoje;
	}

	public void setHoje(String hoje) {
		this.hoje = hoje;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public Calendar getPrimeiroDia() {
		return primeiroDia;
	}

	public void setPrimeiroDia(Calendar primeiroDia) {
		this.primeiroDia = primeiroDia;
	}

	public String getProximo() {
		return proximo;
	}

	public void setProximo(String proximo) {
		this.proximo = proximo;
	}

	public Calendar getProximoDia() {
		return proximoDia;
	}

	public void setProximoDia(Calendar proximoDia) {
		this.proximoDia = proximoDia;
	}

	public String getUrlProx() {
		return urlProx;
	}

	public void setUrlProx(String urlProx) {
		this.urlProx = urlProx;
	}

	public Calendar getAntDia() {
		return antDia;
	}

	public void setAntDia(Calendar antDia) {
		this.antDia = antDia;
	}

	public String getUrlAnt() {
		return urlAnt;
	}

	public void setUrlAnt(String urlAnt) {
		this.urlAnt = urlAnt;
	}

	public String getAnterior() {
		return anterior;
	}

	public void setAnterior(String anterior) {
		this.anterior = anterior;
	}

	public Calendar getHOJE() {
		return HOJE;
	}

	public void setHOJE(Calendar hOJE) {
		HOJE = hOJE;
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
