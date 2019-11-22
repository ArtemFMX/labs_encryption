import java.math.BigInteger;
import java.security.SecureRandomParameters;
import java.util.Random;
import java.util.RandomAccess;

public class Main {

    public static void main(String [] args){
        //String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";

        String N_hex = "00:c0:37:c3:75:88:b4:32:98:87:e6:1c:2d:a3:32:" +
                "4b:1b:a4:b8:1a:63:f9:74:8f:ed:2d:8a:41:0c:2f:" +
                "c2:1b:12:32:f0:d3:bf:a0:24:27:6c:fd:88:44:81:" +
                "97:aa:e4:86:a6:3b:fc:a7:b8:bf:77:54:df:b3:27:" +
                "c7:20:1f:6f:d1:7f:d7:fd:74:15:8b:d3:1c:e7:72:" +
                "c9:f5:f8:ab:58:45:48:a9:9a:75:9b:5a:2c:05:32:" +
                "16:2b:7b:62:18:e8:f1:42:bc:e2:c3:0d:77:84:68:" +
                "9a:48:3e:09:5e:70:16:18:43:79:13:a8:c3:9c:3d:" +
                "d0:d4:ca:3c:50:0b:88:5f:e3";

        //BigInteger N = new BigInteger(N_hex.replace(":", ""), 16);

        BigInteger N = BigInteger.valueOf(2927);

        System.out.println("N = " + N);
        BigInteger g = BigInteger.valueOf(2);
        // in SRP6a, k = H(N, g)
        BigInteger k = SHA256.hash(N, g);

        String a = "asdfasdf";
        String b = "adasfasdgas";


       // System.out.println("b = " + SHA256.hash( b));
       // System.out.println("ab = " + SHA256.hash( a, b));

        Client client = new Client(N, g, k);
        Client client2 = new Client(N, g, k);
        Server server = new Server(N, g, k);
        client.setServer(server);
        client2.setServer(server);

        client2.register("ro1ds", "parol", server);

        client.register("bob", "1234", server);
        client.authorization("bob", "1234");
        client2.authorization("ro1ds", "parol");

    }
}


