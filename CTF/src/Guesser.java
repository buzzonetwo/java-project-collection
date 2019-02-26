import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Guesser {


	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket gameSocket = new Socket("pwn.sunshinectf.org", 30002);
		PrintWriter out = new PrintWriter(gameSocket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(gameSocket.getInputStream()));
		
		int low = 0;
		int high = 0;
		int guess = 0;
		for (int i = 0; i < 50; i++) {
			String input = in.readLine();
			System.out.println(input);
			if (input.contains("Low:")) {
				low = Integer.parseInt(input.substring(5));
			}
			else if (input.contains("High:")) {
				high = Integer.parseInt(input.substring(6));
			}
			else if (input.contains("Input")) {
				guess = (low+high)/2;
				System.out.println(guess);
				out.print(guess + "\r\n");
				out.flush();
			}
			else if (input.contains("Lower")) {
				high = guess;
			}
			else if (input.contains("Higher")) {
				low = guess;
			}
			System.out.print("----");
		}
	}

}
