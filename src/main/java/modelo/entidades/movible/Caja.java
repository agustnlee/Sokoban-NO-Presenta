package modelo.entidades.movible;

import modelo.entidades.Direccion;
import modelo.entidades.Tablero;

public class Caja extends CajaAbstracta {

    private boolean bloqueada;

    public Caja() {
        this.bloqueada = false;
    }

    @Override
    public boolean mover(Direccion dir, Tablero tablero) {
        if (bloqueada) {
            return false;
        }
        return super.mover(dir, tablero);
    }

    @Override
    public void reaccionarLlegada(Tablero tablero) {
        estrategia.reaccionarLlegada(this, tablero);
    }

    @Override
    public boolean estaBloqueada() {
        return bloqueada;
    }

    public void alterarBloqueada(boolean estado) {
        this.bloqueada = estado;
    }
}