package util;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A fraction class, to represent a fraction with a numerator and denominator.
 */
public class Fraction implements Comparable<Fraction>, Cloneable {
    public double numerator;
    public double denominator;
    public double value;


    public static final Fraction ZERO = new Fraction(0, 1);
    public static final Fraction ONE = new Fraction(1, 1);

    public Fraction(long numerator, long denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        simplify();
    }

    public Fraction(double numerator, double denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        this.value = this.numerator / this.denominator;
    }

    public void add(Fraction addend) {
        double oldDenom = denominator;
        denominator *= addend.denominator;
        numerator *= addend.denominator;
        numerator += addend.numerator * oldDenom;
        simplify();
    }

    public void multiply(int factor) {
        numerator *= factor;
        simplify();
    }

    public void multiply(Fraction factor) {
        numerator *= factor.numerator;
        denominator *= factor.denominator;
        simplify();
    }

    public void divide(int factor) {
        denominator *= factor;
        simplify();
    }

    public void divide(Fraction factor) {
        denominator *= factor.numerator;
        numerator *= factor.denominator;
        simplify();
    }

    public Fraction reciprocal(){
        return new Fraction(denominator, numerator);
    }

    private void simplify() {
        double GCF = Numbers.GCF(Math.min(numerator, denominator), Math.max(numerator, denominator));
        numerator /= GCF;
        denominator /= GCF;
        if (denominator < 0){
            denominator *= -1;
            numerator *= -1;
        }
        this.value = numerator / denominator;
    }

    @Override
    public int compareTo(Fraction f) {
        return Double.compare(this.value, f.value);
    }

    public String toString() {

        return (DecimalFormat.getInstance().format(numerator) + (denominator == 1 ? "" : "/" + DecimalFormat.getInstance().format(denominator))).replace(",", "");

    }

    public Fraction clone(){
        return new Fraction(numerator, denominator);
    }

    // following methods adapted from https://sagi.io/crypto-classics-wieners-rsa-attack/
    public static ArrayList<Integer> getCFExpansion(int n, int d) {
        ArrayList<Integer> e = new ArrayList<>();
        int q = n / d;
        int r = n % d;
        e.add(q);
        while (r != 0) {
            n = d;
            d = r;
            q = n / d;
            r = n % d;
            e.add(q);
        }
        return e;
    }
    public static ArrayList<Fraction> getConvergents(int n, int d) {
        ArrayList<Integer> e = getCFExpansion(n, d);
        ArrayList<Integer> numerators = new ArrayList<>();
        ArrayList<Integer> denominators = new ArrayList<>();
        ArrayList<Fraction> result = new ArrayList<>();
        for (int i = 0; i < e.size(); i++) {
            if (i == 0) {
                numerators.add(e.get(0));
                denominators.add(1);
            } else if (i == 1) {
                numerators.add(e.get(1) * e.get(0) + 1);
                denominators.add(e.get(1));
            } else {
                numerators.add(e.get(i) * numerators.get(i - 1) + numerators.get(i - 2));
                denominators.add(e.get(i) * denominators.get(i - 1) + denominators.get(i - 2));

            }
            result.add(new Fraction(numerators.get(i), denominators.get(i)));
        }
        return result;
    }

    public static ArrayList<BigInteger> getCFExpansion(BigInteger n, BigInteger d) {
        ArrayList<BigInteger> e = new ArrayList<>();
        BigInteger q = n.divide(d);
        BigInteger r = n.mod(d);
        e.add(q);
        while (!r.equals(BigInteger.ZERO)) {
            n = d;
            d = r;
            q = n.divide(d);
            r = n.mod(d);
            e.add(q);
        }
        return e;
    }
    public static ArrayList<BigInteger[]> getConvergents(BigInteger n, BigInteger d) {
        ArrayList<BigInteger> e = getCFExpansion(n, d);
        ArrayList<BigInteger> numerators = new ArrayList<>();
        ArrayList<BigInteger> denominators = new ArrayList<>();
        ArrayList<BigInteger[]> result = new ArrayList<>();
        for (int i = 0; i < e.size(); i++) {
            if (i == 0) {
                numerators.add(e.get(0));
                denominators.add(BigInteger.ONE);
            } else if (i == 1) {
                numerators.add(e.get(1).multiply(e.get(0)).add(BigInteger.ONE));
                denominators.add(e.get(1));
            } else {
                numerators.add(e.get(i).multiply(numerators.get(i - 1)).add(numerators.get(i - 2)));
                denominators.add(e.get(i).multiply(denominators.get(i - 1)).add(denominators.get(i - 2)));

            }
            result.add(new BigInteger[]{numerators.get(i), denominators.get(i)});
        }
        return result;
    }
}