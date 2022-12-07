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
            System.out.println("+----------------+");
            System.out.println("| CARTA MÉS ALTA |");
            System.out.println("+----------------+");
            System.out.println("1. Jugar");
            System.out.println("2. Continuar Partida");
            System.out.println("3. Sortir");
            System.out.println();
            System.out.print("Opcio: ");
            //CONTROL D'ENTRADA
            try {
                opcioMainMenu = sc.nextInt();

                if (opcioMainMenu < 1 || opcioMainMenu > 3) throw new InputMismatchException();
            } catch (InputMismatchException e) {
                System.out.println("Opcio incorrecta");
            }

            // NO TOCAR
            sc.nextLine();

            if (opcioMainMenu == 1) {
                // PARTIDA NOVA AMB ELEMENTS NOUS
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
                    // LLEGIR DADES
                    try {
                        ObjectInputStream fluxeLectura;
                        fluxeLectura = new ObjectInputStream(new FileInputStream(fitxer));
                        Jugador j1 = (Jugador) fluxeLectura.readObject();
                        Jugador j2 = (Jugador) fluxeLectura.readObject();
                        Baralla baralla = (Baralla) fluxeLectura.readObject();

                        System.out.println("La partida s'ha carregat correstament\n");
                        System.out.println(j1.getNom() + " VS " + j2.getNom());

                        fitxer.delete();

                        jugar(j1, j2, baralla);
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Alguna cosa ha anat malament");
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
        boolean jugar = true;

        while (jugar) {
            int midaBaralla = baralla.cartes.size();

            for (int carta = 0; carta < midaBaralla; carta++) {

                System.out.println("ENTER -> Treure cartes    GUARDAR -> Guardar partida");
                System.out.print("-> ");
                String respostaJugar = sc.nextLine().toUpperCase();

                if (respostaJugar.equals("GUARDAR")) {
                    // GUARDAR PARTIDA
                    try {
                        ObjectOutputStream fluxeEscriptura;
                        fluxeEscriptura = new ObjectOutputStream(new FileOutputStream(fitxer));
                        fluxeEscriptura.writeObject(j1);
                        fluxeEscriptura.writeObject(j2);
                        fluxeEscriptura.writeObject(baralla);
                        fluxeEscriptura.close();
                        System.out.println("La partida s'ha guardat correctament\n");
                        jugar = false;
                        break;
                    } catch (IOException e) {
                        System.out.println("Alguna cosa ha anat malament");
                    }
                }
                if ( respostaJugar.equals("")){
                    // ASSIGNAR CARTES
                    Carta cartaJ1 = baralla.cartes.remove(0);
                    carta++;
                    Carta cartaJ2 = baralla.cartes.remove(0);

                    // SUMAR PUNTS
                    if (cartaJ1.numero < cartaJ2.numero) j2.ferPunt();
                    if (cartaJ1.numero > cartaJ2.numero) j1.ferPunt();

                    // MOSTRAR RESULTAT I CARTES
                    System.out.println("----------------------");
                    System.out.println(j1.getNom() + " -> " + j1.getPuntuacio() + " : " + j2.getPuntuacio() + " <- " + j2.getNom());
                    System.out.println("----------------------");
                    System.out.println(cartaJ1.getPal() + " " + cartaJ1.getNumero() + " --- " + cartaJ2.getNumero() + " " + cartaJ2.getPal());
                    System.out.println("----------------------");
                    System.out.println("Cartes restants: " + baralla.cartes.size());
                    System.out.println();
                }
                else System.out.println("Resposta incorrecta");
            }
            // COMPROVACIO GUAYADOR
            if (baralla.cartes.isEmpty()) {
                if (j1.puntuacio < j2.puntuacio) System.out.println(j2.getNom() + " HA GUANYAT!!!" + "\n");
                if (j1.puntuacio > j2.puntuacio) System.out.println(j1.getNom() + " HA GUANYAT!!!" + "\n");
                if (j1.puntuacio == j2.puntuacio) System.out.println("EMPAT !!!\n");

                String seguirJugant;
                while (true) {
                    System.out.println("Vols fer una altra partida?  (S) Si   (N) No");
                    System.out.print("-> ");
                    seguirJugant = sc.nextLine().toUpperCase();
                    if (seguirJugant.equals("N") || seguirJugant.equals("S")) break;
                    System.out.println("Resposta incorrecta");
                }

                // TORNAR A JUGAR
                if (seguirJugant.equals("S")) {
                    // SETEJAR JUGADORS I BARALLA
                    j1.setPuntuacio(0);
                    j2.setPuntuacio(0);
                    baralla.generarCartes();
                    baralla.barrejarCartes();
                    System.out.println();
                } else {
                    jugar=false;
                }
            }
        }
    }
}
