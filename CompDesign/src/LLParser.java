import java.util.HashMap;
import java.util.List;

public class LLParser {

	private HashMap<String, Token<?>> variables;

	/**
	 * Constructs a new Parser with its own local variable mapping
	 */
	public LLParser() {
		variables = new HashMap<String, Token<?>>();
	}

	/**
	 * Parses a given list of tokens
	 * 
	 * @param tokens
	 *            List of tokens to be parsed
	 * @return the resultant token
	 * @throws ParseException
	 *             if the list of tokens could not be parsed correctly
	 */
	public Token<?> parse(List<Token<?>> tokens) throws ParseException {
		if (!tokens.isEmpty()) {
			if (tokens.get(0).type == Token.TOKEN_TYPE.IDENTIFIER) {
				if (tokens.size() > 1) {
					if (tokens.get(1).type == Token.TOKEN_TYPE.EQUALS) {
						return parseStatement(tokens);
					}
				}
			}
		}
		return parseExpression(tokens);
	}

	private Token<?> parseStatement(List<Token<?>> tokens) throws ParseException {
		Token<?> left = tokens.remove(0);
		if (!tokens.isEmpty()) {
			Token<?> op = tokens.remove(0);
			if (op.type == Token.TOKEN_TYPE.EQUALS) {
				Token<?> right = parseExpression(tokens);
				variables.put((String) left.data, right);
				return right;
			} else {
				throw new ParseException("incorrect assignment statement");
			}
		}
		return null;
	}

	private Token<?> parseExpression(List<Token<?>> tokens) throws ParseException {
		Token<?> result = null;
		Token<?> left = parseTerm(tokens);

		if (!tokens.isEmpty()) {
			Token<?> op = tokens.get(0);
			if (op.type != Token.TOKEN_TYPE.ADDOP && op.type != Token.TOKEN_TYPE.SUBOP
					&& op.type != Token.TOKEN_TYPE.DIVOP && op.type != Token.TOKEN_TYPE.MULTOP
					&& op.type != Token.TOKEN_TYPE.MODOP && op.type != Token.TOKEN_TYPE.RIGHTPARANTHESES) {
				if (op.data != null) {
					throw new ParseException("operator expected before " + op.data.toString());
				} else if (op.type == Token.TOKEN_TYPE.EQUALS) {
					throw new ParseException("incorrect assignment statement");
				} else {
					throw new ParseException("operator expected before " + op.toString());
				}
			}
			while (!tokens.isEmpty() && (op.type == Token.TOKEN_TYPE.ADDOP || op.type == Token.TOKEN_TYPE.SUBOP)) {
				tokens.remove(0);
				Token<?> right = parseTerm(tokens);

				if (op.type == Token.TOKEN_TYPE.ADDOP) {
					if (left.type == Token.TOKEN_TYPE.DOUBLE) {
						if (right.type == Token.TOKEN_TYPE.DOUBLE) {
							Token<Double> temp = new Token<Double>();
							temp.type = Token.TOKEN_TYPE.DOUBLE;
							temp.data = (Double) left.data + (Double) right.data;
							result = temp;
						} else if (right.type == Token.TOKEN_TYPE.INTEGER) {
							Token<Double> temp = new Token<Double>();
							temp.type = Token.TOKEN_TYPE.DOUBLE;
							temp.data = (Double) left.data + (Integer) right.data;
							result = temp;
						} else if (right.type == Token.TOKEN_TYPE.STRING) {
							Token<String> temp = new Token<String>();
							temp.type = Token.TOKEN_TYPE.STRING;
							temp.data = left.data.toString() + right.data.toString();
							result = temp;
						}
					} else if (left.type == Token.TOKEN_TYPE.INTEGER) {
						if (right.type == Token.TOKEN_TYPE.DOUBLE) {
							Token<Double> temp = new Token<Double>();
							temp.type = Token.TOKEN_TYPE.DOUBLE;
							temp.data = (Integer) left.data + (Double) right.data;
							result = temp;
						} else if (right.type == Token.TOKEN_TYPE.INTEGER) {
							Token<Integer> temp = new Token<Integer>();
							temp.type = Token.TOKEN_TYPE.INTEGER;
							temp.data = (Integer) left.data + (Integer) right.data;
							result = temp;
						} else if (right.type == Token.TOKEN_TYPE.STRING) {
							Token<String> temp = new Token<String>();
							temp.type = Token.TOKEN_TYPE.STRING;
							temp.data = left.data.toString() + right.data.toString();
							result = temp;
						}
					} else if (left.type == Token.TOKEN_TYPE.STRING) {
						if (right.type == Token.TOKEN_TYPE.DOUBLE || right.type == Token.TOKEN_TYPE.INTEGER
								|| right.type == Token.TOKEN_TYPE.STRING) {
							Token<String> temp = new Token<String>();
							temp.type = Token.TOKEN_TYPE.STRING;
							temp.data = (String) left.data + right.data.toString();
							result = temp;
						}
					}

				} else if (op.type == Token.TOKEN_TYPE.SUBOP) {
					if (left.type == Token.TOKEN_TYPE.DOUBLE) {
						if (right.type == Token.TOKEN_TYPE.DOUBLE) {
							Token<Double> temp = new Token<Double>();
							temp.type = Token.TOKEN_TYPE.DOUBLE;
							temp.data = (Double) left.data - (Double) right.data;
							result = temp;
						} else if (right.type == Token.TOKEN_TYPE.INTEGER) {
							Token<Double> temp = new Token<Double>();
							temp.type = Token.TOKEN_TYPE.DOUBLE;
							temp.data = (Double) left.data - (Integer) right.data;
							result = temp;
						}
					} else if (left.type == Token.TOKEN_TYPE.INTEGER) {
						if (right.type == Token.TOKEN_TYPE.DOUBLE) {
							Token<Double> temp = new Token<Double>();
							temp.type = Token.TOKEN_TYPE.DOUBLE;
							temp.data = (Integer) left.data - (Double) right.data;
							result = temp;
						} else if (right.type == Token.TOKEN_TYPE.INTEGER) {
							Token<Integer> temp = new Token<Integer>();
							temp.type = Token.TOKEN_TYPE.INTEGER;
							temp.data = (Integer) left.data - (Integer) right.data;
							result = temp;
						}
					} else if (left.type == Token.TOKEN_TYPE.STRING || right.type == Token.TOKEN_TYPE.STRING) {
						throw new ParseException("cannot subtract strings");
					}
				}
				left = result;
				if (!tokens.isEmpty()) {
					op = tokens.get(0);
				}
			}
			return left;
		}
		return left;
	}

