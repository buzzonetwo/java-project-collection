import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;

public class GUI {

	private JFrame frame;
	private JTabbedPane menuTabs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("RSA Encryption");
		frame.setBounds(100, 100, 1000, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setResizable(true);
		
		menuTabs = new JTabbedPane(SwingConstants.TOP);
		menuTabs.setBounds(0, 0, 432, 22);
		frame.add(menuTabs);
		KeyGenPanel kgp = new KeyGenPanel();
		EncryptPanel ep = new EncryptPanel();
		DecryptPanel dp = new DecryptPanel();
		//Using JScrollPanes to automatically handle scrolling for the large numbers/strings
		JScrollPane jsp = new JScrollPane(kgp);
		JScrollPane jsp2 = new JScrollPane(ep);
		JScrollPane jsp3 =  new JScrollPane(dp);
		menuTabs.addTab("Key Gen", jsp);
		menuTabs.addTab("Message Encryption", jsp2);
		menuTabs.addTab("Message Decryption", jsp3);
	}
}
