package modelo.entidades;

import modelo.entidades.movible.EntidadMovible;
import modelo.entidades.movible.Jugador;
import modelo.entidades.nomovible.CasillaCandado;
import modelo.entidades.nomovible.EntidadNoMovible;

public class Tablero {

    private final int filaMax;
    private final int columnaMax;
    private EntidadNoMovible[][] capaNoMovible;
    private EntidadMovible[][]   capaMovible;
    private Jugador jugador;
    private CasillaCandado candado;

    public Tablero() {
        this(DimensionTablero.ESTANDAR.getFilas(), DimensionTablero.ESTANDAR.getColumnas());
    }

    public Tablero(int filas, int columnas) {
        this.filaMax = filas;
        this.columnaMax = columnas;
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
        return c.getFila() >= 0 && c.getFila() < filaMax && c.getColumna() >= 0 && c.getColumna() < columnaMax;
    }

    public void colocarMovible(EntidadMovible e, Coordenada c) {
        this.capaMovible[c.getFila()][c.getColumna()] = e;
        e.setPosicion(c);
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
    public void limpiarMovible(Coordenada c) {
        capaMovible[c.getFila()][c.getColumna()] = null;
    }

    public void restaurarPosicionMovible(EntidadMovible entidad, Coordenada posicionAnterior) {
        capaMovible[posicionAnterior.getFila()][posicionAnterior.getColumna()] = entidad;
        entidad.setPosicion(posicionAnterior);
    }

    public int getFilaMax() {
        return filaMax;
    }

    public int getColumnaMax() {
        return columnaMax;
    }

    public void setJugador(Jugador j) {
        this.jugador = j;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setCandado(CasillaCandado c) { 
        this.candado = c; 
    }
    public CasillaCandado getCandado() {
        return candado; 
    }

}
