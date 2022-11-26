package sandbox.crypto;

import java.util.*;

abstract class Cipher {


    protected String encrypted;
    //protected ArrayList<String> words;
    protected String decrypted;

    //protected ArrayList<Character> frequencies;
    protected HashMap<Character, Character> mapping;
    protected final ArrayList<Character> commonFrequencies = new ArrayList<>(List.of(
        'e', 't', 'a', 'o', 'n', 'i', 'r', 's', 'h', 'l', 'd', 'c', 'u', 'p', 'f',
        'm', 'w', 'y', 'b', 'g', 'v', 'k', 'q', 'x', 'j', 'z'
    ));

    public Cipher(String code) {
        this.encrypted = code.toLowerCase().replaceAll("[\".!?\\-]", "");
        this.decrypted = encrypted;
        this.prepareCipher();
    }

    public void prepareCipher() {
        //frequencies = new ArrayList<>(commonFrequencies);
        //frequencies.sort((o1, o2) -> -countChar(o1).compareTo(countChar(o2)));
        //words = new ArrayList<>(Arrays.asList(encrypted.split(" ")));
    }

    public ArrayList<String> getWords(String s) {
        return new ArrayList<>(Arrays.asList(s.split(" ")));
    }
    public Integer countChar(char x) {
        int c = 0;
        for (char i : encrypted.toCharArray())
            c += (x == i ? 1 : 0);
        return c;
    }

    public String getEncrypted() {
        return encrypted;
    }


    public void setEncrypted(String encrypted) {
        this.encrypted = encrypted;
        this.prepareCipher();
    }

    public abstract boolean solve();
}