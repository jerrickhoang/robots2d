import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ProgramGUI extends JFrame {
	
	private static final int WIDTH_OF_FRAME = 1000;
	private static final int HEIGHT_OF_FRAME = 800;
	
	private static final int FIELD_POSITION_X = 100;
	private static final int FIELD_POSITION_Y = 100;
	private static final int FIELD_SQUARE_SIZE = 30;
	
	private Field field;
	private List<Solution> solutions;
	
	public ProgramGUI(Field field) {
		super("Robots2D");
		setBounds (0, 0, WIDTH_OF_FRAME, HEIGHT_OF_FRAME);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.addMouseListener(new MouseHandler());
		this.addMouseMotionListener(new MouseHandler());
		
		this.field = field;
		solutions = null;
		
		JPanel pane = new JPanel();
		add(pane, BorderLayout.CENTER);
		pane.setBackground(Color.BLACK);
		JButton button = new JButton("Find Path");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				findPath();
			}
		});
		pane.add(button);
		
		setVisible(true);
		
	}
	
	public void findPath() {
		Algorithm flowField = new FlowField(this.field);
		solutions = flowField.solve();
		repaint();
	}
	
	public void visualizeSolutions(List<Solution> solutions, Graphics g) {
		for (Solution s : solutions) 
			visualizeSolution(s, g);
	}
	
	public void visualizeSolution(Solution s, Graphics g) {
		g.setColor(Color.yellow);
		for (int i = 0; i < s.path.size() - 1; i++) {
			System.out.println("drawing path");
			Point cur = getCenter(s.path.get(i).x, s.path.get(i).y);
			Point next = getCenter(s.path.get(i+1).x, s.path.get(i+1).y);
			g.drawLine(cur.x, cur.y, next.x, next.y);
		}
	}
	
	public void displayField(Graphics g) {
		g.setColor(Color.gray);
		for (int i = 0; i < field.height + 1; i ++) {
			g.drawLine(colToX(0), RowToY(i), colToX(field.width), RowToY(i));
		}
		for (int i = 0; i < field.width + 1; i ++) {
			g.drawLine(colToX(i), RowToY(0), colToX(i), RowToY(field.height));
		}
		displayRobots(g);
		displayGoal(g);
		displayObstacles(g);
	}
	
	public void displayRobots(Graphics g) {
		if (field.robots == null) return;
		g.setColor(Color.red);
		for (Robot r: field.robots) {
			g.fillOval(colToX(r.getX()), RowToY(r.getY()), 
					   FIELD_SQUARE_SIZE, FIELD_SQUARE_SIZE);
		}
	}
	
	public void displayGoal(Graphics g) {
		if (field.goal == null) return;
		g.setColor(Color.green);
		g.fillRect(colToX(field.goal.getX()), RowToY(field.goal.getY()), 
				   FIELD_SQUARE_SIZE, FIELD_SQUARE_SIZE);
	}
	
	public void displayObstacles(Graphics g) {
		if (field.obstacles == null) return;
		g.setColor(Color.gray);
		for (Obstacle ob : field.obstacles) {
			g.fillRect(colToX(ob.getX()), RowToY(ob.getY()), 
					   FIELD_SQUARE_SIZE, FIELD_SQUARE_SIZE);
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		displayField(g);
		
		if (solutions != null) {
			visualizeSolutions(solutions, g);
		}
		
	}
	
	public Point getCenter(int row, int col) {
		int x = colToX(row);
		int y = RowToY(col);
		return new Point(x + FIELD_SQUARE_SIZE/2, y + FIELD_SQUARE_SIZE/2);
	}
	
	public int colToX(int col) {
		return FIELD_POSITION_X + col * FIELD_SQUARE_SIZE;
	}
	
	public int RowToY(int row) {
		return FIELD_POSITION_Y + row * FIELD_SQUARE_SIZE;
	}
	
	public int XToCol(int x) {
		System.out.println("x = " + x);
		return (x - FIELD_POSITION_X) / FIELD_SQUARE_SIZE;
	}
	
	public int YToRow(int y) {
		return (y - FIELD_POSITION_Y) / FIELD_SQUARE_SIZE;
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
			System.out.println("Cliekd at " + XToCol(e.getX()) + " " + YToRow(e.getY()));
			int x = XToCol(e.getX()); int y = YToRow(e.getY());
			if (field.isObstacle(x, y)) {
				field.removeObstacle(x, y);
				field.printObstacles();
				repaint();
			} else {
				if (field.addObstacle(XToCol(e.getX()), YToRow(e.getY()))) {
					field.printObstacles();
					repaint();
				}
				else System.out.println("could not add obstacle");
			}
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
			System.out.println("released at " + e.getX() + " " + e.getY());
		}
		
		
	}

}
