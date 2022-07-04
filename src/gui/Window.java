package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Window {

	private JFrame frame;
	
	private JPanel graphDrawer;

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
		frame.getContentPane().add(lblThisIsThe, BorderLayout.NORTH);
		
		JLabel lblThisIsThe_1 = new JLabel("This is the left panel");
		frame.getContentPane().add(lblThisIsThe_1, BorderLayout.WEST);
		
		JLabel lblThisIsThe_2 = new JLabel("This is the right panel");
		frame.getContentPane().add(lblThisIsThe_2, BorderLayout.EAST);
		
		JButton lblThisIsMy = new JButton("This is my beloved bottom panel");
		lblThisIsMy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphDrawer gd = (GraphDrawer) graphDrawer;
				gd.addVertex(0, 50, 50);
				gd.addVertex(1, 100, 100);
				gd.addEdge(0, 1);
			}
		});
		frame.getContentPane().add(lblThisIsMy, BorderLayout.SOUTH);
		
		graphDrawer = new GraphDrawer();
		frame.getContentPane().add(graphDrawer, BorderLayout.CENTER);
		
		JLabel lblLabelInCentral = new JLabel("Label in central JPanel");
		graphDrawer.add(lblLabelInCentral);
	}

}
