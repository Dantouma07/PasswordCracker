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

}