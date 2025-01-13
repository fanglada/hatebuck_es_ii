import java.util.ArrayList;

public class Missatge {

    int idText;
    boolean notificar;
    ArrayList<Element> elements;

    public Missatge(int idText)
    {
        this.idText = idText;
        this.notificar = false;
        elements = new ArrayList<>();
    }

    public String contingut()
    {
        String contingut = "";
        for (int i = 0; i < elements.size(); i++) {
            Element e = elements.get(i);
            if(i != 0 && e instanceof Paraula)
            {
                contingut += " ";
            }
            contingut += e.contingut();
        }
        return contingut;
    }

    void crearParaulesSignes()
    {

    }

    public void afegirElementAlFinal(Element element)
    {
        elements.add(element);
    }
}
