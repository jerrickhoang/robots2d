import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Field {

	public List<Robot> robots;
	public List<Obstacle> obstacles;
	public Goal goal;
	public int width;
	public int height;
	
	public Field(int width, int height) {
		this.width = width;
		this.height = height;
		
		robots = new ArrayList<Robot>();
		obstacles = new ArrayList<Obstacle>();
		initGoal();
		initRobots();
		//initRandomGoal();
		//initRandomObstacles(5);
	}
	
	private void initRobots() {
		robots = new ArrayList<Robot>();
		robots.add(new Robot(10, 6, goal));
	}
	
	private void initRandomObstacles(int num) {
		obstacles = new ArrayList<Obstacle>();
		Random r = new Random();
		int x, y;
		for (int i = 0; i < num; i++) {
			do {
				x = r.nextInt(width);
				y = r.nextInt(height);
			} while (x == goal.getX() && y == goal.getY());
			obstacles.add(new Obstacle(x, y));
		}
		
	}
	
	private void initGoal() {
		goal = new Goal(10, 13);
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

	public void createGoal(int x, int y) {
		goal = new Goal(x, y);
	}
	
	public Boolean isGoal(int x, int y) {
		return (goal.getX() == x) && (goal.getY() == y);
	}
	
	public Boolean isRobot(int x, int y) {
		for (Robot r : robots) {
			if (r.getX() == x && r.getY() == y) return true;
		}
		return false;
	}

	public Boolean isObstacle(int x, int y) {
		for (Obstacle o : obstacles) {
			if (o.getX() == x && o.getY() == y) return true;
		}
		return false;
	}
	
	public Boolean isOccupied(int x, int y) {
		return inRange(x, y) && ( isGoal(x, y) || isRobot(x, y) || isObstacle(x, y) );
	}
	
	public Boolean addObstacle(int x, int y) {
		if (isOccupied(x, y)) return false;
		if (!inRange(x, y)) return false;
		obstacles.add(new Obstacle(x, y));
		return true;
	}
	
	public Boolean removeObstacle(int x, int y) {
		for (int i = 0; i < obstacles.size(); i++) {
			if (obstacles.get(i).getX() == x && obstacles.get(i).getY() == y) {
				obstacles.remove(i);
				return true;
			}
		}
		return false;
	}
}

