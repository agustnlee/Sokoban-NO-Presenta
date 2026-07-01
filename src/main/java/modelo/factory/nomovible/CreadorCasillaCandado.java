package modelo.factory.nomovible;

import modelo.entidades.nomovible.CasillaCandado;
import modelo.entidades.nomovible.EntidadNoMovible;

public class CreadorCasillaCandado implements CreadorNoMovible {

    @Override
    public EntidadNoMovible crear() {
        return new CasillaCandado();
    }
    
}
