import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import org.lwjgl.system.windows.POINTFLOAT;

import java.awt.Color;
import java.nio.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

	// The window handle
	private long window;
	private ArrayList<POINTFLOAT> pointlist;
	private double time;
	private boolean upstate, downstate, leftstate, rightstate;
	private int erase;
	private boolean spinleft, spinright;
	private boolean click, help, color, thickness;
	private float thick;
	private boolean reset;
	private Color backgroundcolor, pointercolor;
	
	public Window() {
		pointlist = new ArrayList<POINTFLOAT>();
		
		POINTFLOAT a = POINTFLOAT.create();
		a.set(.0f,.0f);
		pointlist.add(a);
		pointlist.add(a);
		
		erase = -1;
		
		backgroundcolor = Color.white;
		pointercolor = Color.gray;
	}

	public void run() {
		JOptionPane.showMessageDialog(null, "WASD to move the pointer \nQE to spin \nHold H to erase\nR to reset\n\nNote: the button outlines will also spin, but where they are supposed to be can still be clicked\nYou can find this message again by clicking the question mark in the bottom left", "Help", JOptionPane.PLAIN_MESSAGE);
		
		init();
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
		
		if (reset) {
			new Window().run2();
		}
	}
	
	public void run2() {
		init();
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
		
		if (reset) {
			new Window().run2();
		}
	}
	
	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(500, 500, "Etch a Sketch", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		glfwSetCursorPosCallback(window, (window, xpos, ypos) -> {
			if (ypos < 480 && ypos > 450 && xpos > 30 && xpos < 48) {
				help = true;
			}
			else {
				help = false;
			}
			if (ypos < 475 && ypos > 440 && xpos > 300 && xpos < 452) {
				color = true;
			}
			else {
				color = false;
			}
			if (ypos < 475 && ypos > 440 && xpos > 75 && xpos < 280) {
				thickness = true;
			}
			else {
				thickness = false;
			}
		});
		
		glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
			if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) {
				click = true;
			}
			else if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_RELEASE) {
				click = false;
			}
		});
		
		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ) {
				glfwSetWindowShouldClose(window, true);// We will detect this in the rendering loop
			}
			if ( key == GLFW_KEY_R && action == GLFW_RELEASE ) {
				glfwSetWindowShouldClose(window, true);// We will detect this in the rendering loop
				reset = true;
			}
			if (!downstate && (key == GLFW_KEY_W) && action == GLFW_PRESS) {
				pointlist.add(pointlist.get(pointlist.size()-1));
				time = glfwGetTime();
				upstate = true;
			} 
			if (!rightstate && (key == GLFW_KEY_A) && action == GLFW_PRESS) {
				pointlist.add(pointlist.get(pointlist.size()-1));
				time = glfwGetTime();
				leftstate = true;
			} 
			if (!upstate && (key == GLFW_KEY_S) && action == GLFW_PRESS) {
				pointlist.add(pointlist.get(pointlist.size()-1));
				time = glfwGetTime();
				downstate = true;
			} 
			if (!leftstate && (key == GLFW_KEY_D) && action == GLFW_PRESS) {
				pointlist.add(pointlist.get(pointlist.size()-1));
				time = glfwGetTime();
				rightstate = true;
			} 
			if (key == GLFW_KEY_W && action == GLFW_RELEASE) {
				pointlist.add(pointlist.get(pointlist.size()-1));
				time = glfwGetTime();
				upstate = false;
			}
			if (key == GLFW_KEY_A && action == GLFW_RELEASE) {
				pointlist.add(pointlist.get(pointlist.size()-1));
				time = glfwGetTime();
				leftstate = false;
			}
			if (key == GLFW_KEY_S && action == GLFW_RELEASE) {
				pointlist.add(pointlist.get(pointlist.size()-1));
				time = glfwGetTime();
				downstate = false;
			}
			if (key == GLFW_KEY_D && action == GLFW_RELEASE) {
				pointlist.add(pointlist.get(pointlist.size()-1));
				time = glfwGetTime();
				rightstate = false;
			}
			if (key == GLFW_KEY_H && (action == GLFW_PRESS)) {
				erase = 0;
			}
			if (key == GLFW_KEY_H && (action == GLFW_RELEASE)) {
				erase = -1;
			}
			if (key == GLFW_KEY_Q && (action == GLFW_PRESS)) {
				spinleft = true;
			}
			if (key == GLFW_KEY_E && (action == GLFW_PRESS)) {
				spinright = true;
			}
			if (key == GLFW_KEY_Q && (action == GLFW_RELEASE)) {
				spinleft = false;
			}
			if (key == GLFW_KEY_E && (action == GLFW_RELEASE)) {
				spinright = false;
			}
			
			
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
		
	}

	private void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(window) ) {
			glClearColor((float)(backgroundcolor.getRed()/255.0), (float)(backgroundcolor.getGreen()/255.0), (float)(backgroundcolor.getBlue()/255.0), (float)(backgroundcolor.getAlpha()/255.0));		

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			
			//Couldn't figure out how to do vertex buffering with font rendering in time
			glLineWidth(1);
			glColor3d(0.0, 0.0, 0.0);
			//?
			glBegin(GL_LINE_STRIP);
			glVertex2d(-.88,-.8);
			glVertex2d(-.81,-.8);
			glVertex2d(-.81,-.84);
			glVertex2d(-.85,-.84);
			glVertex2d(-.85,-.88);	
			glEnd();
			glBegin(GL_POINTS);
			glVertex2d(-.85,-.9);	
			glEnd();
			//THICKNESS
			glBegin(GL_LINE_LOOP);
			glVertex2d(-.7,-.78);
			glVertex2d(.12,-.78);
			glVertex2d(0.12,-.9);
			glVertex2d(-.7,-.9);	
			glEnd();
			//T	
			glBegin(GL_LINE_STRIP);
			glVertex2d(-.68,-.8);
			glVertex2d(-.6,-.8);
			glVertex2d(-.64,-.8);
			glVertex2d(-.64,-.88);
			glEnd();
			//H
			glBegin(GL_LINE_STRIP);
			glVertex2d(-.575,-.8);
			glVertex2d(-.575,-.88);
			glVertex2d(-.575,-.84);
			glVertex2d(-.5,-.84);
			glVertex2d(-.5,-.88);
			glVertex2d(-.5,-.8);
			glEnd();
			//I
			glBegin(GL_LINES);
			glVertex2d(-.46,-.88);
			glVertex2d(-.46,-.8);
			glEnd();
			//C
			glBegin(GL_LINE_STRIP);
			glVertex2d(-.36,-.88);
			glVertex2d(-.43,-.88);
			glVertex2d(-.43,-.8);
			glVertex2d(-.36,-.8);
			glEnd();
			//K
			glBegin(GL_LINE_STRIP);
			glVertex2d(-.33,-.88);
			glVertex2d(-.33,-.8);
			glVertex2d(-.33,-.84);
			glVertex2d(-.27,-.8);
			glVertex2d(-.33,-.84);
			glVertex2d(-.27,-.88);
			glEnd();
			//N
			glBegin(GL_LINE_STRIP);
			glVertex2d(-.245,-.88);
			glVertex2d(-.245,-.8);
			glVertex2d(-.175,-.88);
			glVertex2d(-.175,-.8);
			glEnd();
			//E
			glBegin(GL_LINES);
			glVertex2d(-.15,-.88);			
			glVertex2d(-.08,-.88);
			glVertex2d(-.15,-.84);			
			glVertex2d(-.08,-.84);
			glVertex2d(-.15,-.88);			
			glVertex2d(-.15,-.8);
			glVertex2d(-.15,-.8);			
			glVertex2d(-.08,-.8);
			glEnd();
			//S
			glBegin(GL_LINE_STRIP);
			glVertex2d(.0,-.8);			
			glVertex2d(-.06,-.8);
			glVertex2d(-.06,-.84);			
			glVertex2d(.0,-.84);
			glVertex2d(.0,-.88);			
			glVertex2d(-.06,-.88);
			glEnd();
			//S
			glBegin(GL_LINE_STRIP);
			glVertex2d(.085,-.8);			
			glVertex2d(.025,-.8);
			glVertex2d(.025,-.84);			
			glVertex2d(.085,-.84);
			glVertex2d(.085,-.88);			
			glVertex2d(.025,-.88);
			glEnd();
			//COLORS
			glBegin(GL_LINE_LOOP);
			glVertex2d(.2,-.78);
			glVertex2d(.81,-.78);
			glVertex2d(.81,-.9);	
			glVertex2d(0.2,-.9);
			glEnd();
			//C
			glBegin(GL_LINE_STRIP);
			glVertex2d(.3,-.88);
			glVertex2d(.23,-.88);
			glVertex2d(.23,-.8);
			glVertex2d(.3,-.8);
			glEnd();
			//O
			glBegin(GL_LINE_LOOP);
			glVertex2d(.393,-.88);
			glVertex2d(.323,-.88);
			glVertex2d(.323,-.8);	
			glVertex2d(.393,-.8);
			glEnd();
			//L
			glBegin(GL_LINE_STRIP);
			glVertex2d(.4865,-.88);
			glVertex2d(.4165,-.88);
			glVertex2d(.4165,-.8);
			glEnd();
			//O
			glBegin(GL_LINE_LOOP);
			glVertex2d(.58,-.88);
			glVertex2d(.51,-.88);
			glVertex2d(.51,-.8);	
			glVertex2d(.58,-.8);
			glEnd();
			//R
			glBegin(GL_LINE_STRIP);
			glVertex2d(.61,-.88);
			glVertex2d(.61,-.8);
			glVertex2d(.68,-.8);	
			glVertex2d(.68,-.84);
			glVertex2d(.61,-.84);
			glVertex2d(.64,-.84);
			glVertex2d(.68,-.88);
			glEnd();
			//S
			glBegin(GL_LINE_STRIP);
			glVertex2d(.71,-.88);
			glVertex2d(.78,-.88);
			glVertex2d(.78,-.84);
			glVertex2d(.71,-.84);
			glVertex2d(.71,-.8);
			glVertex2d(.78,-.8);
			glEnd();
			// Poll for window events. The key callback above will only be
			// invoked during this call.
			
			glfwPollEvents();
			addPoints();
			
			if (click && help) {
				JOptionPane.showMessageDialog(null, "WASD to move the pointer \nQE to spin \nHold H to erase\nR to reset\n\nNote: the button outlines will also spin, but where they are supposed to be can still be clicked", "Help", JOptionPane.PLAIN_MESSAGE);
				click = false;
				help = false;
			}
			if (click && thickness) {
				String s = (String) JOptionPane.showInputDialog(
                    null, "Choose a thickness:", "Thickness",
                    JOptionPane.PLAIN_MESSAGE,
                    null, new String[]{"1","2","3","4","5"},"1");
				if ((s != null) && (s.length() > 0)) {
				    	thick = Float.parseFloat(s);
				}
				thickness = false;
				click = false;
			}
			if (click && color) {
				ColorPicker c = new ColorPicker(backgroundcolor,pointercolor);
				backgroundcolor = c.getBackgroundColor();
				pointercolor = c.getPointerColor();
				System.out.println(backgroundcolor);
				System.out.println(pointercolor);
				color = false;
				click = false;
			}
			
			if (erase >= 0 && erase <= 29) {
				glRotatef(6f,0f,0f,1f);
				erase++;
			}
			else if (erase > 29 && erase < 160) {
				if (erase%20 <= 10) {
					glTranslated(0.004,0.004,0.004);
				}
				else {
					glTranslated(-0.004,-0.004,-0.004);
				}
				erase++;
			}
			else if (erase >= 160) {
				POINTFLOAT a = pointlist.get(pointlist.size()-1);
				pointlist = new ArrayList<POINTFLOAT>();
				pointlist.add(a);
				pointlist.add(a);
				erase = -1;
				glRotatef(180f,0f,0f,1f);
			}
			
			if (spinleft) {
				glRotatef(2f,0f,0f,1f);
			}
			else if (spinright) {
				glRotatef(2f,0f,0f,-1f);
			}
			
			glLineWidth(thick);
			glColor4d(pointercolor.getRed()/255.0, pointercolor.getGreen()/255.0, pointercolor.getBlue()/255.0, pointercolor.getAlpha()/255.0);
			glBegin(GL_LINES);
			for (int i = 0; i < pointlist.size()-1; i++){
				glVertex2f(pointlist.get(i).x(),pointlist.get(i).y());
				glVertex2f(pointlist.get(i+1).x(),pointlist.get(i+1).y());
			}
			glEnd();
		    glfwSwapBuffers(window); 
		   
		}
	}
	
	private void addPoints() {
		if (upstate || leftstate || downstate || rightstate) {
		double diff = time - glfwGetTime();
		POINTFLOAT a = POINTFLOAT.create();
		a.set(pointlist.get(pointlist.size()-1).x(),(pointlist.get(pointlist.size()-1).y()));
		pointlist.remove(pointlist.size()-1);
		if (upstate) {
			a.set(a.x(),(float) (pointlist.get(pointlist.size()-1).y()-diff/8f));
		}
		else if (downstate) {
			a.set(a.x(),(float) (pointlist.get(pointlist.size()-1).y()+diff/8f));
		}
		if (leftstate) {
			a.set((float) (pointlist.get(pointlist.size()-1).x()+diff/8f),a.y());
		}
		else if (rightstate) {
			a.set((float) (pointlist.get(pointlist.size()-1).x()-diff/8f),a.y());
		}
		pointlist.add(a);
		}		
	}

	public static void main(String[] args) {
		new Window().run();
	}

}