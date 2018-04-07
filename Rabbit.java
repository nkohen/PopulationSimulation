public class Rabbit extends Animal {
	public static int defaultMaxFood = 45;
	public static int defaultMetabolism = 3;
	public static int defaultReproductionAge = 10;
	public static double defaultReproductionProbability = .5;
	public static int defaultReproductionFood = 40;
	public static int defaultMaxAge = 25;
	public static int defaultInitialFood = 10;

	public static int grassValue = 20;

	public Rabbit() {
		super(defaultMaxFood,
	  	  defaultMetabolism,
	  	  defaultReproductionAge,
	  	  defaultReproductionProbability,
	  	  defaultReproductionFood,
	  	  defaultMaxAge,
	  	  defaultInitialFood);
	}

	public Rabbit(int maxFood,
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
		return new Rabbit();
	}

	public Animal eat() {
		int grass = location.getGrass(10); // This number needs a name
		food += grass;
		food -= metabolism;
		if (food > maxFood) {
			food = maxFood;
		}
		return null;
	}

	public boolean safe() {
		for (Animal neighbor : location.getNeighboringAnimals()) {
			if (neighbor instanceof Wolf) {
				return false;
			}
		}
		return true;
	}
}