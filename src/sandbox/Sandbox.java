package sandbox;


import sandbox.crypto.Aristocrat;

public class Sandbox {

    public static void main(String[] args) {

        Aristocrat cipher =
                new Aristocrat(
                        "UKLPN ODIU EPB UKLPN CLJKU EPB UKLPN OQX EPB UKLPN\n" +
                                "KLJK. QK, UKD UKLPJT ZQV HEP UKLPN VR LI ZQV QPOZ\n" +
                                "UCZ.");
        //System.out.println(cipher.getEncrypted());
        cipher.solve();
        //Aristocrat cipher3 = new Aristocrat(
        //        "a'b c def!"
        //);
        //cipher3.solve();
        //cipher.analyzeWords();

    }
}