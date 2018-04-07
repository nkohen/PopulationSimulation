import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class PopulationSimulation extends JApplet {
	Forest forest;
	int height = 25;
	int width = 30;
	int initRabbits = 20;
	int initWolves = 5;

	public void init() {
		JRootPane rootPane = this.getRootPane();
		rootPane.putClientProperty("defeatSystemEventQueueCheck", Boolean.TRUE);

		List<Rabbit> rabbits = new LinkedList<>();
		for (int i = 0; i < initRabbits; i++) {
			rabbits.add(new Rabbit());
		}

		List<Wolf> wolves = new LinkedList<>();
		for (int i = 0; i < initWolves; i++) {
			wolves.add(new Wolf());
		}

		forest = new Forest(height, width, rabbits, wolves);

		this.setSize(width * 30, height * 25);
	}

	public void paint(Graphics g) {
		forest.drawForest(g, this, this.getWidth(), this.getHeight());
		forest.passYear();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (Exception e) {}
		repaint();
	}
}