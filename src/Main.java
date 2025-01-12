import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Usuari> usuaris;

        boolean seguirIntentant = true;

        while (login(usuaris)<0 && seguirIntentant) {
            System.out.println("Vols tornar a intentar-ho? (S/N)");
            String resposta = System.console().readLine();
            if(resposta.equals("N")) {
                seguirIntentant = false;
            }
        }

        if(seguirIntentant) {
            System.out.println("Benvingut!");
        } else {
            System.out.println("Adéu!");
        }
    }

    //retorna -1 si hi ha un error
    //retorna 0 si tot ha anat bé
    private static int inicialitzar(List<Usuari> usuaris)
    {
        //emplenar usuaris
        return 0;
    }

    //retorna -1 si l'usuari no existeix
    //retorna -2 si la contrasenya és incorrecta
    //retorna 1 si tot ha anat bé
    private static int login(List<Usuari> usuaris) {
        System.out.println("Entra el nom d'usuari: ");
        String username = System.console().readLine();

        for (Usuari usuari : usuaris) {
            if (usuari.getNomUsuari().equals(username)) {
                System.out.println("Entra la contrasenya: ");
                String password = System.console().readLine();
                if (usuari.getContrasenya().equals(password)) {
                    return 1;
                }
                else
                {
                    System.out.println("Contrasenya incorrecta");
                    return -2;
                }
            }
            else
            {
                System.out.println("Usuari no trobat");
                return -1;
            }
        }
    }
}