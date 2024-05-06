import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class AESDemo {
    public static void main(String[] args) {
        String plainText = "Hello, AES!";
        String keyString = "6dd512f2b230a2c5681d17c8a0d231220035f2e6cf9e8d05df1f16456fff87e2"; // Change this to your own key string

        try {
            // Generate AES key from SHA-256 hash of keyString
            byte[] key = generateAESKey(keyString);

            // Encrypt the plaintext
            byte[] encryptedText = encrypt(plainText, key);
            System.out.println("Encrypted text: " + Base64.getEncoder().encodeToString(encryptedText));

            // Decrypt the ciphertext
            String decryptedText = decrypt(encryptedText, key);
            System.out.println("Decrypted text: " + decryptedText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Generate AES key from SHA-256 hash of a string
    public static byte[] generateAESKey(String keyString) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] key = digest.digest(keyString.getBytes());
        return Arrays.copyOf(key, 16); // AES-128 requires a 128-bit (16-byte) key
    }

    // Encrypt using AES
    public static byte[] encrypt(String plainText, byte[] key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(plainText.getBytes());
    }

    // Decrypt using AES
    public static String decrypt(byte[] cipherText, byte[] key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(cipherText);
        return new String(decryptedBytes);
    }
}
