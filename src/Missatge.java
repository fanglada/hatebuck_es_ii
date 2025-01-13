import java.util.ArrayList;

public class Missatge {

    int idText;
    boolean notificar;
    ArrayList<Element> elements;

    public String contingut()
    {
        String contingut = "";
        for (Element element : elements)
        {
            contingut += element.contingut();
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
