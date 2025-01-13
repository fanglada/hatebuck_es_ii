import java.util.Random;

public class Prauala implements Format{
    @Override
    public String contingut(String contingut) {
        StringBuilder prauala = new StringBuilder(contingut);
        Random random = new Random();
        for (int i = 0; i < prauala.length(); i++) {
            int j = random.nextInt(prauala.length());
            char temp = prauala.charAt(i);
            prauala.setCharAt(i, prauala.charAt(j));
            prauala.setCharAt(j, temp);
        }
        return prauala.toString();
    }
}
