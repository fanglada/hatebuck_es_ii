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
        double random = Math.random();
        if (random <= 0.5)
        {
            canviarFormat(new ParaulaOriginal());
        }
        else
        {
            random = Math.random()*3;
            if (random <= 1)
            {
                canviarFormat(new ParaulaGrollera());
            }
            else if (random <= 2)
            {
                canviarFormat(new Oculta());
            }
            else
            {
                canviarFormat(new Prauala());
            }
        }
    }

}
