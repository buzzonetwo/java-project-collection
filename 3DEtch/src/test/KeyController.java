package test;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class KeyController implements KeyListener{
	
	private Model model;
	private View view;
	private HashMap<Character,Boolean> keystatus;
	private long starttime;
	private long timer;

	public KeyController(Model m, View v) {
		keystatus = new HashMap<Character,Boolean>();
		keystatus.put('w', false);
		keystatus.put('a', false);
		keystatus.put('s', false);
		keystatus.put('d', false);
		model = m;
		view = v;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Hello?");
		starttime = System.currentTimeMillis();
		if (e.getKeyChar() == 'w') {
			System.out.println("w");
			keystatus.replace('w', true);
		}
		if (e.getKeyChar() == 'a') {
			System.out.println("a");
			keystatus.replace('a', true);
		}
		if (e.getKeyChar() == 's') {
			System.out.println("s");
			keystatus.replace('s', true);
		}
		if (e.getKeyChar() == 'd') {
			System.out.println("d");
			keystatus.replace('d', true);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("Working");
		timer = System.currentTimeMillis() - starttime;
		if (e.getKeyChar() == 'w') {
			System.out.println("w");
			keystatus.replace('w', false);
		}
		if (e.getKeyChar() == 'a') {
			System.out.println("a");
			keystatus.replace('a', false);
		}
		if (e.getKeyChar() == 's') {
			System.out.println("s");
			keystatus.replace('s', false);
		}
		if (e.getKeyChar() == 'd') {
			System.out.println("d");
			keystatus.replace('d', false);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
	
	public HashMap<Character,Boolean> getKeyStatus() {
		return keystatus;
	}
	 

}
