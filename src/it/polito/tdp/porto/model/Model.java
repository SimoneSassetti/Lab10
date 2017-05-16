package it.polito.tdp.porto.model;

import java.util.*;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private List<Author> listaAutori;
	private List<Paper> listaArticoli;
	
	public Model(){
		
	}
	
	public List<Author> getAutori(){
		PortoDAO dao=new PortoDAO();
		if(listaAutori==null){
			listaAutori=dao.listaAutoriDAO();
		}
		return listaAutori;
	}
	
	private List<Paper> getArticoli(){
		PortoDAO dao=new PortoDAO();
		if(listaArticoli==null){
			listaArticoli=dao.listaArticoliDAO();
		}
		return listaArticoli;
	}
	
	
	
}
