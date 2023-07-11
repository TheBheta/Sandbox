package chapter6;

public class Recursive4 {

    public static void main(String args[])
    {
        System.out.println(countBinary(6));

    }

    public static int countBinary(int n)
    {
        //int total = 0;
        //for (int i = 0; i < Math.pow(2, n); i++) {

        //    String s = Integer.toBinaryString(i);
        //    while (s.length() < n)
        //        s = "0" + s;
        //    total += s.contains("00") ? 1 : 0;
        //}
        //return (int) Math.pow(2, n) - total;
        //engineer's induction :skull emoji:
        return (n == 1 ? 2 : n == 2 ? 3 : countBinary(n - 2) + countBinary(n - 1));
    }



}