import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
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
			g.drawLine(convertX(0), convertY(i), convertX(field.width), convertY(i));
		}
		for (int i = 0; i < field.width + 1; i ++) {
			g.drawLine(convertX(i), convertY(0), convertX(i), convertY(field.height));
		}
		displayRobots(g);
		displayGoal(g);
		displayObstacles(g);
	}
	
	public void displayRobots(Graphics g) {
		
	}
	
	public void displayGoal(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(convertX(field.goal.getX()), convertY(field.goal.getY()), 
				   FIELD_SQUARE_SIZE, FIELD_SQUARE_SIZE);
	}
	
	public void displayObstacles(Graphics g) {
		g.setColor(Color.gray);
		for (Obstacle ob : field.obstacles) {
			g.fillRect(convertX(ob.getX()), convertY(ob.getY()), 
					   FIELD_SQUARE_SIZE, FIELD_SQUARE_SIZE);
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

	public Point getCenter(int row, int col) {
		int x = convertX(row);
		int y = convertY(col);
		return new Point(x + FIELD_SQUARE_SIZE/2, y + FIELD_SQUARE_SIZE/2);
	}
	
	public int convertX(int col) {
		return FIELD_POSITION_X + col * FIELD_SQUARE_SIZE;
	}
	
	public int convertY(int row) {
		return FIELD_POSITION_Y + row * FIELD_SQUARE_SIZE;
	}
}
