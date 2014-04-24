import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;


public class Astar implements Algorithm{

	public Boolean[][] visited;
	public Field field;
	
	public Astar(Field field) {
		this.field = field;
		initVisitedGrid();
	}
	
	public void initVisitedGrid(){
		visited = new Boolean[field.height][field.width];
		for (int i = 0; i < field.height; i++) {
			for (int j = 0; j < field.width; j++) {
				visited[j][i] = false;
			}
		}
	}
	
	public double heuristic(int i, int j) {
		int D = 1; double D2 = Math.sqrt(D);
		int dx = (i - field.goal.getX());
		int dy = (j - field.goal.getY());
		return D * (dx + dy) + (D2 - 2 * D) * Math.min(dx, dy);
	}
	
	@Override
	public List<Solution> solve(ProgramGUI view) {
		List<Solution> sols = new ArrayList<Solution>();
		for (Robot r : field.robots) {
			Solution s = solve(r, view);
			sols.add(s);
		}
		return sols;
	}

	@Override
	public Solution solve(Robot r, ProgramGUI view) {
		// TODO Auto-generated method stub
		PriorityQueue<Node> start = new PriorityQueue<Node>(11, new Comparator<Node>() {
			public int compare(Node n1, Node n2) {
				return (int) (n1.fcost() - n2.fcost());
			}
		});
		HashSet<Node> closed = new HashSet<Node>();
		start.add(new Node(r.getX(), r.getY(), 0, null));
		Node cur;
		while(start.size() != 0) {
			cur = start.remove();
			closed.add(cur);
			setVisited(cur.x, cur.y, true, view);
			if (cur.isGoal())
				return reconstructPath(cur);
			
			for (Node neighbor : neighborsOf(cur)) {
				double cost = cur.gcost + movementCost(cur, neighbor);
				if (!start.contains(cur) || cost < neighbor.gcost) {
					neighbor.parent = cur;
					neighbor.gcost = cost;
					if (!start.contains(cur)) start.add(neighbor);
				}
			}
		}
		
		return null;
	}
	
	public Solution reconstructPath(Node n) {
		System.out.println("Path = ");
		Solution s = new Solution();
		while(!n.isRobot()) {
			s.path.add(new Point(n.x, n.y));
			System.out.println("x = " + n.x + " y = " + n.y);
			n = n.parent;
		}
		return s;
	}

	public Iterable<Node> neighborsOf(Node cur) {
		ArrayList<Node> neighbors = new ArrayList<Node>();
		for (int i = -1; i < 2; i ++) {
			for (int j = -1; j < 2; j ++) {
				int x = cur.x + i; int y = cur.y + j;
				if (isValidNeighbor(x, y))
					neighbors.add(new Node(x, y, 0, cur));
			}
		}
		return neighbors;
	}
	
	public double movementCost(Node cur, Node neighbor) {
		if (cur.x == neighbor.x || cur.y == neighbor.y) return 1;
		return Math.sqrt(2);
	}
	
	public boolean isValidNeighbor(int x, int y) {
		return field.inRange(x, y) && !field.isObstacle(x, y) && !visited[y][x];
	}

	@Override
	public Boolean[][] getVisitedSquares() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVisited(int i, int j, boolean b, ProgramGUI view) {
		// TODO Auto-generated method stub
		visited[j][i] = true;
		
		if (b) {
			//view.displayVisitedSquare();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			view.paintSquare(j, i, Color.blue);
			view.displayGoal();
			view.displayRobots();
			view.displayGridLines();
		}
		
	}
	
	public class Node {
		
		int x;
		int y;
		Node parent;
		double gcost;
		double hcost;
		double fcost;
		
		public Node (int x, int y, double gcost, Node parent) {
			this.x = x;
			this.y = y;
			this.gcost = gcost;
			this.parent = parent;
		}
		
		public boolean equals(Node cur) {
			if (cur == null) return false;
			return cur.x == this.x && cur.y == this.y;
		}
		
		public double fcost() {
			return gcost + hcost;
		}
		
		public double hcost() {
			int D = 1; double D2 = Math.sqrt(D);
			int dx = (x - field.goal.getX());
			int dy = (y - field.goal.getY());
			return D * (dx + dy) + (D2 - 2 * D) * Math.min(dx, dy);
		}
		
		public boolean isGoal() {
			return (x == field.goal.getX()) && (y == field.goal.getY());
		}
		
		public boolean isRobot() {
			for (Robot r : field.robots) {
				if (isRobot(r)) return true;
			}
			return false;
		}
		
		public boolean isRobot(Robot r) {
			return (x == r.getX()) && (y == r.getY());
		}
	}

	
}
