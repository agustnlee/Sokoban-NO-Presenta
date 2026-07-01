package modelo.factory.movible;

import modelo.entidades.movible.EntidadMovible;
import modelo.entidades.movible.Escalera;


public class CreadorEscalera implements CreadorMovible {

    @Override
    public EntidadMovible crear() {
        return new Escalera();
    }
    
}
