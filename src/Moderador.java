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

    public String getNomUsuari() {
        return nomUsuari;
    }

    public String getContrasenya() {
        return contrasenya;
    }
}
