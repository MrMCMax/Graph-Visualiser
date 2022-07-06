package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
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
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSpinner;

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
		frame.setBounds(100, 100, 844, 424);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel lblThisIsThe = new JLabel("This is the top panel");
		lblThisIsThe.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblThisIsThe, BorderLayout.NORTH);
		
		JLabel lblThisIsThe_1 = new JLabel("This is the left panel");
		frame.getContentPane().add(lblThisIsThe_1, BorderLayout.WEST);
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridBagLayout());
		
		frame.getContentPane().add(rightPanel, BorderLayout.EAST);
		
		JLabel lblThisIsThe_2 = new JLabel("Add/remove edge");
		
		GridBagConstraints gbc_lblThisIsThe_2 = new GridBagConstraints();
		gbc_lblThisIsThe_2.insets = new Insets(5, 5, 5, 5);
		gbc_lblThisIsThe_2.gridx = 0;
		gbc_lblThisIsThe_2.gridy = 0;
		rightPanel.add(lblThisIsThe_2, gbc_lblThisIsThe_2);
		gbc_lblThisIsThe_2.insets = new Insets(5, 5, 5, 5);
		
		JLabel lblVin = new JLabel("v_in");
		GridBagConstraints gbc_lblVin = new GridBagConstraints();
		gbc_lblVin.insets = new Insets(0, 0, 5, 5);
		gbc_lblVin.gridx = 0;
		gbc_lblVin.gridy = 1;
		rightPanel.add(lblVin, gbc_lblVin);
		
		JLabel lblVout = new JLabel("v_out");
		GridBagConstraints gbc_lblVout = new GridBagConstraints();
		gbc_lblVout.insets = new Insets(0, 0, 5, 0);
		gbc_lblVout.gridx = 1;
		gbc_lblVout.gridy = 1;
		rightPanel.add(lblVout, gbc_lblVout);
		
		JSpinner vInSpinner = new JSpinner();
		GridBagConstraints gbc_vInSpinner = new GridBagConstraints();
		gbc_vInSpinner.insets = new Insets(0, 0, 5, 5);
		gbc_vInSpinner.gridx = 0;
		gbc_vInSpinner.gridy = 2;
		rightPanel.add(vInSpinner, gbc_vInSpinner);
		
		JSpinner vOutSpinner = new JSpinner();
		GridBagConstraints gbc_vOutSpinner = new GridBagConstraints();
		gbc_vOutSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_vOutSpinner.gridx = 1;
		gbc_vOutSpinner.gridy = 2;
		rightPanel.add(vOutSpinner, gbc_vOutSpinner);
		
		JButton btnAdd = new JButton("Add");
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.insets = new Insets(0, 0, 0, 5);
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 3;
		rightPanel.add(btnAdd, gbc_btnAdd);
		
		JButton btnRemove = new JButton("Remove");
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.gridx = 1;
		gbc_btnRemove.gridy = 3;
		rightPanel.add(btnRemove, gbc_btnRemove);
		
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
