package modelo.entidades.nomovible;

import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.EntidadMovible;
import modelo.entidades.movible.strategy.entidadmovible.MovimientoNormal;
import modelo.entidades.nomovible.observer.SubscriptorMuro;
import modelo.entidades.nomovible.state.EstadoMuro;
import modelo.entidades.nomovible.state.MuroCerrado;

public class Muro implements EntidadNoMovible, SubscriptorMuro {

    private EstadoMuro estado;

    public Muro() {
        this.estado = new MuroCerrado();
    }

    public Muro(EstadoMuro estadoInicial) { 
        this.estado = estadoInicial;
    }

    @Override
    public boolean recibirEntidadMovible(EntidadMovible e) {
        return this.estado.esTransitable();
    }

    @Override
    public boolean efectoAlEntrar(EntidadMovible e) {
        e.setComportamientoMovible(new MovimientoNormal());
        return false;
    }

    @Override
    public TipoEntidad getTipoEntidad() {
        return this.estado.getTipoEntidad();
    }


    public void actualizarMuro(boolean estadoCandado) {
        this.estado.evaluarEstado(this, estadoCandado);
    }

    public void setEstado(EstadoMuro nuevoEstado) {
        this.estado = nuevoEstado;
    }


}
