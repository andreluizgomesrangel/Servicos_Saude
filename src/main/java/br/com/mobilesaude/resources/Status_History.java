package br.com.mobilesaude.resources;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.mobilesaude.dao.RequisicaoDao;
import br.com.mobilesaude.dao.ServiceDao;
import br.com.mobilesaude.resources.Requisicao;
import br.com.mobilesaude.resources.Service;

@XmlRootElement(name = "status_history")
@XmlAccessorType(XmlAccessType.FIELD)
public class Status_History {

	// status de 1 servico
	Service service = new Service();
	int[] dia;
	private Calendar today = Calendar.getInstance();
	private String todayString;

	long id;

	private String[] img;

	private String hoje = dataToString(today);
	private Calendar diaInicial = today;

	private String primeiroDia;
	private String url = new String();

	@EJB
	RequisicaoDao rdao;
	@EJB
	ServiceDao sdao;

	public Status_History() {

	}

	public Status_History(long id, int n, String primeiroDia) {
		this.primeiroDia = primeiroDia;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.diaInicial.setTime(sdf.parse(primeiroDia));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.id = id;
		img = new String[n];
		dia = new int[n];
		verifDias(id, n);

		List<Service> services = new ArrayList<Service>();
		services = sdao.getLista();
		service = findService(id, services);

		url = new String("services.xhtml?data=" + hoje);
	}

	Service findService(long id, List<Service> list) {
		for (Service s : list) {
			if (s.getId() == id) {
				return s;
			}
		}
		return service;
	}

	/*
	 * Verificar getOneDay para o servico de id por n dias a partir do
	 * diaInicial
	 */
	public void verifDias(long id, int n) {

		Calendar diax = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			diax.setTime(sdf.parse(primeiroDia));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < n; i++) {

			String dayString = dataToString(diax);

			int v = getOneDay(id, dayString);
			dia[i] = v;
			img[i] = new String();
			if (v == 1) {
				img[i] = "status0.gif";
			}
			if (v == -1) {
				img[i] = "status2.gif";
			}
			if (v == 0) {
				img[i] = "info.gif";
			}
			diax.add(Calendar.DATE, -1);
		}
	}

	public String dataToStringBR1(Calendar c) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String reportDate = df.format(c.getTime());
		return reportDate;
	}

	public String dataToStringBR2(Calendar c) {
		DateFormat df = new SimpleDateFormat("dd MMM");
		String reportDate = df.format(c.getTime());
		return reportDate;
	}

	public String dataToString(Calendar c) {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String reportDate = df.format(c.getTime());
		return reportDate;
	}

	public String dataToStringBR(Calendar c) {
		DateFormat df = new SimpleDateFormat("MMM dd");
		String reportDate = df.format(c.getTime());
		return reportDate;
	}

	public EstatisticasServicoDia getDay(String day, long id) {
		List<Requisicao> list = rdao.getDay(day, id);
		EstatisticasServicoDia EstatisticaDoDia = rdao.getEstatisticas(day, id, list.size());
		EstatisticaDoDia.setRequisicoes(list);
		return EstatisticaDoDia;
	}

	// obter verificacao (-1/1/0) para requisicoes (erro/sem erro/sem
	// requisicao) de um dia
	public int getOneDay(long id, String day) {
		List<Requisicao> reqDia = new ArrayList<Requisicao>();
		RequisicaoDao cr = new RequisicaoDao();
		// reqDia = cr.getDay(id + "", day).getRequisicoes();
		reqDia = getDay(day, id).getRequisicoes();

		if (reqDia.size() > 0) {
			for (Requisicao r : reqDia) {
				if (r.getResponse() != 200) {
					return -1;
				}
			}
			return 1;
		}

		return 0;
	}

	public int[] getDia() {
		return dia;
	}

	public void setDia(int[] dia) {
		this.dia = dia;
	}

	public String getTodayString() {
		return todayString;
	}

	public void setTodayString(String todayString) {
		this.todayString = todayString;
	}

	public Calendar getToday() {
		return today;
	}

	public void setToday(Calendar today) {
		this.today = today;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String[] getImg() {
		return img;
	}

	public void setImg(String[] img) {
		this.img = img;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getHoje() {
		return hoje;
	}

	public void setHoje(String hoje) {
		this.hoje = hoje;
	}

	public Calendar getDiaInicial() {
		return diaInicial;
	}

	public void setDiaInicial(Calendar diaInicial) {
		this.diaInicial = diaInicial;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPrimeiroDia() {
		return primeiroDia;
	}

	public void setPrimeiroDia(String primeiroDia) {
		this.primeiroDia = primeiroDia;
	}

}
