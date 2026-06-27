package modelo.factory.movible;

import modelo.entidades.movible.Caja;
import modelo.entidades.movible.EntidadMovible;
import modelo.entidades.movible.strategy.caja.EstrategiaNormal;


public class CreadorCajaNormal implements CreadorMovible {

    @Override
    public EntidadMovible crear() {
        Caja cajaBase = new Caja();
        cajaBase.setEstrategia(new EstrategiaNormal());
        return cajaBase;
    }
    
}
