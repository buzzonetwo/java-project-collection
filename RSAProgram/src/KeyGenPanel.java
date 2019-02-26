import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import java.awt.Dimension;


public class KeyGenPanel extends JPanel {

	private JComboBox<String> menuKeySize;
	private int keySize;
	private JLabel textKeySize;
	private JButton btnGenerateKey;
	private JLabel textP;
	private JLabel textQ;
	private JLabel textN;
	private JLabel textTotient;
	private JLabel textE;
	private JLabel textD;
	private JLabel textPublic;
	private JLabel textPrivate;
	private JTextArea textShowP;
	private JTextArea textShowQ;
	private JTextArea textShowN;
	private JTextArea textShowTotient;
	private JTextArea textShowE;
	private JTextArea textShowD;
	private JTextArea textShowPrivate;
	private JTextArea textShowPublic;
	
	private JButton btnSavePublic;
	private JButton btnSavePrivate;


	/**
	 * Create the panel.
	 */
	public KeyGenPanel() {
		keySize = 512;
		setLayout(new MigLayout("", "[pref!][800px,leading]", "[24px][24px][24px][24px][24px][24px][24px][24px][47.00px,bottom]"));

		textShowN = new JTextArea();
		textShowN.setEditable(false);
		textShowN.setLineWrap(true);
		add(textShowN, "cell 1 2,growx");
		
		textShowTotient = new JTextArea();
		textShowTotient.setEditable(false);
		textShowTotient.setLineWrap(true);
		add(textShowTotient, "cell 1 3,growx");
		
		textShowP = new JTextArea();
		textShowP.setEditable(false);
		textShowP.setLineWrap(true);
		add(textShowP, "cell 1 0,growx");
		
		textShowQ = new JTextArea();
		textShowQ.setLineWrap(true);
		textShowQ.setEditable(false);
		add(textShowQ, "cell 1 1,growx");
		
		textShowE = new JTextArea();
		textShowE.setLineWrap(true);
		textShowE.setEditable(false);
		add(textShowE, "cell 1 4,growx");
		
		textShowD = new JTextArea();
		textShowD.setLineWrap(true);
		textShowD.setEditable(false);
		add(textShowD, "cell 1 5,growx");
		
		textShowPrivate = new JTextArea();
		textShowPrivate.setEditable(false);
		textShowPrivate.setLineWrap(true);
		add(textShowPrivate, "cell 1 6,growx");
		
		textShowPublic = new JTextArea();	
		textShowPublic.setLineWrap(true);
		textShowPublic.setEditable(false);
		add(textShowPublic, "cell 1 7,growx");
		
		textKeySize = new JLabel();
		textKeySize.setHorizontalAlignment(SwingConstants.RIGHT);
		textKeySize.setText("Key Size: ");
		add(textKeySize, "cell 0 8,growx,aligny center");
		
		textP = new JLabel("P:");
		add(textP, "cell 0 0,aligny top");
		textQ = new JLabel("Q:");
		add(textQ, "cell 0 1,aligny top");
		textN = new JLabel("N:");
		add(textN, "cell 0 2,aligny top");
		textTotient = new JLabel("Totient:");
		add(textTotient, "cell 0 3,aligny top");
		textE = new JLabel("E:");
		add(textE, "cell 0 4,aligny top");
		textD = new JLabel("D:");
		add(textD, "cell 0 5,aligny top");
		textPrivate = new JLabel("Private (d&n):");
		add(textPrivate, "cell 0 6,aligny top");
		textPublic = new JLabel("Public (e&n):");
		add(textPublic, "cell 0 7,aligny top");
		
		btnGenerateKey = new JButton("Generate Key");
		btnGenerateKey.addActionListener(new GenerateKeyListener());
		add(btnGenerateKey, "flowx,cell 1 8,alignx leading,aligny center");

		menuKeySize = new JComboBox<String>();
		menuKeySize.setMinimumSize(new Dimension(31, 25));
		menuKeySize.setModel(new DefaultComboBoxModel<String>(new String[] {"512", "1024", "2048", "4096"}));
		menuKeySize.addItemListener(new KeySizeListener());
		add(menuKeySize, "cell 1 8,alignx left,aligny center");
		
		btnSavePublic = new JButton("Save Public Key");
		btnSavePublic.addActionListener(new SavePublicListener());
		add(btnSavePublic, "cell 1 8,alignx trailing,aligny center");
		
		btnSavePrivate = new JButton("Save Private Key");
		btnSavePrivate.addActionListener(new SavePrivateListener());
		add(btnSavePrivate, "cell 1 8,alignx trailing,aligny center");

		setVisible(true);
	}
	
	/**
	 * Changes the key size setting
	 */
	private class KeySizeListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			keySize = Integer.parseInt((String) e.getItem());			
		} 
	}
	
	/**
	 * Creates a new RSA key and displays its data
	 */
	private class GenerateKeyListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			KeyGen k = new KeyGen(keySize);
			textShowP.setText(k.getp().toString());
			textShowQ.setText(k.getq().toString());
			textShowN.setText(k.getn().toString());
			textShowTotient.setText(k.getTotient().toString());
			textShowE.setText(k.gete().toString());
			textShowD.setText(k.getd().toString());
			textShowPrivate.setText(k.getPrivateKey());
			textShowPublic.setText(k.getPublicKey());
		}
	}
	
	/**
	 * Saves the Base64 public key to a file
	 */
	private class SavePublicListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser jfc = new JFileChooser();
			if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					FileWriter f = new FileWriter(jfc.getSelectedFile());
					f.write(textShowPublic.getText());
					f.close();
				} catch (FileNotFoundException ex2) {
					JOptionPane.showMessageDialog(null, "Failed to write file");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Failed to write file");
				}
			}
		}
	}
	
	/**
	 * Saves the Base64 private key to a file
	 */
	private class SavePrivateListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser jfc = new JFileChooser();
			if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					FileWriter f = new FileWriter(jfc.getSelectedFile());
					f.write(textShowPrivate.getText());
					f.close();
				} catch (FileNotFoundException ex2) {
					JOptionPane.showMessageDialog(null, "Failed to write file");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Failed to write file");
				}
			}
		}
	}
	
}
