package br.com.mobilesaude.temporizador;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
//import br.com.ythalo.session.AutomaticTimer;
import javax.xml.bind.JAXBException;

import br.com.mobilesaude.dao.ServiceDao;
import br.com.mobilesaude.resources.Service;
import br.com.mobilesaude.service.VerificaStatusService;

@Stateless
public class Temporizador {

	@EJB
	VerificaStatusService verificaStatus;

	@Schedule(minute = "1", persistent=false)
	public void doWork() {
		verificaStatus.verificarStatus();
	}

}
