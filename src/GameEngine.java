
public class GameEngine {
	
	public static void main(String[] args) {
		final Field field = new Field(10, 10);
		field.runWaveFront();
		field.generateVectorField();
		field.printGrid();
		field.printVectorField();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//ProgramGUI p = new ProgramGUI(field);
			}
		});
	}
}
