package modelo.entidades.nomovible;

import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.EntidadMovible;
import modelo.entidades.movible.strategy.entidadmovible.MovimientoNormal;
import modelo.entidades.nomovible.observer.PublicadorMuro;

public class CasillaCandado implements EntidadNoMovible {
    
    private PublicadorMuro publicadorMuro;
    private boolean llaveInsertada;

    public CasillaCandado() {
        this.publicadorMuro = null;
        this.llaveInsertada = false;
    }

    public void setPublicadorMuro(PublicadorMuro publicador) {
        this.publicadorMuro = publicador;
    }

    @Override
    public boolean recibirEntidadMovible(EntidadMovible e) {
        return true; 
    }

    @Override
    public boolean efectoAlEntrar(EntidadMovible e) {
        e.setComportamientoMovible(new MovimientoNormal());
        return false;
    }

    @Override
    public void saleEntidadMovible() {
        if (this.llaveInsertada) {
            this.llaveInsertada = false;
            this.publicadorMuro.notificarSubscriptores(this.llaveInsertada); 
        }
    }

    @Override
    public boolean alterarCandado() { 
        this.llaveInsertada = true;
        this.publicadorMuro.notificarSubscriptores(this.llaveInsertada); 
        return this.llaveInsertada; 
    }
    public void restaurarLlaveInsertada(boolean estado) {
        this.llaveInsertada = estado;
        publicadorMuro.notificarSubscriptores(estado);
    }
 
    public boolean getLlaveInsertada() {
        return llaveInsertada;
    }

    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.CANDADO;
    }
}