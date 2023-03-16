package MandelBulb;

import javax.swing.JFrame;

public class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	Canvas canvas;
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		canvas = new Canvas();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.add(canvas);
		this.pack();
	}
	
}