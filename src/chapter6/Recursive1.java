package chapter6;

public class Recursive1 {

    public static void main(String args[])
    {
        System.out.println(power(3, 4));
        System.out.println(power(3, 10));

    }

    public static long power(int base, int exp)
    {
        return (exp == 1 ? base : base * power(base, exp - 1));
    }

}