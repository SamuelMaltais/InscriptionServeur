package client.JavaFxGui;

/**
 * Cette classe vérifie differents strings pour s'assurer qu'ils respectent le format approprié.
 */
public class Validation {
    /**
     * Vérifie si un string respecte le format usuel d'un nom.
     *
     * @param name nom à être validé.
     * @return true si correspond au format, false sinon.
     */
    public String errors = "";

    public boolean validateName(String name) {
        String pattern = "^[a-zA-Z]+$";
        if (!name.matches(pattern)) {
            errors += "\n Le champ nom ou prenom est invalide";
        }
        return name.matches(pattern) && name.length() >= 1;
    }

    /**
     * Vérifie si un string respecte le format usuel d'un email.
     *
     * @param email email à être validé.
     * @return true si correspond au format, false sinon.
     */
    public boolean validateEmail(String email) {
        String pattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(pattern)) {
            errors += "\n Le champ email est invalide";
        }
        return email.matches(pattern);
    }

    /**
     * Vérifie si un string respecte le format usuel d'une matricule étudiant.
     *
     * @param matricule matricule à être validé.
     * @return true si correspond au format, false sinon.
     */
    public boolean validateMatricule(String matricule) {
        String pattern = "^[0-9]{8}$";
        if (!matricule.matches(pattern)) {
            errors += "\n Le champ matricule est invalide";
        }
        return matricule.matches(pattern);
    }

}
