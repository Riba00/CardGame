import java.io.Serializable;

public class Carta implements Serializable {
    enum pals{
        OROS,
        COPES,
        ESPASES,
        BASTOS
    }
    int numero;
    String pal;

    public int getNumero() {
        return numero;
    }
    public String getPal(){
        return pal;
    }

    public Carta(int n, String p){
        this.numero = n;
        this.pal = String.valueOf(pals.valueOf(p));
    }
}
