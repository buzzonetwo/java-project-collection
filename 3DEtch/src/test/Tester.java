package test;

import javax.swing.JFrame;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class Tester {

	
   public static void main(String[] args) {

      //getting the capabilities object of GL profile        
      GLProfile profile = GLProfile.get(GLProfile.GL2);
      GLCapabilities capabilities = new GLCapabilities(profile);
   
      // The canvas
      GLCanvas glcanvas = new GLCanvas(capabilities);
      Line l = new Line();
      glcanvas.addGLEventListener(l);
      glcanvas.setSize(400, 400);
      
   
      //creating frame
      final JFrame frame = new JFrame ("Line Window");
   
      //adding canvas to frame
      frame.getContentPane().add(glcanvas);
                 
      frame.setSize(frame.getContentPane().getPreferredSize());
      frame.setVisible(true);

      glcanvas.requestFocusInWindow();
     
      
   }//end of main

	
}