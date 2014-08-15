import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class Tank {
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	//Ì¹¿ËÎ»ÖÃ
	private int x;
	private int y;
	
	public Tank(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.red);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_LEFT:
			x -= 5;
			break;
		case KeyEvent.VK_UP:
			y -= 5;
			break;
		case KeyEvent.VK_RIGHT:
			x += 5;
			break;
		case KeyEvent.VK_DOWN:
			y += 5;
			break;
		}
	}
	
}
