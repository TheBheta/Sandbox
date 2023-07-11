/*
ID: bheta
LANG: JAVA
TASK: ride
*/

package usaco.module1;

import usaco.Problem;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class ride {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter output = new PrintWriter(System.out);

        int t1 = 1;
        String comet = input.readLine();
        for (char i : comet.toCharArray()) {
            t1 *= ((int) i - 64);
            t1 %= 47;
        }
        int t2 = 1;
        String group = input.readLine();
        for (char i : group.toCharArray()) {
            t2 *= ((int) i - 64);
            t2 %= 47;
        }
        String result = (t1 == t2 ? "GO" : "STAY");
        output.println(result);
        output.close();

    }
}