import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class JuegoDomino {
    private List<FichaDomino> mazo;
    private List<Jugador> jugadores;
    private List<FichaDomino> boneyard;
    private int indiceJugadorActual;
    private int mulaMasGrande;
    private int maxRondas;
    private int rondasJugadas;

    public JuegoDomino(int numJugadores, int mulaMasGrande, int maxRondas) {
        this.mulaMasGrande = mulaMasGrande;
        this.maxRondas = maxRondas;
        mazo = inicializarMazoDomino(mulaMasGrande);
        jugadores = inicializarJugadores(numJugadores);
        boneyard = new ArrayList<>();
        indiceJugadorActual = 0;
        rondasJugadas = 0;
        Collections.shuffle(mazo);
        repartirFichasAJugadores();
    }

    public void jugar() {
        int ronda = 1;
        int[] rondasGanadas = new int[jugadores.size()];

        while (ronda <= maxRondas) {
            System.out.println("Ronda " + ronda);

            while (!esFinDeRonda()) {
                Jugador jugadorActual = jugadores.get(indiceJugadorActual);
                mostrarEstadoJuego(jugadorActual);
                jugarTurno(jugadorActual);
                indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.size();
            }

            Jugador ganadorRonda = determinarGanadorRonda();
            System.out.println("¡El ganador de la ronda es " + ganadorRonda.getNombre() + "!");
            rondasGanadas[jugadores.indexOf(ganadorRonda)]++;
            reiniciarRonda();
            ronda++;
            rondasJugadas++;
        }

        Jugador ganadorJuego = determinarGanadorJuego(rondasGanadas);
        if (ganadorJuego != null) {
            System.out.println("¡El ganador del juego es " + ganadorJuego.getNombre() + "!");
        } else {
            System.out.println("¡Empate entre los jugadores en el juego!");
        }
    }

    private boolean esFinDeRonda() {
        for (Jugador jugador : jugadores) {
            if (jugador.getMano().isEmpty()) {
                return true;
            }
        }

        if (mazo.isEmpty() && boneyard.isEmpty()) {
            return true; // No quedan fichas en el mazo ni en el boneyard
        }

        return false;
    }

    private Jugador determinarGanadorRonda() {
        Jugador ganadorRonda = jugadores.get(indiceJugadorActual);
        int minPuntos = ganadorRonda.calcularPuntosMano();

        for (Jugador jugador : jugadores) {
            int puntos = jugador.calcularPuntosMano();
            if (jugador.getMano().isEmpty() || puntos < minPuntos) {
                ganadorRonda = jugador;
                minPuntos = puntos;
            }
        }

        return ganadorRonda;
    }

    private void jugarTurno(Jugador jugadorActual) {
        Scanner scanner = new Scanner(System.in);
        boolean jugadaValida = false;
        boolean roboDelBoneyard = false;

        while (!jugadaValida) {
            System.out.println("Opciones:");
            System.out.println("1. Jugar una ficha");
            System.out.println("2. Robar del boneyard");
            System.out.println("3. Pasar el turno");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    // Aquí debes permitir al jugador seleccionar una ficha de su mano por índice y jugarla si es válida
                    System.out.print("Elige una ficha para jugar (1, 2, 3, ...): ");
                    int indiceFicha = scanner.nextInt();
                    scanner.nextLine();

                    if (indiceFicha >= 1 && indiceFicha <= jugadorActual.getMano().size()) {
                        FichaDomino fichaSeleccionada = jugadorActual.getMano().get(indiceFicha - 1);

                        if (puedeJugarFicha(fichaSeleccionada)) {
                            mazo.add(fichaSeleccionada);
                            jugadorActual.getMano().remove(fichaSeleccionada);
                            jugadaValida = true;
                        } else {
                            System.out.println("No puedes jugar esa ficha. Intenta de nuevo.");
                        }
                    } else {
                        System.out.println("Índice no válido. Debe ser un número entre 1 y " + jugadorActual.getMano().size());
                    }
                    break;

                case 2:
                    FichaDomino fichaRobada = robarFichaDelBoneyard();
                    if (fichaRobada != null) {
                        jugadorActual.agregarFichaAMano(fichaRobada);
                        roboDelBoneyard = true;
                        jugadaValida = true;
                    } else {
                        System.out.println("No quedan fichas en el mazo ni en el boneyard. Debes pasar el turno.");
                    }
                    break;

                case 3:
                    jugadaValida = true;
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }

        if (roboDelBoneyard) {
            System.out.println(jugadorActual.getNombre() + " ha robado una ficha del boneyard.");
        }
    }

    private void reiniciarRonda() {
        mazo.clear();
        mazo.addAll(boneyard);
        boneyard.clear();
        rondasJugadas++;

        if (mazo.isEmpty()) {
            System.out.println("El mazo también se ha quedado sin fichas. Reiniciando el mazo y boneyard.");
            mazo = inicializarMazoDomino(mulaMasGrande);
            Collections.shuffle(mazo);
        }

        for (Jugador jugador : jugadores) {
            jugador.getMano().clear();
        }

        repartirFichasAJugadores();
    }

    private Jugador determinarGanadorJuego(int[] rondasGanadas) {
        List<Jugador> ganadoresJuego = new ArrayList<>();
        int maxRondasGanadas = -1;

        for (int i = 0; i < jugadores.size(); i++) {
            if (rondasGanadas[i] > maxRondasGanadas) {
                maxRondasGanadas = rondasGanadas[i];
            }
        }

        for (int i = 0; i < jugadores.size(); i++) {
            if (rondasGanadas[i] == maxRondasGanadas) {
                ganadoresJuego.add(jugadores.get(i));
            }
        }

        if (ganadoresJuego.size() == 1) {
            return ganadoresJuego.get(0);
        } else {
            return null;
        }
    }

    private void mostrarEstadoJuego(Jugador jugadorActual) {
        System.out.println("Turno de " + jugadorActual.getNombre());
        System.out.print("Mesa: ");
        for (FichaDomino ficha : mazo) {
            System.out.print(ficha.getLado1() + "/" + ficha.getLado2() + ",");
        }
        System.out.println();

        System.out.print(jugadorActual.getNombre() + ", tus fichas en mano: ");
        List<FichaDomino> mano = jugadorActual.getMano();
        for (int i = 0; i < mano.size(); i++) {
            FichaDomino ficha = mano.get(i);
            System.out.print(ficha.getLado1() + "/" + ficha.getLado2());
            if (i < mano.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.println();
    }

    private List<FichaDomino> inicializarMazoDomino(int mulaMasGrande) {
        return FabricaDomino.crearConjuntoDomino(mulaMasGrande);
    }

    private List<Jugador> inicializarJugadores(int numJugadores) {
        List<Jugador> jugadores = new ArrayList<>();
        for (int i = 1; i <= numJugadores && i <= 10; i++) {
            jugadores.add(new Jugador("Jugador " + i));
        }
        return jugadores;
    }

    private FichaDomino robarFichaDelBoneyard() {
        if (!mazo.isEmpty()) {
            return mazo.remove(0);
        } else if (!boneyard.isEmpty()) {
            Collections.shuffle(boneyard);
            mazo.addAll(boneyard);
            boneyard.clear();
            return mazo.remove(0);
        } else {
            return null;
        }
    }

    private void repartirFichasAJugadores() {
        for (int i = 0; i < 3; i++) {
            for (Jugador jugador : jugadores) {
                FichaDomino ficha = robarFichaDelMazo();
                jugador.agregarFichaAMano(ficha);
            }
        }
    }

    private boolean puedeJugarFicha(FichaDomino ficha) {
        if (mazo.isEmpty()) {
            return true; // Puede jugar cualquier ficha si es el primer turno
        }

        FichaDomino ultimaFichaMesa = mazo.get(mazo.size() - 1);
        return ultimaFichaMesa.getLado2() == ficha.getLado1() || ultimaFichaMesa.getLado2() == ficha.getLado2();
    }

    private FichaDomino robarFichaDelMazo() {
        if (!mazo.isEmpty()) {
            return mazo.remove(0);
        } else {
            return null;
        }
    }
}
