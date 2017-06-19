package br.com.mobilesaude.ws;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.mobilesaude.dao.ServiceDao;
import br.com.mobilesaude.resources.Service;

@SuppressWarnings({ "unused" })
@Path("/ws/servico/service")
public class ServiceWs {

	@EJB
	ServiceDao sdao;

	@GET
	@Path("/teste")
	@Produces(MediaType.APPLICATION_XML)
	public Service teste() {
		Service s = new Service();
		return s;
	}

	@GET
	@Path("/getlistById")
	@Produces(MediaType.APPLICATION_XML)
	public List<Service> getList() {
		List<Service> list = sdao.getLista();

		if (list == null) {
			return null;
		}
		if (list.get(0) == null) {
			return null;
		}
		return list;
	}

	@POST
	@Path("/insert")
	@Produces(MediaType.APPLICATION_XML)
	public List<Service> insert(@FormParam("name") String name, @FormParam("url") String url,
			@FormParam("requestType") String requestType, @FormParam("param") String param) {
		List<Service> list = sdao.getLista();
		Service s = new Service();
		s.setName(name);
		s.setUrl(url);
		s.setParam(param);
		s.setRequestType(requestType);

		if (list.size() == 0) {
			s.setId(1);
			sdao.add(s);
			return sdao.getLista();

		}

		long lastId = list.get(list.size() - 1).getId();
		s.setId(lastId + 1);
		sdao.add(s);
		return sdao.getLista();
	}

	@POST
	@Path("/update")
	@Produces(MediaType.APPLICATION_XML)
	public List<Service> insert(@FormParam("id") long id, @FormParam("name") String name, @FormParam("url") String url,
			@FormParam("requestType") String requestType, @FormParam("param") String param) {
		List<Service> list = sdao.getLista();

		Service s = new Service();

		s.setId(id);

		if (list.size() == 0) {
			return null;

		}

		for (Service x : list) {
			System.out.println(x.getId() + " " + id);
			if (x.getId() == id) {
				x.setName(name);
				x.setUrl(url);
				x.setParam(param);
				x.setRequestType(requestType);
				sdao.update(x);
			}
		}

		return sdao.getLista();
	}

}
