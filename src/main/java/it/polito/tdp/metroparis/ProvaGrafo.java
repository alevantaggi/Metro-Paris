package it.polito.tdp.metroparis;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
//import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleGraph;

public class ProvaGrafo {

	public static void main(String[] args) {
		
		Graph<String,DefaultEdge > grafo = new SimpleGraph<>(DefaultEdge.class);
		
		grafo.addVertex("r");
		grafo.addVertex("s");
		grafo.addVertex("w");
		grafo.addVertex("t");
		grafo.addVertex("v");
		grafo.addVertex("x");

		grafo.addEdge("r", "s");
		grafo.addEdge("v", "r");
		grafo.addEdge("s", "w");
		grafo.addEdge("w", "t");
		grafo.addEdge("w", "x");
		grafo.addEdge("t", "x");
		
		System.out.println(grafo); // se ho un grafo orientato ho le parentesi tonde, mentre se non è orientato ho le graffe
		
		System.out.println("Vertici: "+ grafo.vertexSet().size());
		System.out.println("Archi: "+ grafo.edgeSet().size());
		
		for(String v : grafo.vertexSet()) {
			System.out.println("Vertice "+v+ " ha grado: "+grafo.degreeOf(v));
			
			for(DefaultEdge arco: grafo.edgesOf(v)) {
//				System.out.println(arco);
				String arrivo="";
				
				if(v.equals(grafo.getEdgeTarget(arco))) 
					arrivo= grafo.getEdgeSource(arco);
	
				else 
					arrivo= grafo.getEdgeTarget(arco);

				System.out.println("\t è connesso a: "+arrivo);
				
				String arr= Graphs.getOppositeVertex(grafo, arco, v);
				System.out.println("\t  * è connesso a: "+arr);

			}
			
			List<String> vicini= Graphs.neighborListOf(grafo, v);
			System.out.println("I vicini sono: "+vicini);

 			
				
		}

	}

}
