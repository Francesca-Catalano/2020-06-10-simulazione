package it.polito.tdp.imdb.model;

public class Adiacenza {
	
	
	public Actor a1;
	
	public Actor a2;
	
	public int peso;

	public Adiacenza(Actor a1, Actor a2, int peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}

	public Actor getA1() {
		return a1;
	}

	public Actor getA2() {
		return a2;
	}

	public int getPeso() {
		return peso;
	}
	

}
