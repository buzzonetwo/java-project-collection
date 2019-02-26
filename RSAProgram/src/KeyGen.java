import java.math.BigInteger;
import java.util.Base64;
import java.util.Random;

public class KeyGen {
	
	private BigInteger p,q,n,totient,e,d;
	private String publickey, privatekey;
	
	/**
	 * Generate a RSA public and private key of length bitLength
	 * @param bitLength the length of the RSA key (should be even/multiple of 2)
	 */
	public KeyGen(int bitLength) {
		p = BigInteger.probablePrime(bitLength/2, new Random());
		q = BigInteger.probablePrime(bitLength/2, new Random());
		n = p.multiply(q);
		totient = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		do {
			e = BigInteger.probablePrime(bitLength-2, new Random());
			d = e.modInverse(totient);
		} while (e.gcd(totient).compareTo(BigInteger.valueOf(1)) != 0 || totient.min(e).equals(totient));
		
		publickey = toBase64(e) + "&" + toBase64(n);
		privatekey = toBase64(d) + "&" + toBase64(n);
	}

	/**
	 * Returns the public key
	 * @return a BigInteger public key
	 */
	public String getPublicKey() {
		return publickey;
	}
	
	/**
	 * Returns the private key
	 * @return a BigInteger private key
	 */
	public String getPrivateKey() {
		return privatekey;
	}
	
	/**
	 * Returns p
	 * @return BigInteger p
	 */
	public BigInteger getp() {
		return p;
	}
	
	/**
	 * Returns q
	 * @return BigInteger q
	 */
	public BigInteger getq() {
		return q;	
	}
	
	/**
	 * Returns n = (p x q)
	 * @return BigInteger n
	 */
	public BigInteger getn() {
		return n;
	}
	
	/**
	 * Returns totient of n = ((p-1) x (q-1))
	 * @return BigInteger totient
	 */
	public BigInteger getTotient() {
		return totient;	
	}

	/**
	 * Returns e
	 * @return BigInteger e
	 */
	public BigInteger gete() {
		return e;	
	}
	
	/**
	 * Returns d = modInv(e)
	 * @return BigInteger d
	 */
	public BigInteger getd() {
		return d;
	}
	
	/**
	 * Takes a BigInteger and encodes it in Base64
	 * @param b the big integer to be encoded
	 * @return a Base64 String representation of the BigInteger
	 */
	public static String toBase64(BigInteger b) {
		String encoded = Base64.getEncoder().encodeToString(b.toByteArray());
		return encoded;
	}
	
}
