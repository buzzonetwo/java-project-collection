package test;

import java.awt.event.ActionEvent;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import com.jogamp.opengl.awt.GLJPanel;


public class View extends GLJPanel{

	private Model model;
	private KeyController keycontroller;
	private long timerstart;
	private long timedifference;
	private boolean upstart, leftstart, downstart, rightstart;

	public View(Model m, KeyController kc){
		model = m;
		keycontroller = kc;
	}

	@Override
	public boolean isFocusable() {
		return true;
	}
	
	public void linkKeyBindings() {
		this.getInputMap().put(KeyStroke.getKeyStroke("W"),"Up");
		this.getInputMap().put(KeyStroke.getKeyStroke("released W"),"released Up");
		
		this.getActionMap().put("Up", new UpListenerStart());
		this.getActionMap().put("released Up", new UpListener());
		
		this.getInputMap().put(KeyStroke.getKeyStroke("A"),"Left");
		this.getInputMap().put(KeyStroke.getKeyStroke("released A"),"released Left");
		
		this.getActionMap().put("Left", new LeftListenerStart());
		this.getActionMap().put("released Left", new LeftListener());

		this.getInputMap().put(KeyStroke.getKeyStroke("S"),"Down");
		this.getInputMap().put(KeyStroke.getKeyStroke("released S"),"released Down");
		
		this.getActionMap().put("Down", new DownListenerStart());
		this.getActionMap().put("released Down", new DownListener());
		
		this.getInputMap().put(KeyStroke.getKeyStroke("D"),"Right");
		this.getInputMap().put(KeyStroke.getKeyStroke("released D"),"released Right");
		
		this.getActionMap().put("Right", new RightListenerStart());
		this.getActionMap().put("released Right", new RightListener());
	}
	
	private class UpListenerStart extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!upstart) {
				timerstart = System.currentTimeMillis();
				upstart = true;
			}
			timedifference = System.currentTimeMillis() - timerstart;
			double x = model.getLastPoint().getX();
			double y = model.getLastPoint().getY() + timedifference/10000f;
			ArrayList<Double> pointdata = model.getData();
			pointdata.add(new Double(x,y));
			double doublearray[] = new double[pointdata.size()*2];
			for (int i = 0; i < doublearray.length; i+=2) {
				doublearray[i] = pointdata.get(i/2).x;
				doublearray[i+1] = pointdata.get(i/2).y;
			}
			Line l = new Line(doublearray);
			System.out.println(Arrays.toString(l.vertices));
			addGLEventListener(l);
			repaint();
		}
		
	}
	private class UpListener extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			timedifference = System.currentTimeMillis() - timerstart;
			upstart = false;
			double x = model.getLastPoint().getX();
			double y = model.getLastPoint().getY() + timedifference/10000f;
			System.out.println(timedifference);
			model.addPoints(x, y);
			addGLEventListener(model.getLine());
			System.out.println(Arrays.toString(model.getLine().vertices));
			repaint();
		
		}
	}
	
	private class LeftListenerStart extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!leftstart) {
				timerstart = System.currentTimeMillis();
				leftstart = true;
			}
			timedifference = System.currentTimeMillis() - timerstart;
			double x = model.getLastPoint().getX() - timedifference/10000f;
			double y = model.getLastPoint().getY();
			ArrayList<Double> pointdata = model.getData();
			pointdata.add(new Double(x,y));
			double doublearray[] = new double[pointdata.size()*2];
			for (int i = 0; i < doublearray.length; i+=2) {
				doublearray[i] = pointdata.get(i/2).x;
				doublearray[i+1] = pointdata.get(i/2).y;
			}
			Line l = new Line(doublearray);
			System.out.println(Arrays.toString(l.vertices));
			addGLEventListener(l);
			repaint();
		}
		
	}
	private class LeftListener extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			timedifference = System.currentTimeMillis() - timerstart;
			leftstart = false;
			double x = model.getLastPoint().getX() - timedifference/10000f;
			double y = model.getLastPoint().getY();
			System.out.println(timedifference);
			model.addPoints(x, y);
			addGLEventListener(model.getLine());
			System.out.println(Arrays.toString(model.getLine().vertices));
			repaint();
		}
		
	}
	
	private class DownListenerStart extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!downstart) {
				timerstart = System.currentTimeMillis();
				downstart = true;
			}
			timedifference = System.currentTimeMillis() - timerstart;
			double x = model.getLastPoint().getX();
			double y = model.getLastPoint().getY() - timedifference/10000f;
			ArrayList<Double> pointdata = model.getData();
			pointdata.add(new Double(x,y));
			double doublearray[] = new double[pointdata.size()*2];
			for (int i = 0; i < doublearray.length; i+=2) {
				doublearray[i] = pointdata.get(i/2).x;
				doublearray[i+1] = pointdata.get(i/2).y;
			}
			Line l = new Line(doublearray);
			System.out.println(Arrays.toString(l.vertices));
			addGLEventListener(l);
			repaint();
		}
		
	}
	private class DownListener extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			timedifference = System.currentTimeMillis() - timerstart;
			downstart = false;
			double x = model.getLastPoint().getX();
			double y = model.getLastPoint().getY() - timedifference/10000f;
			System.out.println(timedifference);
			model.addPoints(x, y);
			addGLEventListener(model.getLine());
			System.out.println(Arrays.toString(model.getLine().vertices));
			repaint();
		
		}
	}
	
	private class RightListenerStart extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!rightstart) {
				timerstart = System.currentTimeMillis();
				rightstart = true;
			}
			timedifference = System.currentTimeMillis() - timerstart;
			double x = model.getLastPoint().getX() + timedifference/10000f;
			double y = model.getLastPoint().getY();
			ArrayList<Double> pointdata = model.getData();
			pointdata.add(new Double(x,y));
			double doublearray[] = new double[pointdata.size()*2];
			for (int i = 0; i < doublearray.length; i+=2) {
				doublearray[i] = pointdata.get(i/2).x;
				doublearray[i+1] = pointdata.get(i/2).y;
			}
			Line l = new Line(doublearray);
			System.out.println(Arrays.toString(l.vertices));
			addGLEventListener(l);
			repaint();
		}
		
	}
	private class RightListener extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			timedifference = System.currentTimeMillis() - timerstart;
			rightstart = false;
			double x = model.getLastPoint().getX() + timedifference/10000f;
			double y = model.getLastPoint().getY();
			System.out.println(timedifference);
			model.addPoints(x, y);
			addGLEventListener(model.getLine());
			System.out.println(Arrays.toString(model.getLine().vertices));
			repaint();
		}
		
	}
}
