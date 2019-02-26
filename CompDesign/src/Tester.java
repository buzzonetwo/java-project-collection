import java.util.List;
import java.util.Scanner;

public class Tester {

	/**
	 * Scans in strings of input and converts them to tokens
	 * 
	 * @param args
	 * @throws InvalidToken if an invalid expression is in the string
	 * @throws ParseException if the tokens cannot be parsed correctly within the grammar
	 */
	public static void main(String[] args) throws InvalidToken, ParseException {
		Tokenizer t = new Tokenizer();
		LLParser p = new LLParser();
		Scanner s = new Scanner(System.in);
		while (s.hasNextLine()) {
			String str = s.nextLine();
			try {
				t.load(str);
				List<Token<?>> tokens = t.allTokens();
				try {
					System.out.println(p.parse(tokens));
				} catch (ParseException e) {
					System.err.println(e.toString());
				}
			} catch (InvalidToken e) {
				System.err.println(e.toString());
			}
		}
		s.close();

	}
}
