public class Moderador {

    String nomUsuari;
    String email;

    String contrasenya;


    //falten atributs de les relacions

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
    public void eliminarContingut(Text text)
    {

    }

    public void publicarText(Text text, Usuari usuari)
    {

    }
}
