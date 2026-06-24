package modelo.entidades.nomovible;

import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.EntidadMovible;
import modelo.entidades.nomovible.observer.SubscriptorMuro;
import modelo.entidades.nomovible.state.EstadoMuro;
import modelo.entidades.nomovible.state.MuroCerrado;

public class Muro implements EntidadNoMovible, SubscriptorMuro {

    // Patrón State: El muro no sabe si es transitable, se lo pregunta a su estado.
    private EstadoMuro estado;

    public Muro() {
        // Por defecto, el muro comienza cerrado.
        this.estado = new MuroCerrado();
    }

    public Muro(EstadoMuro estadoInicial) { 
        this.estado = estadoInicial;
    }

    @Override
    public boolean recibirEntidadMovible(EntidadMovible e) {
        // Delega la responsabilidad: si está abierto retorna true, si está cerrado false.
        return this.estado.esTransitable();
    }

    @Override
    public void efectoAlEntrar(EntidadMovible e) {
        // Como el muro abierto es funcionalmente idéntico a una casilla vacía,
        // no tiene un efecto special sobre la entidad al entrar.
    }

    @Override
    public void saleEntidadMovible() {
        // Sin comportamiento especial al salir
    }

    @Override
    public boolean alterarCandado() {
        // El muro es el que reacciona al candado, no el que lo altera.
        return false;
    }

    @Override
    public boolean alterarDestino() {
        // El muro no tiene un destino que alterar, sólo reacciona al candado.
        return false;
    }
    @Override
    public TipoEntidad getTipoEntidad() {
        return this.estado.getTipoEntidad();
    }

    // --- MÉTODOS DEL PATRÓN OBSERVER --- //

    public void actualizarMuro(boolean estadoCandado) {
        // Cuando el Publicador avisa que la llave entró (o salió con un Undo),
        // el muro le pide a su estado actual que se evalúe y cambie si es necesario.
        this.estado.evaluarEstado(this, estadoCandado);
    }

    // --- GETTERS Y SETTERS (Para el Patrón State) --- //

    // Este método lo usarán MuroAbierto y MuroCerrado para cambiarse a sí mismos
    public void setEstado(EstadoMuro nuevoEstado) {
        this.estado = nuevoEstado;
    }


}
