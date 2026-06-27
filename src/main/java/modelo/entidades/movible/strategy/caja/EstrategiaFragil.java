package modelo.entidades.movible.strategy.caja;

import modelo.entidades.Tablero;
import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.Caja;

public class EstrategiaFragil implements ComportamientoCaja {
 
    private static final int EMPUJES_POR_DEFECTO = 10;
 
    private int empujesRestantes;
 
    public EstrategiaFragil() {
        this.empujesRestantes = EMPUJES_POR_DEFECTO;
    }
    public int getEmpujesRestantes() {
        return empujesRestantes;
    }
 
    public void setEmpujesRestantes(int restantes) {
        this.empujesRestantes = restantes;
    }
 
    @Override
    public void reaccionarLlegada(Caja caja, Tablero tablero) {
        if (empujesRestantes > 0) {
            empujesRestantes--;
        }
 
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
        return empujesRestantes <= 0;
    }

    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.CAJA;
    }
    
}
