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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;


public class ProgramGUI extends JFrame {
	
	private static final int WIDTH_OF_FRAME = 1000;
	private static final int HEIGHT_OF_FRAME = 800;
	
	private static final int FIELD_POSITION_X = 100;
	private static final int FIELD_POSITION_Y = 100;
	private static final int FIELD_SQUARE_SIZE = 30;
	
	//private static final int DELAY_TIME = 1000;
	
	private Field field;
	private List<Solution> solutions;
	private Algorithm currentAlgo;
	
	
	public int pressedRobot = -1;
	public Boolean pressedGoal = false;
	
	
	
	public ProgramGUI(Field field) {
		super("Robots2D");
		
//		try {
//			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
		this.field = field;
		solutions = null;
		
		setBounds (0, 0, WIDTH_OF_FRAME, HEIGHT_OF_FRAME);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		
		this.addMouseListener(new MouseHandler());
		this.addMouseMotionListener(new MouseHandler());
		
		// Main panel
		JPanel pane = new JPanel();
		add(pane, BorderLayout.CENTER);
		
		// Menu panel
		MenuPanel menuPanel = new MenuPanel();
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
		menuPanel.setOpaque(false);
		//menuPanel.setBackground(new Color(255, 255, 255, 70));
		add(menuPanel, BorderLayout.EAST);
		
		JButton button = new JButton("Find Path");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				findPath();
			}
		});
		menuPanel.add(button);
		
		setVisible(true);
		
	}
	
	public void findPath() {
		repaint();
		currentAlgo = new FlowField(this.field);
		solutions = currentAlgo.solve(this);

	}
	
	public void visualizeSolutions(List<Solution> solutions) {
		for (Solution s : solutions) 
			visualizeSolution(s);
	}
	
	public void visualizeSolution(Solution s) {
		Graphics g = getGraphics();
		g.setColor(Color.yellow);
		System.out.println("path size = " + s.path.size());
		for (int i = 0; i < s.path.size() - 1; i++) {
			Point cur = getCenter(s.path.get(i).x, s.path.get(i).y);
			Point next = getCenter(s.path.get(i+1).x, s.path.get(i+1).y);
			System.out.println("drawing path from " + XToCol(cur.x) + " " + YToRow(cur.y) + 
					           " to " + XToCol(next.x) + " " + YToRow(next.y));
			g.drawLine(cur.x, cur.y, next.x, next.y);
		}
	}
	
	public void displayField(Graphics g) {
		//displayVisitedSquares();
		displayRobots();
		displayGoal();
		displayObstacles();
		displayGridLines();
	}
	
	public void displayGridLines() {
		Graphics g = getGraphics();
		g.setColor(Color.gray);
		for (int i = 0; i < field.height + 1; i ++) {
			g.drawLine(colToX(0), RowToY(i), colToX(field.width), RowToY(i));
		}
		for (int i = 0; i < field.width + 1; i ++) {
			g.drawLine(colToX(i), RowToY(0), colToX(i), RowToY(field.height));
		}
		
	}
	
	public void displayRobots() {
		Graphics g = getGraphics();
		if (field.robots == null) return;
		g.setColor(Color.red);
		for (Robot r: field.robots) {
			g.fillOval(colToX(r.getX()), RowToY(r.getY()), 
					   FIELD_SQUARE_SIZE, FIELD_SQUARE_SIZE);
		}
	}
	
	public void displayGoal() {
		if (field.goal == null) return;
		Graphics g = getGraphics();
		g.setColor(Color.green);
		g.fillRect(colToX(field.goal.getX()), RowToY(field.goal.getY()), 
				   FIELD_SQUARE_SIZE, FIELD_SQUARE_SIZE);
	}
	
	public void displayObstacles() {
		if (field.obstacles == null) return;
		Graphics g = getGraphics();
		g.setColor(Color.gray);
		for (Obstacle ob : field.obstacles) {
			g.fillRect(colToX(ob.getX()), RowToY(ob.getY()), 
					   FIELD_SQUARE_SIZE, FIELD_SQUARE_SIZE);
		}
	}
	
	public void displayVisitedSquares() {
		if (currentAlgo == null) return;
		if (currentAlgo.getVisitedSquares() == null) return;
		Graphics g = getGraphics();
		System.out.println("displaying visited squares");
		g.setColor(Color.blue);
		for (int i = 0; i < field.height; i++) {
			for (int j = 0; j < field.width; j ++) {
				if (currentAlgo.getVisitedSquares()[i][j]) {
					g.fillRect(colToX(j), RowToY(i), FIELD_SQUARE_SIZE, FIELD_SQUARE_SIZE);
				}
			}
		}
	}
	
	public void paintSquare(int i, int j, Color c) {
		Graphics g = getGraphics();
		g.setColor(c);
		g.fillRect(colToX(i), RowToY(j), FIELD_SQUARE_SIZE, FIELD_SQUARE_SIZE);
	}
	
	public void paint(Graphics g) {
		System.out.println("repainting");
		super.paint(g);
		displayField(g);
		
		if (solutions != null) {
			visualizeSolutions(solutions);
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
			System.out.println("Dragging at " + e.getX() + " " + e.getY());
			int x = XToCol(e.getX()); int y = YToRow(e.getY());
			if (pressedGoal) {
				System.out.println("dragging goal");
				field.goal.setX(x);
				field.goal.setY(y);
				repaint();
			}
			if (pressedRobot != -1) {
				field.robots.get(pressedRobot).setX(x);
				field.robots.get(pressedRobot).setY(y);
				repaint();
			}
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
			System.out.println("pressed at " + e.getX() + " " + e.getY());
			int x = XToCol(e.getX()); int y = YToRow(e.getY());
			pressedGoal = field.isGoal(x, y);
			pressedRobot = field.getRobotByCoordinate(x, y);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("released at " + e.getX() + " " + e.getY());
			pressedGoal = false; 
			pressedRobot = -1;
		}
		
		
	}



}
