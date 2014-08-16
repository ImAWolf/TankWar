package gxu.wyg.jsj;

import java.awt.Color;
import java.awt.Graphics;


public class Explode {
	private int x;
	private int y;
	private boolean live = true;
	private TankFrame tf;
	
	private int step = 0;
	int[] diameter = {4,7,12,18,26,32,49,30,14,6};
	
	public Explode(int x,int y,TankFrame tf) {
		this.x = x;
		this.y = y;
		this.tf = tf;
	}
	
	public void draw(Graphics g) {
		if(!live) { 
			tf.explodes.remove(this);
			return;
		}
		
		if(step == diameter.length) {
			live = false;
			step = 0;
			return ;
		}
		
		Color c = g.getColor();
		g.setColor(Color.orange);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);
		step ++;
	}
}
