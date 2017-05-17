package it.polito.tdp.porto.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private List<Author> listaAutori;
	private List<Paper> listaArticoli;
	private Graph<Author, DefaultEdge> grafo;
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
	
	public Graph<Author, DefaultEdge> getGrafo(){
		if(grafo==null){
			this.creaGrafo();
		}
		return grafo;
	}
	
	private List<Author> coAutori(Author a){
		PortoDAO dao=new PortoDAO();
		List<Author> coAutori=new ArrayList<Author>();
		coAutori=dao.getCoAutori(a);
		
		return coAutori;
	}
	
	private void creaGrafo(){
		grafo=new SimpleGraph<Author, DefaultEdge>(DefaultEdge.class);
		
		Graphs.addAllVertices(grafo, listaAutori);
		
		for(Author a: listaAutori){
			for(Author co: this.coAutori(a)){
				grafo.addEdge(a, co);
			}
		}
	}
	
	public List<Author> getCoAutoriGrafo(Author a){
		List<Author> lista=new ArrayList<Author>();
		
		lista=Graphs.neighborListOf(grafo, a);
		return lista;
	}
	
}
