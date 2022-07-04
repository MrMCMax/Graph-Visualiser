package graphviz;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mrmcmax.data_structures.graphs.OneEndpointEdge;
import mrmcmax.data_structures.graphs.TwoEndpointEdge;
import mrmcmax.data_structures.graphs.UndirectedGraph;

public class GraphVizPrinter {

	private UndirectedGraph underlyingGraph;
	private HashSet<TwoEndpointEdge> redEdges;
	
	public GraphVizPrinter() {
		underlyingGraph = null; //Has to be set
		redEdges = new HashSet<>(); //Empty by default
	}
	
	public void setUnderlyingGraph(UndirectedGraph g) {
		underlyingGraph = g;
	}
	
	public void setRedEdges(HashSet<TwoEndpointEdge> edges) {
		redEdges = edges;
	}
	
	public Image getDotImage() {
		UndirectedGraph g = underlyingGraph;
		GraphViz gv = new GraphViz();
		gv.addln(gv.start_graph());
		//To draw from left to right
        gv.addln("rankdir=LR;");
        //not sure
        gv.addln("size=\"8,5;\"");
        //Start printing an undirected graph
        //First the nodes
        StringBuilder nodeLine = new StringBuilder("node [shape = circle];");
        for (int i = 0; i < g.getNumVertices(); i++) {
        	nodeLine.append(" " + i);
        }
        nodeLine.append(";");
        gv.addln(nodeLine.toString());
        //Then the edges
        for (int i = 0; i < g.getNumVertices(); i++) {
        	List<OneEndpointEdge> adj = g.getAdjacencyList(i);
        	for (OneEndpointEdge edge : adj) {
        		if (edge.endVertex >= i) {
        			if (redEdges.contains(new TwoEndpointEdge(i, edge.endVertex)) ||
        					redEdges.contains(new TwoEndpointEdge(edge.endVertex, i))) {
            			gv.addln(printEdge(i, edge.endVertex, "red"));
        			} else {
            			gv.addln(printEdge(i, edge.endVertex, "black"));
        			}
        		} //Else we will find it in the other direction
        	}
        }
        gv.addln(gv.end_graph());
		return imageCreation(gv);
	}
	
	private String printEdge(int i, int j, String color) {
		StringBuilder sb = new StringBuilder();
		sb.append(i);
		sb.append(" -> ");
		sb.append(j);
		sb.append(" [color = ");
		sb.append(color);
		sb.append("];");
		return sb.toString();
	}
	
	private HashSet<TwoEndpointEdge> parseRedEdges(String s) {
		if (s.equals("")) {
			return new HashSet<>();
		}
		String[] vertices = s.split(", ");
		if (vertices.length < 2) {
			return new HashSet<>();
		}
		HashSet<TwoEndpointEdge> ret = new HashSet<>();
		//Starting at 1, taking the previous vertex
		for (int i = 1; i < vertices.length; i++) {
			TwoEndpointEdge e = new TwoEndpointEdge(
					Integer.parseInt(vertices[i-1]), Integer.parseInt(vertices[i]));
			ret.add(e);
		}
		return ret;
	}
	
	/**
	 * Creates the AWT Image object from a finished graphviz object.
	 * @param gv the graphViz object
	 * @return the image
	 */
	private Image imageCreation(GraphViz gv) {
		System.out.println(gv.getDotSource());
		Image img = Toolkit.getDefaultToolkit().createImage(
	            gv.getGraph(gv.getDotSource(), "gif", "dot"));
		return img;
	}
}
