package gxu.wyg.jsj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;


public class Blood {
	private int x,y;
	private int w = 15,h = 15;
	TankFrame tf = null;
	int step = 0;
	public boolean live = true;
	private Random r = new Random();
	
	private int[][] pos= {
			{350,300},{360,300},{380,336},{265,378},
			{110,360},{257,364},{78,65},{45,123}
	};
	
	public Blood() {
		x = pos[0][0];
		y = pos[0][1];
	}
	
	public Blood(int x, int y, int w, int h, TankFrame tf) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tf = tf;
	}

	public void draw(Graphics g) {
		if(!live) return;
		
		Color c =g.getColor();
		g.setColor(Color.magenta);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		
		move();
	}

	private void move() {
		if(r.nextInt(50) > 45) {
			step ++;
		}
		if(step == pos.length) {
			step = 0;
		}
		x = pos[step][0];
		y = pos[step][1];
		
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,w,h);
	}
}
