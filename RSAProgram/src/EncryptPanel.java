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

public class EncryptPanel extends JPanel {
	
	private JLabel lblPublicKey, lblMessage, lblCiphertext;
	private JTextArea textEnterPublic, textEnterMessage, textShowCiphertext;
	private JButton btnEncrypt;
	private JLabel lblError;
	private JButton btnLoadPublic;
	private JButton btnSaveCipher;
	private JButton btnLoadMessage;
	
	/**
	 * Create the panel.
	 */
	public EncryptPanel() {
		setLayout(new MigLayout("", "[pref!][800px,leading]", "[][][35.00][][]"));
		
		lblPublicKey = new JLabel("Public Key:");
		add(lblPublicKey, "cell 0 0");
		
		lblMessage = new JLabel("Message:");
		add(lblMessage, "cell 0 1");
		
		lblCiphertext = new JLabel("Ciphertext:");
		add(lblCiphertext, "cell 0 3");
		
		textEnterPublic = new JTextArea();
		textEnterPublic.setLineWrap(true);
		add(textEnterPublic, "cell 1 0,growx");
		
		textEnterMessage = new JTextArea();
		textEnterMessage.setLineWrap(true);
		add(textEnterMessage, "cell 1 1,growx");
		
		lblError = new JLabel("");
		lblError.setVerticalAlignment(SwingConstants.TOP);
		lblError.setForeground(Color.red);
		add(lblError, "cell 1 2");

		textShowCiphertext = new JTextArea();
		textShowCiphertext.setLineWrap(true);
		textShowCiphertext.setEditable(false);
		add(textShowCiphertext, "cell 1 3,growx");
		
		btnEncrypt = new JButton("Encrypt");
		btnEncrypt.addActionListener(new EncryptListener());
		add(btnEncrypt, "cell 0 4");
		
		btnLoadPublic = new JButton("Load Public Key");
		btnLoadPublic.addActionListener(new LoadPublicListener());
		add(btnLoadPublic, "flowx,cell 1 4");
		
		btnLoadMessage = new JButton("Load Message");
		btnLoadMessage.addActionListener(new LoadMessageListener());
		add(btnLoadMessage, "cell 1 4");
		
		btnSaveCipher = new JButton("Save Ciphertext");
		btnSaveCipher.addActionListener(new SaveCipherListener());
		add(btnSaveCipher, "cell 1 4");

	}
	
	/**
	 * Encrypts a given message with the given public key and displays it in the ciphertext field
	 */
	private class EncryptListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				String[] publickeyarray = textEnterPublic.getText().split("&");
				BigInteger e = new BigInteger(Base64.getDecoder().decode(publickeyarray[0]));
				BigInteger n = new BigInteger(Base64.getDecoder().decode(publickeyarray[1])); 
				
				byte[] messagebytes = textEnterMessage.getText().getBytes();
				
				BigInteger m = new BigInteger(messagebytes);
				BigInteger c = m.modPow(e, n);
				
				lblError.setText("");
				textShowCiphertext.setText(KeyGen.toBase64(c));
				
				if (textEnterMessage.getText().length()*8 > e.bitLength()) {
					lblError.setText("The message may have been too long and the ciphertext may decrypt improperly");
				}
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				lblError.setText("Please enter a valid key and/or message");
			} catch (NullPointerException e) {
				lblError.setText("Please enter a valid key and/or message");
			}
		}
	}
	
	/**
	 * Loads the first line of text from a file into the public key field
	 */
	private class LoadPublicListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					BufferedReader f = new BufferedReader(new FileReader(jfc.getSelectedFile()));
					textEnterPublic.setText(f.readLine());
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
	 * Loads the first line of text from a file into the message field
	 */
	private class LoadMessageListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					BufferedReader f = new BufferedReader(new FileReader(jfc.getSelectedFile()));
					textEnterMessage.setText(f.readLine());
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
	 * Writes the ciphertext into a file
	 */
	private class SaveCipherListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser jfc = new JFileChooser();
			if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					FileWriter f = new FileWriter(jfc.getSelectedFile());
					f.write(textShowCiphertext.getText());
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
