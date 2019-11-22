import java.math.BigInteger;

public class Node {

    private String name;
    private String salt;
    private BigInteger password;
    private Client client;

    public Node(String name, String salt, BigInteger password, Client client) {
        this.name = name;
        this.salt = salt;
        this.password = password;
        this.client = client;
    }

    public String getSalt() {
        return salt;
    }

    public BigInteger getPassword() {
        return password;
    }

    public String getName(){
        return name;
    }

    public Client getClient() {
        return client;
    }
}
