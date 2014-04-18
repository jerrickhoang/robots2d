
public class GameEngine {
	
	public static void main(String[] args) {
		Field field = new Field(10, 10);
		field.runWaveFront();
		field.printGrid();
	}
}
