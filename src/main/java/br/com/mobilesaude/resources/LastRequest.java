package br.com.mobilesaude.resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "lastRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class LastRequest {

	// BD
	private String nome;
	private String resposta;
	private Calendar hora;
	private int response;

	// Not in BD
	private String img;
	private String hora2; // hh:mm
	private String hora3; // hh:mm:ss

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public Calendar getHora() {
		return hora;
	}

	public void setHora(Calendar hora) {
		this.hora = hora;
	}

	public String getImg() {
		if (response == 200) {
			return "status0.gif";
		}
		if (response == 404) {
			return "status3.gif";
		}
		if (response == 500) {
			return "status3.gif";
		}
		if (response == 503) {
			return "status3.gif";
		}
		if (response == 504) {
			return "status3.gif";
		}
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getResponse() {
		return response;
	}

	public void setResponse(int response) {
		this.response = response;
	}

	public String getHora2() {
		hora2 = dataToString2(hora);
		return hora2;
	}

	public void setHora2(String hora2) {
		this.hora2 = hora2;
	}

	public String getHora3() {
		hora3 = dataToString3(hora);
		return hora3;
	}

	public void setHora3(String hora3) {
		this.hora3 = hora3;
	}

	public String dataToString2(Calendar c) {
		DateFormat df = new SimpleDateFormat("hh:mm");
		String reportDate = df.format(c.getTime());
		return reportDate;
	}

	public String dataToString3(Calendar c) {
		DateFormat df = new SimpleDateFormat("hh:mm:ss");
		String reportDate = df.format(c.getTime());
		return reportDate;
	}
}
