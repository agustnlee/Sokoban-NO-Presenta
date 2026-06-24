package modelo.entidades.nomovible;

import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.EntidadMovible;
import modelo.entidades.nomovible.observer.PublicadorMuro;

public class CasillaCandado implements EntidadNoMovible {
    
    private PublicadorMuro publicador;
    private boolean llaveInsertada;

    public CasillaCandado(PublicadorMuro publicador) {
        this.publicador = publicador;
        this.llaveInsertada = false;
    }

    public CasillaCandado() {
        this.publicador = null;
        this.llaveInsertada = false;
    }

    public void setPublicador(PublicadorMuro publicador) {
        this.publicador = publicador;
    }

    @Override
    public boolean recibirEntidadMovible(EntidadMovible e) {
        return true; // Siempre se puede empujar algo hacia el candado
    }

    @Override
    public void efectoAlEntrar(EntidadMovible e) {
        // Pseudo-código: if (e.esLlaveCorrecta()) {
        //this.llaveInsertada = true;
        //this.publicador.notificarSubscriptores(this.llaveInsertada); // Envía 'true'
        // }
    }

    @Override
    public void saleEntidadMovible() {
        // Si el jugador hace Undo, la llave sale de la casilla.
        // Hay que volver a cerrar los muros.
        if (this.llaveInsertada) {
            this.llaveInsertada = false;
            this.publicador.notificarSubscriptores(this.llaveInsertada); // Envía 'false'
        }
    }

    @Override
    public boolean alterarCandado() { 
        this.llaveInsertada = true;
        this.publicador.notificarSubscriptores(this.llaveInsertada); // Envía 'true'
        return this.llaveInsertada; // Retorna el nuevo estado del candado (true si se insertó la llave)
    }

    @Override
    public boolean alterarDestino() {
        // Descripcion: Sólo override CasillaCandado
        return false;
    }

    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.CANDADO;
    }
}