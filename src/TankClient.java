import java.awt.Frame;


public class TankClient {
	public static void main(String[] args) {
		TankFrame tf = new TankFrame();
		tf.launch();
	}

}

class TankFrame extends Frame {
	public void launch() {
		this.setLocation(400,300);
		this.setSize(800,600);
		this.setVisible(true);
	}
}