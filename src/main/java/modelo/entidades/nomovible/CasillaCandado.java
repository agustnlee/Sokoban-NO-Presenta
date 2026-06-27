package modelo.entidades.nomovible;

import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.EntidadMovible;
import modelo.entidades.movible.strategy.entidadmovible.MovimientoNormal;
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
        return true; 
    }

    @Override
    public boolean efectoAlEntrar(EntidadMovible e) {
        // Pseudo-código: if (e.esLlaveCorrecta()) {
        //this.llaveInsertada = true;
        //this.publicador.notificarSubscriptores(this.llaveInsertada);
        // }
        e.setComportamientoMovible(new MovimientoNormal());
        return false;
    }

    @Override
    public void saleEntidadMovible() {
        if (this.llaveInsertada) {
            this.llaveInsertada = false;
            this.publicador.notificarSubscriptores(this.llaveInsertada); 
        }
    }

    @Override
    public boolean alterarCandado() { 
        this.llaveInsertada = true;
        this.publicador.notificarSubscriptores(this.llaveInsertada); 
        return this.llaveInsertada; 
    }

    @Override
    public boolean alterarDestino() {
        return false;
    }

    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.CANDADO;
    }
}