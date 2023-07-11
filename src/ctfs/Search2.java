package ctfs;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Search2 {
    private final static BigInteger d = new BigInteger("121315178613456618791760451312241561783260225968836320564545861248311927957224662237357745090712142419506190277845511869242467250849473");
    //private final static BigInteger c = new BigInteger("38057539309967517147742735882682499549109157540657139738693784838145632261448");
    private final static BigInteger n = new BigInteger("421784236646689996061305289000019906344271906064701641423800642367723132808510259630710049798146723806104825939357603052276773816427483");
    private final static BigInteger e = BigInteger.valueOf(65537);
    private final static BigInteger cap256 = BigInteger.TWO.pow(256);
    private final static BigInteger cap254 = BigInteger.TWO.pow(254);
    private final static BigInteger cap128 = BigInteger.TWO.pow(128);
    private final static BigInteger cap127 = BigInteger.TWO.pow(127);

    //private final static BigInteger realP = new BigInteger("309184515294200672671073974511535646417");

    public static void main(String[] args) {
        BigInteger de = d.multiply(e).subtract(BigInteger.ONE);
        Scanner sc = new Scanner(System.in);
        BigInteger l = BigInteger.ONE; //find lambda
        for (int divisor = 1; divisor < 10000000; divisor++) {
            if (!de.mod(BigInteger.valueOf(divisor)).equals(BigInteger.ZERO)) continue;
            BigInteger quotient = de.divide(BigInteger.valueOf(divisor));
            if (quotient.compareTo(n) < 0) {
                l = quotient;
                break;
            }
        }
        //l = (p - 1)(q - 1) = pq - (p + q) + 1
        BigInteger sumPQ = n.subtract(l.subtract(BigInteger.ONE));
        BigInteger m = sumPQ.divide(BigInteger.TWO);
        //n = (m + d)(m - d) = m^2 - d^2
        BigInteger d = m.multiply(m).subtract(n).sqrt();
        BigInteger p = m.add(d);
        BigInteger q = m.subtract(d);
        assert p.multiply(q).equals(n);
        System.out.println(p);
        System.out.println(q);


    }

    public static boolean isAlphaNumeric(String s) {
        return s != null && s.matches("^[a-zA-Z0-9]*$");
    }
}