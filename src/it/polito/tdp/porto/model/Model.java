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
	//private List<Paper> listaArticoli;
	private Graph<Author, DefaultEdge> grafo;
	private AuthorIdMap mapAutori;
	private PaperIdMap mapPaper;
	
	public Model(){
		this.mapAutori=new AuthorIdMap();
		this.mapPaper=new PaperIdMap();
	}
	
	public List<Author> getAutori(){
		PortoDAO dao=new PortoDAO();
		if(listaAutori==null){
			listaAutori=dao.listaAutoriDAO(mapAutori);
		}
		return listaAutori;
	}
	
//	private List<Paper> getArticoli(){
//		PortoDAO dao=new PortoDAO();
//		if(listaArticoli==null){
//			listaArticoli=dao.listaArticoliDAO();
//		}
//		return listaArticoli;
//	}
	
	public Graph<Author, DefaultEdge> getGrafo(){
		if(grafo==null){
			this.creaGrafo();
		}
		return grafo;
	}
	
	private List<Author> coAutori(Author a){
		PortoDAO dao=new PortoDAO();
		List<Author> coAutori=new ArrayList<Author>();
		coAutori=dao.getCoAutori(a, mapAutori);
		
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
	
	public void creaListaArticoliPerAutore() {
		PortoDAO dao=new PortoDAO();
		for(Author a: listaAutori){
			a.setListaArticoli(dao.creaListaArticoli(a, mapPaper));
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
		DijkstraShortestPath<Author,DefaultEdge> percorsoMinimo= new DijkstraShortestPath<Author,DefaultEdge>(grafo, a,b);
		
		List<DefaultEdge> archi=percorsoMinimo.getPathEdgeList();
		List<Paper> listaArticoli=new ArrayList<Paper>();
		
		for(DefaultEdge d: archi){
			boolean trovato=false;
			System.out.println(d);
			Author sorgente=grafo.getEdgeSource(d);
			Author dest=grafo.getEdgeTarget(d);
			for(int i=0; i<sorgente.getListaArticoli().size() && !trovato; i++){
				for(int j=0; j<dest.getListaArticoli().size() && !trovato; j++){
					if(sorgente.getListaArticoli().get(i).equals(dest.getListaArticoli().get(j))){
						listaArticoli.add(sorgente.getListaArticoli().get(i));
						trovato=true;
					}
				}
				
			}
		}
		return listaArticoli;
	}
	
}
