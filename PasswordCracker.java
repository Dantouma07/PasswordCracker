public class PasswordCracker {

    public static void main(String[] args) {
        String method = null;
        String hash = null;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-m") && i + 1 < args.length) {
                method = args[i + 1];
            } else if (args[i].equals("-h") && i + 1 < args.length) {
                hash = args[i + 1];
            }
        }

        // Vérification de la présence des paramètres obligatoires
        if (method == null || hash == null) {
            System.out.println("Usage : passwordCracker -m <BRUTE|DICO> -h <hash_md5>");
            return;
        }

        try {
            HashCracker cracker = HashCrackerFactory.create(method);

            // 3. Exécution et mesure des performances
            long startTime = System.currentTimeMillis();

            String result = cracker.crack(hash);

            long endTime = System.currentTimeMillis();

            // 4. Affichage du résultat selon le format attendu
            if (result != null) {
                System.out.println("Password found: " + result);
            } else {
                System.out.println("Password not found");
            }

            // Affichage d'une information pertinente supplémentaire
            System.out.println("Temps d'exécution : " + (endTime - startTime) + " ms");

        } catch (IllegalArgumentException e) {
            System.err.println("Erreur : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Une erreur inattendue est survenue : " + e.getMessage());
        }
    }
}