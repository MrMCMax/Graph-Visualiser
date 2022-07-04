package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JPanel;

public class GraphDrawer extends JPanel {
	
	HashMap<Integer, Vertex> vertices;
	HashSet<Edge> edges;
	
	int nextID = 0;
	
	public GraphDrawer() {
		vertices = new HashMap<>();
		edges = new HashSet<>();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Edge e : edges) {
			e.draw(g);
		}
		for (Vertex v : vertices.values()) {
			v.draw(g);
		}
	}
	
	public void addVertex(int x, int y) {
		addVertex(nextID, x, y);
	}
	
	public void addVertex(int id, int x, int y) {
		Vertex v = new Vertex(id, x, y);
		if (vertices.containsKey(id)) {
			throw new RuntimeException("Duplicated vertex");
		} else {
			if (id >= nextID) {
				nextID++;
			}
			vertices.put(id, v);
		}
		repaint();
	}
	
	public void addEdge(int id1, int id2) {
		Vertex v1 = vertices.get(id1);
		Vertex v2 = vertices.get(id2);
		if (v1 == null || v2 == null) 
			throw new RuntimeException("Not defined vertices");
		edges.add(new Edge(v1, v2));
		repaint();
	}
	
	public class Vertex {
		
		int id, x, y;
		
		final static int VERTEX_SIZE = 20;
		
		public Vertex(int id, int x, int y) {
			this.id = id;
			this.x = x;
			this.y = y;
		}
		
		public void draw(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			Ellipse2D.Double circleBorder = new Ellipse2D.Double(x, y, VERTEX_SIZE, VERTEX_SIZE);
			Ellipse2D.Double circle = new Ellipse2D.Double(x+1, y+1, VERTEX_SIZE - 2, VERTEX_SIZE - 2);
			g2d.setColor(Color.BLACK);
			System.out.println("Printing border");
	        g2d.draw(circleBorder);
	        g2d.setColor(Color.WHITE);
	        g2d.fill(circle);
		}
		
		public boolean equals(Object o) {
			if (!(o instanceof Vertex)) return false;
			return ((Vertex) o).id == this.id;
		}
		
		public Point.Double getCenter() {
			return new Point.Double(x + VERTEX_SIZE / 2, y + VERTEX_SIZE / 2);
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
