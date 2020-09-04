package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	private ImdbDAO dao;
	private Graph<Actor, DefaultWeightedEdge> graph;
	private Map<Integer, Actor> map;
	
	

	public Model() {
		this.dao = new ImdbDAO();
		map= new HashMap<Integer, Actor>();
		this.dao.listAllActors(map);
		
	}

	public List<String> listAllGenre(){
		return this.dao.listAllGenre();
	}
	
	public void creaGrafo(String genere)
	{
		this.graph= new SimpleWeightedGraph<Actor, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		if(dao.listVertex(genere, map)==null)
		{
			System.out.print("Errore lettura vertex!\n");
			return;
		}
		Graphs.addAllVertices(graph, dao.listVertex(genere, map));
		
		for(Adiacenza a : this.dao.listAdiacenza(genere, map))
		{
			Graphs.addEdge(this.graph, a.getA1(), a.getA2(), a.getPeso());
		}
	}
	
	public List<Actor> simili(Actor a)
	{
		List<Actor> list = new ArrayList<Actor>();
		BreadthFirstIterator<Actor, DefaultWeightedEdge> bfi= new BreadthFirstIterator<Actor, DefaultWeightedEdge>(this.graph, a);
		
		while(bfi.hasNext())
		{
			list.add(bfi.next());
		}
		Collections.sort(list);
		return list;
	}
	
	public Set<DefaultWeightedEdge> E()
	{
		return this.graph.edgeSet();
	}
	public List<Actor> V()
	{
		List<Actor> list = new ArrayList<Actor>(this.graph.vertexSet());
		Collections.sort(list);
		return list;
	}

}
