package modelo.metricas;

public class Puntaje {

    private static final int PUNTAJE_BASE = 1000;
    private static final int COSTO_MOVIMIENTO = 1;
    private static final int COSTO_UNDO = 10;

    private int puntaje;
    private int movimientos;
    private int undos;

    public Puntaje() {
        resetPuntaje();
    }

    public void registrarMovimiento() {
        movimientos++;
        recalcularPuntaje();
    }

    public void registrarUndo() {
        undos++;
        recalcularPuntaje();
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getMovimientos() {
        return movimientos;
    }

    public int getUndos() {
        return undos;
    }

    public void resetPuntaje() {
        this.movimientos = 0;
        this.undos = 0;
        recalcularPuntaje();
    }

    private void recalcularPuntaje() {
        int bruto = PUNTAJE_BASE - (movimientos * COSTO_MOVIMIENTO) - (undos * COSTO_UNDO);
        this.puntaje = Math.max(0, bruto);
    }
}