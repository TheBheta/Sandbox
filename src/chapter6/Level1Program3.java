package chapter6;

public class Level1Program3 {

    public static void main(String args[])
    {
        System.out.println(randomString(4));

    }

    public static String randomString(int len)
    {
        String rand = "";
        for (int i = 0; i < len; i++)
            rand = rand + (char) ((int) (Math.random() * 26) + 65);
        return rand;
    }
}