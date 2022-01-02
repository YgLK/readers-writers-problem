package pl.edu.agh.kis.pz1.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class used for generating
 * Hash corresponding to Strings.
 *
 */
public class TextUtils {
    private static final Logger logger = Logger.getLogger(Thread.currentThread().getName());

    /**
     * Private Constructor to avoid
     * Class instantiation.
     */
    private TextUtils(){}

    /**
     * Generate SHA512 hash
     * corresponding to entered String.
     *
     * @param str       input String
     * @return String   returned Hash512 of the input
     */
    public static String sha512Hash(String str) {
        return DigestUtils.sha512Hex(str);
    }

    /**
     * Generate Md5 hash
     * corresponding to entered String.
     *
     * @param input       input String
     * @return  Md5 hash of the input
     */
    public static String getMd5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            StringBuilder hashtext = new StringBuilder(no.toString(16));
            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }
            return hashtext.toString();
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            logger.log(Level.WARNING, "Exception has been thrown", e);
        }
        return "";
    }

    /**
     * The method returns Random Hash.
     *
     * @return random Hash
     */
    public static String generateRandomHash() {
        return UUID.randomUUID().toString();
    }

}