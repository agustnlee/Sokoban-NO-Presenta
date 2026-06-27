package modelo.entidades.movible;

import modelo.entidades.Coordenada;
import modelo.entidades.Direccion;
import modelo.entidades.Tablero;
import modelo.entidades.movible.strategy.entidadmovible.ComportamientoEntidadMovible;
import modelo.entidades.movible.strategy.entidadmovible.MovimientoNormal;
import modelo.entidades.nomovible.EntidadNoMovible;


public abstract class EntidadMovible {

    private Coordenada posicion;
    private ComportamientoEntidadMovible comportamientoMovible;

    protected EntidadMovible() {
        this.comportamientoMovible = new MovimientoNormal();
    }

    private Coordenada calcularSiguientePosicion(Direccion dir, Tablero tablero) {
        return comportamientoMovible.calcularSiguientePosicion(this.posicion, dir, tablero);
    }

    // logica generica de movimiento
    public boolean mover(Direccion dir, Tablero tablero) {
        Coordenada destino = calcularSiguientePosicion(dir, tablero);

        if (!tablero.dentroDelTablero(destino)) {
            return false;
        }

        if (tablero.hayMovible(destino)) { // propagacion de empuje
            return tablero.getMovible(destino).recibirEmpuje(dir, tablero, this);
        }

        EntidadNoMovible casillaDestino = tablero.getNoMovible(destino);
        if (!casillaDestino.recibirEntidadMovible(this)) {
            return false; 
        }

        tablero.getNoMovible(this.posicion).saleEntidadMovible();
        tablero.moverEntidad(this, destino);
        boolean sigueMoviendo = casillaDestino.efectoAlEntrar(this); // efecto aplicado
        this.reaccionarLlegada(tablero); //TODO: CHQUEAR SI ES NECESARIO

        if (sigueMoviendo) {
            this.mover(dir, tablero);
        }

        return true;
    }

    /**
    Sin preguntar tipo concreto, empuja y si funciona reposiciona la entidad que lo empujo
     */
    public boolean recibirEmpuje(Direccion dir, Tablero tablero, EntidadMovible empujador) {
        Coordenada posicionVieja = this.getPosicion();
        boolean movido = this.mover(dir, tablero);
        if (movido) {
            tablero.moverEntidad(empujador, posicionVieja);
        }
        return movido;
    }


    public void reaccionarLlegada(Tablero tablero) { // defecto no hace nada
    }

    public boolean cuentaParaVictoria() {
        return false;
    }

    public boolean estaBloqueada() {
        return false;
    }

    public boolean estaEnEstadoDerrota() {
        return false;
    }

        public Coordenada getPosicion() {
        return posicion;
    }

    public void setPosicion(Coordenada c) {
        this.posicion = c;
    }

    public void setComportamientoMovible(ComportamientoEntidadMovible c) {
        this.comportamientoMovible = c;
    }

}