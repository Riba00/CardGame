import java.io.Serializable;
import java.util.ArrayList;

public class Baralla implements Serializable {

    ArrayList<Carta> cartes;


    public ArrayList<Carta> getCartes() {
        return cartes;
    }

    public void setCartes(ArrayList<Carta> cartes) {
        this.cartes = cartes;
    }

    public void generarCartes() {

        String[] pals = {"OROS", "COPES", "ESPASES", "BASTOS"};
        ArrayList<Carta> barallaGenerada = new ArrayList<>();

        // RECORRER ELS PALS
        for (String pal : pals) {
            // RECORRER ELS NUMEROS
            for (int j = 0; j < 12; j++) {
                Carta cartaCreada = new Carta((j + 1), pal);
                barallaGenerada.add(cartaCreada);
            }
        }
        this.cartes = barallaGenerada;
    }

    public void barrejarBaralla() {
        ArrayList<Carta> barallaBarrejada = new ArrayList<>();

        while (this.cartes.size() != 0) {
            int numeroRandom = (int) (Math.random() * (this.cartes.size()) + 0);
            barallaBarrejada.add(this.cartes.remove(numeroRandom));
        }
        this.cartes = barallaBarrejada ;
    }





}
