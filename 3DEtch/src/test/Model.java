package test;

import java.awt.Dimension;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;


public class Model {
	
	private ArrayList<Double> pointdata;
	private KeyController keycontroller;
	private View view;

	public Model() {
		pointdata = new ArrayList<Double>();
		keycontroller = new KeyController(this, view);
		view = new View(this, keycontroller);
        
        //GLProfile profile = GLProfile.get(GLProfile.GL2);
      //  GLCapabilities capabilities = new GLCapabilities(profile);
       // GLCanvas glcanvas = new GLCanvas(capabilities);
        


		JFrame frame=new JFrame();
		frame.setPreferredSize(new Dimension(1000,1000));

		frame.add(view);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);


	    BlankCanvas bc = new BlankCanvas();
	    view.addGLEventListener(bc);
	    
		view.linkKeyBindings();
		this.addPoints(0,0);
	    view.setSize(1000, 1000);		
		view.repaint();
		//frame.addKeyListener(keycontroller);
		frame.setVisible(true);
		
	}
	
	public void addPoints(double x, double y) {
		pointdata.add(new Double(x,y));
	}
	
	public void addPoints(Double p) {
		pointdata.add(p);
	}
	
	public ArrayList<Double> getData() {
		return pointdata;
	}
	
	public double[] getDoubleArray() {
		double doublearray[] = new double[pointdata.size()*2];
		for (int i = 0; i < doublearray.length; i+=2) {
			doublearray[i] = pointdata.get(i/2).x;
			doublearray[i+1] = pointdata.get(i/2).y;
		}
		return doublearray;
	}
	
	public Double getLastPoint() {
		return pointdata.get(pointdata.size()-1);
	}

	public Line getLine() {
		return new Line(getDoubleArray());
	}

	
}
