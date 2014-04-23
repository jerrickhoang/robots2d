import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class FlowField implements Algorithm{

	public Field field;
	
	public int[][] grid;
	public Point[][] vectorField;
	public Boolean[][] visited;
	
	public FlowField(Field field) {
		this.field = field;
		initGrid(field.width, field.height);
		initVectorField(field.width, field.height);
		initVisitedGrid(field.width, field.height);
	}
	
	public void waveFront() {
		Queue<Point> curLevel = new LinkedList<Point>();
		Queue<Point> nextLevel = new LinkedList<Point>();
		int curDistance = 0;
		Point p = new Point(field.goal.getX(), field.goal.getY());
		visited[p.y][p.x] = true;
		curLevel.add(p);
		while(curLevel.size() != 0) {
			Point cur = curLevel.remove();
			grid[cur.y][cur.x] = curDistance;
			for (int i = -1; i < 2; i ++) {
				for (int j = -1; j < 2; j++) {
					if (i == 0 && j == 0) continue;
					if (field.inRange (cur.x + i, cur.y + j) && isValid(cur.x + i, cur.y + j)) {
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
		for (int i = 0; i < field.height; i ++) {
			for (int j = 0; j < field.width; j ++) {
				if (!notObstacle(j, i)) continue;
				int min = Integer.MAX_VALUE;
				Point grad = new Point(0, 0);
				for (int r = -1; r < 2; r++) {
					for (int c = -1; c < 2; c++) {
						if (r == 0 && c == 0) continue;
						if (field.inRange(i + r, j + c) && notObstacle(j + c, i + r) && grid[i + r][j + c] < min) {
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
		
		for (int i = 0; i < field.height; i ++) {
			for (int j = 0; j < field.width; j ++) {
				System.out.print("(" + vectorField[j][i].x + " " + vectorField[j][i].y + ")");
			}
			System.out.println();
		}
	}
	
	public void printGrid() {
		for (int i = 0; i < field.height; i ++) {
			for (int j = 0; j < field.width; j++) {
				System.out.print("  " + grid[i][j]);
			}
			System.out.println("");
		}
		
	}
	
	public List<Solution> solve() {
		
		waveFront(); generateVectorField();
		
		List<Solution> solutionList = new ArrayList<Solution>();
		for (Robot r : field.robots) {
			Solution solution = solve(r);
			if (solution != null) solutionList.add(solution);
		}
		return solutionList;
	}
	
	public Solution solve(Robot r) {
		Solution sol = new Solution();
		Point start = new Point(r.getX(), r.getY());
		Point end = start;
		
		sol.add(start);
		while (end.x != field.goal.getX() && end.y != field.goal.getY()) {
			int x = start.x;
			int y = start.y;
			Point next = new Point(x + vectorField[y][x].x, y + vectorField[y][x].y);
			sol.add(next);
		}
		return sol;
	}
	
	private void initVectorField(int width, int height) {
		vectorField = new Point[height][width];
		for (int i = 0; i < height; i ++) {
			for (int j = 0; j < width; j ++) {
				vectorField[j][i] = new Point(0, 0);
			}
		}
	}
	
	private void initVisitedGrid(int width, int height) {
		visited = new Boolean[height][width];
		for (int i = 0; i < height; i ++) {
			for (int j = 0; j < width; j ++) {
				visited[j][i] = false;
			}
		}
	}
	
	private void initGrid(int width, int height) {
		grid = new int[height][width];
		for (int i = 0; i < height; i ++) {
			for (int j = 0; j < width; j++) {
				grid[i][j] = 0;
			}
		}
	}
	
	private Boolean isVisited(int x, int y) {
		return visited[y][x];
	}
	
	private Boolean isValid(int x, int y) {
		return !isVisited(x, y) && notObstacle(x, y);
	}
	
	private Boolean notObstacle(int x, int y) {
		for (int i = 0; i < field.obstacles.length; i ++) {
			if (field.obstacles[i].getX() == x && field.obstacles[i].getY() == y)
				return false;
		}
		return true;
	}

}
