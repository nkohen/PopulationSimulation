import java.util.List;
import java.util.LinkedList;

public class Land {
	public static int defaultInitialGrass = 20;
	public static int defaultGrassGrowthRate = 1;

	private int grass;
	private int grassGrowthRate;
	public Animal animal;
	private Land[] neighborList;

	public Land() {
		animal = null;
		this.grass = defaultInitialGrass;
		this.grassGrowthRate = defaultGrassGrowthRate;
		neighborList = null;
	}

	public boolean isEmpty() {
		return animal == null;
	}

	public void setNeighbors(Land[] neighborList) {
		this.neighborList = neighborList;
	}

	public void growGrass() {
		grass += grassGrowthRate;
	}

	public int getGrass(int amount) {
		if (grass < amount) {
			amount = grass;
			grass = 0;
			return amount;
		} else {
			grass -= amount;
			return amount;
		}
	}

	public int totalGrass() {
		return grass;
	}

	public boolean hasEmptyNeighbor() {
		for (Land neighbor : neighborList) {
			if (neighbor.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public Land getEmptyNeighbor() {
		List<Land> emptyNeighbors = new LinkedList<>();
		for (Land neighbor : neighborList) {
			if (neighbor.isEmpty()) {
				emptyNeighbors.add(neighbor);
			}
		}

		if (emptyNeighbors.isEmpty()) {
			return null;
		}

		return emptyNeighbors.get(
		  (int)(Math.random()*(emptyNeighbors.size())));
	}

	public List<Animal> getNeighboringAnimals() {
		List<Animal> neighboringAnimals = new LinkedList<>();
		for (Land neighbor : neighborList) {
			if (neighbor.animal != null) {
				neighboringAnimals.add(neighbor.animal);
			}
		}
		return neighboringAnimals;
	}
}