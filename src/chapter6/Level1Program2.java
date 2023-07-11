package chapter6;

public class Level1Program2 {

    public static void main(String args[])
    {
        System.out.println(volumeCone(2, 3));

    }

    public static double volumeCone(double r, double h)
    {
        return r * r * Math.PI * h / 3.0;
    }
}