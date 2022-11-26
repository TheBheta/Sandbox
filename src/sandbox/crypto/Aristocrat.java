package sandbox.crypto;

import util.English;
import util.Files;

import java.util.*;

public class Aristocrat extends Cipher {

    private ArrayList<String> words_20k = new ArrayList<>();
    private ArrayList<String> words_all = new ArrayList<>();

    public Aristocrat(String code) {
        super(code);
        prepareCipher();
        //load words
        Scanner sc_20k = Files.fileScanner("/words_20k.txt");
        while (sc_20k.hasNextLine())
            words_20k.add(sc_20k.nextLine().strip());
        //Scanner sc_all = Files.fileScanner("/words_all.txt");
        //while (sc_all.hasNextLine())
        //    words_all.add(sc_20k.nextLine().strip());
    }


    public HashMap<String, ArrayList<String>> getWordGuesses(String s) {
        HashMap<String, ArrayList<String>> guesses = new HashMap<>();
        for (String word : new HashSet<>(getWords(s))) {
            //check if word is already solved or too short
            if (word.toUpperCase().equals(word)) continue;
            ArrayList<String> possibilities = new ArrayList<>();
            for (String potentialMatch : words_20k) {
                if (possibilities.size() == 10) break;
                if (patternMatch(word, potentialMatch))
                    possibilities.add(potentialMatch.toUpperCase());
            }
            guesses.put(word, possibilities);
        }
        return guesses;
    }

    /**
     * Matches two words and compares their pattern (e.g. "abca" + "that" returns true,
     * while "abcd" + "that" returns false).
     * If a letter in s1 is CAPITALIZED, this means it is a "locked" letter and the word in
     * s2 must match.
     * @param s1 First string to be compared, from the encryption. Can have LOCKED capitals.
     * @param s2 Second string to be compared, from the words list.
     * @return A boolean representing whether or not the two patterns match.
     */
    public static boolean patternMatch(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        HashMap<Character, Character> mapping = new HashMap<>();
        for (int c = 0; c < s1.length(); c++) {
            if (s1.charAt(c) == '\'') {
                if (s2.charAt(c) != '\'') return false;
            }
            else if (!mapping.containsKey(s1.charAt(c))) {
                if (mapping.values().contains(s2.charAt(c)) || s1.charAt(c) == s2.charAt(c))
                    return false;
                //check for LOCKED
                if ((int) s1.charAt(c) < 91 && (int) s1.charAt(c) != (int) s2.charAt(c) - 32)
                    return false;
                mapping.put(s1.charAt(c), s2.charAt(c));
            } else {
                if (mapping.get(s1.charAt(c)) != s2.charAt(c))
                    return false;
            }
        }
        return true;
    }

    @Override
    public boolean solve() {
        ArrayList<String> solutions = investigate(decrypted, new HashMap<>());
        System.out.println("Solutions found: " + solutions);
        return solutions.size() != 0;
    }

    public ArrayList<String> investigate(String working, HashMap<Character, Character> mapping) {
        System.out.println(working + " | " + mapping);
        ArrayList<String> answers = new ArrayList<>();
        HashMap<String, ArrayList<String>> choices = getWordGuesses(working);
        //choose to fill in word with least possibilities
        ArrayList<String> choiceWords = new ArrayList<>(choices.keySet());
        System.out.println(choiceWords);
        //if no choices, RETURN
        if (choiceWords.size() == 0) {
            answers.add(working);
            return answers;
        }
        choiceWords.sort(Comparator.comparingInt(o -> choices.get(o).size()));
        String targetWord = choiceWords.get(0);
        //investigate guessing from the best guesses
        tryWord:
        for (String possibility : choices.get(targetWord)) {
            //update mapping and check if guess is legal
            HashMap<Character, Character> updatedMapping = new HashMap<>(mapping);
            for (int i = 0; i < targetWord.length(); i++) {
                //if already locked, don't check it
                if ((int) targetWord.charAt(i) < 91) continue;
                if (!updatedMapping.containsKey(targetWord.charAt(i))) {
                    //check if already used letter
                    if (updatedMapping.containsValue(possibility.charAt(i)))
                        continue tryWord;
                    updatedMapping.put(
                            targetWord.charAt(i), (char) ((int) possibility.charAt(i)));
                } else {
                    //check if the new mapping doesn't match with what we already have
                    if (updatedMapping.get(targetWord.charAt(i)) != possibility.charAt(i))
                        continue tryWord;
                }
            }
            System.out.println("Guessing " + targetWord + " is the word " + possibility + "...");
            answers.addAll(
                    investigate(applyMapping(working, updatedMapping), updatedMapping)
            );
        }

        return answers;
    }

    /*
    public ArrayList<String> finish(String almostSolved, HashMap<Character, Character> mapping) {
        System.out.println("Finishing " + almostSolved + ", " + mapping);
        ArrayList<String> solutions = new ArrayList<>();
        HashSet<Character> toDecrypt = new HashSet<>();
        for (char i : almostSolved.toCharArray())
            if (i > 91) toDecrypt.add(i);
        ArrayList<Character> remainingChars = new ArrayList<>();
        for (char i : English.letters.toCharArray()) remainingChars.add(i);
        remainingChars.removeAll(mapping.values());

        for (char i : toDecrypt) {

        }

        return null;

    }

     */


    /**
     * Determines confidence in a solution. Computes a double in the interval 0 to 1, inclusive,
     * representing how confident the program is that its answer is reasonable.
     * @return A double between 0 and 1, where 0 is not confident and all
     * and 1 is 100% confident.
     */
    public double confidence(String s) {
        double realWords = 0;
        for (String word : getWords(s)) {
            realWords += (words_20k.contains(word.toLowerCase()) ? 1 : 0);
        }
        return realWords/getWords(s).size();
    }

    public String applyMapping(String s, HashMap<Character, Character> mapping) {
        String newString = s;
        for (Character d : mapping.keySet())
            newString = newString.replace(d, mapping.get(d));
        return newString;

    }


}