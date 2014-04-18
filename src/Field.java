import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class Field {
	public int[][] grid;
	public Boolean[][] visited;
	public Goal goal;
	public Robot[] robots;
	private int width;
	private int height;
	
	public Field(int width, int height) {
		this.width = width;
		this.height = height;
		initGrid(width, height);
		initRandomGoal();
	}
	
	private void initGrid(int width, int height) {
		grid = new int[width][height];
		visited = new Boolean[width][height];
		for (int i = 0; i < width; i ++) {
			for (int j = 0; j < height; j++) {
				grid[i][j] = 0;
				visited[i][j] = false;
			}
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
		return visited[x][y];
	}
	
	public void printGrid() {
		for (int i = 0; i < width; i ++) {
			for (int j = 0; j < height; j++) {
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
		visited[p.x][p.y] = true;
		curLevel.add(p);
		while(curLevel.size() != 0) {
			Point cur = curLevel.remove();
			grid[cur.x][cur.y] = curDistance;
			for (int i = -1; i < 2; i ++) {
				for (int j = -1; j < 2; j++) {
					if (i == 0 && j == 0) continue;
					if (inRange (cur.x + i, cur.y + j) && !isVisited(cur.x + i, cur.y + j)) {
						Point t = new Point(cur.x + i, cur.y + j);
						visited[t.x][t.y] = true;
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
}

