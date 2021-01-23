

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

    private static String toHexString(byte[] hash) {
        // convert from byte to string
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    public static String hash(String originalString) {
        // hash function
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = md.digest(originalString.getBytes(StandardCharsets.UTF_8));
            return toHexString(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            //e.printStackTrace();
        }
        return null;
    }
}
