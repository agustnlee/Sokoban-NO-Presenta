package modelo.factory.nomovible;

import modelo.entidades.nomovible.Pared;
import modelo.entidades.nomovible.EntidadNoMovible;

public class CreadorPared implements CreadorNoMovible{

    @Override
    public EntidadNoMovible crear() {
        return new Pared();
    }
}
