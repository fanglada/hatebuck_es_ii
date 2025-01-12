import java.sql.*;
import java.util.*;

public class Main {
    static Scanner lector = new Scanner(System.in);

    static List<Usuari> usuaris = new ArrayList<>();
    static List<Moderador> moderadors = new ArrayList<>();

    public static void main(String[] args) {

        if(inicialitzar() == -1) {
            System.out.println("Error inicialitzant la base de dades");
            return;
        }

//        Usuari usuari = new Usuari("","", "");
//
//        boolean seguirIntentant = true;
//
//        while (login(usuaris, usuari)<0 && seguirIntentant) {
//            System.out.println("Vols tornar a intentar-ho? (S/N)");
//            String resposta = System.console().readLine();
//            if(resposta.equals("N")) {
//                seguirIntentant = false;
//            }
//        }
//
//        if(seguirIntentant) {
//            int opcio = menu();
//            while (opcio != 0)
//            {
//                switch (opcio) {
//                case 1:
//                    enviarMissatgePrivat(usuaris,usuari);
//                    break;
//                case 2:
//                    int a = loginModerador(moderadors);
//                    break;
//                case 3:
//
//                    break;
//                default:
//                    System.out.println("Opció no vàlida");
//                    break;
//                }
//                opcio = menu();
//            }
//        }
//
//            System.out.println("Adéu!");
    }

    public static Connection connect(String path) {
        try {
            return DriverManager.getConnection( "jdbc:sqlite:" + path);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //retorna -1 si hi ha un error
    //retorna 0 si tot ha anat bé
    private static int inicialitzar()
    {
        Connection conn = connect("./db/hatebuck.db");
        ResultSet rs;
        if (conn == null) {
            return -1;
        }

        try {
            String sql = "SELECT * FROM Usuari";
            rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                //TODO: Agafar els Texts de l'usuari (cada simbol és diferent o han de ser el mateix (reutilitzar paraules))
                List<Text> texts = new ArrayList<>();

                usuaris.add(new Usuari(rs.getString("nomUsuari"), rs.getString("email"), rs.getString("password"), texts));
            }

            for (Usuari u : usuaris) {
                sql = "SELECT * FROM RelacioUsuari WHERE idUsuari1 = '" + u.getNomUsuari() + "'";
                rs = conn.createStatement().executeQuery(sql);
                while (rs.next()) {
                    Usuari u2 = buscarUsuari(usuaris, rs.getString("idUsuari2"));
                    EstatUsuari estat = EstatUsuari.getEstatUsuari(rs.getInt("idEstatAmic"));
                    u.assignarRelacio(u2, estat);
                }
            }

            sql = "SELECT * FROM Moderador";
            rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                moderadors.add(new Moderador(rs.getString("nomUsuari"), rs.getString("email"), rs.getString("password")));
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    private static int menu() {
        System.out.println("1. Enviar missatge privat");
        System.out.println("2. Modificar text penjat per un usuari");
        System.out.println("3. Canviar relació amb un usuari");
        System.out.println("0. Sortir");
        System.out.println("Escull una opció: ");

        int opcio = lector.nextInt();
        lector.nextLine();

        return opcio;
    }

    //retorna -1 si l'usuari no existeix
    //retorna -2 si la contrasenya és incorrecta
    //retorna 1 si tot ha anat bé
    private static int login(List<Usuari> usuaris, Usuari usu) {
        System.out.println("Entra el nom d'usuari: ");
        String username = lector.nextLine();

        Usuari u = buscarUsuari(usuaris, username);
        if (u == null) {
            System.out.println("Usuari no trobat");
            return -1;
        }
        else
        {
            System.out.println("Entra la contrasenya: ");
            String password = lector.nextLine();
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

//    private static int loginModerador(List<Moderador> moderadors) {
//        System.out.println("Entra el nom d'usuari: ");
//        String username = System.console().readLine();
//
//        for (Moderador moderador : moderadors) {
//            if (moderador.getNomUsuari().equals(username)) {
//                System.out.println("Entra la contrasenya: ");
//                String password = System.console().readLine();
//                if (moderador.getContrasenya().equals(password)) {
//                    return 1;
//                }
//                else
//                {
//                    System.out.println("Contrasenya incorrecta");
//                    return -2;
//                }
//            }
//            else
//            {
//                System.out.println("Usuari no trobat");
//                return -1;
//            }
//        }
//    }

    private static Usuari buscarUsuari(List<Usuari> usuaris, String nomUsuari) {
        for (Usuari usuari : usuaris) {
            if (usuari.getNomUsuari().equals(nomUsuari)) {
                return usuari;
            }
        }
        return null;
    }

//    private void enviarMissatgePrivat(List<Usuari> usuaris, Usuari usuari)
//    {
//        System.out.println("Entra el nom de l'usuari al que vols enviar el missatge: ");
//        String nomUsuari = System.console().readLine();
//        Usuari destinatari = buscarUsuari(usuaris, nomUsuari);
//
//        Missatge m = entrarMissatge();
//
//        usuari.enviarMissatgePrivat(destinatari, m, false);
//
//    }
}