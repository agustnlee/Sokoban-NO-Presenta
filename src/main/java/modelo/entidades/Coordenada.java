package modelo.entidades;

public class Coordenada {
    private final int fila;
    private final int columna;

    public Coordenada(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;   
    }

    public boolean mismaCoordenada(Coordenada otraCoordenada) {
        return this.fila == otraCoordenada.fila && this.columna == otraCoordenada.columna;
    }

    public Coordenada sumar(Direccion dir) {
        return new Coordenada(fila + dir.getDireccionFila(), columna + dir.getDireccionColumna());
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
}