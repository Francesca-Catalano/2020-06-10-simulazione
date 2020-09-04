package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Adiacenza;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public void listAllActors(Map<Integer, Actor> map){
		String sql = "SELECT * FROM actors";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				map.put(actor.getId(),actor);
			}
			conn.close();
			return;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> listAllDirectors(){
		String sql = "SELECT * FROM directors";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	public List<String> listAllGenre(){
		String sql = "select distinct genre " + 
				"from movies_genres ";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

			
				result.add(res.getString("genre"));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public List<Actor> listVertex(String genere,Map<Integer, Actor> map){
		String sql ="select distinct actor_id " + 
				"from movies_genres,movies,roles " + 
				"where roles.movie_id=movies_genres.movie_id " + 
				"and movies_genres.movie_id=movies.id " + 
				"and movies_genres.genre=? ";
		List<Actor> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, genere);
			ResultSet res = st.executeQuery();
			while (res.next()) {

			
				result.add(map.get(res.getInt("actor_id")));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public List<Adiacenza> listAdiacenza(String genere,Map<Integer, Actor> map){
		String sql ="select roles.actor_id , r.actor_id, count(distinct m.id) as tot " + 
				"from movies_genres,movies,roles,movies_genres as mg,movies as m,roles as r " + 
				"where roles.movie_id=movies_genres.movie_id " + 
				"and movies_genres.movie_id=movies.id " + 
				"and movies_genres.genre=? " + 
				"and r.movie_id=mg.movie_id " + 
				"and mg.movie_id=m.id " + 
				"and mg.genre=? " + 
				"and roles.movie_id=r.movie_id " + 
				"and roles.actor_id > r.actor_id " + 
				"group by roles.actor_id , r.actor_id ";
		List<Adiacenza> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, genere);
			st.setString(2, genere);
			ResultSet res = st.executeQuery();
			while (res.next()) {

			Actor a1=map.get(res.getInt("roles.actor_id"));
			Actor a2=map.get(res.getInt("r.actor_id"));
			int peso=res.getInt("tot");

			
				result.add(new Adiacenza(a1, a2, peso));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
}
