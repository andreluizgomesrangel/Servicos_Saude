package br.com.mobilesaude.bean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.mobilesaude.connection.DBConnection;
import br.com.mobilesaude.resources.LastRequest;

@ManagedBean
@RequestScoped
public class Bean {

	DBConnection DB;

	List<LastRequest> lastRequests = new ArrayList<LastRequest>();

	public Bean() {
		System.out.println(">>>>>>>>>>>>>>>>>>> Bean");
		DB = (DBConnection) lookup(null);
		DB.setLists();
		lastRequests = DB.getLastRequests();
	}

	public Object lookup(Class<?> clazz) {
        InitialContext context;
        try {
            context = new InitialContext();
            return context.lookup("java:global/Servicos_Saude/DBConnection");
        } catch (NamingException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro na busca do EJB");
        }
    }
	
	public List<LastRequest> getLastRequests() {
		return lastRequests;
	}

	public void setLastRequests(List<LastRequest> lastRequests) {
		this.lastRequests = lastRequests;
	}

}
