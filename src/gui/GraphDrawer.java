package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JComponent;

import mrmcmax.data_structures.graphs.DynamicGraph;
import mrmcmax.data_structures.graphs.GraphException;

public class GraphDrawer extends Canvas {
	
	HashMap<Integer, Vertex> vertices;
	HashSet<Edge> edges;
	
	DynamicGraph dynGraph;
	
	int nextID = 0;
	
	public GraphDrawer() {
		vertices = new HashMap<>();
		edges = new HashSet<>();
		dynGraph = new DynamicGraph();
	}
	
	/**
	 * This method paints in the screen whenever it is called.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (Edge e : edges) {
			e.draw(g);
		}
		for (Vertex v : vertices.values()) {
			v.draw(g);
		}
	}
	
	/**
	 * This method is called whenever the window is resized.
	 * The default implementation of update clears the screen, and then
	 * calls paint. This implementation skips the clearing to avoid flickering.
	 * (There is still flickering happening)
	 */
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	/**
	 * Adds a new vertex to the graph, drawn in the specified position (xPos, yPos)
	 * @param xPos the x coordinate of the vertex
	 * @param yPos the y coordinate of the vertex
	 */
	public int addVertex(double xPos, double yPos) {
		int id = dynGraph.addVertex();
		addVertexData(id, xPos, yPos);
		return id;
	}
	
	/**
	 * Tries to add a new vertex to the graph with the given ID and drawn in the given position
	 * (xPos, yPos)
	 * @param id The ID of the vertex to add
	 * @param xPos the x coordinate of the vertex
	 * @param yPos the y coordinate of the vertex
	 * @throws GraphException If there is already a vertex with that ID in the graph.
	 */
	public void addVertex(int id, double xPos, double yPos) throws GraphException {
		dynGraph.addVertex(id);
		addVertexData(id, xPos, yPos);
	}
	
	/**
	 * Does the necessary work to store the data about the position of a new vertex and its drawing.
	 * @param id
	 * @param xPos
	 * @param yPos
	 */
	private void addVertexData(int id, double xPos, double yPos) {
		Vertex v = new Vertex(id, xPos, yPos);
		vertices.put(id, v);
		repaint();
	}
	
	
	public void addEdge(int id1, int id2) throws GraphException {
		dynGraph.addEdge(id1, id2); 
		//Will throw an exception if any of the vertices does not exist,
		//aborting the rest
		Vertex v1 = vertices.get(id1);
		Vertex v2 = vertices.get(id2);
		edges.add(new Edge(v1, v2));
		repaint();
	}
	
	public class Vertex extends JComponent implements MouseMotionListener {
		
		int id;
		double x, y;
		
		final static int VERTEX_SIZE = 20;
		
		public Vertex(int id, double x, double y) {
			this.id = id;
			this.x = x;
			this.y = y;
		}
		
		public void draw(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			Ellipse2D.Double circleBorder = new Ellipse2D.Double(x - VERTEX_SIZE / 2, y - VERTEX_SIZE / 2, VERTEX_SIZE, VERTEX_SIZE);
			Ellipse2D.Double circle = new Ellipse2D.Double(x+1 - VERTEX_SIZE / 2, y+1 - VERTEX_SIZE / 2, VERTEX_SIZE - 2, VERTEX_SIZE - 2);
			g2d.setColor(Color.BLACK);
			//System.out.println("Printing border");
	        g2d.draw(circleBorder);
	        g2d.setColor(Color.WHITE);
	        g2d.fill(circle);
		}
		
		public boolean equals(Object o) {
			if (!(o instanceof Vertex)) return false;
			return ((Vertex) o).id == this.id;
		}
		
		public Point.Double getCenter() {
			return new Point.Double(x, y);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			System.out.println("Hey");
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}
	}
	
	public class Edge {
		
		Vertex v1, v2;
		
		public Edge(Vertex v1, Vertex v2) {
			this.v1 = v1;
			this.v2 = v2;
		}
		
		public boolean equals(Object o) {
			if (!(o instanceof Edge)) return false;
			Edge other = (Edge) o;
			return (other.v1.equals(v1) && other.v2.equals(v2)) ||
					other.v2.equals(v1) && other.v1.equals(v2);
		}
		
		public void draw(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			Line2D.Double line = new Line2D.Double(v1.getCenter(), v2.getCenter());
			g2d.setColor(Color.BLACK);
			g2d.draw(line);
		}
	}
	
}
