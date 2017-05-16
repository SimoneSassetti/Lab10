package it.polito.tdp.porto.model;

import java.util.*;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private List<Author> listaAutori;
	
	public Model(){
		
	}
	
	public List<Author> getAutori(){
		PortoDAO dao=new PortoDAO();
		if(listaAutori==null){
			listaAutori=dao.listaAutoriDAO();
		}
		return listaAutori;
	}
	
}
