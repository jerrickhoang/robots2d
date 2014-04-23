
public class GameEngine {
	
	public static void main(String[] args) {
		final Field field = new Field(10, 10);
		FlowField flowField = new FlowField(field);
		flowField.waveFront();
		flowField.generateVectorField();
		
		
		flowField.printGrid();
		flowField.printVectorField();
		if (flowField.solve() == null) System.out.println("cannot solve");
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ProgramGUI p = new ProgramGUI(field);
			}
		});
	}
}
