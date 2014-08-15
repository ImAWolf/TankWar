import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient {
	public static void main(String[] args) {
		TankFrame tf = new TankFrame();
		tf.launch();
	}
}

@SuppressWarnings("serial")
class TankFrame extends Frame {
	private static final int GAME_WIDTH = 400;
	private static final int GAME_HIGHT = 400;
	
	private int x = 50;
	private int y = 50;	
	private Image offScreenImage = null;
	
	public TankFrame() {
		super("TankWar....");
	}

	public void launch() {
		this.setLocation(400, 300);
		this.setSize(GAME_WIDTH, GAME_HIGHT);
		this.setVisible(true);
		this.setResizable(false);
		this.setBackground(Color.green);
		new Thread(new PaintThread()).start();

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.red);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);

		x += 5;
		y += 2;
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
}
