package ctfs;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.util.Scanner;

public class SRA {
    private final static BigInteger d = new BigInteger("17904884578147264154824600225763453492290677262608285691486102278473191211393");
    private final static BigInteger c = new BigInteger("38057539309967517147742735882682499549109157540657139738693784838145632261448");
    //private final static BigInteger n = new BigInteger("84388177857536250715767864387774028112381700275619548086684212984046258324661");
    private final static BigInteger e = BigInteger.valueOf(65537);
    private final static BigInteger cap256 = BigInteger.TWO.pow(256);
    private final static BigInteger cap254 = BigInteger.TWO.pow(254);
    private final static BigInteger cap128 = BigInteger.TWO.pow(128);
    private final static BigInteger cap127 = BigInteger.TWO.pow(127);

    //private final static BigInteger realP = new BigInteger("309184515294200672671073974511535646417");

    public static void main(String[] args) {
        BigInteger de = d.multiply(e).subtract(BigInteger.ONE);
        Scanner sc = new Scanner(System.in);

        for (int divisor = 1; divisor < 1000000; divisor++) {
            if (!de.mod(BigInteger.valueOf(divisor)).equals(BigInteger.ZERO)) continue;
            BigInteger quotient = de.divide(BigInteger.valueOf(divisor));
            if (quotient.compareTo(cap256) > 0 ||
                    !quotient.mod(BigInteger.valueOf(4)).equals(BigInteger.ZERO)) continue;
            if (quotient.compareTo(cap254) < 0) break;
            //quotient is a potential lambda
            StringSelection stringSelection = new StringSelection(quotient.toString(10));
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);

            System.out.println("copied " + quotient);
            String[] input = sc.nextLine().replace(" ", "").split("×");
            ArrayList<BigInteger> factors = new ArrayList<>();
            //find factors of potential lambda to guess (p - 1), (q - 1)
            for (String f : input) {
                if (f.contains("^")) {
                    BigInteger factor = BigInteger.valueOf(Integer.parseInt(f.split("\\^")[0]));
                    int exponent = Integer.parseInt(f.split("\\^")[1]);
                    if (factor.equals(BigInteger.TWO)) exponent-=2;
                    for (int i = 0; i < exponent; i++) {
                        factors.add(factor);
                    }
                } else {
                    factors.add(new BigInteger(f));
                }
            }

            //binmask thru all possible combos
            for (int i = 0; i < 1 << factors.size(); i++) {
                BigInteger p1 = BigInteger.TWO;
                BigInteger q1 = BigInteger.TWO;
                for (int m = 0; m < factors.size(); m++) {
                    if ((i >> m & 1) == 1) p1 = p1.multiply(factors.get(m));
                    else q1 = q1.multiply(factors.get(m));
                    if (p1.compareTo(cap128) > 0 || q1.compareTo(cap128) > 0) break;
                }
                if (p1.compareTo(cap127) > 0 && q1.compareTo(cap127) > 0) {
                    BigInteger p = p1.add(BigInteger.ONE);

                    BigInteger q = q1.add(BigInteger.ONE);
                    BigInteger n = p.multiply(q);
                    BigInteger m = c.modPow(d, n);
                    String stringed = new String(m.toByteArray());
                    if (isAlphaNumeric(stringed)) {
                        System.out.println("m: " + stringed);
                        System.out.println("raw m: " + m);
                        System.out.println("HYYYYYYYYYPE");
                        System.out.println("n: " + n);
                        System.out.println("p: " + p);
                        System.out.println("q: " + q);
                        return;
                    }
                }
            }

            System.out.println("nothing :(");
            //System.out.println(factors);
        }

    }

    public static boolean isAlphaNumeric(String s) {
        return s != null && s.matches("^[a-zA-Z0-9]*$");
    }
}