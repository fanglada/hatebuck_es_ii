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
        calculProbDeSerModificada();
        return mostrar.contingut(contingut);
    }

    public void canviarFormat(Format format)
    {
        this.mostrar = format;
    }

    void calculProbDeSerModificada()
    {
        double random = Math.random()*10;
        if (random <= 2.5)
        {
            canviarFormat(new ParaulaOriginal());
        }
        else if(random <= 5)
        {
            canviarFormat(new Oculta());
        }
        else if(random <= 7.5)
        {
            canviarFormat(new ParaulaGrollera());
        }
        else
        {
            canviarFormat(new Prauala());
        }
    }

}
