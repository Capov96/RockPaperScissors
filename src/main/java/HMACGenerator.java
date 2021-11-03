import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;

public class HMACGenerator {
  private static final String HMAC_ALGO = "HmacSHA512";
  final static int KEY_LENGTH = 128;

  public static String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder(bytes.length * 2);
    for (byte b : bytes) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString().toUpperCase(Locale.ROOT);
  }

  public static byte[] getKey() {
    SecureRandom secureRandom = new SecureRandom();
    byte[] key = new byte[KEY_LENGTH / 8];
    secureRandom.nextBytes(key);
    return key;
  }

  public static byte[] getHmac(String message, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
    Mac signer = Mac.getInstance(HMAC_ALGO);
    SecretKeySpec keySpec = new SecretKeySpec(key, HMAC_ALGO);
    signer.init(keySpec);
    byte[] digest = signer.doFinal(message.getBytes(StandardCharsets.UTF_8));
    return digest;
  }
}
