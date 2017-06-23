package br.com.mobilesaude.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import br.com.mobilesaude.resources.Service;

@Stateless
public class ServiceDao {

	@Resource(mappedName = "java:/jdbc/WsHealthApp")
	private DataSource datasource;

	public void add(Service service) {
		String sql = "insert into service " + "(id,name,url,requestType,param)" + " values (?,?,?,?,?)";
		try {
			// prepared statement para inserção
			PreparedStatement stmt = datasource.getConnection().prepareStatement(sql);

			// seta os valores
			stmt.setLong(1, service.getId());
			stmt.setString(2, service.getName());
			stmt.setString(3, service.getUrl());
			stmt.setString(4, service.getRequestType());
			stmt.setString(5, service.getParam());

			// executa
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Service> getLista() {

		try {
			List<Service> services = new ArrayList<Service>();
			Connection connection = datasource.getConnection();
			PreparedStatement stmt = connection.prepareStatement("select * from service");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto Contato
				Service service = new Service();
				service.setId(rs.getLong("id"));
				service.setName(rs.getString("name"));
				service.setUrl(rs.getString("url"));
				service.setRequestType(rs.getString("requestType"));
				service.setParam(rs.getString("param"));

				// adicionando o objeto à lista
				services.add(service);
			}
			rs.close();
			stmt.close();
			connection.close();
			return services;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Service getService(long id) {
		try {
			PreparedStatement stmt = datasource.getConnection()
					.prepareStatement("select * from service where id=" + id);

			ResultSet rs = stmt.executeQuery();
			rs.next();

			// criando o objeto Contato
			Service service = new Service();
			service.setId(rs.getLong("id"));
			service.setName(rs.getString("name"));
			service.setUrl(rs.getString("url"));
			service.setRequestType(rs.getString("requestType"));
			service.setParam(rs.getString("param"));

			rs.close();
			stmt.close();
			return service;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void updateTime(long id, Timestamp time) {
		String sql = "update service set lastRequest=? " + "where id=?";
		try {
			PreparedStatement stmt = datasource.getConnection().prepareStatement(sql);

			stmt.setTimestamp(1, time);

			stmt.setLong(2, id);

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void update(Service service) {
		String sql = "update service set name=?, requestType=?, param=?, " + "url=? where id=?";
		try {
			PreparedStatement stmt = datasource.getConnection().prepareStatement(sql);
			stmt.setString(1, service.getName());
			stmt.setString(2, service.getRequestType());
			stmt.setString(3, service.getParam());
			stmt.setString(4, service.getUrl());
			stmt.setLong(5, service.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
