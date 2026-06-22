package modelo.entidades.nomovible;

import modelo.entidades.EntidadVisual;
import modelo.entidades.movible.EntidadMovible;

public interface EntidadNoMovible extends EntidadVisual {
    boolean recibirEntidadMovible(EntidadMovible e);
    void efectoAlEntrar(EntidadMovible e);
    void saleEntidadMovible();
    boolean alterarCandado();
    boolean alterarDestino();
}