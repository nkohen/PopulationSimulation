import java.util.List;
import java.util.LinkedList;

public class Wolf extends Animal {
	public static int defaultMaxFood = 200;
	public static int defaultMetabolism = 2;
	public static int defaultReproductionAge = 10;
	public static double defaultReproductionProbability = .5;
	public static int defaultReproductionFood = 120;
	public static int defaultMaxAge = 50;
	public static int defaultInitialFood = 150;

	public static int rabbitValue = 10;

	public Wolf() {
		super(defaultMaxFood,
	  	  defaultMetabolism,
	  	  defaultReproductionAge,
	  	  defaultReproductionProbability,
	  	  defaultReproductionFood,
	  	  defaultMaxAge,
	  	  defaultInitialFood);
	}

	public Wolf(int maxFood,
	  int metabolism,
	  int reproductionAge,
	  double reproductionProbability,
	  int reproductionFood,
	  int maxAge,
	  int food) {
		super(maxFood,
	  	  metabolism,
	  	  reproductionAge,
	  	  reproductionProbability,
	  	  reproductionFood,
	  	  maxAge,
	  	  food);
	}

	public Animal getBaby() {
		return new Wolf();
	}

	public boolean safe() {
		return true;
	}

	public Animal eat() {
		Rabbit prey = null;
		List<Rabbit> rabbitNeighbors = new LinkedList<>();
		for (Animal neighbor : location.getNeighboringAnimals()) {
			if (neighbor instanceof Rabbit) {
				rabbitNeighbors.add((Rabbit)neighbor);
			}
		}

		if (!rabbitNeighbors.isEmpty()) {
			prey = rabbitNeighbors.get(
			  (int)(Math.random()*rabbitNeighbors.size()));
			prey.location.animal = null;
			food += Wolf.rabbitValue;
		}
		food -= metabolism;
		return prey;
	}
}