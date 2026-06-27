package modelo.entidades.nomovible;

import modelo.entidades.EntidadVisual;
import modelo.entidades.movible.EntidadMovible;

public interface EntidadNoMovible extends EntidadVisual {
    boolean recibirEntidadMovible(EntidadMovible e);
    void efectoAlEntrar(EntidadMovible e);
    default void saleEntidadMovible() { }
    
    default boolean alterarCandado() {
        return false;
    }
    default boolean alterarDestino() {
        return false;
    }
}