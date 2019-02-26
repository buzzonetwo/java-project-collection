import java.math.BigInteger;
import java.util.Random;

public class asd {

	public static void main(String[] args) {
		BigInteger p = new BigInteger("198378619719859874021");
		BigInteger q = new BigInteger("365861514538520394941");
		BigInteger e = new BigInteger("65537");
		BigInteger c = new BigInteger("150815");
		BigInteger n = new BigInteger("435979");
		p.multiply(q);
		BigInteger totient = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		BigInteger d = new BigInteger("1941");//e.modInverse(totient);
		BigInteger m = c.modPow(d, n);
		System.out.println(m);
		System.out.println(new String(m.toByteArray()));
		
		BigInteger x = new BigInteger("9b9c138e0d473b6e6cf44acfa3becb358b91d0ba9bfb37bf11effcebf9e0fe4a86439e8217819c273ea5c1c5acfd70147533aa550aa70f2e07cc98be1a1b0ea36c0738d1c994c50b1bd633e3873fc0cb377e7", 16);
		BigInteger t = new BigInteger("27335d21ca51432fa000ddf9e81f630314a0ef2e35d81a839584c5a7356b94934630ebfc2ef9c55b111e8c373f2db66ca3be0c0818b1d4eda7d53c1bd0067f66a12897099b5e322d85a8da45b72b828813af23", 16);
		System.out.println(x.toString());
		System.out.println(t.toString());
	}

}
