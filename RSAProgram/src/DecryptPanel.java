import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Base64;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;

public class DecryptPanel extends JPanel {
	
	private JLabel lblPrivateKey, lblMessage, lblCiphertext;
	private JTextArea textEnterPrivate, textShowMessage, textEnterCiphertext;
	private JButton btnDecrypt;
	private JLabel lblError;
	private JButton btnLoadPrivate;
	private JButton btnSaveMessage;
	private JButton btnLoadCiphertext;
	
	/**
	 * Create the panel.
	 */
	public DecryptPanel() {
		setLayout(new MigLayout("", "[pref!][800px,leading]", "[][][35.00][][]"));
		
		lblPrivateKey = new JLabel("Private Key:");
		add(lblPrivateKey, "cell 0 0");
		lblMessage = new JLabel("Message:");
		add(lblMessage, "cell 0 3");
		
		lblCiphertext = new JLabel("Ciphertext:");
		add(lblCiphertext, "cell 0 1");
		
		textEnterPrivate = new JTextArea();
		textEnterPrivate.setLineWrap(true);
		add(textEnterPrivate, "cell 1 0,growx");
		
		textShowMessage = new JTextArea();
		textShowMessage.setLineWrap(true);
		textShowMessage.setEditable(false);
		add(textShowMessage, "cell 1 3,growx");
		
		lblError = new JLabel("");
		lblError.setVerticalAlignment(SwingConstants.TOP);
		lblError.setForeground(Color.red);
		add(lblError, "cell 1 2");

		textEnterCiphertext = new JTextArea();
		textEnterCiphertext.setLineWrap(true);
		add(textEnterCiphertext, "cell 1 1,growx");
		
		btnDecrypt = new JButton("Decrypt");
		btnDecrypt.addActionListener(new DecryptListener());
		add(btnDecrypt, "cell 0 4");
		
		btnLoadPrivate = new JButton("Load Private Key");
		btnLoadPrivate.addActionListener(new LoadPrivateListener());
		add(btnLoadPrivate, "flowx,cell 1 4");
		
		btnLoadCiphertext = new JButton("Load Ciphertext");
		btnLoadCiphertext.addActionListener(new LoadCiphertextListener());
		add(btnLoadCiphertext, "cell 1 4");
		
		btnSaveMessage = new JButton("Save Message");
		btnSaveMessage.addActionListener(new SaveMessageListener());
		add(btnSaveMessage, "cell 1 4");


	}
	
	/**
	 * Decrypts the given ciphertext using the given private key and displays it in the message field
	 */
	private class DecryptListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				String[] publickeyarray = textEnterPrivate.getText().split("&");
				BigInteger d = new BigInteger(Base64.getDecoder().decode(publickeyarray[0]));
				BigInteger n = new BigInteger(Base64.getDecoder().decode(publickeyarray[1])); 
				
				byte[] ciphertextbytes = Base64.getDecoder().decode(textEnterCiphertext.getText());
				BigInteger c = new BigInteger(ciphertextbytes);
				BigInteger m = c.modPow(d, n);
				
				lblError.setText("");
				
				textShowMessage.setText(new String(m.toByteArray()));

			} catch (IllegalArgumentException e) {
				lblError.setText("Please enter a valid key and/or ciphertext");
			} catch (NullPointerException e) {
				lblError.setText("Please enter a valid key and/or ciphertext");
			}  	
		}
	}

	/**
	 * Loads the first line of text from a file into the private key field
	 */
	private class LoadPrivateListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					BufferedReader f = new BufferedReader(new FileReader(jfc.getSelectedFile()));
					textEnterPrivate.setText(f.readLine());
					f.close();
				} catch (FileNotFoundException ex2) {
					JOptionPane.showMessageDialog(null, "Failed to load file");
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(null, "Failed to load file");
				} 
			}
		}	
	}
	
	/**
	 * Loads the first line of text from a file into the ciphertext field
	 */
	private class LoadCiphertextListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					BufferedReader f = new BufferedReader(new FileReader(jfc.getSelectedFile()));
					textEnterCiphertext.setText(f.readLine());
					f.close();
				} catch (FileNotFoundException ex2) {
					JOptionPane.showMessageDialog(null, "Failed to load file");
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(null, "Failed to load file");
				} 
			}
		}	
	}
	
	/**
	 * Writes the message into a file
	 */
	private class SaveMessageListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser jfc = new JFileChooser();
			if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					FileWriter f = new FileWriter(jfc.getSelectedFile());
					f.write(textShowMessage.getText());
					f.close();
				} catch (FileNotFoundException ex2) {
					JOptionPane.showMessageDialog(null, "Failed to write file");
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(null, "Failed to write file");
				} 
			}
		}
	}
}
