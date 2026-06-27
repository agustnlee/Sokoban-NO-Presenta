package modelo.entidades.movible.strategy.caja;

import modelo.entidades.Tablero;
import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.Caja;

public class EstrategiaNormal implements ComportamientoCaja {

    @Override
    public void reaccionarLlegada(Caja caja, Tablero tablero) {
        boolean llegoADestino = tablero.getNoMovible(caja.getPosicion()).alterarDestino();
        if (llegoADestino) {
            caja.alterarBloqueada(true);
        }
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