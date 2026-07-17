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
            if (calculateMD5(current).equals(targetHash)) {
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
}