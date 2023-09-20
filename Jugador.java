import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Jugador {
    private String nombre;
    private List<FichaDomino> mano;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public List<FichaDomino> getMano() {
        return mano;
    }

    public void agregarFichaAMano(FichaDomino ficha) {
        mano.add(ficha);
    }

    public int calcularPuntosMano() {
        int puntos = 0;
        for (FichaDomino ficha : mano) {
            puntos += ficha.getLado1() + ficha.getLado2();
        }
        return puntos;
    }
}
