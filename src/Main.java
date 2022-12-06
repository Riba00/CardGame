import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        mainMenu();

    }

    public static ArrayList<Carta> generarBaralla() {
        String[] pals = {"OROS", "COPES", "ESPASES", "BASTOS"};
        ArrayList<Carta> barallaGenerada = new ArrayList<>();

        // RECORRER ELS PALS
        for (int i = 0; i < pals.length; i++) {
            // RECORRER ELS NUMEROS
            for (int j = 0; j < 12; j++) {
                Carta cartaCreada = new Carta((j + 1), pals[i]);
                barallaGenerada.add(cartaCreada);
            }
        }
        return barallaGenerada;
    }

    public static ArrayList<Carta> barrejarBaralla(ArrayList<Carta>barallaAMesclar) {
        ArrayList<Carta> barallaBarrejada = new ArrayList<>();

        while (barallaAMesclar.size() != 0) {
            int numeroRandom = (int) (Math.random() * (barallaAMesclar.size()) + 0);
            barallaBarrejada.add(barallaAMesclar.remove(numeroRandom));
        }
        return barallaBarrejada;
    }

    public static void mainMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int opcioMainMenu = 0;
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
            if (opcioMainMenu == 1){
                jugar();
            }
        }
    }

    private static void jugar() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nom jugador 1: ");
        String nomJugador1 = sc.nextLine();
        System.out.print("Nom jugador 2: ");
        String nomJugador2 = sc.nextLine();

        ArrayList<Carta> baralla = generarBaralla();
        baralla = barrejarBaralla(baralla);

        Stack<Carta> pilaCartes = new Stack<Carta>();
        for (int i = 0; i < baralla.size(); i++) {
            //TODO PLENAR PILA
        }


        System.out.print("Apreta ENTER per a començar...");
        sc.nextLine();


    }
}