package it.polito.tdp.porto.model;

import java.util.*;

public class Author {

	private int id;
	private String lastname;
	private String firstname;
	
	private List<Paper> listaArticoli;
	
	public Author(int id, String lastname, String firstname) {
		super();
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		listaArticoli=new ArrayList<Paper>();
	}

	public void addArticolo(Paper p){
		listaArticoli.add(p);
	}
	public Paper cercaArticolo(Paper p){
		return null;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Override
	public String toString() {
		return lastname+" "+firstname;	
	}
}
