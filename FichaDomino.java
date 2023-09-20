import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class FichaDomino {
    private int lado1;
    private int lado2;

    public FichaDomino(int lado1, int lado2) {
        this.lado1 = lado1;
        this.lado2 = lado2;
    }

    public int getLado1() {
        return lado1;
    }

    public int getLado2() {
        return lado2;
    }

    @Override
    public String toString() {
        return "[" + lado1 + "|" + lado2 + "]";
    }
}