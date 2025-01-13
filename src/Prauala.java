import java.util.Random;

public class Prauala implements Format{
    @Override
    public String contingut(String contingut) {
        String prauala = contingut;
        for (int i = 0; i < contingut.length(); i++) {
            char c = prauala.charAt(i);
            prauala = prauala.replace(c, prauala.charAt(new Random().nextInt(prauala.length())));
        }
        return prauala;
    }
}
