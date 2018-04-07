import java.util.List;
import java.util.LinkedList;
import java.util.Random;
import java.awt.*;
import java.applet.Applet;

public class Forest {
	private final Land[][] land;
	private final List<Rabbit> rabbitList;
	private final List<Wolf> wolfList;
	private final Random random = new Random();

	public Forest(int forestRows, int forestColumns) {
		land = new Land[forestRows][forestColumns];
		rabbitList = new LinkedList<>();
		wolfList = new LinkedList<>();
		initLand();
	}

	public Forest(int forestRows,
	  int forestColumns,
	  List<Rabbit> rabbitList,
	  List<Wolf> wolfList) {
		land = new Land[forestRows][forestColumns];
		this.rabbitList = rabbitList;
		this.wolfList = wolfList;
		initLand();
	}

	private void initLand() {
		for (int i = 0; i < land.length; i++) {
			for (int j = 0; j < land[0].length; j++) {
				land[i][j] = new Land();
			}
		}
		for (int i = 0; i < land.length; i++) {
			for (int j = 0; j < land[0].length; j++) {
				initNeighbors(i,j,land[i][j]);
			}
		}
		placeAnimalsRandomly(wolfList);
		placeAnimalsRandomly(rabbitList);
	}

	private void initNeighbors(int i, int j, Land place) {
		Land[] neighbors = new Land[8];
		int rows = land.length;
		int cols = land[0].length;
		neighbors[0] = land[(i-1 + rows) % rows][(j-1 + cols) % cols];
		neighbors[1] = land[(i + rows) % rows][(j-1 + cols) % cols];
		neighbors[2] = land[(i+1 + rows) % rows][(j-1 + cols) % cols];
		neighbors[3] = land[(i-1 + rows) % rows][(j + cols) % cols];
		neighbors[4] = land[(i+1 + rows) % rows][(j + cols) % cols];
		neighbors[5] = land[(i-1 + rows) % rows][(j+1 + cols) % cols];
		neighbors[6] = land[(i + rows) % rows][(j+1 + cols) % cols];
		neighbors[7] = land[(i+1 + rows) % rows][(j+1 + cols) % cols];
		place.setNeighbors(neighbors);
	}

	private void placeAnimalsRandomly(List animalList) {
		for (Object animal : animalList) {
			boolean set = false;

			while (!set) {
				int row = random.nextInt(land.length);
				int column = random.nextInt(land[0].length);

				if (land[row][column].animal == null) {
					((Animal)animal).place(land[row][column]);
					set = true;
				}
			}
		}
	}

	// One step in the simulation
	public void passYear() {
		 growGrass();
		 animalsEat();
		 animalsReproduce();
		 animalsMove();
		 killOldAndStarvingAnimals();
		 ageAnimals();
		 updateStats();
	}

	private void growGrass() {
		for (int i = 0; i < land.length; i++) {
			for (int j = 0; j < land[0].length; j++) {
				land[i][j].growGrass();
			}
		}
	}

	private void animalsEat() {
		for (Rabbit rabbit : rabbitList) {
			rabbit.eat();
		}
		for (Wolf wolf : wolfList) {
			Animal prey = wolf.eat();
			if (prey != null) {
				rabbitList.remove((Rabbit)prey);
			}
		}
	}

	private void animalsReproduce() {
		List<Wolf> babyWolves = new LinkedList<>();
		for (Wolf wolf : wolfList) {
			if (wolf.canReproduce(random.nextDouble())) {
				Animal baby = wolf.reproduce();
				babyWolves.add((Wolf)baby);
			}
		}
		for (Wolf baby : babyWolves) {
			wolfList.add(baby);
		}

		List<Rabbit> babyRabbits = new LinkedList<>();
		for (Rabbit rabbit : rabbitList) {
			if (rabbit.canReproduce(random.nextDouble())) {
				Animal baby = rabbit.reproduce();
				babyRabbits.add((Rabbit)baby);
			}
		}
		for (Rabbit baby : babyRabbits) {
			rabbitList.add(baby);
		}
	}

	private void animalsMove() {
		for (Wolf wolf : wolfList) {
			wolf.move();
		}
		for (Rabbit rabbit : rabbitList) {
			rabbit.move();
		}
	}

	private void killOldAndStarvingAnimals() {
		for (int i = 0; i < rabbitList.size(); i++) {
			Rabbit rabbit = rabbitList.get(i);
			if (rabbit.isTooOld() || rabbit.isStarving()) {
				rabbit.location.animal = null;
				rabbitList.remove(i);
				i--;
			}
		}

		for (int i = 0; i < wolfList.size(); i++) {
			Wolf wolf = wolfList.get(i);
			if (wolf.isTooOld() || wolf.isStarving()) {
				wolf.location.animal = null;
				wolfList.remove(i);
				i--;
			}
		}
	}

	private void ageAnimals() {
		for (Rabbit rabbit : rabbitList) {
			rabbit.age();
		}
		for (Wolf wolf : wolfList) {
			wolf.age();
		}
	}

	private void updateStats() {

	}

	public void printResults() {
		System.out.println("Rabbits: " + rabbitList.size() + "\nWolves: " + wolfList.size());
		for (int i = 0; i < land.length; i++) {
			System.out.print(" -");
			for (int j = 0; j < land[0].length; j++) {
				System.out.print("--");
			}
			System.out.println();
			System.out.print(i + "|");
			for (int j = 0; j < land[0].length; j++) {
				System.out.print(getLetter(i,j) + "|");
			}
			System.out.println();
		}
		System.out.print(" -");
		for (int j = 0; j < land[0].length; j++) {
			System.out.print("--");
		}
		System.out.println();
	}

	private String getLetter(int i, int j) {
		Animal animal = land[i][j].animal;
		if (animal == null)
			return " ";
		if (animal instanceof Rabbit)
			return "R";
		if (animal instanceof Wolf)
			return "W";
		return "";
	}

	public void drawForest(Graphics g, Applet applet, int totalWidth, int totalHeight) {
		g.setColor(Color.white);
		g.fillRect(0,0,totalWidth,totalHeight);

		int height = land.length;
		int width = land[0].length;

		int singleHeight = totalHeight/height;
		int singleWidth = totalWidth/width;

		g.setColor(Color.black);

		for (int i = 0; i < totalHeight; i += singleHeight) {
			g.drawLine(0,i,singleWidth*width,i);
		}

		for (int i = 0; i < totalWidth; i += singleWidth) {
			g.drawLine(i,0,i,singleHeight*height);
		}

		Image rabbitImage = applet.getImage(applet.getDocumentBase(), "rabbit.png");
		Image wolfImage = applet.getImage(applet.getDocumentBase(), "wolf.png");
		for (int i = 0; i < land.length; i++) {
			for (int j = 0; j < land[0].length; j++) {
				int grass = land[i][j].totalGrass();
				if (grass < 10) {
					g.setColor(Color.yellow);
				} else if (grass < 25) {
					g.setColor(Color.green);
				} else {
					g.setColor(new Color(0,100,0));
				}
				g.fillRect(j * singleWidth + 1, i * singleHeight + 1, singleWidth - 1, singleHeight - 1);

				if(land[i][j].animal instanceof Rabbit) {
					g.drawImage(rabbitImage, j * singleWidth + 1, i * singleHeight + 1, singleWidth - 1, singleHeight - 1, applet);
				} else if (land[i][j].animal instanceof Wolf) {
					g.drawImage(wolfImage, j * singleWidth + 1, i * singleHeight + 1, singleWidth - 1, singleHeight - 1, applet);
				}
			}
		}
	}
}