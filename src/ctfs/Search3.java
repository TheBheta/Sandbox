package ctfs;

import java.math.BigInteger;
import java.util.Scanner;

public class Search3 {
    //private final static BigInteger d = new BigInteger("121315178613456618791760451312241561783260225968836320564545861248311927957224662237357745090712142419506190277845511869242467250849473");
    //private final static BigInteger c = new BigInteger("38057539309967517147742735882682499549109157540657139738693784838145632261448");
    private final static BigInteger n = new BigInteger("83854371201833943692424506841505876495105051457791566012254611038743702311129");
    private final static BigInteger e = BigInteger.valueOf(65537);
    private final static BigInteger cap256 = BigInteger.TWO.pow(256);
    private final static BigInteger cap254 = BigInteger.TWO.pow(254);
    private final static BigInteger cap128 = BigInteger.TWO.pow(128);
    private final static BigInteger cap127 = BigInteger.TWO.pow(127);

    //private final static BigInteger realP = new BigInteger("309184515294200672671073974511535646417");

    public static void main(String[] args) {
        BigInteger p = n.sqrt();
        System.out.println(p);

    }


}