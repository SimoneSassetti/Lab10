package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Paper;

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

	public List<Author> listaAutoriDAO() {
		
		List<Author> listaAutori=new ArrayList<Author>();
		String sql="SELECT * FROM author ORDER BY lastname";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				listaAutori.add(autore);
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
	
	public List<Author> getCoAutori(Author a){
		
		List<Author> listaCoAutori=new ArrayList<Author>();
		
		String sql="SELECT DISTINCT(author.id),author.lastname,author.firstname "+
					"FROM author, creator as c1, creator as c2 "+
					"WHERE c1.authorid=? AND c1.eprintid=c2.eprintid AND author.id=c2.authorid AND c2.authorid<>c1.authorid ORDER BY author.lastname;";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, a.getId());
			ResultSet rs = st.executeQuery();

			while(rs.next()) {
				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				listaCoAutori.add(autore);
			}
			
			conn.close();

			return listaCoAutori;

		} catch (SQLException e) {
			throw new RuntimeException("Errore Db");
		}
		
	}

	public Paper getArticoloInComune(Author sorgente, Author dest) {
		
		String sql="SELECT * "+
					"FROM paper "+
					"WHERE paper.eprintid IN (SELECT c1.eprintid FROM creator as c1, creator as c2 "+
											"WHERE c1.authorid=? and c2.authorid=? AND c1.eprintid=c2.eprintid);";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, sorgente.getId());
			st.setInt(2, dest.getId());
			ResultSet rs = st.executeQuery();
		
			Paper paper=null;
			if(rs.next()) {
				paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
			}
			
			conn.close();

			return paper;

		} catch (SQLException e) {
			throw new RuntimeException("Errore Db");
		}
		
	}
}