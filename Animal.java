abstract public class Animal {
	protected int maxFood;
	protected int metabolism;
	protected int reproductionAge;
	protected double reproductionProbability;
	protected int reproductionFood;
	protected int maxAge;
	protected int food;
	protected int age;
	protected Land location;

	public Animal(int maxFood,
	  int metabolism,
	  int reproductionAge,
	  double reproductionProbability,
	  int reproductionFood,
	  int maxAge,
	  int food) {
		this.maxFood = maxFood;
		this.metabolism = metabolism;
		this.reproductionAge = reproductionAge;
		this.reproductionProbability = reproductionProbability;
		this.reproductionFood = reproductionFood;
		this.maxAge = maxAge;
		this.food = food;
		age = 0;
		location = null;
	}

	public void place(Land location) {
		this.location = location;
		location.animal = this;
	}

	public void move(Land newLocation) {
		location.animal = null;
		newLocation.animal = this;
		location = newLocation;
	}

	public void move() {
		Land newLocation = location.getEmptyNeighbor();
		if (newLocation != null) {
			this.move(newLocation);
		}
	}

	public abstract Animal eat();

	public abstract boolean safe();

	public boolean canReproduce(double random) {
		return (age >= reproductionAge)
		  && (random < reproductionProbability)
		  && (food >= reproductionFood)
		  && location.hasEmptyNeighbor()
		  && this.safe();
	}

	public Animal reproduce() {
		Animal baby = this.getBaby();
		baby.place(location.getEmptyNeighbor());
		food -= reproductionFood;
		return baby;
	}

	protected abstract Animal getBaby();

	public boolean isTooOld() {
		return age > maxAge;
	}

	public void age() {
		age++;
	}

	public boolean isStarving() {
		return food == 0;
	}
}