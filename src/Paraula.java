import java.text.Normalizer;

public class Paraula extends Element {

    Format mostrar;


    public Paraula(String contingut)
    {
        super(contingut);
        this.mostrar = new ParaulaOriginal();
    }

    @Override
    public String contingut()
    {
        return mostrar.contingut();
    }

    public  void canviarFormat(Format format)
    {
        this.mostrar = format;
    }

    void calculProbDeSerModificada()
    {

    }

}
