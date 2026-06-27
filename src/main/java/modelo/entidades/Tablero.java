package modelo.entidades;

import modelo.entidades.movible.EntidadMovible;
import modelo.entidades.movible.Jugador;
import modelo.entidades.nomovible.EntidadNoMovible;

public class Tablero {

    // Constantes que definen los límites estructurales inmutables del nivel
    private final int FILAS = 5;
    private final int COLUMNAS = 10;

    // Matriz que representa el terreno estático del mapa
    private EntidadNoMovible[][] capaNoMovible;
    private EntidadMovible[][]   capaMovible;

    private Jugador jugador;

    public Tablero() {
        this.capaNoMovible = new EntidadNoMovible[FILAS][COLUMNAS];
        this.capaMovible = new EntidadMovible[FILAS][COLUMNAS];
    }
    
    public Tablero(int filas, int columnas) {
        if (filas != FILAS || columnas != COLUMNAS) {
            throw new IllegalArgumentException("Las dimensiones deben ser " + FILAS + "x" + COLUMNAS);
        }
        this.capaNoMovible = new EntidadNoMovible[filas][columnas];
        this.capaMovible = new EntidadMovible[filas][columnas];
    }

    public EntidadNoMovible getNoMovible(Coordenada c) {
        return this.capaNoMovible[c.getFila()][c.getColumna()];
    }

    public EntidadMovible getMovible(Coordenada c) {
        return this.capaMovible[c.getFila()][c.getColumna()];
    }

    public boolean hayMovible(Coordenada c) {
        return this.capaMovible[c.getFila()][c.getColumna()] != null;
    }

    public boolean dentroDelTablero(Coordenada c) {
        return c.getFila() >= 0 && c.getFila() < FILAS && c.getColumna() >= 0 && c.getColumna() < COLUMNAS;
    }

    public void colocarMovible(EntidadMovible e, Coordenada c) {
        this.capaMovible[c.getFila()][c.getColumna()] = e;
    }

    public void colocarNoMovible(EntidadNoMovible e, Coordenada c) {
        this.capaNoMovible[c.getFila()][c.getColumna()] = e;
    }

    public void moverEntidad(EntidadMovible entidad, Coordenada destino) {
        Coordenada actual = entidad.getPosicion();
        capaMovible[actual.getFila()][actual.getColumna()] = null;
        capaMovible[destino.getFila()][destino.getColumna()] = entidad;
        entidad.setPosicion(destino);
    }

    public int getFilaMax() {
        return FILAS;
    }

    public int getColumnaMax() {
        return COLUMNAS;
    }

    public void setJugador(Jugador j) {
        this.jugador = j;
    }

    public Jugador getJugador() {
        return jugador;
    }

}
