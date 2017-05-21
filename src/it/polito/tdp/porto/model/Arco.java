package it.polito.tdp.porto.model;

import java.util.List;

import org.jgrapht.graph.DefaultEdge;

public class Arco extends DefaultEdge {
	
	private List<Paper> listaArticoli;

	public Arco(List<Paper> listaArticoli) {
		super();
		this.listaArticoli = listaArticoli;
	}

	public Paper getArticolo() {
		return listaArticoli.get(0);
	}
	public void setListaArticoli(List<Paper> listaArticoli) {
		this.listaArticoli = listaArticoli;
	}	
}
