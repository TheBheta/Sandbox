package chapter6;

public class Level1Program4 {

    public static void main(String args[])
    {
        System.out.println(hasZero(4));
        System.out.println(hasZero(40));
        System.out.println(hasZero(401));

    }

    public static boolean hasZero(int n)
    {
        return String.valueOf(n).contains("0");
    }
}