package client.JavaFxGui;

/**
 * Cette classe valide differents strings pour assurer des bons inputs du form.
 */
public class Validation {
    /**
     Validates if a given string respects the format of a name.
     @param name the name to be validated
     @return true if the name is valid, false otherwise
     */
    public boolean validateName(String name){
        String pattern = "^[a-zA-Z]+$";
        return name.matches(pattern) && name.length() >= 1;
    }
    /**
     Same as first method but for emails
     */
    public boolean validateEmail(String email){
        String pattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(pattern);
    }
    /**
     Same as first method but for a matricule (student code)
     */
    public boolean validateMatricule(String matricule){
        String pattern = "^[0-9]{7}$";
        return matricule.matches(pattern);
    }

}
