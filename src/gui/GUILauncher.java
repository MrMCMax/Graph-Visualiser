package gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import graphviz.GraphVizPrinter;
import interfaces.MrMCMaxDataStructuresInterface;
import logic.Lollipop;
import mrmcmax.data_structures.graphs.Graph;
import mrmcmax.data_structures.graphs.UndirectedGraph;

public class GUILauncher extends JFrame {
	private final Action action = new SwingAction();
	
	private final JTextArea txtrErrorLog;
	private final Canvas canvas;
	private final TextField textField;
	private MrMCMaxDataStructuresInterface connector;
	private UndirectedGraph loadedGraph;
	private GraphVizPrinter printer;
	private Lollipop lollipop;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUILauncher frame = new GUILauncher();
					frame.setVisible(true);
					frame.initialize();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUILauncher() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 760, 515);
		
		canvas = new Canvas();
		getContentPane().add(canvas, BorderLayout.CENTER);
		
		Panel panel = new Panel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblOpenTikzPicture = new JLabel("Open TikZ picture: ");
		panel.add(lblOpenTikzPicture);
		
		JButton btnOpenFile = new JButton("Open file");
		btnOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseFile();
			}
		});
		btnOpenFile.setAction(action);
		panel.add(btnOpenFile);
		
		Panel panel_1 = new Panel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JLabel lblHighlightPath = new JLabel("Highlight path:");
		panel_1.add(lblHighlightPath);
		
		textField = new TextField();
		textField.setText("1, 2, 3, 4, 5, 6, 7, 8");
		panel_1.add(textField);
		
		JButton btnHighlight = new JButton("Highlight");
		btnHighlight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				highlightGraph(textField.getText());
			}
		});
		panel_1.add(btnHighlight);
		
		txtrErrorLog = new JTextArea();
		txtrErrorLog.setText("Error log");
		panel_1.add(txtrErrorLog);
		
		JButton leftLollipopButton = new JButton("<-");
		leftLollipopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lollipop.previousHamiltonianPath();
				updateHighlight();
			}
		});
		panel_1.add(leftLollipopButton);
		
		JButton rightLollipopButton = new JButton("->");
		rightLollipopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lollipop.nextHamiltonianPath();
				updateHighlight();
			}
		});
		panel_1.add(rightLollipopButton);
	}
	
	public void chooseFile() {
		JFileChooser fc = new JFileChooser();
		int retVal = fc.showOpenDialog(this);
		if (retVal == JFileChooser.APPROVE_OPTION) {
			File f = fc.getSelectedFile();
			log(f.getAbsolutePath());
			loadGraph(f.getAbsolutePath());
		} else {
			//Do nothing
		}
	}
	
	private void initialize() {
		connector = new MrMCMaxDataStructuresInterface();
		printer = new GraphVizPrinter();
		lollipop = new Lollipop();
	}
	
	private void loadGraph(String path) {
		try {
			loadedGraph = (UndirectedGraph) connector.loadGraph(path);
			log(path);
			printer.setUnderlyingGraph(loadedGraph);
			drawGraph();
		} catch (IOException e) {
			log(e.getMessage());
		}
	}
	
	private void drawGraph() {
		Image img = printer.getDotImage();
		savePic(img, "gif", "/home/max/Pictures/graphviz.gif");
		canvas.getGraphics().drawImage(img, 0,0, null);
	}
	
	private void highlightGraph(String subgraph) {
		lollipop.setInitialHamiltonianPath(loadedGraph, subgraph);
		printer.setRedEdges(lollipop.getEdgeSet());
		drawGraph();
	}
	
	private void updateHighlight() {
		printer.setRedEdges(lollipop.getEdgeSet());
		drawGraph();
	}
	
	private void savePic(Image image, String type, String dst){ 
	    int width = image.getWidth(this); 
	    int height = image.getHeight(this);
	    BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR);
	    Graphics g = bi.getGraphics();
	    try { 
	        g.drawImage(image, 0, 0, null);
	        ImageIO.write(bi, type, new File(dst)); 
	    } catch (IOException e) { 
	        // TODO Auto-generated catch block 
	        e.printStackTrace(); 
	    } 
	}
	
	private void log(String text) {
		txtrErrorLog.setText(text);
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
