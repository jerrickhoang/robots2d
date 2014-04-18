import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class ProgramGUI extends JFrame {
	
	private static final int WIDTH_OF_FRAME = 1000;
	private static final int HEIGHT_OF_FRAME = 800;
	
	private static final int FIELD_POSITION_X = 100;
	private static final int FIELD_POSITION_Y = 100;
	private static final int FIELD_SQUARE_SIZE = 30;
	private Field field;

	public ProgramGUI(Field field) {
		super("Robots2D");
		setBounds (0, 0, WIDTH_OF_FRAME, HEIGHT_OF_FRAME);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.field = field;
		
		JPanel pane = new JPanel();
		add(pane, BorderLayout.CENTER);
		pane.setBackground(Color.BLACK);
		pane.addMouseListener(new MouseHandler());
		pane.addMouseMotionListener(new MouseHandler());
		
		
		setVisible(true);
	}
	
	public void displayField(Graphics g) {
		g.setColor(Color.gray);
		for (int i = 0; i < field.height + 1; i ++) {
			g.drawLine(FIELD_POSITION_X, FIELD_POSITION_Y + i * FIELD_SQUARE_SIZE, 
					   FIELD_POSITION_X + (field.width) * FIELD_SQUARE_SIZE, FIELD_POSITION_Y + i * FIELD_SQUARE_SIZE);
		}
		for (int i = 0; i < field.width + 1; i ++) {
			g.drawLine(FIELD_POSITION_X + i * FIELD_SQUARE_SIZE, FIELD_POSITION_Y,
					   FIELD_POSITION_X + i * FIELD_SQUARE_SIZE, FIELD_POSITION_Y + (field.height) * FIELD_SQUARE_SIZE);
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		displayField(g);
		
	}
	
	public class MouseHandler implements MouseListener, MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
}
