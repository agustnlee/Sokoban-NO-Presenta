package modelo.entidades.nomovible;

import modelo.entidades.TipoEntidad;
import modelo.entidades.movible.EntidadMovible;
import modelo.entidades.movible.strategy.entidadmovible.MovimientoNormal;
import modelo.entidades.nomovible.observer.PublicadorMuro;
import modelo.entidades.nomovible.observer.SubscriptorMuro;

public class CasillaCandado implements EntidadNoMovible {
    
    private PublicadorMuro publicadorMuro;
    private boolean llaveInsertada;

    public CasillaCandado() {
        this.publicadorMuro = new PublicadorMuro();
        this.llaveInsertada = false;
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
    
    public void suscribir(SubscriptorMuro s) {
        this.publicadorMuro.suscribir(s); 
    }

    public void desuscribir(SubscriptorMuro s) {
        this.publicadorMuro.desuscribir(s);
    }

    @Override
    public TipoEntidad getTipoEntidad() {
        return TipoEntidad.CANDADO;
    }
}