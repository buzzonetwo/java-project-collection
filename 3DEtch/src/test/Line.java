package test;

import java.nio.DoubleBuffer;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import org.lwjgl.BufferUtils;


public class Line implements GLEventListener {

	double[] vertices; 
	
	public Line() {
	//	vertices = new double[]{.3,.4,0,.2,0,.1,.2,0,.35,.4};
	}
		
	public Line(double[] doublearray) {
		vertices = doublearray;
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		DoubleBuffer db = BufferUtils.createDoubleBuffer(vertices.length);
		db.put(vertices);
		db.flip();
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		 
		gl.glLoadIdentity();  // Reset The View     
		gl.glColor3d(0, 0, 0);
		
		gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
		gl.glVertexPointer(2, GL2.GL_DOUBLE, 0, db);
		gl.glDrawArrays(GL2.GL_LINE_STRIP, 0, vertices.length);
		gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
		
		
	}
	            
	@Override
	public void dispose(GLAutoDrawable arg0) {
	      //method body
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();	
		gl.glClearColor(0.392f, 0.582f, 0.929f, 1.0f);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x,  int y, int width, int height) {
		
	}
	
}