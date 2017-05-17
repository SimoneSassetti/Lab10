package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.AuthorIdMap;
import it.polito.tdp.porto.model.Paper;
import it.polito.tdp.porto.model.PaperIdMap;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public List<Author> listaAutoriDAO(AuthorIdMap map) {
		
		List<Author> listaAutori=new ArrayList<Author>();
		String sql="SELECT * FROM author ORDER BY lastname";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				Author a= map.get(rs.getInt("id"));
				if(a==null){
					a = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
					map.put(a);
				}
				listaAutori.add(a);
			}
			conn.close();
			return listaAutori;

		} catch (SQLException e) {
			throw new RuntimeException("Errore Db");
		}
	}

	public List<Paper> listaArticoliDAO() {
		
		List<Paper> listaArticoli=new ArrayList<Paper>();
		String sql = "SELECT * FROM paper";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				listaArticoli.add(paper);
			}
			conn.close();
			return listaArticoli;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<Author> getCoAutori(Author a, AuthorIdMap map){
		
		List<Author> listaCoAutori=new ArrayList<Author>();
		
		String sql="SELECT DISTINCT(author.id),author.lastname,author.firstname "+
					"FROM author, creator as c1, creator as c2 "+
					"WHERE c1.authorid=? AND c1.eprintid=c2.eprintid AND author.id=c2.authorid AND c2.authorid<>c1.authorid;";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a.getId());
			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				Author au= map.get(rs.getInt("id"));
				if(au==null){
					au = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
					map.put(au);
				}
				listaCoAutori.add(au);
			}
			
			conn.close();

			return listaCoAutori;

		} catch (SQLException e) {
			throw new RuntimeException("Errore Db");
		}
		
	}

	public List<Paper> creaListaArticoli(Author a, PaperIdMap map) {
		List<Paper> lista=new ArrayList<Paper>();
		
		String sql="SELECT paper.eprintid, paper.title,paper.issn,paper.publication,paper.`type`,paper.types "+
					"FROM paper,creator as c1 "+
					"WHERE c1.authorid=? and c1.eprintid=paper.eprintid";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a.getId());
			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				Paper au= map.get(rs.getInt("eprintid"));
				if(au==null){
					au = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
							rs.getString("publication"), rs.getString("type"), rs.getString("types"));
					map.put(au);
				}
				lista.add(au);
			}
			
			conn.close();

			return lista;

		} catch (SQLException e) {
			throw new RuntimeException("Errore Db");
		}
	}
	

}