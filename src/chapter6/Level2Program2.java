package chapter6;

public class Level2Program2 {

    public static void main(String args[])
    {
        System.out.println(emirp(37));
        System.out.println(emirp(73));
        System.out.println(emirp(24));
        System.out.println(emirp(59));

    }

    public static boolean emirp(int n) {
        String rev = "";
        int m = n;
        while (m > 0)
        {
            rev += m%10;
            m /= 10;
        }
        return isPrime(n) && isPrime(Integer.parseInt(rev));
    }

    public static boolean isPrime(int num){
        if (num <= 1){
            return false;
        }
        if (num == 2){
            return true;
        }
        for (int factor = 2; factor <= Math.ceil(Math.sqrt(num)); factor++){
            if (num%factor == 0){
                return false;
            }
        }
        return true;
    }
}