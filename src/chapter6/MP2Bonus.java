package chapter6;

public class MP2Bonus {

    public static void main(String[] args)
    {
        //2:24
        int n = 999999;
        long last3 = 1;
        int twos = 0;
        int fives = 0;
        for (int i = 1; i <= n; i++) {
            int m = i;
            while (m%5 == 0) {
                fives++;
                m /= 5;
            }
            while (m%2 == 0) {
                twos++;
                m /= 2;
            }
            last3 *= m;
            last3 %= 1000;
        }
        Math.log(3);
        int netTwos = twos - fives;
        for (int i = 0; i < netTwos; i++) {
            last3 *= 2;
            last3%=1000;
        }
        System.out.println(last3);
    }



}