import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Dialog.ModalityType;
public class ColorPicker {

	private JDialog frame;
	private Color backgroundcolor;
	private Color pointercolor;
	private JButton backbutton, pointerbutton;

	public ColorPicker(Color b, Color p) {
		backgroundcolor = b;
		pointercolor = p;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setBounds(500, 300, 300, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setModalityType(ModalityType.APPLICATION_MODAL);
		
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		backbutton = new JButton("Background Color");
		backbutton.setBackground(backgroundcolor);
		backbutton.addActionListener(new backActionListener());
		frame.getContentPane().add(backbutton);
		
		pointerbutton = new JButton("Pointer Color");
		pointerbutton.setBackground(pointercolor);
		pointerbutton.addActionListener(new pointerActionListener());
		frame.getContentPane().add(pointerbutton);
		
		frame.setVisible(true);
	}
	
	class backActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			Color newColor = JColorChooser.showDialog(
                    null,
                    "Choose Background Color",
                    backgroundcolor);
			if (newColor != null) {
				backgroundcolor = newColor;
				backbutton.setBackground(backgroundcolor);
			}
		}
	}
	
	class pointerActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			Color newColor = JColorChooser.showDialog(
                    null,
                    "Choose Pointer Color",
                    pointercolor);
			if (newColor != null) {
				pointercolor = newColor;
				pointerbutton.setBackground(pointercolor);
			}
		}
	}
	
	public Color getBackgroundColor() {
		return backgroundcolor;
	}
	
	public Color getPointerColor() {
		return pointercolor;
	}
}
