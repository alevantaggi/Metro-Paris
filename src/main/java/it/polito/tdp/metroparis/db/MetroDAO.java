package it.polito.tdp.metroparis.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metroparis.model.Connessione;
import it.polito.tdp.metroparis.model.CoppieFermate;
import it.polito.tdp.metroparis.model.Fermata;
import it.polito.tdp.metroparis.model.Linea;

public class MetroDAO {

	public List<Fermata> readFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_fermata"), rs.getString("nome"),
						new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}

	public List<Linea> readLinee() {
		final String sql = "SELECT id_linea, nome, velocita, intervallo FROM linea ORDER BY nome ASC";

		List<Linea> linee = new ArrayList<Linea>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Linea f = new Linea(rs.getInt("id_linea"), rs.getString("nome"), rs.getDouble("velocita"),
						rs.getDouble("intervallo"));
				linee.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return linee;
	}

	public Boolean isConnesse(Fermata partenza, Fermata arrivo) {
		String sql= "SELECT COUNT(*) AS c FROM connessione WHERE id_stazP=? AND id_stazA=?";
		
		try {
			Connection conn= DBConnect.getConnection();
			
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, partenza.getIdFermata());
			st.setInt(2, arrivo.getIdFermata());
			
			ResultSet rs= st.executeQuery();
			
			rs.first();
			
			int c= rs.getInt("c");
			conn.close();
			
			return (c!=0);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public List<Fermata> trovaCollegate(Fermata partenza) {
		List<Fermata> risultato= new ArrayList<>();
		String sql= "SELECT f.id_fermata, f.nome, f.coordX, f.coordY FROM connessione c, fermata f WHERE c.id_stazA= f.id_fermata AND c.id_stazP=?";
		
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, partenza.getIdFermata());

			ResultSet rs= st.executeQuery();
			
			while (rs.next()) {
				Fermata f= new Fermata(rs.getInt("id_fermata"), rs.getString("nome"),
						new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				risultato.add(f);
			}
			conn.close();
			return risultato;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public List<Fermata> trovaIdCollegate(Fermata partenza, Map<Integer, Fermata> fermateIdMap) {
		List<Fermata> risultato= new ArrayList<>();
		String sql= "SELECT DISTINCT c.id_stazA FROM connessione c WHERE c.id_stazP=?";
		
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setInt(1, partenza.getIdFermata());

			ResultSet rs= st.executeQuery();
			
			while (rs.next()) {
				int id= rs.getInt("id_stazA");
				risultato.add(fermateIdMap.get(id));	
			}
			
			conn.close();
			return risultato;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<CoppieFermate> getAllCoppie(Map<Integer, Fermata> fermateIdMap){
		List<CoppieFermate> risultato= new ArrayList<>();
		String sql= "SELECT distinct c.id_stazA, c.id_stazP FROM connessione c";
		
		try {
			Connection conn= DBConnect.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);

			ResultSet rs= st.executeQuery();
			
			while (rs.next()) {
				
				CoppieFermate cf= new CoppieFermate(fermateIdMap.get(rs.getInt("id_stazP")),fermateIdMap.get(rs.getInt("id_stazA")));
				risultato.add(cf);
				
			}
			conn.close();
			return risultato;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}		
	}

}
