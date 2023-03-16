package MandelBulb;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;


public class Canvas extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private int WIDTH = 1000;
	private int HEIGHT = 1000;
	
	double distance = 10;
	double scale = 2000;
	double rotation = 0;
	
	ArrayList<double[][]> points = new ArrayList<>();
	
	
	public Canvas() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.BLACK);
		points = generatePoints(164);
		Timer timer = new Timer(1, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		renderPoints(g);
	}
	
	private void renderPoints(Graphics g) {
		
		double [][]rotation_z = {
			{Math.cos(rotation), -Math.sin(rotation), 0},
			{Math.sin(rotation), Math.cos(rotation), 0},
			{0, 0, 1}
		};
		
		double [][]rotation_x = {
			{1, 0, 0},
			{0, Math.cos(rotation), -Math.sin(rotation)},
			{0, Math.sin(rotation), Math.cos(rotation)}
		};
		
		double [][]rotation_y = {
			{Math.cos(rotation), 0, Math.sin(rotation)},
			{0, 1, 0},
			{-Math.sin(rotation), 0, Math.cos(rotation)}
		};
		
		for(double[][] point : points) {
			point = Matrix.multiplyMatrix(rotation_x, point);
			point = Matrix.multiplyMatrix(rotation_y, point);
			point = Matrix.multiplyMatrix(rotation_z, point);
			
			double oz = 1/(distance-point[2][0]);
			double[][] orto_projection = {
				{oz, 0, 0},
				{0, oz, 0}
			};
			
			double[][] new_point = Matrix.multiplyMatrix(orto_projection, point);
			
			int mx = (int)(new_point[0][0] * scale + this.getWidth()/2);
			int my = (int)(new_point[1][0] * scale + this.getHeight()/2);
			
			g.setColor(Color.WHITE);
			int d = 1;
			g.drawOval(mx-d/2, my-d/2, d, d);
		}
	}

	public ArrayList<double[][]> generatePoints(int a) {
		ArrayList<double[][]> points = new ArrayList<>();
		for(int i = 1; i <= a; ++i) {
			for(int j = 1; j <= a; ++j) {
				
				boolean edge = false;
				for(int k = 1; k <= a; ++k) {
					double x = mapValue(i, 1, a, -1, 1); 
					double y = mapValue(j, 1, a, -1, 1);
					double z = mapValue(k, 1, a, -1, 1);
					
					double[][] zeta = {{0}, {0}, {0}};
					
					int n = 16;
					int maxiterations = 30;
					int iteration = 0;
					while(true) {
						
						double[][] sz = triplexToSpherical(zeta[0][0], zeta[1][0], zeta[2][0]);
						
						double newx = Math.pow(sz[0][0], n) * Math.sin(sz[1][0]*n) * Math.cos(sz[2][0]*n);
						double newy = Math.pow(sz[0][0], n) * Math.sin(sz[1][0]*n) * Math.sin(sz[2][0]*n);
						double newz = Math.pow(sz[0][0], n) * Math.cos(sz[1][0]*n);
						
						zeta[0][0] = newx + x;
						zeta[1][0] = newy + y;
						zeta[2][0] = newz + z;
						iteration++;
						
						if(sz[0][0] > 1.6) {
							if(edge) {
								edge = false;
							}
							break;
						}
						
						if(iteration > maxiterations) {
							if(!edge) {
								edge = true;
								double[][] single_point = {{x},{y},{z}};
								points.add(single_point);
							}
							break;
						}
					}
				}
			}
		}
		return points;
	}
	
	public double[][] triplexToSpherical(double x, double y, double z) {
		double r = Math.sqrt(x*x + y*y + z*z);
		double theta = Math.atan2(Math.sqrt(x*x + y*y), z);
		double phi = Math.atan2(y, x);
		return new double[][]{{r}, {theta}, {phi}};
	}
	
	public double mapValue(int num, int s, int e, int m, int n)
	{
	    return (num-s)/(double)(e-s) * (n-m) + m;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		rotation += 0.01;
		repaint();
	}
}
