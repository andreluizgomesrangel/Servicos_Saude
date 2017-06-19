package br.com.mobilesaude.temporizador;

import javax.ejb.EJB;

import br.com.mobilesaude.dao.RequisicaoDao;
import br.com.mobilesaude.dao.ServiceDao;
import br.com.mobilesaude.resources.Requisicoes;
import br.com.mobilesaude.resources.Service;

public class Pipeline implements Runnable {
	Service s;
	Requisicoes clienteRequisicao;

	public Pipeline(Service service, RequisicaoDao requisicaoDao) {
		this.s = service;
		this.clienteRequisicao = new Requisicoes(requisicaoDao);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//System.out.println(">>>> run");
		//System.out.println("executar pipeline service: " + s.getId());
		clienteRequisicao.newRequest(s);

	}

	public Service getS() {
		//System.out.println(">>>> getS");
		return s;
	}

	public void setS(Service s) {
		//System.out.println(">>>> SET service: " + s.getName());
		this.s = s;
	}

	public Requisicoes getClienteRequisicao() {
		//System.out.println(">>>> getClienteRequisicao()");
		return clienteRequisicao;
	}

	public void setClienteRequisicao(Requisicoes clienteRequisicao) {
		//System.out.println(">>>> setClienteRequisicao");
		this.clienteRequisicao = clienteRequisicao;
	}

}