	private Token<?> parseTerm(List<Token<?>> tokens) throws ParseException {
		Token<?> result = null;
		Token<?> left = parseFactor(tokens);

		if (!tokens.isEmpty()) {
			Token<?> op = tokens.get(0);
			if (op.type != Token.TOKEN_TYPE.ADDOP && op.type != Token.TOKEN_TYPE.SUBOP
					&& op.type != Token.TOKEN_TYPE.DIVOP && op.type != Token.TOKEN_TYPE.MULTOP
					&& op.type != Token.TOKEN_TYPE.MODOP && op.type != Token.TOKEN_TYPE.RIGHTPARANTHESES) {
				if (op.data != null) {
					throw new ParseException("operator expected before " + op.data.toString());
				} else if (op.type == Token.TOKEN_TYPE.EQUALS) {
					throw new ParseException("incorrect assignment statement");
				} else {
					throw new ParseException("operator expected before " + op.toString());
				}
			}
			while (!tokens.isEmpty() && (op.type == Token.TOKEN_TYPE.MULTOP || op.type == Token.TOKEN_TYPE.DIVOP
					|| op.type == Token.TOKEN_TYPE.MODOP)) {
				tokens.remove(0);
				Token<?> right = parseFactor(tokens);
				if (op.type == Token.TOKEN_TYPE.MULTOP) {
					if (right.type == Token.TOKEN_TYPE.STRING) {
						throw new ParseException("cannot multiply by strings");
					} else if (left.type == Token.TOKEN_TYPE.DOUBLE) {
						if (right.type == Token.TOKEN_TYPE.DOUBLE) {
							Token<Double> temp = new Token<Double>();
							temp.type = Token.TOKEN_TYPE.DOUBLE;
							temp.data = (Double) left.data * (Double) right.data;
							result = temp;
						} else if (right.type == Token.TOKEN_TYPE.INTEGER) {
							Token<Double> temp = new Token<Double>();
							temp.type = Token.TOKEN_TYPE.DOUBLE;
							temp.data = (Double) left.data * (Integer) right.data;
							result = temp;
						}
					} else if (left.type == Token.TOKEN_TYPE.INTEGER) {
						if (right.type == Token.TOKEN_TYPE.DOUBLE) {
							Token<Double> temp = new Token<Double>();
							temp.type = Token.TOKEN_TYPE.DOUBLE;
							temp.data = (Integer) left.data * (Double) right.data;
							result = temp;
						} else if (right.type == Token.TOKEN_TYPE.INTEGER) {
							Token<Integer> temp = new Token<Integer>();
							temp.type = Token.TOKEN_TYPE.INTEGER;
							temp.data = (Integer) left.data * (Integer) right.data;
							result = temp;
						}
					} else if (left.type == Token.TOKEN_TYPE.STRING) {
						if (right.type == Token.TOKEN_TYPE.INTEGER) {
							Token<String> temp = new Token<String>();
							temp.type = Token.TOKEN_TYPE.STRING;
							temp.data = "";
							for (int i = 0; i < (Integer) right.data; i++) {
								temp.data += (String) left.data;
							}
							result = temp;
						} else if (right.type == Token.TOKEN_TYPE.STRING) {
							throw new ParseException("cannot multiply by strings");
						} else if (right.type == Token.TOKEN_TYPE.DOUBLE) {
							throw new ParseException("cannot string by double");
						}
					}
				} else if (op.type == Token.TOKEN_TYPE.DIVOP) {
					if (left.type == Token.TOKEN_TYPE.STRING || right.type == Token.TOKEN_TYPE.STRING) {
						throw new ParseException("cannot divide strings");
					} else if (left.type == Token.TOKEN_TYPE.DOUBLE) {
						if (right.type == Token.TOKEN_TYPE.DOUBLE) {
							Token<Double> temp = new Token<Double>();
							temp.type = Token.TOKEN_TYPE.DOUBLE;
							temp.data = (Double) left.data / (Double) right.data;
							if (temp.data == Double.POSITIVE_INFINITY || temp.data == Double.NEGATIVE_INFINITY) {
								throw new ParseException("cannot divide by zero");
							}
							result = temp;
						} else if (right.type == Token.TOKEN_TYPE.INTEGER) {
							Token<Double> temp = new Token<Double>();
							temp.type = Token.TOKEN_TYPE.DOUBLE;
							temp.data = (Double) left.data / (Integer) right.data;
							if (temp.data == Double.POSITIVE_INFINITY || temp.data == Double.NEGATIVE_INFINITY) {
								throw new ParseException("cannot divide by zero");
							}
							result = temp;
						}
					} else if (left.type == Token.TOKEN_TYPE.INTEGER) {
						if (right.type == Token.TOKEN_TYPE.DOUBLE) {
							Token<Double> temp = new Token<Double>();
							temp.type = Token.TOKEN_TYPE.DOUBLE;
							temp.data = (Integer) left.data / (Double) right.data;
							if (temp.data == Double.POSITIVE_INFINITY || temp.data == Double.NEGATIVE_INFINITY) {
								throw new ParseException("cannot divide by zero");
							}
							result = temp;
						} else if (right.type == Token.TOKEN_TYPE.INTEGER) {
							try {
								Token<Integer> temp = new Token<Integer>();
								temp.type = Token.TOKEN_TYPE.INTEGER;
								temp.data = (Integer) left.data / (Integer) right.data;
								result = temp;
							} catch (ArithmeticException e) {
								throw new ParseException("cannot divide by zero");
							}
						}
					}
				} else if (op.type == Token.TOKEN_TYPE.MODOP) {
					if (left.type != Token.TOKEN_TYPE.INTEGER || right.type != Token.TOKEN_TYPE.INTEGER) {
						throw new ParseException("mod operation can only be performed on integers");
					} else {
						try {
							Token<Integer> temp = new Token<Integer>();
							temp.type = Token.TOKEN_TYPE.INTEGER;
							temp.data = (Integer) left.data % (Integer) right.data;
							result = temp;
						} catch (ArithmeticException e) {
							throw new ParseException("cannot divide by zero");
						}
					}
				}
				left = result;
				if (!tokens.isEmpty()) {
					op = tokens.get(0);
				}
			}
			return left;
		} else {
			return left;
		}

	}

	private Token<?> parseFactor(List<Token<?>> tokens) throws ParseException {
		try {
			Token<?> factor = tokens.remove(0);
			if (factor.type == Token.TOKEN_TYPE.IDENTIFIER) {
				if (variables.containsKey(factor.data)) {
					factor = variables.get(factor.data);
				} else {
					throw new ParseException("identifier not yet assigned");
				}
			} else if (factor.type == Token.TOKEN_TYPE.LEFTPARANTHESES) {
				try {
					Token<?> temp = parseExpression(tokens);
					tokens.remove(0);
					return temp;
				} catch (IndexOutOfBoundsException e) {
					throw new ParseException("incorrect parantheses usage");
				}
			} else if (!(factor.type == Token.TOKEN_TYPE.STRING || factor.type == Token.TOKEN_TYPE.INTEGER
					|| factor.type == Token.TOKEN_TYPE.DOUBLE)) {
				throw new ParseException("not a valid line or token type not yet implemented");
			}
			return factor;
		} catch (IndexOutOfBoundsException e) {
			throw new ParseException("operation requires two operands");
		}
	}

}
