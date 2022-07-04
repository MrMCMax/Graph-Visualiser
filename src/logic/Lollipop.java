package logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import mrmcmax.data_structures.graphs.OneEndpointEdge;
import mrmcmax.data_structures.graphs.TwoEndpointEdge;
import mrmcmax.data_structures.graphs.UndirectedGraph;

/**
 * A class that is gonna manage the lollipop method
 * @author max
 *
 */
public class Lollipop {
	
	private int[] next;
	private UndirectedGraph underlyingGraph;
	private int n;
	
	private int x, y, second_to_last, last;
	
	private int previousNeighbor, currentNeighbor, nextNeighbor;
	
	private LinkedList<UndirectedGraph> H;
	
	public void setInitialHamiltonianPath(UndirectedGraph underlyingGraph, String path) {
		this.underlyingGraph = underlyingGraph;
		this.n = underlyingGraph.getNumVertices();
		parsePath(path);
		//We got the underlying graph and the path subgraph.
		//Now we are going to update the knowledge we have about current, previous and next
		//Hamiltonian paths
		previousNeighbor = -1;
		currentNeighbor = second_to_last;
		List<OneEndpointEdge> adj = underlyingGraph.getAdjacencyList(last);
		int i = 0; 
		OneEndpointEdge edge;
		edge = adj.get(i);
		while (edge.endVertex == x || edge.endVertex == second_to_last) {
			edge = adj.get(++i);
		}
		//We got a different neighbor
		nextNeighbor = edge.endVertex;
	}
	
	/**
	 * By a path string constructs a subgraph with the edges of the path and all the vertices
	 * of the underlying graph to avoid conflicts (naming).
	 * @param path
	 * @return The Undirected spanning subgraph
	 */
	private void parsePath(String path) {
		if (path.equals("")) {
			throw new RuntimeException("Null path");
		}
		next = new int[n];
		String[] vertices = path.split(", ");
		x = Integer.parseInt(vertices[0]);
		y = Integer.parseInt(vertices[1]);
		second_to_last = Integer.parseInt(vertices[vertices.length - 2]);
		last = Integer.parseInt(vertices[vertices.length - 1]);
		int vi, vj;
		//Taking the back vertex
		for (int i = 1; i < vertices.length; i++) {
			vi = Integer.parseInt(vertices[i-1]);
			vj = Integer.parseInt(vertices[i]);
			next[vi] = vj;
		}
		next[last] = -1;
	}
	
	public HashSet<TwoEndpointEdge> getEdgeSet() {
		HashSet<TwoEndpointEdge> edgeSet = new HashSet<>();
		int vertex = x;
		while (next[vertex] != -1) {
			edgeSet.add(new TwoEndpointEdge(vertex, next[vertex]));
			vertex = next[vertex];
		}
		return edgeSet;
	}
	
	public void nextHamiltonianPath() {
		if (nextNeighbor == -1) {
			return; //Do nothing
		}
		changeEdge(nextNeighbor);
	}
	
	public void previousHamiltonianPath() {
		if (previousNeighbor == -1) {
			return; //Do nothing
		}
		changeEdge(previousNeighbor);
	}
	
	/**
	 * Changes the edge from last to neighbor.
	 * @param neighbor
	 */
	private void changeEdge(int neighbor) {
		next[last] = neighbor; //Will be flipped around
		//Backwards change
		int previousV = neighbor;
		int currentV = next[neighbor];
		int nextV = next[currentV];
		//Update fields now cuz we got the right pointers
		last = currentV;
		second_to_last = nextV;
		currentNeighbor = second_to_last;
		previousNeighbor = neighbor;
		//Do the changes
		next[currentV] = -1;
		previousV = currentV;
		currentV = nextV;
		nextV = next[nextV];
		while (!(currentV == neighbor)) {
			next[currentV] = previousV;
			previousV = currentV;
			currentV = nextV;
			nextV = next[nextV];
		}
		next[currentV] = previousV;
		//Done. Now update next neighbor
		List<OneEndpointEdge> adj = underlyingGraph.getAdjacencyList(last);
		int i = 0; 
		OneEndpointEdge edge;
		//dirty search for the other neighbor
		do {
			edge = adj.get(i++);
		} while (isBadNeighbor(edge.endVertex) && i < adj.size());
		if (isBadNeighbor(edge.endVertex)) {
			nextNeighbor = -1;
		} else {
			nextNeighbor = edge.endVertex;
		}
	}
	
	private boolean isBadNeighbor(int v) {
		return (v == second_to_last || v == previousNeighbor || v == x);
	}
}
