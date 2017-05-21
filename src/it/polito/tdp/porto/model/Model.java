package it.polito.tdp.porto.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private List<Author> listaAutori;
	private List<Paper> listaArticoli;
	private Graph<Author,Arco> grafo;
	
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
	
	public Graph<Author, Arco> getGrafo(){
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
		PortoDAO dao=new PortoDAO();
		grafo=new SimpleGraph<Author, Arco>(Arco.class);
		
		Graphs.addAllVertices(grafo, listaAutori);
		
		for(Author a: listaAutori){
			for(Author co: this.coAutori(a)){
				Arco arco=new Arco(dao.articoliInComune(a, co));
				grafo.addEdge(a, co,arco);
			}
		}
	}
	
	public List<Author> getCoAutoriGrafo(Author a){
		List<Author> lista=new ArrayList<Author>();
		lista=Graphs.neighborListOf(grafo, a);
		return lista;
	}
	
	public List<Author> getAutoriSecondatendina(Author a){
		List<Author> lista=new ArrayList<Author>();
		lista.addAll(listaAutori);
		lista.removeAll(Graphs.neighborListOf(grafo, a));
		lista.remove(a);
		return lista;
	}
	
	public List<Paper> getListaArticoli(Author a, Author b){
		DijkstraShortestPath<Author,Arco> percorsoMinimo= new DijkstraShortestPath<Author,Arco>(grafo, a,b);
		
		List<Arco> archi=percorsoMinimo.getPathEdgeList();
		List<Paper> listaArticoli=new ArrayList<Paper>();
		
		for(Arco d: archi){
			listaArticoli.add(d.getArticolo());
		}
		return listaArticoli;
	}
}
