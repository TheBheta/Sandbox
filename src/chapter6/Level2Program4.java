package chapter6;

public class Level2Program4 {

    public static void main(String args[])
    {
        System.out.println(removeDoubleLetter("AABBBCDDEEEFAAAABB"));
        System.out.println(removeDoubleLetter("AABBC"));
    }

    public static String removeDoubleLetter(String s)
    {
        s = "   " + s +  "    ";
        String newS = "";
        for (int i = 0; i < s.length() - 2; i+=2)
        {
            if ((s.charAt(i) != s.charAt(i + 1)) &&
                    (s.charAt(i + 1) == s.charAt(i + 2)) &&
                    (s.charAt(i + 2) != s.charAt(i + 3)))
            {
                continue;
            }
            else
            {
                newS += String.valueOf(s.charAt(i + 1)) + s.charAt(i + 2);
            }
        }
        return newS.strip();
    }

}