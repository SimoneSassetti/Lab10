package it.polito.tdp.porto.model;

import org.jgrapht.graph.DefaultEdge;

public class Arco extends DefaultEdge {
	
	private Paper articolo;

	public Arco(Paper articolo) {
		this.articolo = articolo;
	}

	public Paper getArticolo() {
		return articolo;
	}

	public void setArticolo(Paper articolo) {
		this.articolo = articolo;
	}
	
	
}
