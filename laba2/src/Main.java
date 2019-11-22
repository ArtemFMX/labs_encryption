import java.util.*;

public class Main {
    public static void main(String[] args){
        User Alice, Bob;
        Spy  Eve;

        Random random = new Random();

        int []primaryMass = {  2,   3,   5,   7,   11,  13,  17,  19,  23,  29,
                        31,  37,  41,  43,  47,  53,  59,  61,  67,  71,
                        73,  79,  83,  89,  97,  101, 103, 107, 109, 113,
                        127, 131, 137, 139, 149, 151, 157, 163, 167, 173,
                        179, 181, 191, 193, 197, 199, 211, 223, 227, 229,
                        233, 239, 241, 251, 257, 263, 269, 271, 277, 281,
                        283, 293, 307, 311, 313, 317, 331, 337, 347, 349,
                        353, 359, 367, 373, 379, 383, 389, 397, 401, 409,
                        419, 421, 431, 433, 439, 443, 449, 457, 461, 463,
                        467, 479, 487, 491, 499, 503, 509, 521, 523, 541};
        Map<String, Integer> channel = new HashMap<String, Integer>();

        Alice = new User("Alice");
        Bob = new User("Bob");
        Eve = new Spy("Eve");


        step1(random, primaryMass, channel, Alice, Bob, Eve);
        step2(random, channel, Alice, Bob, Eve);    }

    private static int getRand(Random random, int min, int max){
        return min + random.nextInt(max - min);
    }

    private static void step1(Random random, int [] mass, Map<String, Integer> channel, User alice, User bob, Spy eve){

        channel.put("q", mass[getRand(random, 0, mass.length)]);
        channel.put("p", (int)GetPRoot(channel.get("q" + "")));//

        System.out.print("Alice и Bob выбрали \"p\", оно равно: " + channel.get("p") + "\n");
        System.out.print("Alice и Bob выбрали \"q\", оно равно: " + channel.get("q") + "\n");

        alice.setP(channel.get("p"));
        alice.setQ(channel.get("q"));
        bob.setP(channel.get("p"));
        bob.setQ(channel.get("q"));

        checkChannel(channel, eve, "p");
        checkChannel(channel, eve, "q");
    }

    private static void step2(Random random, Map<String, Integer> channel, User alice, User bob, Spy eve){

        createSecretNumber(random, alice);
        createSecretNumber(random, bob);

        System.out.print("Alice выбрала чиcло \"" + "secretNumber" + "\", который равен: " + alice.getSecretNumber() + "\n");
        System.out.print("Bob выбрал чиcло \"" + "secretNumber" + "\", который равен: " + bob.getSecretNumber() + "\n");

        channel.put("secretResultA", alice.createSecretResult());
        channel.put("secretResultB", bob.createSecretResult());

        System.out.print("Alice получила \"" + "secretResultB" + "\", который равен: " + channel.get("secretResultB") + "\n");
        System.out.print("Bob получил \"" + "secretResultA" + "\", который равен: " + channel.get("secretResultA") + "\n");

        checkChannel(channel, eve, "secretResultA");
        checkChannel(channel, eve, "secretResultB");

        alice.setYourSecretResult(channel.get("secretResultB"));
        bob.setYourSecretResult(channel.get("secretResultA"));


        alice.setSecretKey();
        bob.setSecretKey();

        System.out.print("Alice посчитала \"" + "SecretKey" + "\", который равен: " + alice.getSecretKey() + "\n");
        System.out.print("Bob посчитал \"" + "SecretKey" + "\", который равен: " + bob.getSecretKey() + "\n");
    }

    private static void checkChannel(Map<String, Integer> channel, Spy eve, String Key){
        if(channel.containsKey(Key)){
            eve.setNumberForEve(Key, channel.get(Key));
            System.out.print("Eve услышала \"" + Key + "\", который равен: " + channel.get(Key) + "\n");
        }
    }

    private static void createSecretNumber(Random random, User user){
        user.setSecretNumber (getRand(random, 0, 100));
    }

    public static double GetPRoot(double p) {
        for (long i = 0; i < p; i++)
            if (IsPRoot(p, i))
                return i;
        return 0;
    }

    public static boolean IsPRoot(double p, double a) {
        if (a == 0 || a == 1)
            return false;
        double last = 1;

        Set<Double> set = new HashSet<>();
        for (double i = 0; i < p - 1; i++) {
            last = (last * a) % p;
            if (set.contains(last)) // Если повтор
                return false;
            set.add(last);
        }
        return true;
    }
}
