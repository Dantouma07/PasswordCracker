import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BruteForceHashCracker implements HashCracker {

    // L'alphabet défini dans les spécifications
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    // Longueur maximale autorisée
    private static final int MAX_LENGTH = 4;

    @Override
    public String crack(String targetHash) {
        // Teste toutes les combinaisons de la taille 1 jusqu'à MAX_LENGTH
        for (int length = 1; length <= MAX_LENGTH; length++) {
            String result = generateAndTest("", length, targetHash);
            if (result != null) {
                return result; // Le mot de passe a été trouvé
            }
        }
        return null; // Aucun résultat obtenu
    }

    /**
     * Méthode récursive pour générer et tester les combinaisons.
     */
    private String generateAndTest(String current, int targetLength, String targetHash) {
        // Condition d'arrêt : la chaîne générée a atteint la longueur ciblée
        if (current.length() == targetLength) {
            // Comparaison avec le hash recherché
            if (hashMD5(current).equals(targetHash)) {
                return current;
            }
            return null;
        }

        // Ajout itératif de chaque lettre de l'alphabet
        for (char c : ALPHABET) {
            String result = generateAndTest(current + c, targetLength, targetHash);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    /**
     * Fonction utilitaire pour calculer le hash MD5 d'une chaîne de caractères.
     */
    private String hashMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            // Conversion des bytes en chaîne hexadécimale
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
            throw new RuntimeException("Erreur : Algorithme MD5 introuvable", e);
        }
    }
}