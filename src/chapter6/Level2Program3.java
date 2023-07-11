package chapter6;

import java.util.ArrayList;

public class Level2Program3 {

    public static void main(String args[])
    {
        System.out.println(areFactorFriends(12, 18));
        System.out.println(areFactorFriends(1, 18));
        System.out.println(areFactorFriends(17, 18));

    }

    public static boolean areFactorFriends(int x, int y)
    {
        return getFactors(x) == getFactors(y);
    }

    public static int getFactors(int num){
        int factors = 0;
        for (int i = 1; i <= Math.floor(num/2.0); i++){
            if (num%i == 0){
                factors++;
            }
        }
        return factors;
    }
}