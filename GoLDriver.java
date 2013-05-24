package gameOfLife;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class GoLDriver {
	private JFrame frame = new JFrame("Conway's Game of Life");
	
	public void setupGUI() {
		Model model = new Model();
		GridComponent component = new GridComponent(model);
		//model.setupGosper();
		model.setupRandomStart();
		JMenuBar menuBar = new JMenuBar();
		MenuSetup.setupBar(menuBar, model);
		frame.setJMenuBar(menuBar);
		frame.add(component);
		frame.setSize(400,300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		model.setAnimation(true);
		model.startTimer();
	}
	
	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GoLDriver().setupGUI();
			}
		});
	}
}
