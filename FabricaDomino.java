import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class FabricaDomino {
    public static List<FichaDomino> crearConjuntoDomino(int mulaMasGrande) {
        List<FichaDomino> conjuntoDomino = new ArrayList<>();

        for (int i = 0; i <= mulaMasGrande; i++) {
            for (int j = i; j <= mulaMasGrande; j++) {
                conjuntoDomino.add(new FichaDomino(i, j));
            }
        }

        return conjuntoDomino;
    }
}
