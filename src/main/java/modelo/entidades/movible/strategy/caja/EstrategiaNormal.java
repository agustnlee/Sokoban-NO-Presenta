package modelo.entidades.movible.strategy.caja;

import modelo.entidades.Tablero;
import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.Caja;

public class EstrategiaNormal implements ComportamientoCaja {

    @Override
    public void reaccionarLlegada(Caja caja, Tablero tablero) {
        tablero.getNoMovible(caja.getPosicion()).alterarDestino(caja);
    }

    @Override
    public boolean cuentaParaVictoria() {
        return true;
    }

    @Override
    public boolean estaEnEstadoDerrota() {
        return false;
    }

    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.CAJA;
    }
}