package modelo.entidades.movible.strategy.caja;

import modelo.entidades.Tablero;
import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.Caja;

public class EstrategiaLlave implements ComportamientoCaja {
    
    @Override
    public void reaccionarLlegada(Caja caja, Tablero tablero) {
        tablero.getNoMovible(caja.getPosicion()).alterarCandado();
    }
 
    @Override
    public boolean cuentaParaVictoria() {
        return false;
    }
 
    @Override
    public boolean estaEnEstadoDerrota() {
        return false;
    }
    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.CAJA_LLAVE;
    }
    
}
