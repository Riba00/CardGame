import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static File fitxer = new File("data.txt");

    public static void main(String[] args) {
        mainMenu();

    }


    public static void mainMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int opcioMainMenu = 0;
            System.out.println("--------------");
            System.out.println("CARTA MÉS ALTA");
            System.out.println("--------------");
            System.out.println("1. Jugar");
            System.out.println("2. Continuar Partida");
            System.out.println("3. Sortir");
            System.out.println();
            System.out.print("Opcio: ");
            try {
                opcioMainMenu = sc.nextInt();

                if (opcioMainMenu < 1 || opcioMainMenu > 3) throw new InputMismatchException();
            } catch (InputMismatchException e) {
                System.out.println("Opcio incorrecta");
            }
            sc.nextLine();
            if (opcioMainMenu == 1) {

                Jugador j1 = new Jugador();
                Jugador j2 = new Jugador();
                Baralla baralla = new Baralla();
                baralla.generarCartes();
                baralla.barrejarCartes();

                System.out.print("Nom jugador 1: ");
                j1.setNom(sc.nextLine());
                System.out.print("Nom jugador 2: ");
                j2.setNom(sc.nextLine());

                jugar(j1, j2, baralla);
            }
            if (opcioMainMenu == 2) {
                if (!fitxer.exists()) {
                    System.out.println("No hi ha cap partida guardada\n");
                } else {
                    //lectura:
                    try {
                        ObjectInputStream fluxeLectura;
                        fluxeLectura = new ObjectInputStream(new FileInputStream(fitxer));
                        Jugador j1 = (Jugador) fluxeLectura.readObject();
                        Jugador j2 = (Jugador) fluxeLectura.readObject();
                        Baralla baralla = (Baralla) fluxeLectura.readObject();

                        fitxer.delete();

                        jugar(j1, j2, baralla);
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (opcioMainMenu == 3) {
                System.out.println("Adeu, fins la proxima !!!");
                break;
            }
        }
    }

    private static void jugar(Jugador j1, Jugador j2, Baralla baralla) {

        Scanner sc = new Scanner(System.in);
        int midaBaralla = baralla.cartes.size();

        for (int carta = 0; carta <= midaBaralla; carta++) {
            {
                System.out.println("ENTER -> Treure cartes    GUARDAR -> Guardar partida");
                System.out.print("-> ");
                String jugar = sc.nextLine();
                if (jugar.equals("GUARDAR")) {

                    // GUARDAR PARTIDA
                    try {
                        ObjectOutputStream fluxeEscriptura;
                        fluxeEscriptura = new ObjectOutputStream(new FileOutputStream(fitxer));
                        fluxeEscriptura.writeObject(j1);
                        fluxeEscriptura.writeObject(j2);
                        fluxeEscriptura.writeObject(baralla);
                        fluxeEscriptura.close();
                        System.out.println("\nLa partida s'ha guardat correctament\n");
                    } catch (IOException e) {
                        System.out.println("Alguna cosa ha anat malament");
                    }
                    break;
                }

                // ASSIGNAR CARTES
                Carta cartaJ1 = baralla.cartes.remove(0);
                carta++;
                Carta cartaJ2 = baralla.cartes.remove(0);

                // SUMAR PUNTS
                if (cartaJ1.numero < cartaJ2.numero) j2.ferPunt();
                if (cartaJ1.numero > cartaJ2.numero) j1.ferPunt();

                // MOSTRAR RESULTAT I CARTES

                System.out.println(j1.getNom() + " -> " + j1.getPuntuacio() + " : " + j2.getPuntuacio() + " <- " + j2.getNom());
                System.out.println("---------------------------------");
                System.out.println(cartaJ1.getPal() + " " + cartaJ1.getNumero() + " ----- " + cartaJ2.getNumero() + " " + cartaJ2.getPal());
                System.out.println("Cartes restants: " + baralla.cartes.size());

                // COMPROVACIO GUANYADOR
                if (baralla.cartes.isEmpty()) {
                    if (j1.puntuacio < j2.puntuacio) System.out.println("\n" + j2.getNom() + " HA GUANYAT!!!" + "\n");
                    if (j1.puntuacio > j2.puntuacio) System.out.println("\n" + j1.getNom() + " HA GUANYAT!!!" + "\n");
                    if (j1.puntuacio == j2.puntuacio) System.out.println("\n" + "EMPAT !!!" + "\n");

                    String seguirJugant;

                    while (true) {
                        System.out.println("Vols fer una altra partida?  (S) Si   (N) No");
                        System.out.print("-> ");
                        seguirJugant = sc.nextLine();
                        if (seguirJugant.equals("N") || seguirJugant.equals("S")) break;
                        System.out.println("Resposta incorrecta");
                    }

                    if (seguirJugant.equals("N")) break;

                    j1.setPuntuacio(0);
                    j2.setPuntuacio(0);

                    baralla.generarCartes();
                    baralla.barrejarCartes();

                }
                System.out.println();
            }
        }
    }
}