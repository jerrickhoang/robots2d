
public class GameEngine {
	
	public static void main(String[] args) {
		final Field field = new Field(20, 20);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ProgramGUI p = new ProgramGUI(field);
			}
		});
	}
}
