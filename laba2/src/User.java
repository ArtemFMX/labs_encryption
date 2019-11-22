import java.math.BigInteger;

public class User {
    protected String name;
    protected int p, q;
    private int secretNumber;
    private int secretKey;

    protected int mySecretResult, yourSecretResult;

    User(String name){
        this.name = name;
    }

    public void setP(int p) {
        this.p = p;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public void setSecretNumber(int secretNumber) {
        this.secretNumber = secretNumber;
    }

    public int createSecretResult() {
        mySecretResult = (int)f(p, q, secretNumber);
        return mySecretResult;
    }

    public void setYourSecretResult(int yourSecretResult) {
        this.yourSecretResult = yourSecretResult;
    }

    public int getSecretKey() {
        return secretKey;
    }

    public void setSecretKey() {
        secretKey = (int)f(yourSecretResult, q ,secretNumber);
    }

    public int getSecretNumber() {
        return secretNumber;
    }

    private double f(int p, int q, int step){
        double f = modPower(p, q, step);// % q;
        //f = f % q;
        return f;
    }

    public static double modPower(int  p, int  q, int  step)
    {
        BigInteger  BigInteger1= new BigInteger(String.valueOf(p));
        BigInteger BigInteger2 = new BigInteger(String.valueOf(q));
        BigInteger BigInteger3 = new BigInteger(String.valueOf(step));
        return BigInteger1.modPow(BigInteger3, BigInteger2).doubleValue();
    }
}
