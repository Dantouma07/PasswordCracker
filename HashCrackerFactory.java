/**
 * Fabrique simple chargée d'instancier la bonne stratégie de cassage.
 */
public class HashCrackerFactory {

    /**
     * Crée et retourne une instance de HashCracker selon la méthode demandée.
     *
     * @param method La méthode choisie ("BRUTE" ou "DICO").
     * @return Une instance concrète de HashCracker.
     */
    public static HashCracker create(String method) {
        switch (method.toUpperCase()) {
            case "BRUTE":
                return new BruteForceHashCracker();

            case "DICO":
                return new DictionaryHashCracker("dictionnaire.txt");

            default:
                throw new IllegalArgumentException("Méthode inconnue : " + method);
        }
    }
}