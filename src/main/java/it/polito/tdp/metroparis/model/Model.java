package it.polito.tdp.metroparis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	
	private Graph<Fermata, DefaultEdge> grafo ;
	private List<Fermata> fermate ;
	private Map<Integer, Fermata> fermateIdMap ;
	
	public void creaGrafo() {
		
		// crea l'oggetto grafo
		this.grafo = new SimpleGraph<Fermata, DefaultEdge>(DefaultEdge.class) ;
		
		// aggiungi i vertici
		MetroDAO dao = new MetroDAO() ;
		this.fermate = dao.readFermate() ;
		
		fermateIdMap = new HashMap<>();
		for(Fermata f: this.fermate)
			this.fermateIdMap.put(f.getIdFermata(), f) ;
		
		Graphs.addAllVertices(this.grafo, this.fermate) ;
		
		// aggiungi gli archi
		
		/* Metodo 1: considero tutti i potenziali archi */
//		long tic = System.currentTimeMillis();
//		for(Fermata partenza: this.grafo.vertexSet()) {
//			for(Fermata arrivo: this.grafo.vertexSet()) {
//				if(dao.isConnesse(partenza, arrivo)) {
//					this.grafo.addEdge(partenza, arrivo) ;
//				}
//			}
//		}
//		long toc = System.currentTimeMillis();
//		System.out.println("Elapsed time "+ (toc-tic));
		
		/* Metodo 2: data una fermata, trova la lista di quelle adiacente */
//		long tic = System.currentTimeMillis();
//		for(Fermata partenza: this.grafo.vertexSet()) {
//			List<Fermata> collegate = dao.trovaCollegate(partenza) ;
//			
//			for(Fermata arrivo: collegate) {
//				this.grafo.addEdge(partenza, arrivo) ;
//			}
//		}
//		long toc = System.currentTimeMillis();
//		System.out.println("Elapsed time "+ (toc-tic));
		
		/* Metodo 3: data una fermata, troviamo la lista di id connessi */
//		tic = System.currentTimeMillis();
//		for(Fermata partenza: this.grafo.vertexSet()) {
//			List<Fermata> collegate = dao.trovaIdCollegate(partenza, fermateIdMap) ;
//			
//			for(Fermata arrivo: collegate) {
//				this.grafo.addEdge(partenza, arrivo) ;
//			}
//		}
//		toc = System.currentTimeMillis();
//		System.out.println("Elapsed time "+ (toc-tic));
		
		/* Metodo 4: faccio una query per prendermi tutti gli edges*/
		
		long tic = System.currentTimeMillis();
		List<CoppieFermate> allCoppie = dao.getAllCoppie(fermateIdMap);
		for (CoppieFermate coppia : allCoppie)
			this.grafo.addEdge(coppia.getPartenza(), coppia.getArrivo());
		
		long toc = System.currentTimeMillis();
		System.out.println("Elapsed time "+ (toc-tic));
		
		
		System.out.println("Grafo creato con "+this.grafo.vertexSet().size()+" vertici e " + this.grafo.edgeSet().size() + " archi") ;
		System.out.println(this.grafo);
	}
	
	/**
	 * Determina il percorso minimo tra le 2 fermate
	 * @param partenza
	 * @param arrivo
	 * @return
	 */ 
	public List<Fermata> percorso(Fermata partenza, Fermata arrivo) {
		// Visita il grafo partendo da "partenza"
//		List<Fermata> raggiungibili = new ArrayList<Fermata>() ;
		BreadthFirstIterator<Fermata, DefaultEdge> visita = new BreadthFirstIterator<>(this.grafo, partenza);
		
		while(visita.hasNext()) {
			Fermata f = visita.next();
//			raggiungibili.add(f) ;
		}
//		System.out.println(raggiungibili) ;
		
		// Trova il percorso sull'albero di visita
		List<Fermata> percorso = new ArrayList<Fermata>();
		Fermata corrente = arrivo;
		percorso.add(arrivo);
		
		DefaultEdge e = visita.getSpanningTreeEdge(corrente);
		while(e!=null) {
			Fermata precedente = Graphs.getOppositeVertex(this.grafo, e, corrente);
			percorso.add(0, precedente);
			corrente = precedente;
			
			e = visita.getSpanningTreeEdge(corrente);
		}
		
		return percorso;
	}
	
	public List<Fermata> getAllFermate(){
		MetroDAO dao = new MetroDAO() ;
		return dao.readFermate() ;
	}
	
	public boolean isGrafoLoad() {
		return this.grafo.vertexSet().size()>0;
	}

}
