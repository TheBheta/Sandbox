package ctfs;

import util.Fraction;
import util.Numbers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class DachshundAttacks {
    private static BigInteger n = new BigInteger("108527713914666277360210403650546010801021651230359918253703092370608154398731520210296333313608250610213030327743463316508989950789486431466189922722990646749046514064810932991335008050688030453386963367147853497058974880370292318091865064893599737270235687941548429230856754853140118101088992182139772826483");
    private static BigInteger c = new BigInteger("13609442071804375000571373179890684688737620822768402392887500479484322309390344660091848962215122601312074897234294063707484881606903649233681231786457215080261014125286503478534766893962578653985647576691622164466918128308012816937354626854359000562436122498050387858747135326221291473880019407911052532261");
    private static BigInteger e = new BigInteger("96189912023169015483604269414804777833618570340902386697719019139317953430602095946443748694449625109955890368096558943125163474564181936406699490105328995266847751846463445291118247472298849417474774877439805714022425243869167595780040886188186477150420354503112977240265104834732050970747843467368880677553");
    //private static BigInteger n = BigInteger.valueOf(101701141);
    //private static BigInteger e = BigInteger.valueOf(56489429);
    //private static BigInteger c = BigInteger.valueOf(23039017);
    public static void main(String[] args) {
        //we know m^e (mod n) = c
        //and c^d (mod n) = m
        //we just don't know d
        /*
        BigInteger m = c;
        for (int d = 1; d < 10000000; d++) {
            m = m.multiply(m).mod(n);
            try {
                String r = Numbers.hexToAscii(m.toString(16));
                if (r.contains("pico")){
                    System.out.println(r);
                }
            } catch (Exception e)
            {
                //okay
            }
        }

         */

        ArrayList<BigInteger[]> convergents = Fraction.getConvergents(e, n);
        for (BigInteger[] conv : convergents) {
            BigInteger pk = conv[0];
            BigInteger pd = conv[1];
            if (pk.equals(BigInteger.ZERO)) continue;

            BigInteger possible_phi = e.multiply(pd).subtract(BigInteger.ONE).divide(pk);
            //System.out.println(possible_phi);
            //p^2 + p(phi(N) - N - 1) - N = 0
            BigInteger b = possible_phi.subtract(n).subtract(BigInteger.ONE);
            //System.out.println(b);
            //System.out.println(c);
            BigInteger discriminant = b.pow(2)
                    .subtract(n.multiply(BigInteger.valueOf(4)));
            //System.out.println(discriminant);
            if (discriminant.compareTo(BigInteger.ZERO) <= 0 ||
            discriminant.sqrtAndRemainder()[1].compareTo(BigInteger.ZERO) > 0) continue;
            BigInteger[] roots = {
                    (discriminant.sqrt().subtract(b).divide(BigInteger.TWO)),
                    (discriminant.sqrt().negate().subtract(b).divide(BigInteger.TWO))
            };
            //System.out.println(Arrays.toString(roots));
            if (roots[0].multiply(roots[1]).equals(n)) {
                System.out.println("WWWWWWW");
                System.out.println("p: " + roots[0]);
                System.out.println("q: " + roots[1]);
                System.out.println("d: " + pd);
                System.out.println("m: " + Numbers.hexToAscii(c.modPow(pd, n).toString(16)));

            }
        }
    }



}