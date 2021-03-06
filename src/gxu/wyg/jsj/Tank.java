package gxu.wyg.jsj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

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
	public boolean bGood;
	public boolean live = true;
	public int oldX,oldY;
	public Direction oldDir;
	public int life = 100;
	private BloodBar bb = new BloodBar();
	
	public static Random r = new Random();
	private int step = r.nextInt(12) + 3;
	
	public enum Direction {
		L, LU, U, RU, R, RD, D, LD, STOP
	};

	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.D;
	
	public Tank(int x, int y,boolean bGood) {
		this.x = x;
		this.y = y;
		this.bGood = bGood;
		this.oldX = x;
		this.oldY = y;
	}
	
	public Tank(int x, int y,boolean bGood,TankFrame tf) {
		this(x,y,bGood);
		this.tf = tf;
	}
	
	public Tank(int x, int y,boolean bGood,Direction dir,TankFrame tf) {
		this(x,y,bGood,tf);
		this.dir = dir;
		 
	}

	public void draw(Graphics g) {
		if(live != true) {
			if(bGood == false) {
				tf.tanks.remove(this);
			}
			return;
		}
		
		Color c = g.getColor();
		if(bGood)
			g.setColor(Color.red);
		else 
			g.setColor(Color.white);
		if(bGood == true) 
			bb.draw(g);
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
		this.oldX = x;
		this.oldY = y;
		
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
		
		int borderL = 3;
		int borderU = 25;
		int borderR = TankFrame.GAME_WIDTH - TANKWIDTH - 3;
		int borderD = TankFrame.GAME_HIGHT - TANKHIGHT - 3;
		
		if(x < borderL) {
			x = 3;
		}
		if(y < borderU) {
			y = borderU;
		}
		if(x > borderR) {
			x = borderR;
		}
		if(y > borderD) {
			y = borderD;
		}
		
		if(!bGood) {
			Direction[] dirs = Direction.values();
			
			if(step == 0) {
				int rn = r.nextInt(dirs.length);
				this.dir = dirs[rn];			
				if(this.dir != Direction.STOP) //使生成的子弹可以飞行
					this.ptDir = this.dir;
				
				step = r.nextInt(12) + 3;
			}		
			step --;	
			if(r.nextInt(40) > 38 ) //机器坦克随机开火
				this.fire();
		}
				
	}

	public void keyPressed(KeyEvent e) {
		//if(this.live == false) return;
		oldDir = this.dir; 
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_F2: //主战坦克满血复活
			if(!this.live) {
				this.live = true;
				this.life = 100;
				this.dir = Direction.STOP;
			}
			break;
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
		if(this.live == false) return;
		
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
		case KeyEvent.VK_A:
			superFire();
			break;
		}
		locateDirection();
	}


	//重新定位方向
	public void locateDirection() {
		if(this.live == false) {
			bL = bU = bR = bD = false;
		}
		
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
		oldDir = this.dir;
		if(this.dir != Direction.STOP)
			ptDir = dir;
	}

	public Missile fire() {
		if(!live) {
			return null;
		}
		
		int x = this.x + Tank.TANKWIDTH/2 - Missile.MISSILEWIDTH/2; 
		int y = this.y + Tank.TANKHIGHT/2 - Missile.MISSILEHIGHT/2;
		
		Missile m = new Missile(x,y,this.bGood,ptDir,tf);
		tf.missiles.add(m);
		return m;
	}
	
	public Missile fire(Direction dir) {
		if(!live) {
			return null;
		}
		
		int x = this.x + Tank.TANKWIDTH/2 - Missile.MISSILEWIDTH/2; 
		int y = this.y + Tank.TANKHIGHT/2 - Missile.MISSILEHIGHT/2;
		
		Missile m = new Missile(x,y,this.bGood,dir,tf);
		
		return m;
	}
	
	public void superFire() {
		Direction[] dirs = Direction.values();
		
		for(int i=0; i<8; i++) {
			Missile m = fire(dirs[i]);
			tf.missiles.add(m);
		}
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,TANKWIDTH,TANKHIGHT);
	}
	
	public boolean colliedWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.stay();			
			return true;
		}		
		return false;
	}
	
	public boolean colledesWithTanks(List<Tank> tanks) {
		for(int i=0; i<tanks.size(); i++) {
			Tank t = tanks.get(i);
			if(this != t) {
				if(this.live && t.live && this.getRect().intersects(t.getRect())) {
					this.stay();	
					t.stay();
					return true;
				}		
			}
		}		
		return false;
	}
	
	private void stay() {
		x = oldX;
		y = oldY;
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
	
	private class BloodBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.red); 
			g.drawRect(x, y-10, TANKWIDTH, 10);
			int w = TANKWIDTH * life/100;
			g.fillRect(x,y-10,w,10);
			g.setColor(c);
		}
	}
	
	public boolean eat(Blood b) {

		if(this.live && b.live && this.getRect().intersects(b.getRect())) {
			this.life = 100;
			b.live = false;
			return true;
		}		
		return false;
	
	}
}
