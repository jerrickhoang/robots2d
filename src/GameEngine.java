
public class GameEngine {
	
	public final static int FIELD_WIDTH = 20;
	public final static int FIELD_HEIGHT = 20;
	
	public static void main(String[] args) {
		final Field field = new Field(FIELD_WIDTH, FIELD_HEIGHT);
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ProgramGUI p = new ProgramGUI(field);
			}
		});
	}
}
