package chapter6;

public class Recursive3 {

    public static void main(String args[])
    {
        System.out.println(testPalindrome("aha"));
        System.out.println(testPalindrome("take on me"));
        System.out.println(testPalindrome("racecar"));

    }

    public static boolean testPalindrome(String s)
    {
        return (s.length() == 0 || s.length() == 1 ? true :
                s.charAt(0) == s.charAt(s.length()-1) && testPalindrome(s.substring(1, s.length()-1)));
    }

}