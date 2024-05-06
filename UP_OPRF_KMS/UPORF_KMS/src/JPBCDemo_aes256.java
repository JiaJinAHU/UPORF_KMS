import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class JPBCDemo_aes256 {
    public static void main(String[] args) {
        Pairing bp = PairingFactory.getPairing("a.properties");
        Field G1 = bp.getG1();
        Field Zr = bp.getZr();
        Element g1 = G1.newRandomElement().getImmutable();
        Element t = G1.newRandomElement().getImmutable();
        Element k = Zr.newRandomElement();
        Element R = Zr.newRandomElement();
        Element r = Zr.newRandomElement();
        Element Wrap = g1.powZn(R); // 计算W
        Element X = Wrap.powZn(r); // 计算X
        Element y1 = bp.pairing(t, X);
        Element y = y1.powZn(k); // 计算y
        Element z1 = bp.pairing(t, Wrap);
        Element kr = k.mul(r);
        Element z2 = z1.powZn(kr);

        System.out.println(y.isEqual(z2));

        Element inverse_r = r.duplicate().invert();
        Element z = z2.powZn(inverse_r);
        Element test = z1.powZn(k);

        System.out.println(z.isEqual(test));

        // Hash the element z into a 256-bit value
        byte[] zBytes = z.toBytes();
        byte[] hashedValue = hashTo256Bits(zBytes);
        System.out.println("Hashed value of z (in hexadecimal): " + bytesToHex(hashedValue));
    }

    // Hashing function to SHA-256
    public static byte[] hashTo256Bits(byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(input);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Convert byte array to hexadecimal string
    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
