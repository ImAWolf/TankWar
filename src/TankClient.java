import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankClient {	
	public static void main(String[] args) {
		TankFrame tf = new TankFrame();
		tf.launch();
	}
}

@SuppressWarnings("serial")
class TankFrame extends Frame {
	//游戏界面大小
	public static final int GAME_WIDTH = 400;
	public static final int GAME_HIGHT = 400;		
	private Image offScreenImage = null;
	
	public Wall w1 = new Wall(200,200,20,150,this);
	
	Tank myTank = new Tank(300,300,true,Tank.Direction.STOP,this);
	 
	List<Explode> explodes = new ArrayList<Explode>();
	List<Missile> missiles = new ArrayList<Missile>();
	List<Tank> tanks = new ArrayList<Tank>();
	
	public TankFrame() {
		super("TankWar....");
	}

	public void launch() {
		for(int i=0; i<10; i++) {
			Tank t = new Tank(50 + 40*(i+1),50,false,Tank.Direction.D,this); 
			tanks.add(t);
		}
		
		this.setLocation(400, 300);
		this.setSize(GAME_WIDTH, GAME_HIGHT);
		this.setVisible(true);
		this.setResizable(false);
		this.setBackground(Color.green);
		new Thread(new PaintThread()).start();

		this.addKeyListener(new KeyMonitor());
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		String str = "missile count:" + missiles.size();
		g.drawString(str, 10, 40);
		str = "explodes count:" + explodes.size();
		g.drawString(str, 10, 55);
		str = "tanks count:" + tanks.size();
		g.drawString(str, 10, 70);
		str = "tank life:" + myTank.life;
		g.drawString(str, 10, 85);
		
		
		myTank.draw(g);
		w1.draw(g);
		
		for(int i=0; i<missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.draw(g);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
		}
		
		for(int i=0; i<explodes.size(); i++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}
		
		for(int i=0; i<tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.draw(g);
			t.colliedWall(w1);
			t.colledesWithTanks(tanks);
		}
	}
	//调用repaint方法时，先调用update方法，再调用paint方法
	//双缓冲方法
	@Override
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HIGHT);
		}
		//获取画笔
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		//重绘窗口
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_WIDTH);
		gOffScreen.setColor(c);
		
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
		//offScreenImage = null;
	}
	
	// 创建线程，每隔一段时间重画坦克
	private class PaintThread implements Runnable {
		@Override
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		//控制坦克移动
		@Override
		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}		
	}
}
