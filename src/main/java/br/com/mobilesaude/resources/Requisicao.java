package br.com.mobilesaude.resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.mobilesaude.dao.ServiceDao;

@XmlRootElement(name = "requisicao")
@XmlAccessorType(XmlAccessType.FIELD)
public class Requisicao {

	@Id
	@GeneratedValue
	private long id;
	private long idService;
	private Date time;
	private String details;
	private int response;
	private long requisicao;

	private String dataBR;
	private String timeString;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdService() {
		return idService;
	}

	public void setIdService(long idService) {
		this.idService = idService;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date c) {
		this.time = c;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getResponse() {
		return response;
	}

	public void setResponse(int response) {
		this.response = response;
	}

	public long getRequisicao() {
		return requisicao;
	}

	public void setRequisicao(long requisicao) {
		this.requisicao = requisicao;
	}

	public String dataToString(Date d) {
		DateFormat df = new SimpleDateFormat("hh:mm:ss a");
		String reportDate = df.format(d.getTime());
		return reportDate;
	}
	
	public String getTimeString() {
		this.timeString = dataToString(time);
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}
	
	public String dataBR(Date d) {
		DateFormat df = new SimpleDateFormat("dd/MM/yy");
		String reportDate = df.format(d.getTime());
		return reportDate;
	}

	public String getDataBR() {
		this.dataBR = dataBR(time);
		return dataBR;
	}

	public void setDataBR(String dataBR) {
		this.dataBR = dataBR;
	}

}
