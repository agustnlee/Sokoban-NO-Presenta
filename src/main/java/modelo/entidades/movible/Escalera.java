package modelo.entidades.movible;

import modelo.entidades.Coordenada;
import modelo.entidades.Direccion;
import modelo.entidades.Tablero;
import modelo.entidades.nomovible.EntidadNoMovible;

 
/**
 * La escalera recalcula su funcionamiento durante el uso, sin necesitdad de patrones como strategy o satete
 *  1) Si la casilla en la dirección del empuje está dentro del tablero, no
 *     tiene otra entidad movible, y es transitable: la Escalera se mueve a esa
 *     posición.
 * 
 *  2) Si esa casilla tiene otra entidad movible, NO se intenta empujar en
 *     cadena.
 *     Si esa casilla no es transitable, o cae en el caso 2,
 *     se intenta trepar: el empujador busca, siguiendo en la misma dirección,
 *     la primera posición realmente libre más allá de la Escalera y teletransporta a la entidad empujante
 */
public class Escalera extends EntidadMovible {
 
    private Direccion direccionApoyada;
 
    public Escalera() {
        this.direccionApoyada = null;
    }
 
    public Direccion getDireccionApoyada() {
        return direccionApoyada;
    }
 
    private Coordenada calcularSiguientePosicion(Direccion dir) {
        return this.getPosicion().sumar(dir);
    }
 
    @Override
    public boolean recibirEmpuje(Direccion dir, Tablero tablero, EntidadMovible empujador) {
        this.direccionApoyada = dir;
 
        Coordenada destino = calcularSiguientePosicion(dir);
 
        boolean hayEspacioLibre = tablero.dentroDelTablero(destino)
                && !tablero.hayMovible(destino)
                && tablero.getNoMovible(destino).recibirEntidadMovible(this);
 
        if (hayEspacioLibre) {
            return moverNormal(destino, tablero, empujador);
        }
 
        return intentarTrepar(dir, tablero, empujador);
    }
 
    
    private boolean moverNormal(Coordenada destino, Tablero tablero, EntidadMovible empujador) {
        Coordenada posicionVieja = this.getPosicion();
        EntidadNoMovible casillaDestino = tablero.getNoMovible(destino);
 
        tablero.getNoMovible(posicionVieja).saleEntidadMovible();
        tablero.moverEntidad(this, destino);
        boolean sigueMoviendose = casillaDestino.efectoAlEntrar(this);
        this.reaccionarLlegada(tablero);
        if (sigueMoviendose) {
            this.mover(direccionApoyada, tablero);
        }
 
        tablero.moverEntidad(empujador, posicionVieja);
        return true;
    }
 


    private boolean intentarTrepar(Direccion dir, Tablero tablero, EntidadMovible empujador) {
        Coordenada candidata = this.getPosicion().sumar(dir);
 
        while (tablero.dentroDelTablero(candidata)) {
            boolean libre = !tablero.hayMovible(candidata)
                    && tablero.getNoMovible(candidata).recibirEntidadMovible(empujador);
 
            if (libre) {
                EntidadNoMovible casillaDestino = tablero.getNoMovible(candidata);
                tablero.getNoMovible(empujador.getPosicion()).saleEntidadMovible();
                tablero.moverEntidad(empujador, candidata);
                boolean sigueMoviendose = casillaDestino.efectoAlEntrar(empujador);
                empujador.reaccionarLlegada(tablero);
                if (sigueMoviendose) {
                    empujador.mover(dir, tablero);
                }
                return true;
            }
 
            candidata = candidata.sumar(dir);
        }
 
        return false; // no hay ninguna posicion libre hasta el borde del tablero
    }
}