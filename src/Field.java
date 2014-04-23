import java.util.Random;


public class Field {

	public Robot[] robots;
	public Obstacle[] obstacles;
	public Goal goal;
	public int width;
	public int height;
	
	public Field(int width, int height) {
		this.width = width;
		this.height = height;
		initRandomGoal();
		initRandomObstacles(5);
		initRobots();
	}
	
	private void initRobots() {
		robots = new Robot[1];
		robots[0] = new Robot(1, 1, goal);
	}
	
	private void initRandomObstacles(int num) {
		obstacles = new Obstacle[num];
		Random r = new Random();
		int x, y;
		for (int i = 0; i < num; i++) {
			do {
				x = r.nextInt(width);
				y = r.nextInt(height);
			} while (x == goal.getX() && y == goal.getY());
			Obstacle tempOb = new Obstacle(x, y);
			obstacles[i] = tempOb;
		}
		
	}
	
	private void initRandomGoal() {
		Random r = new Random();
		int x = r.nextInt(width);
		int y = r.nextInt(height);
		goal = new Goal(x, y);
	}
	
	public Boolean inRange(int x, int y) {
		return (x >= 0 && x < width && y >= 0 && y < height);
		
	}
	
	public Boolean finished() {
		for (Robot r : robots) {
			if (!r.reachedGoal()) return false;
		}
		return true;
	}
}

