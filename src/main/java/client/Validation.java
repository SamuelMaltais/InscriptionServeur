package client;
public class Validation {
    public boolean validateName(String name){
        String pattern = "^[a-zA-Z]+$";
        return name.matches(pattern) && name.length() >= 1;
    }
    public boolean validateEmail(String email){
        String pattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(pattern);
    }

    public boolean validateMatricule(String matricule){
        String pattern = "^[0-9]{7}$";
        return matricule.matches(pattern);
    }

}
