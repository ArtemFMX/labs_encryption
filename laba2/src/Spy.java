public class Spy extends User {

    private int secretResultA, secretResultB;

    Spy(String name){
        super(name);
    }

    public void setNumberForEve(String key, int value){
        switch (key){
            case "p":
                p = value;
                break;
            case "q":
                q = value;
                break;
            case "secretResultA":
                secretResultA = value;
                break;
            case "secretResultB":
                secretResultB = value;
                break;
        }
    }
}
