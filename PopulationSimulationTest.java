import java.util.List;
import java.util.LinkedList;

public class PopulationSimulationTest {
	public static void main(String[] args) {
		List<Rabbit> rabbits = new LinkedList<>();
		for (int i = 0; i < 20; i++) {
			rabbits.add(new Rabbit());
		}

		List<Wolf> wolves = new LinkedList<>();
		for (int i = 0; i < 5; i++) {
			wolves.add(new Wolf());
		}

		Forest forest = new Forest(25, 30, rabbits, wolves);

		forest.printResults();
		for(int i = 0; i < 100; i++) {
			System.out.println("Step " + (i+1));
			forest.passYear();
			forest.printResults();
		}
	}
}