import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;


public class MenuPanel extends JPanel{
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(255, 255, 255, 40));
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
