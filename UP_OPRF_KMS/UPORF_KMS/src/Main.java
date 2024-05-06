import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class Main {
    public static void main(String[] args) {
        Pairing bp = PairingFactory.getPairing("a.properties");
        Field G1 = bp.getG1();
        Field Zr = bp.getZr();
        Element g=G1.newElement().getImmutable();
        Element r=Zr.newRandomElement().getImmutable();
        Element inver=r.invert().getImmutable();
        System.out.println(r);
        System.out.println(inver);
        System.out.println(r.mul(inver));




    }
}