package chapter6;

public class Level1Program1 {

    public static void main(String args[])
    {
        System.out.println(distance(0, 0, 3, 4));
        System.out.println(distance(1, 1, 3, 4));

    }

    public static double distance(double x1, double y1, double x2, double y2)
    {
        return Math.sqrt(Math.pow(Math.abs(x1-x2), 2) + Math.pow(Math.abs(y1-y2),2));
    }
}