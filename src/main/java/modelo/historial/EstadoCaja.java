package modelo.historial;

import modelo.entidades.Coordenada;
import modelo.entidades.movible.Caja;

public class EstadoCaja extends EstadoEntidadMovible {

    private final boolean bloqueada;
    private final int empujesRestantes;

    public EstadoCaja(Caja caja, Coordenada posicion, boolean bloqueada, int empujesRestantes) {
        super(caja, posicion);
        this.bloqueada = bloqueada;
        this.empujesRestantes = empujesRestantes;
    }

    @Override
    public void restaurar() {
        super.restaurar();
        Caja caja = (Caja) entidad;
        caja.alterarBloqueada(bloqueada);
        caja.restaurarEmpujesRestantes(empujesRestantes);
    }
}