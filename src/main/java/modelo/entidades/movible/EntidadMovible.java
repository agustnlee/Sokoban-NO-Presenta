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

    protected EntidadMovible(Coordenada posicion) {
        this.posicion = posicion;
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

        if (tablero.hayMovible(destino)) {
            tablero.getMovible(destino).recibirEmpuje(dir, tablero, this);
            return false; // quien empuja nunca avanza directamente, solo si la cadena lo permite
        }

        EntidadNoMovible casillaDestino = tablero.getNoMovible(destino);
        if (!casillaDestino.recibirEntidadMovible(this)) {
            return false; 
        }

        tablero.getNoMovible(this.posicion).saleEntidadMovible();
        tablero.moverEntidad(this, destino);
        casillaDestino.efectoAlEntrar(this); // efecto aplicado
        return true;
    }

    /**
    Sin preguntar tipo concreto, empuja y si funciona reposiciona la entidad que lo empujo
     */
    public void recibirEmpuje(Direccion dir, Tablero tablero, EntidadMovible empujador) {
        Coordenada posicionVieja = this.getPosicion();
        if (this.mover(dir, tablero)) {
            tablero.moverEntidad(empujador, posicionVieja);
        }
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