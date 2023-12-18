

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

public class Encryptos {

    private static final int IV_LENGTH = 16; // 128 bits

    public String encrypt(String upi, String encryptionKey) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, makeKey(encryptionKey), generateIv());

        byte[] encryptedBytes = cipher.doFinal(upi.getBytes(StandardCharsets.UTF_8));
        byte[] iv = cipher.getIV(); // Get the IV used for encryption

        // Combine IV and encrypted data, and then encode as Base64
        byte[] ivAndEncrypted = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, ivAndEncrypted, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, ivAndEncrypted, iv.length, encryptedBytes.length);
        String encryptedString = Base64.getEncoder().encodeToString(ivAndEncrypted);
        System.out.println("encryptedString is " + encryptedString);
        return Base64.getEncoder().encodeToString(ivAndEncrypted);

    }

    static AlgorithmParameterSpec generateIv() {
        byte[] iv = new byte[IV_LENGTH];
        // Generate a random IV
        // Note: You should use a secure random number generator
        // Here, we're using a simple example for demonstration
        for (int i = 0; i < IV_LENGTH; i++) {
            iv[i] = (byte) (Math.random() * 256);
        }
        return new IvParameterSpec(iv);
    }

    static Key makeKey(String encryptionKey) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] key = md.digest(encryptionKey.getBytes(StandardCharsets.UTF_8));
            return new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String encryptedUpi, String encryptionKey) throws Exception {

        byte[] ivAndEncrypted = Base64.getDecoder().decode(encryptedUpi);
        byte[] iv = new byte[IV_LENGTH];
        byte[] encryptedBytes = new byte[ivAndEncrypted.length - IV_LENGTH];

        // Separate IV and encrypted data
        System.arraycopy(ivAndEncrypted, 0, iv, 0, IV_LENGTH);
        System.arraycopy(ivAndEncrypted, IV_LENGTH, encryptedBytes, 0, encryptedBytes.length);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, makeKey(encryptionKey), new IvParameterSpec(iv));

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedString = new String(decryptedBytes, StandardCharsets.UTF_8);
        System.out.println("decryptedString is " + decryptedString);
        return decryptedString;

    }
}
