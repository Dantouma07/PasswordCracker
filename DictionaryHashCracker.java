import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DictionaryHashCracker implements HashCracker {

    private String dictionaryPath;

    /**
     * Constructeur
     * @param dictionaryPath chemin vers le fichier dictionnaire
     */
    public DictionaryHashCracker(String dictionaryPath) {
        this.dictionaryPath = dictionaryPath;
    }

    /**
     * Implémente la stratégie de cassage par dictionnaire
     * @param targetHash le hash MD5 cible
     * @return le mot trouvé ou null
     */
    @Override
    public String crack(String targetHash) {
        try (BufferedReader reader = new BufferedReader(new FileReader(dictionaryPath))) {
            String word;
            while ((word = reader.readLine()) != null) {
                // Trim() pour enlever les espaces avant/après
                word = word.trim();

                // Si la ligne est vide, on passe
                if (word.isEmpty()) {
                    continue;
                }

                // Calculer le hash MD5 du mot
                String wordHash = calculateMD5(word);

                // Comparer avec le hash cible (case-insensitive)
                if (wordHash.equalsIgnoreCase(targetHash)) {
                    return word;  // Mot trouvé !
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du dictionnaire: " + e.getMessage());
        }

        return null;  // Aucun mot ne correspond
    }

    /**
     * Calcule le hash MD5 d'une chaîne
     * @param input la chaîne à hasher
     * @return le hash MD5 en hexadécimal
     */
    private String calculateMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            // Convertir les bytes en hexadécimal
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