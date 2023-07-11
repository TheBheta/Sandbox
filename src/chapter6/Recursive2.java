package chapter6;

public class Recursive2 {

    public static void main(String args[])
    {
        System.out.println(gcd(1, 15));
        System.out.println(gcd(144, 288));
        System.out.println(gcd(144, 54));

    }

    public static long gcd(int x, int y)
    {

        return (y == 0 ? x : gcd(y, x % y));
    }

}