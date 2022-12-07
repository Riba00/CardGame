import java.io.Serializable;

public class Jugador implements Serializable {
    String nom;
    int puntuacio = 0;


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPuntuacio() {
        return puntuacio;
    }

    public void setPuntuacio(int puntuacio) {
        this.puntuacio = puntuacio;
    }
    public void ferPunt(){
        this.puntuacio++;
    }

}
