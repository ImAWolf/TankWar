import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Tank {
	// 坦克位置
	private int x;
	private int y;
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	public static final int TANKWIDTH = 30;
	public static final int TANKHIGHT = 30;
	private boolean bL = false;
	private boolean bU = false;
	private boolean bD = false;
	private boolean bR = false;

	public TankFrame tf;
	
	public enum Direction {
		L, LU, U, RU, R, RD, D, LD, STOP
	};

	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.D;
	
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Tank(int x, int y,TankFrame tf) {
		this.x = x;
		this.y = y;
		this.tf = tf;
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.red);
		g.fillOval(x, y, TANKWIDTH, TANKHIGHT);
		g.setColor(c);
		
		switch (ptDir) {
		case L:
			g.drawLine(x+Tank.TANKWIDTH/2, y+Tank.TANKHIGHT/2, x, y+Tank.TANKHIGHT/2);
			break;
		case LU:
			g.drawLine(x+Tank.TANKWIDTH/2, y+Tank.TANKHIGHT/2, x, y);
			break;
		case U:
			g.drawLine(x+Tank.TANKWIDTH/2, y+Tank.TANKHIGHT/2, x+Tank.TANKWIDTH/2, y);
			break;
		case RU:
			g.drawLine(x+Tank.TANKWIDTH/2, y+Tank.TANKHIGHT/2, x+Tank.TANKWIDTH, y);
			break;
		case R:
			g.drawLine(x+Tank.TANKWIDTH/2, y+Tank.TANKHIGHT/2, x+Tank.TANKWIDTH, y+Tank.TANKHIGHT/2);
			break;
		case RD:
			g.drawLine(x+Tank.TANKWIDTH/2, y+Tank.TANKHIGHT/2, x+Tank.TANKWIDTH, y+Tank.TANKHIGHT);
			break;
		case D:
			g.drawLine(x+Tank.TANKWIDTH/2, y+Tank.TANKHIGHT/2, x+Tank.TANKWIDTH/2, y+Tank.TANKHIGHT);
			break;
		case LD:
			g.drawLine(x+Tank.TANKWIDTH/2, y+Tank.TANKHIGHT/2, x, y+Tank.TANKHIGHT);
			break;
		case STOP:
			break;

		}
		
		move();
	}

	public void move() {
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
		case STOP:
			break;

		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {		
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		locateDirection();
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		locateDirection();
	}


	//重新定位方向
	public void locateDirection() {
		if (bL && !bU && !bR && !bD) {
			dir = Direction.L;
		} else if (bL && bU && !bR && !bD) {
			dir = Direction.LU;
		} else if (!bL && bU && !bR && !bD) {
			dir = Direction.U;
		} else if (!bL && bU && bR && !bD) {
			dir = Direction.RU;
		} else if (!bL && !bU && bR && !bD) {
			dir = Direction.R;
		} else if (!bL && !bU && bR && bD) {
			dir = Direction.RD;
		} else if (!bL && !bU && !bR && bD) {
			dir = Direction.D;
		} else if (bL && !bU && !bR && bD) {
			dir = Direction.LD;
		} else if (!bL && !bU && !bR && !bD) {
			dir = Direction.STOP;
		} else if (bL && !bU && bR && !bD) { 
			dir = Direction.STOP;
		} else if (!bL && bU && !bR && bD) { 
			dir = Direction.STOP;
		} else if (bL && bU && bR && bD) { 
			dir = Direction.STOP;
		}
		
		if(this.dir != Direction.STOP)
			ptDir = dir;
	}

	public Missile fire() {
		int x = this.x + Tank.TANKWIDTH/2 - Missile.MISSILEWIDTH/2; 
		int y = this.y + Tank.TANKHIGHT/2 - Missile.MISSILEHIGHT/2;
		
		Missile m = new Missile(x,y,ptDir,tf);
		tf.missiles.add(m);
		return m;
	}
	
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

	
}
