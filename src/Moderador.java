public class Moderador {

    String nomUsuari;
    String email;
    String contrasenya;

    public Moderador(String nomUsuari, String email, String contrasenya)
    {
        this.nomUsuari = nomUsuari;
        this.email = email;
        this.contrasenya = contrasenya;
    }

    public boolean compararNomUsuari(String n) {
        return nomUsuari.compareTo(n) == 0;
    }

    public boolean compararContrasenya(String c) {
        return contrasenya.compareTo(c) == 0;
    }

    @Override
    public String toString() {
        return nomUsuari;
    }
}
