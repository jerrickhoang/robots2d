import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class Field {
	public int[][] grid;
	public Point[][] vectorField;
	public Boolean[][] visited;
	public Robot[] robots;
	public Obstacle[] obstacles;
	public Goal goal;
	public int width;
	public int height;
	
	public Field(int width, int height) {
		this.width = width;
		this.height = height;
		initGrid(width, height);
		initRandomGoal();
		initRandomObstacles(5);
		initRobots();
		initVectorField(width, height);
	}
	
	private void initVectorField(int width, int height) {
		vectorField = new Point[height][width];
		for (int i = 0; i < height; i ++) {
			for (int j = 0; j < width; j ++) {
				vectorField[j][i] = new Point(0, 0);
			}
		}
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
	
	private void initGrid(int width, int height) {
		grid = new int[height][width];
		visited = new Boolean[height][width];
		for (int i = 0; i < height; i ++) {
			for (int j = 0; j < width; j++) {
				grid[i][j] = 0;
				visited[i][j] = false;
			}
		}
	}
	
	public void run() {
		for (Robot r: robots) {
			Point grad = vectorField[r.getY()][r.getX()];
			r.setX(r.getX() + grad.x);
			r.setY(r.getY() + grad.y);
		}
	}
	
	private void initRandomGoal() {
		Random r = new Random();
		int x = r.nextInt(width);
		int y = r.nextInt(height);
		goal = new Goal(x, y);
	}
	
	private Boolean inRange(int x, int y) {
		return (x >= 0 && x < width && y >= 0 && y < height);
		
	}
	
	private Boolean isVisited(int x, int y) {
		return visited[y][x];
	}
	
	private Boolean isValid(int x, int y) {
		return !isVisited(x, y) && notObstacle(x, y);
	}
	
	private Boolean notObstacle(int x, int y) {
		for (int i = 0; i < obstacles.length; i ++) {
			if (obstacles[i].getX() == x && obstacles[i].getY() == y)
				return false;
		}
		return true;
	}
	
	public void printGrid() {
		for (int i = 0; i < height; i ++) {
			for (int j = 0; j < width; j++) {
				System.out.print("  " + grid[i][j]);
			}
			System.out.println("");
		}
		
	}
	
	public void runWaveFront() {
		Queue<Point> curLevel = new LinkedList<Point>();
		Queue<Point> nextLevel = new LinkedList<Point>();
		int curDistance = 0;
		Point p = new Point(goal.getX(), goal.getY());
		visited[p.y][p.x] = true;
		curLevel.add(p);
		while(curLevel.size() != 0) {
			Point cur = curLevel.remove();
			grid[cur.y][cur.x] = curDistance;
			for (int i = -1; i < 2; i ++) {
				for (int j = -1; j < 2; j++) {
					if (i == 0 && j == 0) continue;
					if (inRange (cur.x + i, cur.y + j) && isValid(cur.x + i, cur.y + j)) {
						Point t = new Point(cur.x + i, cur.y + j);
						visited[t.y][t.x] = true;
						nextLevel.add(t);
					}
				}
			}
			if (curLevel.size() == 0) {
				curDistance++;
				curLevel = nextLevel;
				nextLevel = new LinkedList<Point>();
			}
		}
	}

	public void generateVectorField() {
		for (int i = 0; i < height; i ++) {
			for (int j = 0; j < width; j ++) {
				if (!notObstacle(i, j)) continue;
				int min = Integer.MAX_VALUE;
				Point grad = new Point(0, 0);
				for (int r = -1; r < 2; r++) {
					for (int c = -1; c < 2; c++) {
						if (r == 0 && c == 0) continue;
						if (inRange(i + r, j + c) && grid[i + r][j + c] < min) {
							min = grid[i + r][j + c];
							grad.x = c;
							grad.y = r;
						}
					}
				}
				vectorField[j][i] = grad;
			}
		}
	}
	
	public void printVectorField() {
		
		for (int i = 0; i < height; i ++) {
			for (int j = 0; j < width; j ++) {
				System.out.print("(" + vectorField[j][i].x + " " + vectorField[j][i].y + ")");
			}
			System.out.println();
		}
	}
	
	public Boolean finished() {
		for (Robot r : robots) {
			if (!r.reachedGoal()) return false;
		}
		return true;
	}
}

