import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Server {

    private boolean checkA = false;
    private boolean checkU = false;
    private boolean checkM = false;

    private BigInteger N, g, k;
    private ArrayList<Node> base = new ArrayList<Node>();
    private Node buf;
    private String s, name;
    private BigInteger  v, A, B, b;
    private BigInteger  u;
    private BigInteger  S, K;
    private BigInteger  M;

    private Client client;

    public Server(BigInteger n, BigInteger g, BigInteger k) {
        N = n;
        this.g = g;
        this.k = k;
    }

    public void addClient(String I, BigInteger v, String s, Client client){
        if(findUser(I) == null){
            base.add(new Node(I, s, v, client));
        } else {
            System.out.println("Такое имя уже занято!!!");
        }
    }

    public BigInteger step1(String login, BigInteger A){
        this.A = A;
        checkA = this.A.compareTo(BigInteger.ZERO) != 0;

        if(checkA){
            buf = findUser(login);

            if (buf != null) {
                client = buf.getClient();
                s = buf.getSalt();
                name = login;
                v = buf.getPassword();

                b = new BigInteger(1024, new Random());
                B = (k.multiply(v).add(g.modPow(b, N))).mod(N);

                client.step2(s, B);
            } else {
                System.out.println("Пользователь не найден");
            }
        } else {
            System.out.println("A = 0");
        }

        return B;
    }

    public void step2(){
            if(checkA){
                u = SHA256.hash(A, B);
                checkU = u.compareTo(BigInteger.ZERO) != 0;

                step4();
            } else {
                System.out.println("A = 0");
            }
    }

    public void step4(){
        if(checkU){
            S = A.multiply(v.modPow(u, N)).modPow(b, N);
            System.out.println("S server = " + S);
            K = SHA256.hash(S);

            step5();
        } else {
            System.out.println("A = 0");
        }
    }

    public void step5(){
        BigInteger M = client.step5();
        this.M = SHA256.hash(SHA256.hash(N).xor(SHA256.hash(g)), SHA256.hash(name), s, A, B, K);

        checkM = this.M.compareTo(M) == 0;

        step6();
    }

    public void step6(){
        if(checkM){

            client.step6(SHA256.hash(A, M, K ));
        } else {
            System.out.println("Неверный логин!!!");
        }
    }


    private Node findUser(String name){
        for (Node n : base) {
            if(n.getName().equals(name)){
                return n;
            }
        }

        return null;
    }
}
