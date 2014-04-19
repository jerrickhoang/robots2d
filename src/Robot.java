
public class Robot {
	private int x;
	private int y;
	private Goal goal;
	
	public Robot(int x, int y, Goal goal) {
		this.x = x;
		this.y = y;
		this.goal = goal;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
