import java.util.ArrayList;

public class Text {
    int idText;
    ArrayList<Element> elements;

    public Text(int idText, ArrayList<Element> elements)
    {
        this.idText = idText;
        this.elements = elements;
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

    public String contingutOriginal()
    {
        String contingut = "";
        for (int i = 0; i < elements.size(); i++) {
            Element e = elements.get(i);
            if(i != 0 && e instanceof Paraula)
            {
                contingut += " ";
            }
            contingut += e.contingutOriginal();
        }
        return contingut;
    }

    public void afegirElementAlFinal(Element element)
    {
        elements.add(element);
    }
}
