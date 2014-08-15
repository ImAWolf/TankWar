import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class Missile {
	private static final int XSPEED = 10;
	private static final int YSPEED = 10;
	public static final int MISSILEWIDTH = 10;
	public static final int MISSILEHIGHT = 10;
	private int x;
	private int y;
	public Tank.Direction dir;
	private boolean live = true;
	private TankFrame tf;
	public boolean good = true;
		
	public Missile(int x, int y, Tank.Direction ptDir) {
		this.x = x;
		this.y = y;
		this.dir = ptDir;
	}
	
	public Missile(int x, int y,boolean good, Tank.Direction ptDir,TankFrame tf) {
		this.x = x;
		this.y = y;
		this.dir = ptDir;
		this.tf = tf;
		this.good = good;
	}
	
	public void draw(Graphics g) {
		if(!live) {
			tf.missiles.remove(this);
		}
		
		Color c = g.getColor();
		if(this.good == true)
			g.setColor(Color.black);
		else
			g.setColor(Color.blue);
		g.fillOval(x, y, MISSILEWIDTH, MISSILEHIGHT);
		g.setColor(c);
		
		move();
	}
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
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
		
		if(x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HIGHT) {
			live = false;			 
		}	
	}
	//Åö×²¼ì²â¸¨ÖúÀà
	public Rectangle getRect() {
		return new Rectangle(x,y,MISSILEWIDTH,MISSILEHIGHT);
	}
	

	public boolean hitTank(Tank t) {
		if(this.live && this.getRect().intersects(t.getRect()) 
				&& t.live && this.good != t.bGood) {
			if(t.bGood == true) {
				t.life -= 20;
				if(t.life <= 0)
					t.live = false;
			} else {
				t.live = false;
			}			 
			this.live = false;
			
			Explode e = new Explode(x,y,this.tf);			
			tf.explodes.add(e);
			
			return true;
		}
		
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks) {
		for(int i=0; i<tanks.size(); i++) {
			Tank t = tanks.get(i);
			if(hitTank(t)) {				
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hitWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			
			return true;
		}
		
		return false;
	}	
}
