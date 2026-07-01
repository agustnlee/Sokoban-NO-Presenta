package modelo.historial;

import modelo.entidades.nomovible.CasillaCandado;

public class EstadoCandado {

    private final CasillaCandado casilla;
    private final boolean llaveInsertada;

    public EstadoCandado(CasillaCandado casilla, boolean llaveInsertada) {
        this.casilla = casilla;
        this.llaveInsertada = llaveInsertada;
    }

    public void restaurar() {
        casilla.restaurarLlaveInsertada(llaveInsertada);
    }
}