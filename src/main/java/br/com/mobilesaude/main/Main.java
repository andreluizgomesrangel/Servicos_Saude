package br.com.mobilesaude.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.mobilesaude.connection.ConnectionFactory;
import br.com.mobilesaude.dao.RequisicaoDao;
import br.com.mobilesaude.dao.ServiceDao;
import br.com.mobilesaude.resources.LastRequest;
import br.com.mobilesaude.resources.Requisicao;
import br.com.mobilesaude.resources.Service;
import br.com.mobilesaude.resources.Status_History;
import sun.util.resources.cldr.CalendarData;

public class Main {
	
	public static void main(String[] args) throws SQLException{

		
		ServiceDao sdao = new ServiceDao();
		List<Service> slist = sdao.getLista();
		System.out.println(slist.size());
		
		//RequisicaoDao rdao = new RequisicaoDao();
		
		
		
	}
}
