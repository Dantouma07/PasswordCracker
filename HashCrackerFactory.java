public class HashCrackerFactory {

    // Définition d'un chemin par défaut pour le fichier dictionnaire
    private static final String DEFAULT_DICTIONARY_PATH = "dico.txt";

    /**
     * Méthode de fabrication (Factory Method).
     *
     * @param method La méthode de cassage souhaitée ("BRUTE" ou "DICO").
     * @return Une instance implémentant l'interface HashCracker.
     */
    public static HashCracker create(String method) {
        if (method == null) {
            throw new IllegalArgumentException("La méthode de cassage ne peut pas être nulle.");
        }

        switch (method.toUpperCase()) {
            case "BRUTE":
                return new BruteForceHashCracker();

            case "DICO":
                // On passe le chemin du fichier au constructeur de DictionaryHashCracker
                return new DictionaryHashCracker(DEFAULT_DICTIONARY_PATH);

            default:
                throw new IllegalArgumentException("Méthode de cassage inconnue : " + method +
                        ". Utilisez 'BRUTE' ou 'DICO'.");
        }
    }
}