package sandbox.crypto;

import util.English;
import util.Files;
import util.Timer;

import java.util.*;

public class Aristocrat extends Cipher {

    private static ArrayList<String> words_20k = new ArrayList<>();
    private static ArrayList<String> words_all = new ArrayList<>();
    private int searchDepth = 40000;
    public Aristocrat(String code) {
        super(code);
        prepareCipher();
        //load words
        Scanner sc_20k = Files.fileScanner("/words_20k.txt");
        while (sc_20k.hasNextLine())
            words_20k.add(sc_20k.nextLine().strip());
        Scanner sc_all = Files.fileScanner("/words_all_2.txt");
        while (sc_all.hasNextLine())
            words_all.add(sc_all.nextLine().strip());
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
    public boolean patternMatch(String s1, String s2) {
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

    public ArrayList<String> findMatches(String word) {
        int trueDepth = searchDepth;
        if (word.length() < 4) trueDepth = searchDepth/4;
        ArrayList<String> matches = new ArrayList<>();
        for (String match : words_all.subList(0, trueDepth)) {
            if (patternMatch(word, match)) matches.add(match.toUpperCase());
        }
        return matches;
    }

    @Override
    public boolean solve() {
        Timer.startTimer();
        ArrayList<String> solutions = investigate(decrypted, new HashMap<>());
        System.out.println(solutions.get(0));
        System.out.println(confidence("confidence: " + solutions.get(0)));
        System.out.println("solved in " + Timer.elapsedTime() + "ms");
        //System.out.println(solutions.subList(0, 5).toString().replace(",", "\n"));
        return solutions.size() != 0;
    }

    public ArrayList<String> investigate(String working, HashMap<Character, Character> mapping) {
        //System.out.println(working + " | " + mapping);
        ArrayList<String> answers = new ArrayList<>();

        ArrayList<String> words = getWords(working);
        words.sort((o1, o2) -> -Integer.compare(o1.length(), o2.length()));
        HashMap<String, ArrayList<String>> guessCandidates = new HashMap();
        for (String word : words) {
            //if already solved, don't do that one
            if (word.toUpperCase().equals(word)) continue;
            if (guessCandidates.size() == 5)
                break;
            guessCandidates.put(word, new ArrayList<>());
        }
        //System.out.println(targetWord);
        if (guessCandidates.isEmpty()) {
            answers.add(working);
            return answers;
        }
        ArrayList<String> candidateList = new ArrayList<>(guessCandidates.keySet());
        for (String targetWord : guessCandidates.keySet())
            guessCandidates.put(targetWord, findMatches(targetWord));
        candidateList.sort(Comparator.comparingInt(o -> guessCandidates.get(o).size()));
        String targetWord = candidateList.get(0);
        ArrayList<String> guesses = guessCandidates.get(targetWord);
        //investigate guessing from the best guesses
        tryWord:
        for (String possibility : guesses) {
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
            //System.out.println("Guessing " + targetWord + " is the word " + possibility + "...");
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
     * Determines confidence in a solution. Returns an integer representing doubt, where a lower number
     * means it is more likely to be the solution.
     * @return An integer, where a lower number is more likely to be the solution.
     */
    public int confidence(String s) {
        int total = 0;
        for (String word : getWords(s)) {
            total += (words_20k.contains(word.toLowerCase()) ? words_20k.indexOf(word) : 99999);
        }
        return total;
    }

    public String applyMapping(String s, HashMap<Character, Character> mapping) {
        String newString = s;
        for (Character d : mapping.keySet())
            newString = newString.replace(d, mapping.get(d));
        return newString;

    }


}