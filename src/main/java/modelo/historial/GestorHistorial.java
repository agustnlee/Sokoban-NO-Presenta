package modelo.historial;

public class GestorHistorial {

    private static final int CAPACIDAD = 15;
    private static final int BLOQUE = 5;

    private final Memento[] historial;
    private int cantidad;

    public GestorHistorial() {
        this.historial = new Memento[CAPACIDAD];
        this.cantidad = 0;
    }

    public void guardar(Memento m) {
        if (cantidad == CAPACIDAD) {
            desplazarHaciaAtras();
            historial[CAPACIDAD - 1] = m;
        } else {
            historial[cantidad] = m;
            cantidad++;
        }
    }

    public Memento deshacer() {
        if (cantidad == 0) {
            return null;
        }

        int salto = (cantidad < BLOQUE) ? cantidad : BLOQUE;
        int indiceObjetivo = cantidad - salto;

        Memento objetivo = historial[indiceObjetivo];

        for (int i = indiceObjetivo + 1; i < cantidad; i++) {
            historial[i] = null;
        }
        cantidad = indiceObjetivo;

        return objetivo;
    }

    private void desplazarHaciaAtras() {
        for (int i = 1; i < CAPACIDAD; i++) {
            historial[i - 1] = historial[i];
        }
    }
}