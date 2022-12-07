import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

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
                sc.nextLine();
                if (opcioMainMenu < 1 || opcioMainMenu > 3) throw new InputMismatchException();
            } catch (InputMismatchException e) {
                System.out.println("Opcio incorrecta");
            }
            if (opcioMainMenu == 1) {
                jugar();
            }
            if (opcioMainMenu == 2){
                if (!fitxer.exists()){
                    System.out.println("No hi ha cap partida guardada\n");
                }else {
                    jugar();
                }
            }
            if (opcioMainMenu == 3) {
                System.out.println("Adeu, fins la proxima !!!");
                break;
            }
        }
    }

    private static void jugar() {

        Scanner sc = new Scanner(System.in);

        Jugador j1;
        Jugador j2;
        Baralla baralla;

        if (fitxer.exists()){
            System.out.println("LLEGIR FITXER");
            ObjectInputStream fluxeLectura;
            try {
                fluxeLectura = new ObjectInputStream(new FileInputStream(fitxer));
                j1 = (Jugador) fluxeLectura.readObject();
                j2 = (Jugador) fluxeLectura.readObject();
                baralla = (Baralla) fluxeLectura.readObject();
                System.out.println(j1.puntuacio);
                System.out.println(j2.puntuacio);
                fluxeLectura.close();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            fitxer.delete();
        } else {
            j1 = new Jugador();
            j2 = new Jugador();
            baralla = new Baralla();
            baralla.generarCartes();

            System.out.print("Nom jugador 1:");
            j1.setNom(sc.nextLine());
            System.out.print("Nom jugador 2:");
            j2.setNom(sc.nextLine());

        }

        // CREAR I PLENAR PILA
        Stack<Carta> pilaCartes = new Stack<>();
        for (Carta carta : baralla.getCartes()) {
            pilaCartes.push(carta);
        }

        while (true) {
            System.out.println("ENTER -> Treure cartes    GUARDAR -> Guardar partida");
            System.out.print("-> ");
            String jugar = sc.nextLine();
            if (jugar.equals("GUARDAR")) {

                //TODO TOTES LES CARTES DE LA PILA GUARDARLES A LA BARALLA

                // GUARDAR PARTIDA
                try {
                    ObjectOutputStream fluxeEscriptura = null;
                    fluxeEscriptura = new ObjectOutputStream(new FileOutputStream(fitxer));
                    fluxeEscriptura.writeObject(j1);
                    fluxeEscriptura.writeObject(j2);
                    fluxeEscriptura.writeObject(baralla);
                    fluxeEscriptura.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }

            // ASSIGNAR CARTES
            Carta cartaJ1 = pilaCartes.pop();
            Carta cartaJ2 = pilaCartes.pop();

            // SUMAR PUNTS
            if (cartaJ1.numero < cartaJ2.numero) j2.ferPunt();
            if (cartaJ1.numero > cartaJ2.numero) j1.ferPunt();

            // MOSTRAR RESULTAT I CARTES
            System.out.println("Cartes restants: " + pilaCartes.size());
            System.out.println(j1.getNom() + " -> " + j1.getPuntuacio() + " : " + j2.getPuntuacio() + " <- " + j2.getNom());
            System.out.println("------------------------------------");
            System.out.println(cartaJ1.getPal() + " " + cartaJ1.getNumero() + " --------- " + cartaJ2.getNumero() + " " + cartaJ2.getPal());

            // COMPROVACIO GUANYADOR
            if (pilaCartes.empty()) {
                if (j1.puntuacio < j2.puntuacio) System.out.println("\n"+j2.getNom() + " HA GUANYAT!!!"+"\n");
                if (j1.puntuacio > j2.puntuacio) System.out.println("\n"+j1.getNom() + " HA GUANYAT!!!"+"\n");
                if (j1.puntuacio == j2.puntuacio) System.out.println("\n"+"EMPAT !!!"+"\n");

                String seguirJugant = "";

                do {
                    System.out.println("Vols fer una altra partida?  (S) Si   (N) No");
                    System.out.print("-> ");
                    seguirJugant = sc.nextLine();
                    System.out.println("Resposta incorrecta");
                } while (!seguirJugant.equals("S") && !seguirJugant.equals("N"));
                if (seguirJugant.equals("N")) break;

                j1.setPuntuacio(0);
                j2.setPuntuacio(0);

                baralla.generarCartes();
                baralla.barrejarBaralla();

                for (Carta carta : baralla.getCartes()) {
                    pilaCartes.push(carta);
                }
            }
            System.out.println();
        }
    }
}