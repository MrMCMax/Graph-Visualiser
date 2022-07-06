package gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.event.MouseMotionAdapter;

public class Window {

	private JFrame frame;
	
	private GraphDrawer graphDrawer;
	
	private ButtonGroup mouseGroup;
	private JRadioButton rdbtnSelect;
	private JRadioButton rdbtnNewVertex;
	private JRadioButton rdbtnNewEdge;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 673, 420);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel lblThisIsThe = new JLabel("This is the top panel");
		lblThisIsThe.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblThisIsThe, BorderLayout.NORTH);
		
		JLabel lblThisIsThe_1 = new JLabel("This is the left panel");
		frame.getContentPane().add(lblThisIsThe_1, BorderLayout.WEST);
		
		JLabel lblThisIsThe_2 = new JLabel("This is the right panel");
		frame.getContentPane().add(lblThisIsThe_2, BorderLayout.EAST);
		
		JButton lblThisIsMy = new JButton("This is my beloved bottom panel");
		lblThisIsMy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphDrawer gd = (GraphDrawer) graphDrawer;
				int id1 = gd.addVertex(50, 50);
				int id2 = gd.addVertex(100, 100);
				gd.addEdge(id1, id2);
			}
		});
		FlowLayout fl = new FlowLayout();
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(fl);
		
		rdbtnSelect = new JRadioButton("Select", true);
		bottomPanel.add(rdbtnSelect);
		
		rdbtnNewVertex = new JRadioButton("New Vertex");
		bottomPanel.add(rdbtnNewVertex);
		
		rdbtnNewEdge = new JRadioButton("New Edge");
		bottomPanel.add(rdbtnNewEdge);
		
		mouseGroup = new ButtonGroup();
		mouseGroup.add(rdbtnSelect);
		mouseGroup.add(rdbtnNewVertex);
		mouseGroup.add(rdbtnNewEdge);
		
		bottomPanel.add(lblThisIsMy);
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
		graphDrawer = new GraphDrawer();
		graphDrawer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (rdbtnNewVertex.isSelected()) {
					graphDrawer.addVertex(e.getX(), e.getY());
				}
			}
		});
		frame.getContentPane().add(graphDrawer, BorderLayout.CENTER);
	}

}
