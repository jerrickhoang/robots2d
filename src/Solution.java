import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public class Solution {
	
	public List<Point> path;
	
	public Solution() {
		path = new ArrayList<Point>();
	}
	
	public void add(Point p) {
		path.add(p);
	}
	
	public Point peek() {
		return path.get(path.size() - 1);
	}
	
	public Point remove() {
		return path.remove(path.size() - 1);
	}
}
