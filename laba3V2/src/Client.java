import java.math.BigInteger;
import java.util.Random;

public class Client {

    private boolean checkB  = false;
    private boolean checkU  = false;
    private boolean checkR  = false;

    private String password;
    private String salt;
    private String name;
    private BigInteger N, g, k;
    private BigInteger a, A;
    private BigInteger B;
    private BigInteger u;
    private BigInteger x, S, K;
    private BigInteger M;
    private BigInteger R;

    private Server server;

    public Client(BigInteger n, BigInteger g, BigInteger k) {
        N = n;
        this.g = g;
        this.k = k;
    }

    public void register(String login, String password, Server server){
        String salt = random_string(12);
        BigInteger x = SHA256.hash(salt, password);
        BigInteger v = g.modPow(x, N);
        server.addClient(login, v, salt, this);
    }

    public void authorization(String login, String password){
        name = login;
        this.password = password;

        server.step1(name, step1());
    }

    public BigInteger step1(){//a = random(), A = g^a % N

        a = new BigInteger(1024, new Random());
        A = g.modPow(a, N);
        return A;
    }

    public void step2(String s, BigInteger B){
        this.B = B;
        salt = s;

        checkB = B.compareTo(BigInteger.ZERO) != 0;

        step3();
    }

    public void step3(){
        if(checkB && A != null){
            u = SHA256.hash(A, B);
            checkU = u.compareTo(BigInteger.ZERO) != 0;

            step4();
            server.step2();
        } else {
            System.out.println("B = 0");
        }

    }

    public void step4(){
        if(checkU){
            x = SHA256.hash(salt, password);
            // S = (B - K*(g^x mod N))^(a+u*x)) mod N
            S = (B.subtract(k.multiply(g.modPow(x, N)))).modPow(a.add(u.multiply(x)), N);
            System.out.println("S client = " + S);
            K = SHA256.hash(S);
        }else {
            System.out.println("U = 0");
        }
    }

    public BigInteger step5(){
        M = SHA256.hash(SHA256.hash(N).xor(SHA256.hash(g)), SHA256.hash(name), salt, A, B, K);
        return M;
    }

    public BigInteger step6(BigInteger R){
        this.R = SHA256.hash(A, M, K );
        checkR = this.R.compareTo(R) == 0;

        if(checkR){
            System.out.println("Вход выполнен");
        } else {
            System.out.println("Ошибка входа, некорректный пароль");
        }

        return R;
    }

    public String random_string(int lenght){
        String salt = "";
        Random random = new Random();
        int x;

        for(int i = 0; i < lenght; i++){
            x = 45 + random.nextInt(122 - 45);//getRand(random, 45, 122);
            if(x == 45 || (x >= 48 && x <= 57)  || (x >= 65 && x <= 90)|| x == 95 || (x >= 97 && x <= 122)){
                salt += (char)x;
            } else {
                i--;
            }
        }

        return salt;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
