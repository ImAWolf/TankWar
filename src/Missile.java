import java.awt.Color;
import java.awt.Graphics;


public class Missile {
	private static final int XSPEED = 10;
	private static final int YSPEED = 10;
	public static final int MISSILEWIDTH = 10;
	public static final int MISSILEHIGHT = 10;
	private int x;
	private int y;
	Tank.Direction dir;
		
	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.black);
		g.fillOval(x, y, MISSILEWIDTH, MISSILEHIGHT);
		g.setColor(c);
		
		move();
	}

	private void move() {
		switch (dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;		
		}
	}
	
}
