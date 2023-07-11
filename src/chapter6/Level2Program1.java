package chapter6;

public class Level2Program1 {

    public static void main(String args[])
    {
        coins(0.04);
        coins(0.09);
        coins(0.24);
        coins(0.99);
        coins(0.82);

    }

    public static void coins(double money)
    {
        int cents = (int) (100 * money);
        int q = 0;
        int d = 0;
        int n = 0;
        int p = 0;
        while (cents >= 25)
        {
            q++;
            cents -= 25;
        }
        while (cents >= 10)
        {
            d++;
            cents -= 10;
        }
        while (cents >= 5)
        {
            n++;
            cents -= 5;
        }
        p = cents;
        System.out.println(q + " quarters, " + d + " dimes, " + n + " nickels, " + " and " + p + " pennies");
    }
}