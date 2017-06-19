package br.com.mobilesaude.resources;

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

}
