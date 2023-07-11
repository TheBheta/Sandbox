package sandbox;


import sandbox.crypto.Aristocrat;
import util.Files;
import util.Sets;
import util.Timer;

import java.util.ArrayList;
import java.util.Scanner;

public class Sandbox {

    public static void main(String[] args) {
        //get list of quotes
        Scanner f = Files.fileScanner("/quotes.txt");
        StringBuilder raw = new StringBuilder();
        while (f.hasNextLine()) raw.append(f.nextLine());
        String[] quotes = raw.toString().split("\\|");
        ArrayList<Integer> times = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Timer.startTimer();
            Aristocrat cipher = new Aristocrat(
                    Aristocrat.encrypt(quotes[i]));
            System.out.println(cipher.getEncrypted());
            cipher.solve();
            times.add((int) Timer.elapsedTime());
        }
        System.out.println("avg time: " + Sets.sum(times)/10 + "ms");

    }
}