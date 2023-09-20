import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class OneArmedJoe {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la cantidad de jugadores (2-10): ");
        int numJugadores = scanner.nextInt();
        scanner.nextLine();

        if (numJugadores < 2 || numJugadores > 10) {
            System.out.println("Cantidad de jugadores no válida. Debe ser entre 2 y 10.");
            return;
        }

        System.out.print("Ingrese el límite de rondas: ");
        int maxRondas = scanner.nextInt();
        scanner.nextLine();

        int mulaMasGrande = determinarMulaMasGrande(numJugadores);
        System.out.println("La mula más grande es: " + mulaMasGrande);

        JuegoDomino juego = new JuegoDomino(numJugadores, mulaMasGrande, maxRondas);
        juego.jugar();
    }

    private static int determinarMulaMasGrande(int numJugadores) {
        if (numJugadores <= 3) {
            return 6;
        } else if (numJugadores <= 6) {
            return 9;
        } else {
            return 12;
        }
    }
}
