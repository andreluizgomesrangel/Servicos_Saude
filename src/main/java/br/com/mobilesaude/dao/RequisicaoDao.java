package br.com.mobilesaude.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import br.com.mobilesaude.connection.ConnectionFactory;
import br.com.mobilesaude.resources.EstatisticasServicoDia;
import br.com.mobilesaude.resources.LastRequest;
import br.com.mobilesaude.resources.Requisicao;
import br.com.mobilesaude.resources.Service;
import br.com.mobilesaude.resources.TipoDeResposta;

@Stateless
public class RequisicaoDao {

	@Resource(mappedName = "java:/jdbc/WsHealthApp")
	private DataSource datasource;

	@EJB
	ServiceDao sdao;

	public RequisicaoDao() {
	}

	public List<Requisicao> getLista() {

		StringBuilder sql = new StringBuilder();
		sql.append("select * from requisicao");

		try {
			List<Requisicao> historics = new ArrayList<Requisicao>();

			Connection connection = datasource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql.toString());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto Contato
				Requisicao historic = new Requisicao();
				historic.setId(rs.getLong("id"));

				historic.setIdService(rs.getLong("idService"));
				historic.setDetails(rs.getString("details"));
				historic.setResponse(rs.getInt("response"));
				historic.setRequisicao(rs.getLong("requisicao"));

				Timestamp timestamp = rs.getTimestamp("time");
				java.util.Date date = timestamp;

				historic.setTime(date);
				historics.add(historic);

			}
			rs.close();
			stmt.close();
			connection.close();
			return historics;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void add(Requisicao req) {
		String sql = "insert into requisicao " + "(idService,details,response,requisicao,time)" + " values (?,?,?,?,?)";
		// System.out.println(">>> insert dao");
		try {
			// prepared statement para inserção
			Connection connection = datasource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores
			stmt.setLong(1, req.getIdService());
			stmt.setString(2, req.getDetails());
			stmt.setInt(3, req.getResponse());
			stmt.setLong(4, req.getRequisicao());

			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			stmt.setTimestamp(5, timestamp);

			// executa
			stmt.executeUpdate();
			stmt.close();
			connection.close();

			// sdao.updateTime(req.getIdService(), timestamp);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Requisicao> getDay(String day, long id) {

		StringBuilder sql = new StringBuilder();
		sql.append("select * from requisicao WHERE idService='" + id);
		sql.append("' AND DATE(requisicao.time) = '" + day + "' ORDER BY id DESC");

		try {
			List<Requisicao> historics = new ArrayList<Requisicao>();
			Connection connection = datasource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql.toString());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Requisicao historic = new Requisicao();
				historic.setId(rs.getLong("id"));
				historic.setIdService(rs.getLong("idService"));
				historic.setDetails(rs.getString("details"));
				historic.setResponse(rs.getInt("response"));
				historic.setRequisicao(rs.getLong("requisicao"));

				Timestamp timestamp = rs.getTimestamp("time");
				java.util.Date date = timestamp; // You can just upcast.

				historic.setTime(date);
				historics.add(historic);
			}
			rs.close();
			stmt.close();
			connection.close();
			return historics;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Requisicao> getDayResponse(String day, long id, int response) {

		StringBuilder sql = new StringBuilder();
		sql.append("select * from requisicao WHERE idService='" + id);
		sql.append("' AND DATE(requisicao.time) = '" + day + "'  and response='" + response + "'  ORDER BY id DESC");

		try {
			List<Requisicao> historics = new ArrayList<Requisicao>();
			Connection connection = datasource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql.toString());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Requisicao historic = new Requisicao();
				historic.setId(rs.getLong("id"));
				historic.setIdService(rs.getLong("idService"));
				historic.setDetails(rs.getString("details"));
				historic.setResponse(rs.getInt("response"));
				historic.setRequisicao(rs.getLong("requisicao"));

				Timestamp timestamp = rs.getTimestamp("time");
				java.util.Date date = timestamp; // You can just upcast.

				historic.setTime(date);
				historics.add(historic);
			}
			rs.close();
			stmt.close();
			connection.close();
			return historics;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public EstatisticasServicoDia getEstatisticas(String day, long id, int qtdServicos) {
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> GET
		// ESTATISTICAS");
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT COUNT(ID) as QTD, RESPONSE, DETALHES FROM (SELECT id AS ID, time AS HORA, response AS RESPONSE, details as DETALHES FROM ");
		sql.append("ping.requisicao WHERE idService='" + id + "' AND DATE(requisicao.time)='" + day
				+ "' ORDER BY RESPONSE, requisicao.time DESC) tab GROUP BY ");
		sql.append("RESPONSE, DETALHES;");

		try {
			EstatisticasServicoDia estatistica = new EstatisticasServicoDia(id, day);
			Connection connection = datasource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql.toString());
			ResultSet rs = stmt.executeQuery();
			List<TipoDeResposta> Respostas = new ArrayList<TipoDeResposta>();

			while (rs.next()) {
				TipoDeResposta resposta = new TipoDeResposta(rs.getInt("QTD"), rs.getInt("RESPONSE"), qtdServicos,
						rs.getString("DETALHES"));
				Respostas.add(resposta);
			}
			rs.close();
			stmt.close();
			connection.close();
			estatistica.setRespostas(Respostas);
			estatistica.setDia(day);
			estatistica.setIdService(id);
			return estatistica;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<LastRequest> getLastRequests() {

		try {
			List<LastRequest> req = new ArrayList<LastRequest>();

			StringBuilder sql = new StringBuilder();
			sql.append(
					"SELECT GRP.ID AS ID, GRP.NOME AS NOME, GRP.TIPO AS TIPO , req.details AS RESPOSTA, req.time AS HORA, req.response AS RCODE ");
			sql.append("FROM ( ");
			sql.append(
					"SELECT service.id AS ID, service.name AS NOME, service.requestType AS TIPO, MAX(ping.requisicao.id) as idrequisicao FROM ping.service ");
			sql.append("INNER JOIN ping.requisicao ");
			sql.append("WHERE service.id = requisicao.idService  ");
			sql.append("GROUP BY ID, NOME ) GRP, ");
			sql.append("Requisicao req ");
			sql.append("where grp.idrequisicao = req.id ");

			Connection connection = datasource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql.toString());

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				LastRequest r = new LastRequest();
				r.setNome(rs.getString("NOME"));
				r.setResposta(rs.getString("RESPOSTA"));
				r.setResponse(rs.getInt("RCODE"));
				Timestamp timestamp = rs.getTimestamp("HORA");
				java.util.Date date = timestamp;
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				r.setHora(cal);
				req.add(r);
			}
			rs.close();
			stmt.close();
			connection.close();
			return req;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
