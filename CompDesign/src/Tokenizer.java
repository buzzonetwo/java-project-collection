import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

	private List<Token<?>> templist;
	private List<Token<?>> tokenlist;

	public Tokenizer() {
		templist = new ArrayList<Token<?>>();
		tokenlist = new ArrayList<Token<?>>();
	}

	/**
	 * Loads a string onto the end of the tokenizer
	 * 
	 * @param str
	 *            String to be tokenized
	 * @throws InvalidToken
	 *             if an invalid expression is in the string
	 */
	public void load(String str) throws InvalidToken {
		if (str.length() > 0) {
			if (str.charAt(0) == ' ') {
				load(str.substring(1));
			} else if (str.charAt(0) == '"') {
				createStringToken(str);
			} else if (str.charAt(0) >= '0' && str.charAt(0) <= '9') {
				createIntegerToken(str);
			} else if ((str.charAt(0) >= 'a' && str.charAt(0) <= 'z')
					|| (str.charAt(0) >= 'A' && str.charAt(0) <= 'Z')) {
				createIdentifierToken(str);
			} else if (str.charAt(0) == '+') {
				Token<?> a = new Token<>();
				a.type = Token.TOKEN_TYPE.ADDOP;
				templist.add(a);
				if (str.length() > 1)
					load(str.substring(1));
			} else if (str.charAt(0) == '-') {
				Token<?> a = new Token<>();
				a.type = Token.TOKEN_TYPE.SUBOP;
				templist.add(a);
				if (str.length() > 1)
					load(str.substring(1));
			} else if (str.charAt(0) == '*') {
				Token<?> a = new Token<>();
				a.type = Token.TOKEN_TYPE.MULTOP;
				templist.add(a);
				if (str.length() > 1)
					load(str.substring(1));
			} else if (str.charAt(0) == '/') {
				Token<?> a = new Token<>();
				a.type = Token.TOKEN_TYPE.DIVOP;
				templist.add(a);
				if (str.length() > 1)
					load(str.substring(1));
			} else if (str.charAt(0) == '%') {
				Token<?> a = new Token<>();
				a.type = Token.TOKEN_TYPE.MODOP;
				templist.add(a);
				if (str.length() > 1)
					load(str.substring(1));
			} else if (str.charAt(0) == '[') {
				Token<?> a = new Token<>();
				a.type = Token.TOKEN_TYPE.LEFTBRACKET;
				templist.add(a);
				if (str.length() > 1)
					load(str.substring(1));
			} else if (str.charAt(0) == ']') {
				Token<?> a = new Token<>();
				a.type = Token.TOKEN_TYPE.RIGHTBRACKET;
				templist.add(a);
				if (str.length() > 1)
					load(str.substring(1));
			} else if (str.charAt(0) == '(') {
				Token<?> a = new Token<>();
				a.type = Token.TOKEN_TYPE.LEFTPARANTHESES;
				templist.add(a);
				if (str.length() > 1)
					load(str.substring(1));
			} else if (str.charAt(0) == ')') {
				Token<?> a = new Token<>();
				a.type = Token.TOKEN_TYPE.RIGHTPARANTHESES;
				templist.add(a);
				if (str.length() > 1)
					load(str.substring(1));
			} else if (str.charAt(0) == ',') {
				Token<?> a = new Token<>();
				a.type = Token.TOKEN_TYPE.COMMA;
				templist.add(a);
				if (str.length() > 1)
					load(str.substring(1));
			} else if (str.charAt(0) == '=') {
				Token<?> a = new Token<>();
				a.type = Token.TOKEN_TYPE.EQUALS;
				templist.add(a);
				if (str.length() > 1)
					load(str.substring(1));
			} else {
				templist.clear();
				throw new InvalidToken("unrecognized character: " + str.charAt(0));
			}

		}
		for (int i = 0; i < templist.size(); i++) {
			tokenlist.add(templist.get(i));
		}
		templist.clear();
	}

	private void createIdentifierToken(String str) throws InvalidToken {
		int i = 0;
		while (i < str.length() && ((str.charAt(i) >= 'a' && str.charAt(i) <= 'z')
				|| (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') || (str.charAt(i) >= '0' && str.charAt(i) <= '9'))) {
			i++;
		}

		String sub = str.substring(0, i);

		if (i < str.length() && str.charAt(i) == '.') {
			templist.clear();
			throw new InvalidToken("not an double: " + sub + str.charAt(i));
		} else {
			Token<String> a = new Token<String>();
			a.type = Token.TOKEN_TYPE.IDENTIFIER;
			a.data = sub;
			templist.add(a);
			if (str.length() > 0)
				load(str.substring(i));
		}

	}

	private void createIntegerToken(String str) throws InvalidToken {
		int i = 0;
		while (i < str.length() && str.charAt(i) >= '0' && str.charAt(i) <= '9') {
			i++;
		}
		String sub = str.substring(0, i);
		if (i < str.length() && str.charAt(i) == '.') {
			createDoubleToken(sub, str.substring(i + 1));
		} else if (i < str.length() && !symbolChecker(str.charAt(i))) {
			boolean haspoint = false;
			while (i < str.length() && str.charAt(i) != ' ') {
				if (str.charAt(i) == '.') {
					haspoint = true;
				}
				i++;
			}
			sub = str.substring(0, i);
			if (haspoint) {
				templist.clear();
				throw new InvalidToken("not a double: " + sub);
			}
			templist.clear();
			throw new InvalidToken("not an integer: " + sub);
		} else {
			Token<Integer> a = new Token<Integer>();
			a.type = Token.TOKEN_TYPE.INTEGER;
			try {
				a.data = Integer.parseInt(sub);
			} catch (NumberFormatException e) {
				templist.clear();
				throw new InvalidToken("integer too big: " + sub);
			}
			templist.add(a);
			if (str.length() > 0)
				load(str.substring(i));
		}
	}

	private void createStringToken(String str) throws InvalidToken {
		int i = 1;
		while (i < str.length() && str.charAt(i) != '\"') {
			i++;
		}
		String sub = str.substring(1, i);
		if (i >= str.length() || str.charAt(i) != '\"') {
			templist.clear();
			throw new InvalidToken("unclosed string: " + "\"" + sub);
		} else {
			Token<String> a = new Token<String>();
			a.type = Token.TOKEN_TYPE.STRING;
			a.data = sub;
			templist.add(a);
			if (str.length() > 0)
				load(str.substring(i + 1));
		}
	}

	private void createDoubleToken(String integer, String str) throws InvalidToken {
		int i = 0;
		while (i < str.length() && str.charAt(i) >= '0' && str.charAt(i) <= '9') {
			i++;
		}
		String sub = str.substring(0, i);
		if (i < str.length() && !symbolChecker(str.charAt(i))) {
			while (i < str.length() && str.charAt(i) != ' ') {
				i++;
			}
			sub = str.substring(0, i);
			templist.clear();
			throw new InvalidToken("not an double: " + integer + "." + sub);
		} else {
			Token<Double> a = new Token<Double>();
			a.type = Token.TOKEN_TYPE.DOUBLE;
			a.data = Double.parseDouble(integer + "." + sub);
			templist.add(a);
			if (str.length() > 0)
				load(str.substring(i));
		}
	}

	/**
	 * Returns the next token in the list and removes it from the list
	 * 
	 * @return the next token in the list
	 */
	public Token<?> nextToken() {
		if (!tokenlist.isEmpty()) {
			Token<?> t = tokenlist.get(0);
			tokenlist.remove(0);
			return t;
		}
		return null;

	}

	/**
	 * Returns a list of all tokens and clears the list
	 * 
	 * @return a list of the tokens
	 */
	public List<Token<?>> allTokens() {
		List<Token<?>> l = new ArrayList<Token<?>>();
		for (int i = 0; i < tokenlist.size(); i++) {
			l.add(tokenlist.get(i));
		}
		tokenlist.clear();
		return l;

	}

	private boolean symbolChecker(char c) {
		if (c == ' ' || c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '{' || c == '}' || c == '('
				|| c == ')' || c == ',' || c == '"' || c == '=') {
			return true;
		}
		return false;
	}

}
