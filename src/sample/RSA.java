package sample;

import java.util.ArrayList;

public class RSA {
    //public key (n, e)
    private int k = 2;
    private int l = 3;
    private long p;
    private long q;
    private long n=1643;

    private long e=67;
    private long fi=1560;
    private long d=163;

    public void setK(int k) {
        this.k = k;
    }

    public void setL(int l) {
        this.l = l;
    }

    public void setN(long n) {
        this.n = n;
    }

    public void setE(long e) {
        this.e = e;
    }

    public void setD(long d) { this.d = d;}

    public String getPublicKey() {
        return "" + n + " " + e;
    }

    public Long getPrivateKey() {
        return d;
    }

    public void setPublicKey(String key){
        String values[] = key.split(" ");
        setN(Integer.parseInt(values[0]));
        setE(Integer.parseInt(values[1]));
    }

    public void setPrivateKey(String key){
        setD(Integer.parseInt(key));
    }

    public void generateRandomPrimes() {
        double leftLimit = Math.pow(27, k);
        double rightLimit = Math.pow(27, l);
        double leftLimitSquared = Math.sqrt(leftLimit);
        double rightLimitSquared = Math.sqrt(rightLimit);
        do {
            do {
                p = (long) (leftLimitSquared + (Math.random() * (rightLimitSquared - leftLimitSquared)));
            } while (!isPrime(p, 4));
            do {
                q = (long) (leftLimitSquared / 2 + (Math.random() * (rightLimitSquared - leftLimitSquared)));
            } while (!isPrime(q, 4));
            n = p * q;
        } while (leftLimit < n && n > rightLimit);
        fi = (p - 1) * (q - 1);
        generateEncyptionExponent();
        computePrivateKey();
    }

    public void computePrivateKey() {
        d = egcd(fi, e);
        d = d % fi;
        if(d < 0)
        {
            d+=fi;
        }
    }

    public long egcd(long a, long b){
        long s1 = 1;
        long s2 = 0;
        long t1 = 0;
        long t2 = 1;
        while (b > 0) {
            long q = a/b;
            long r = a - q*b;
            a = b;
            b = r;
            long s = s1 - q * s2;
            s1 = s2;
            s2 = s;
            long t = t1 - q * t2;
            t1 = t2;
            t2 = t;
        }
        return t1;
    }

    public void generateEncyptionExponent()
    {
        do{
            e = (long) (1 + (Math.random()*(fi - 1)));
        } while (gcd(e, fi) != 1);
    }

    public long gcd(long a, long b)
    {
        while(b != 0)
        {
            long r = a % b;
            a = b;
            b = r;
        }

        return a;
    }

    private static long power(long x, long y, long p) {

        long res = 1;
        x = x % p;
        while (y > 0) {

            if ((y & 1) == 1)
                res = (res * x) % p;

            y = y >> 1;
            x = (x * x) % p;
        }

        return res;
    }

    private static boolean miillerTest(long d, long n) {

        int a = 2 + (int) (Math.random() % (n - 4));

        long x = power(a, d, n);

        if (x == 1 || x == n - 1)
            return true;

        while (d != n - 1) {
            x = (x * x) % n;
            d *= 2;

            if (x == 1)
                return false;
            if (x == n - 1)
                return true;
        }

        return false;
    }

    private static boolean isPrime(long n, int k) {

        if (n <= 1 || n == 4)
            return false;
        if (n <= 3)
            return true;

        long d = n - 1;

        while (d % 2 == 0)
            d /= 2;

        for (int i = 0; i < k; i++)
            if (!miillerTest(d, n))
                return false;

        return true;
    }

    public String encrypt(String message)
    {
        StringBuilder encrytedMessage = new StringBuilder();
        message = paddedMessage(message, k);

        for(int i=0; i<message.length();i+=k)
        {
            long m = convertBlockToValue(message.substring(i, i+k), k, 96);
            m = power(m, e, n);
            encrytedMessage.append(convertValueToBlock(m, 64, l));
        }

        return String.valueOf(encrytedMessage);
    }

    public String decrypt(String plaintext)
    {
        StringBuilder decryptedMessage = new StringBuilder();
        plaintext = paddedMessage(plaintext, l);
        System.out.println("Plaintext-ul este " + plaintext);

        for(int i=0; i<plaintext.length();i+=l)
        {
            long c = convertBlockToValue(plaintext.substring(i, i+l), l, 64);
            c = power(c, d, n);
            System.out.println(c);
            decryptedMessage.append(convertValueToBlock(c, 96, k));
        }

        return String.valueOf(decryptedMessage);
    }

    private String paddedMessage(String message, int size)
    {
        int padding = message.length() % size;
        if(padding != 0)
        {
            StringBuilder messageBuilder = new StringBuilder(message);
            for(int i = 0; i<size-padding; i++)
            {
                messageBuilder.append('_');
            }
            message = messageBuilder.toString();
            return message;
        }
        return message;
    }

    private long convertBlockToValue(String block, int size, int letter)
    {
        long m = 0;
        for (int i=0; i < size ; i++)
        {
            m += convertCharToInt(block.charAt(i), letter) * Math.pow(27, size-i-1);
        }
        return m;
    }

    private int convertCharToInt(char a, int letter)
    {
        System.out.println(a);
        if(a == '_') return 0;
        return a - letter;
    }

    private String convertValueToBlock(long value, int letter, int blockSize)
    {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0;i<blockSize;i++)
        {
            stringBuilder.append(convertIntToChar((int)value % 27, letter));
            value = value / 27;
        }

        return String.valueOf(stringBuilder.reverse());
    }

    private char convertIntToChar(int a, int letter)
    {
        if(a == 0) return '_';
        return (char) (a + letter);
    }

    public int getK() {
        return k;
    }

    public int getL() {
        return l;
    }
}

