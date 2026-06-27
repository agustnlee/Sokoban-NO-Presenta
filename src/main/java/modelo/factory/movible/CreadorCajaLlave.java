package modelo.factory.movible;

import modelo.entidades.movible.Caja;
import modelo.entidades.movible.EntidadMovible;
import modelo.entidades.movible.strategy.caja.EstrategiaLlave;

public class CreadorCajaLlave implements CreadorMovible {
    
    @Override
    public EntidadMovible crear() {
        Caja cajaBase = new Caja();
        cajaBase.setEstrategia(new EstrategiaLlave());
        return cajaBase;
    }
}
