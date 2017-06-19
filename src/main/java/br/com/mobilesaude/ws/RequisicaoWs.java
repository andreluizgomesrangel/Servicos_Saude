package br.com.mobilesaude.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.mobilesaude.dao.RequisicaoDao;
import br.com.mobilesaude.dao.ServiceDao;
import br.com.mobilesaude.resources.EstatisticasServicoDia;
import br.com.mobilesaude.resources.LastRequest;
import br.com.mobilesaude.resources.Requisicao;
import br.com.mobilesaude.resources.Service;

/**
 * 
 * @author AndreLuiz
 *
 */

@SuppressWarnings({ "unused" })
@Path("/ws/servico/requisicao")
public class RequisicaoWs {

	@EJB
	RequisicaoDao hdao;

	@GET
	@Path("/teste")
	@Produces(MediaType.APPLICATION_XML)
	public Requisicao teste() {
		Requisicao h = new Requisicao();
		return h;
	}

	@GET
	@Path("/getList")
	@Produces(MediaType.APPLICATION_XML)
	public List<Requisicao> getList() {
		// RequisicaoDao hdao = new RequisicaoDao();
		List<Requisicao> list = hdao.getLista();

		if (list == null) {
			return null;
		}
		if (list.get(0) == null) {
			return null;
		}

		return list;
	}

	@GET
	@Path("/getLastOnes")
	@Produces(MediaType.APPLICATION_XML)
	public List<LastRequest> getLast() {
		// RequisicaoDao hdao = new RequisicaoDao();
		List<LastRequest> list = hdao.getLastRequests();

		if (list == null) {
			return null;
		}
		if (list.get(0) == null) {
			return null;
		}

		return list;
	}

	/**
	 * 
	 * @param idService
	 * @param response
	 * @return
	 */
	@POST
	@Path("/insert")
	@Produces(MediaType.APPLICATION_XML)
	public Requisicao insert(@FormParam("idService") long idService, @FormParam("response") int response) {
		Requisicao h = new Requisicao();
		h.setIdService(idService);
		h.setResponse(response);
		hdao.add(h);
		return h;
	}

	/**
	 * 
	 * @param day
	 * @param id
	 * @return
	 */
	@POST
	@Path("/getDay")
	@Produces(MediaType.APPLICATION_XML)
	public EstatisticasServicoDia getDay(@FormParam("day") String day, @FormParam("id") long id) {
		List<Requisicao> list = hdao.getDay(day, id);
		EstatisticasServicoDia EstatisticaDoDia = hdao.getEstatisticas(day, id, list.size());
		EstatisticaDoDia.setRequisicoes(list);

		return EstatisticaDoDia;

	}

	@POST
	@Path("/getDayResponse")
	@Produces(MediaType.APPLICATION_XML)
	public EstatisticasServicoDia getDayResponse(@FormParam("day") String day, @FormParam("id") long id,
			@FormParam("response") int response) {
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> GET DAY WS:
		// "+id+" "+day);
		List<Requisicao> list = hdao.getDayResponse(day, id, response);
		EstatisticasServicoDia EstatisticaDoDia = hdao.getEstatisticas(day, id, list.size());
		EstatisticaDoDia.setRequisicoes(list);

		// System.out.println(EstatisticaDoDia.toString());

		return EstatisticaDoDia;

	}

}
