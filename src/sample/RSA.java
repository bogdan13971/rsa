package sample;

public class RSA {
    private int k = 2;
    private int l = 3;
    private long p;
    private long q;
    private long n;
    private long e;
    private long fi;

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

    public void generateRandomPrimes() {
        double leftLimit =  Math.pow(27, k);
        double rightLimit = Math.pow(27, l);
        double leftLimitSquared = Math.sqrt(leftLimit);
        double rightLimitSquared = Math.sqrt(rightLimit);
        do{
            do {
                p = (long) (leftLimitSquared + (Math.random() * (rightLimitSquared - leftLimitSquared)));
            } while (!isPrime(p, 4));
            do {
                q = (long) (leftLimitSquared / 2 + (Math.random() * (rightLimitSquared - leftLimitSquared)));
            } while (!isPrime(q, 4));
            n = p*q;
        } while (leftLimit < n && n > rightLimit);
        fi = (p-1)*(q-1);
    }

    public void generateEncyptionExponent()
    {
        do{
            e = (long) (1 + (Math.random()*(fi - 1)));
        } while (gcd(e, fi) == 1);
    }

    private long gcd(long a, long b)
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
        message = paddedMessage(message);

        for(int i=0; i<message.length();i+=k)
        {
            long m = convertBlockToValue(message.substring(i, i+k));
            encrytedMessage.append(convertValueToBlock(m));
        }

        return String.valueOf(encrytedMessage);
    }

    private String paddedMessage(String message)
    {
        int padding = message.length() % k;
        if(padding != 0)
        {
            StringBuilder messageBuilder = new StringBuilder(message);
            for(int i = 0; i<k-padding; i++)
            {
                messageBuilder.append('_');
            }
            message = messageBuilder.toString();
            return message;
        }
        return message;
    }

    private long convertBlockToValue(String block)
    {
        long m = 0;
        for (int i=0; i < k ; i++)
        {
            m += convertCharToInt(block.charAt(i)) * Math.pow(27, k-i-1);
        }
        return power(m, e, n);
    }

    private int convertCharToInt(char a)
    {
        if(a == '_') return 0;
        return a - 96;
    }

    private String convertValueToBlock(long value)
    {
        StringBuilder stringBuilder = new StringBuilder();

        while(value != 0)
        {
            stringBuilder.append(convertIntToChar((int)value % 27));
            value = value / 27;
        }

        return String.valueOf(stringBuilder.reverse());
    }

    private char convertIntToChar(int a)
    {
        if(a == 0) return '_';
        return (char) (a + 64);
    }



}

