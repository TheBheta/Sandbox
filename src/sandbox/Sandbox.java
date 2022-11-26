package sandbox;


import sandbox.crypto.Aristocrat;

public class Sandbox {

    public static void main(String[] args) {

        Aristocrat cipher =
                new Aristocrat("Od wtuoffofu zg ytts soat q kqh ugr. Qss dn htghst ykgd zit ykgfz zg zit wqea fgr.");

        System.out.println(cipher.getEncrypted());
        cipher.solve();
        //cipher.analyzeWords();

    }
}