package br.com.mobilesaude.temporizador;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

import br.com.mobilesaude.service.VerificaStatusService;

@Stateless
public class Temporizador {

	@EJB
	VerificaStatusService verificaStatus;

	@Schedule(second = "0", minute = "*", hour = "*", persistent = false)
	public void doWork() {
		System.out.println("DO WORK");
		verificaStatus.verificarStatus();
	}

}
