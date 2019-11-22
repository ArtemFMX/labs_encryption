import java.math.BigInteger;
import java.util.Random;

public class Main {
    public static void main(String [] args){
        BigInteger p, q;//secret key
        BigInteger n, e;//open key
        BigInteger fi, d;

        Random random = new Random();

        p = BigInteger.probablePrime(1024, random);
        q = BigInteger.probablePrime(1024, random);

        n = p.multiply(q);
        System.out.println("n = " + n);
        fi = f(p, q);
        e = findE(fi);
        System.out.println("e = " + e);

        d = e.modInverse(fi);
        System.out.println("d = " + d);


        String message1 = "Я сделал 4 лабу!!!";
        BigInteger[] message2 = encrypt1(n, e, message1);

        for (BigInteger big : message2) {
            System.out.println(big);
        }

        String finish = decrypt(n, d, message2);

        System.out.println(finish);

    }
    private static BigInteger f(BigInteger p, BigInteger q){
        BigInteger a = p.subtract(BigInteger.ONE);
        BigInteger b = q.subtract(BigInteger.ONE);
        return a.multiply(b);
    }

    private static BigInteger findE(BigInteger f){
        BigInteger e = BigInteger.TWO;
        while((e.compareTo(f) == -1) & (e.gcd(f).compareTo(BigInteger.ONE) != 0)){
            e = e.add(BigInteger.ONE);
        }

        return e;
    }

    private static BigInteger[] encrypt1 (BigInteger n, BigInteger e, String message){

        char[] messageMass = message.toCharArray();
        BigInteger[] encryptedMessage = new BigInteger[messageMass.length];
        int buf;

        System.out.println("encrypt");
        for (int i = 0; i < messageMass.length; i ++) {
            buf = (int)(messageMass[i]);
            encryptedMessage[i] = BigInteger.valueOf(buf).modPow(e, n);
        }


        return encryptedMessage;
    }

    private static String decrypt(BigInteger n, BigInteger d, BigInteger[] encryptedMessage){
        String decryptedMessage = "";
        int buf;

        System.out.println("decrypt");

        for (BigInteger c : encryptedMessage){
            buf = (c.modPow(d, n)).intValue();
            decryptedMessage += (char)buf;
        }

        return decryptedMessage;
    }
}
