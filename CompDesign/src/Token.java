
public class Token<T> {

	/**
	 * Type of token
	 */
	public enum TOKEN_TYPE {
		DIGIT,
		LETTER,
		ADDOP,
		SUBOP,
		MULTOP,
		DIVOP,
		MODOP,
		LEFTBRACKET,
		RIGHTBRACKET,
		LEFTPARANTHESES,
		RIGHTPARANTHESES,
		COMMA,
		QUOTES,		
		INTEGER,
		DOUBLE,
		STRING,
		IDENTIFIER,
		EQUALS
		
	}
	
	public T data;
	public TOKEN_TYPE type;
	
	@Override
	public String toString() {
		String converted = "<" + type;
		if (data != null) {
			converted += ":" + data.toString();
		}
		converted += ">";
		return converted;
	}
	

}
