import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public interface HashCracker {
    String crack(String hash);

    /**
     * Méthode par défaut pour calculer le hash MD5 d'une chaîne.
     * Partagée automatiquement par toutes les classes qui implémentent l'interface.
     */
    default String calculateMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
}