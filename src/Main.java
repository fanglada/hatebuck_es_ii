import java.sql.SQLOutput;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Usuari> usuaris;
        List<Moderador> moderadors;

        Usuari usuari = new Usuari("","", "");

        boolean seguirIntentant = true;

        while (login(usuaris, usuari)<0 && seguirIntentant) {
            System.out.println("Vols tornar a intentar-ho? (S/N)");
            String resposta = System.console().readLine();
            if(resposta.equals("N")) {
                seguirIntentant = false;
            }
        }

        if(seguirIntentant) {
            String opcio = menu();
            while (!opcio.equals("0"))
            {
                switch (opcio) {
                case "1":
                    enviarMissatgePrivat(usuaris,usuari);
                    break;
                case "2":
                    int a = loginModerador(moderadors);
                    break;
                case "3":

                    break;
                case "0":
                    System.out.println("Adéu!");
                    break;
                default:
                    System.out.println("Opció no vàlida");
                    break;
                }
                opcio = menu();
            }
        } else {
            System.out.println("Adéu!");
        }
    }

    //retorna -1 si hi ha un error
    //retorna 0 si tot ha anat bé
    private static int inicialitzar(List<Usuari> usuaris, List<Moderador> moderadors)
    {
        //emplenar dades
        return 0;
    }

    private static String menu() {
        System.out.println("1. Enviar missatge privat");
        System.out.println("2. Modificar text penjat per un usuari");
        System.out.println("3. Canviar relació amb un usuari");
        System.out.println("0. Sortir");
        System.out.println("Escull una opció: ");
        return System.console().readLine();
    }

    //retorna -1 si l'usuari no existeix
    //retorna -2 si la contrasenya és incorrecta
    //retorna 1 si tot ha anat bé
    private static int login(List<Usuari> usuaris, Usuari usu) {
        System.out.println("Entra el nom d'usuari: ");
        String username = System.console().readLine();

        Usuari u = buscarUsuari(usuaris, username);
        if (u == null) {
            System.out.println("Usuari no trobat");
            return -1;
        }
        else
        {
            System.out.println("Entra la contrasenya: ");
            String password = System.console().readLine();
            if (u.getContrasenya().equals(password)) {
                usu = u;
                return 1;
            }
            else
            {
                System.out.println("Contrasenya incorrecta");
                return -2;
            }
        }
    }

    private static int loginModerador(List<Moderador> moderadors) {
        System.out.println("Entra el nom d'usuari: ");
        String username = System.console().readLine();

        for (Moderador moderador : moderadors) {
            if (moderador.getNomUsuari().equals(username)) {
                System.out.println("Entra la contrasenya: ");
                String password = System.console().readLine();
                if (moderador.getContrasenya().equals(password)) {
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

    private static Usuari buscarUsuari(List<Usuari> usuaris, String nomUsuari) {
        for (Usuari usuari : usuaris) {
            if (usuari.getNomUsuari().equals(nomUsuari)) {
                return usuari;
            }
        }
        return null;
    }

    private void enviarMissatgePrivat(List<Usuari> usuaris, Usuari usuari)
    {
        System.out.println("Entra el nom de l'usuari al que vols enviar el missatge: ");
        String nomUsuari = System.console().readLine();
        Usuari destinatari = buscarUsuari(usuaris, nomUsuari);

        Missatge m = entrarMissatge();

        usuari.enviarMissatgePrivat(destinatari, m, false);

    }
}